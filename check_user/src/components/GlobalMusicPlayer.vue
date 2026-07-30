<script setup lang="ts">
import { computed, ref, watch, onMounted, onUnmounted } from 'vue';
import { useMusicStore } from '@/store/music';
import {
   VideoPlay,
   VideoPause,
   DArrowLeft,
   DArrowRight
} from '@element-plus/icons-vue';
import { storeToRefs } from 'pinia';

const musicStore = useMusicStore();
const {
   currentSong,
   isPlaying,
   currentTime,
   duration,
   playlist,
   currentIndex,
   hasSong
} = storeToRefs(musicStore);

const audioRef = ref<HTMLAudioElement | null>(null);
const playUrl = ref('');

// 收起状态
const isCollapsed = ref(false);
let collapseTimer: ReturnType<typeof setTimeout> | null = null;

// 启动收起定时器
const startCollapseTimer = () => {
   if (collapseTimer) {
      clearTimeout(collapseTimer);
   }
   // 先展开显示，3秒后收起
   isCollapsed.value = false;
   console.log('启动收起定时器，3秒后收起');
   collapseTimer = setTimeout(() => {
      console.log('自动收起');
      isCollapsed.value = true;
   }, 3000);
};

// 停止收起定时器并展开
const stopCollapseTimer = () => {
   if (collapseTimer) {
      clearTimeout(collapseTimer);
      collapseTimer = null;
   }
   isCollapsed.value = false;
};

// 点击收起状态时展开
const toggleCollapse = () => {
   if (isCollapsed.value) {
      stopCollapseTimer();
      startCollapseTimer();
   }
};

// 格式化时间
const formatTime = (seconds: number): string => {
   const mins = Math.floor(seconds / 60);
   const secs = Math.floor(seconds % 60);
   return `${mins}:${secs.toString().padStart(2, '0')}`;
};

// 进度百分比
const progressPercent = computed(() => {
   if (duration.value > 0) {
      return (currentTime.value / duration.value) * 100;
   }
   return 0;
});

// 播放/暂停
const togglePlay = async () => {
   stopCollapseTimer();
   startCollapseTimer();
   if (!audioRef.value) return;

   if (isPlaying.value) {
      audioRef.value.pause();
      musicStore.pause();
   } else {
      try {
         await audioRef.value.play();
         musicStore.resume();
      } catch (error) {
         console.error('播放失败:', error);
      }
   }
};

// 下一首
const playNext = async () => {
   stopCollapseTimer();
   startCollapseTimer();
   if (playlist.value.length === 0) return;
   musicStore.playNext();
   await loadAndPlayCurrentSong();
};

// 上一首
const playPrev = async () => {
   stopCollapseTimer();
   startCollapseTimer();
   if (playlist.value.length === 0) return;
   musicStore.playPrev();
   await loadAndPlayCurrentSong();
};

// 加载并播放当前歌曲
const loadAndPlayCurrentSong = async () => {
   if (!currentSong.value) return;

   try {
      // 使用 NetEase API 获取播放链接
      const response = await fetch(
         `https://www.ffapi.cn/int/v1/netease_url?id=${currentSong.value.id}&quality=standard`
      );
      const data = await response.json();

      if (data.code === 200 && data.url) {
         playUrl.value = data.url.trim().replace(/^`|`$/g, '');

         await new Promise<void>(resolve => {
            if (!audioRef.value) return;
            audioRef.value.addEventListener(
               'loadedmetadata',
               () => {
                  resolve();
               },
               { once: true }
            );
            audioRef.value.load();
         });

         if (isPlaying.value) {
            await audioRef.value?.play();
         }
      }
   } catch (error) {
      console.error('加载歌曲失败:', error);
   }
};

// 进度条点击
const seekTo = (e: MouseEvent) => {
   if (!audioRef.value || !currentSong.value) return;
   const target = e.currentTarget as HTMLElement;
   const rect = target.getBoundingClientRect();
   const percent = (e.clientX - rect.left) / rect.width;
   const newTime = percent * duration.value;
   audioRef.value.currentTime = newTime;
   musicStore.setCurrentTime(newTime);
};

// 时间更新
const onTimeUpdate = () => {
   if (audioRef.value) {
      musicStore.setCurrentTime(audioRef.value.currentTime);
   }
};

// 加载完成
const onLoadedMetadata = () => {
   if (audioRef.value) {
      musicStore.setDuration(audioRef.value.duration);
   }
};

// 播放结束
const onEnded = () => {
   playNext();
};

// 监听当前歌曲变化
watch(currentSong, newSong => {
   if (newSong) {
      loadAndPlayCurrentSong();
      // 新歌曲加载后启动收起定时器
      startCollapseTimer();
   }
});

// 监听播放状态变化
watch(isPlaying, playing => {
   if (!audioRef.value) return;
   if (playing) {
      audioRef.value.play().catch(() => {});
   } else {
      audioRef.value.pause();
   }
});

onMounted(() => {
   // 初始化时如果有歌曲，加载播放
   if (currentSong.value) {
      loadAndPlayCurrentSong();
   }
   // 启动自动收起定时器
   startCollapseTimer();
});

onUnmounted(() => {
   audioRef.value?.pause();
   if (collapseTimer) {
      clearTimeout(collapseTimer);
   }
});
</script>

<template>
   <div
      v-if="hasSong"
      class="global-music-player"
      :class="{ collapsed: isCollapsed }"
      @click="toggleCollapse"
      @mouseenter="stopCollapseTimer"
      @mouseleave="startCollapseTimer">
      <div class="player-content">
         <!-- 上半部分：封面 + 歌曲信息 -->
         <div class="player-top">
            <!-- 封面 -->
            <div class="album-cover">
               <img
                  :src="
                     currentSong?.album.picUrl ||
                     `https://p3.music.126.net/${currentSong?.album.picId}/109951163069550895.jpg`
                  "
                  :alt="currentSong?.name" />
               <div v-if="isPlaying" class="playing-indicator">
                  <span></span>
                  <span></span>
                  <span></span>
               </div>
            </div>

            <!-- 歌曲信息 -->
            <div class="song-detail">
               <div class="song-name">{{ currentSong?.name }}</div>
               <div class="song-artist">
                  {{ currentSong?.artists.map(a => a.name).join(' / ') }}
               </div>
            </div>
         </div>

         <!-- 下半部分：进度条 + 控制按钮（上下布局） -->
         <div class="player-bottom">
            <!-- 进度条 -->
            <div class="progress-bar">
               <span class="time-text">{{ formatTime(currentTime) }}</span>
               <div class="progress-track" @click.stop="seekTo">
                  <div
                     class="progress-fill"
                     :style="{ width: progressPercent + '%' }"></div>
               </div>
               <span class="time-text">{{ formatTime(duration) }}</span>
            </div>

            <!-- 控制按钮 -->
            <div class="controls">
               <button class="control-btn" @click="playPrev">
                  <DArrowLeft />
               </button>
               <button class="control-btn play-btn" @click="togglePlay">
                  <VideoPause v-if="isPlaying" />
                  <VideoPlay v-else />
               </button>
               <button class="control-btn" @click="playNext">
                  <DArrowRight />
               </button>
            </div>
         </div>
      </div>

      <audio
         ref="audioRef"
         :src="playUrl"
         @timeupdate="onTimeUpdate"
         @loadedmetadata="onLoadedMetadata"
         @ended="onEnded" />
   </div>
</template>

<style lang="scss" scoped>
.global-music-player {
   position: fixed;
   bottom: 20px;
   right: 20px;
   width: 300px;
   min-height: 80px;
   background: var(--back-op-color);
   backdrop-filter: blur(20px);
   border: 1px solid var(--cart-border-color);
   transition:
      width 0.3s ease,
      height 0.3s ease,
      padding 0.3s ease,
      border-radius 0.3s ease;
   border-radius: 12px;
   padding: 14px;
   z-index: 1000;
   box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);

   .player-content {
      display: flex;
      flex-direction: column;
      gap: 12px;
   }

   .player-top {
      display: flex;
      align-items: center;
      gap: 12px;
   }

   .album-cover {
      width: 52px;
      height: 52px;
      border-radius: 8px;
      overflow: hidden;
      border: 1px solid var(--cart-border-color);
      position: relative;
      flex-shrink: 0;

      img {
         width: 100%;
         height: 100%;
         object-fit: cover;
      }

      .playing-indicator {
         position: absolute;
         bottom: 4px;
         right: 4px;
         display: flex;
         align-items: flex-end;
         gap: 2px;
         padding: 2px 4px;
         background: rgba(0, 0, 0, 0.6);
         border-radius: 4px;

         span {
            display: block;
            width: 3px;
            background: var(--cart-border-color);
            border-radius: 2px;
            animation: playing 1s infinite ease-in-out;

            &:nth-child(1) {
               height: 8px;
               animation-delay: 0s;
            }
            &:nth-child(2) {
               height: 12px;
               animation-delay: 0.2s;
            }
            &:nth-child(3) {
               height: 6px;
               animation-delay: 0.4s;
            }
         }
      }
   }

   .song-detail {
      flex: 1;
      display: flex;
      flex-direction: column;
      min-width: 0;

      .song-name {
         font-size: 14px;
         font-weight: 500;
         color: var(--bk-font-color);
         white-space: nowrap;
         overflow: hidden;
         text-overflow: ellipsis;
      }

      .song-artist {
         font-size: 12px;
         color: var(--bk-font-color);
         opacity: 0.6;
         white-space: nowrap;
         overflow: hidden;
         text-overflow: ellipsis;
      }
   }

   .player-bottom {
      display: flex;
      flex-direction: column;
      gap: 10px;
   }

   .progress-bar {
      display: flex;
      align-items: center;
      gap: 8px;

      .time-text {
         font-size: 11px;
         color: var(--bk-font-color);
         opacity: 0.5;
         min-width: 40px;
         text-align: center;
      }

      .progress-track {
         flex: 1;
         height: 3px;
         background: var(--bk-font-color);
         opacity: 0.2;
         border-radius: 2px;
         cursor: pointer;
         position: relative;

         .progress-fill {
            height: 100%;
            background: var(--cart-border-color);
            border-radius: 2px;
            transition: width 0.1s linear;
         }

         &:hover .progress-fill {
            opacity: 0.8;
         }
      }
   }

   .controls {
      display: flex;
      justify-content: center;
      align-items: center;
      gap: 20px;

      .control-btn {
         width: 34px;
         height: 34px;
         border: none;
         background: transparent;
         border-radius: 6px;
         display: flex;
         align-items: center;
         justify-content: center;
         cursor: pointer;
         transition: all 0.3s;
         color: var(--bk-font-color);
         opacity: 0.6;

         &:hover {
            opacity: 1;
            background: rgba(255, 255, 255, 0.1);
         }

         &.play-btn {
            width: 42px;
            height: 42px;
            background: var(--cart-border-color);
            opacity: 1;
            color: var(--cart-muisc-color);
            border-radius: 10px;

            &:hover {
               opacity: 0.9;
               transform: scale(1.05);
            }
         }
      }
   }
}

@keyframes playing {
   0%,
   100% {
      transform: scaleY(0.5);
   }
   50% {
      transform: scaleY(1);
   }
}

/* 收起状态 - 圆角样式 */
.collapsed {
   width: 40px !important;
   height: 40px !important;
   padding: 4px !important;
   border-radius: 12px !important;

   .song-detail,
   .player-bottom {
      opacity: 0;
      height: 0;
      overflow: hidden;
      padding: 0;
      margin: 0;
      display: none;
   }

   .player-top {
      justify-content: center;
      gap: 0;
      width: 100%;
      height: 100%;
   }

   .album-cover {
      width: 32px;
      height: 32px;
      border-radius: 50%;
   }

   .player-content {
      gap: 0;
      width: 100%;
      height: 100%;
   }
}

@media (max-width: 360px) {
   .global-music-player {
      width: calc(100% - 40px);
      bottom: 10px;
      right: 10px;

      &.collapsed {
         width: 60px !important;
      }
   }
}
</style>
