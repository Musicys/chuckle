<template>
   <div class="music-player">
      <div class="player-header">
         <div class="search-box">
            <el-icon class="search-icon"><Search /></el-icon>
            <input
               v-model="searchText"
               placeholder="搜索歌曲..."
               @keyup.enter="handleSearch" />
            <button class="search-btn" @click="handleSearch">
               <el-icon><Search /></el-icon>
            </button>
         </div>
      </div>

      <div class="player-body">
         <div class="playlist-panel">
            <div class="panel-title">
               <el-icon><VideoPlay /></el-icon>
               <span>搜索结果</span>
               <span class="song-count">{{ songList.length }} 首</span>
            </div>
            <div class="song-list">
               <div
                  v-for="(song, index) in songList"
                  :key="song.id"
                  :class="{ active: currentSong?.id === song.id }"
                  class="song-item"
                  @click="playSong(song)">
                  <div class="song-index">
                     <el-icon v-if="currentSong?.id === song.id && isPlaying"
                        ><VideoPause
                     /></el-icon>
                     <span v-else>{{ index + 1 }}</span>
                  </div>
                  <div class="song-info">
                     <div class="song-name">{{ song.name }}</div>
                     <div class="song-artist">
                        {{ song.artists.map(a => a.name).join(' / ') }}
                     </div>
                  </div>
                  <div class="song-album">{{ song.album.name }}</div>
                  <div class="song-duration">
                     {{ formatDuration(song.duration) }}
                  </div>
               </div>
               <div
                  v-if="!isSearching && songList.length === 0"
                  class="empty-state">
                  <el-icon class="empty-icon"><VideoPlay /></el-icon>
                  <p>搜索你喜欢的歌曲</p>
               </div>
               <div v-if="isSearching" class="loading-state">
                  <el-icon class="loading-icon"><Loading /></el-icon>
                  <p>搜索中...</p>
               </div>
            </div>
         </div>

         <div class="player-panel">
            <div v-if="currentSong" class="current-player">
               <div
                  class="cover-background"
                  :style="{
                     backgroundImage:
                        'url(' +
                        (currentSong.album.picUrl ||
                           `https://p3.music.126.net/${currentSong.album.picId}/109951163069550895.jpg`) +
                        ')'
                  }"></div>
               <div class="album-cover">
                  <div class="cover-image" :class="{ spinning: isPlaying }">
                     <img
                        :src="
                           currentSong.album.picUrl ||
                           `https://p3.music.126.net/${currentSong.album.picId}/109951163069550895.jpg`
                        "
                        :alt="currentSong.name" />
                  </div>
                  <div class="cover-overlay">
                     <button class="play-btn" @click="togglePlay">
                        <el-icon v-if="isPlaying"><VideoPause /></el-icon>
                        <el-icon v-else><VideoPlay /></el-icon>
                     </button>
                  </div>
               </div>

               <div class="song-detail">
                  <div class="detail-title">{{ currentSong.name }}</div>
                  <div class="detail-artist">
                     {{ currentSong.artists.map(a => a.name).join(' / ') }}
                  </div>
                  <div class="detail-album">{{ currentSong.album.name }}</div>
               </div>

               <div class="lyrics-panel" ref="lyricsContainer">
                  <div v-if="isLoadingLyrics" class="loading-lyrics">
                     <el-icon class="loading-icon"><Loading /></el-icon>
                     <span>加载歌词中...</span>
                  </div>
                  <div v-else-if="lyrics.length === 0" class="no-lyrics">
                     <el-icon><Document /></el-icon>
                     <span>暂无歌词</span>
                  </div>
                  <div
                     v-for="(line, index) in lyrics"
                     :key="index"
                     :class="{ active: currentLyricIndex === index }"
                     class="lyric-line">
                     {{ line.text }}
                  </div>
               </div>

               <div class="progress-bar">
                  <span class="time-current">{{
                     formatTime(currentTime)
                  }}</span>
                  <div class="progress-track" @click="seekTo">
                     <div
                        class="progress-fill"
                        :style="{ width: progressPercent + '%' }"></div>
                     <div
                        class="progress-thumb"
                        :style="{ left: progressPercent + '%' }"></div>
                  </div>
                  <span class="time-total">{{ formatTime(duration) }}</span>
               </div>

               <div class="player-controls">
                  <button class="control-btn" @click="playPrev">
                     <el-icon><DArrowLeft /></el-icon>
                  </button>
                  <button class="control-btn play-main" @click="togglePlay">
                     <el-icon v-if="isPlaying"><VideoPause /></el-icon>
                     <el-icon v-else><VideoPlay /></el-icon>
                  </button>
                  <button class="control-btn" @click="playNext">
                     <el-icon><DArrowRight /></el-icon>
                  </button>
                  <div class="volume-control">
                     <el-icon><Mic /></el-icon>
                     <input
                        type="range"
                        min="0"
                        max="100"
                        v-model="volume"
                        class="volume-slider" />
                  </div>
                  <button class="control-btn" @click="toggleFullscreen">
                     <span v-if="!isFullscreen">全屏</span>
                     <span v-else>退出</span>
                  </button>
               </div>
            </div>

            <div v-else class="welcome-state">
               <div class="welcome-icon">
                  <el-icon><VideoPlay /></el-icon>
               </div>
               <h3>音乐播放器</h3>
               <p>搜索并播放你喜欢的歌曲</p>
            </div>
         </div>
      </div>

      <audio
         ref="audioRef"
         :src="playUrl"
         @timeupdate="onTimeUpdate"
         @loadedmetadata="onLoadedMetadata"
         @ended="onEnded"></audio>
   </div>
</template>

<script setup lang="ts">
import { ref, watch, nextTick, onMounted, onUnmounted } from 'vue';
import {
   Search,
   VideoPlay,
   VideoPause,
   DArrowLeft,
   DArrowRight,
   Mic,
   Loading,
   Document
} from '@element-plus/icons-vue';
import {
   searchMusic,
   getNeteaseUrl,
   getLyric,
   getSongDetail,
   parseLyric,
   type Song,
   type LyricLine
} from '@/api/muisc';

const searchText = ref('');
const songList = ref<Song[]>([]);
const currentSong = ref<Song | null>(null);
const currentTime = ref(0);
const duration = ref(0);
const volume = ref(70);
const isPlaying = ref(false);
const isFullscreen = ref(false);
const isSearching = ref(false);
const playUrl = ref('');
const lyrics = ref<LyricLine[]>([]);
const currentLyricIndex = ref(-1);
const isLoadingLyrics = ref(false);
const lyricsContainer = ref<HTMLElement | null>(null);
const audioRef = ref<HTMLAudioElement | null>(null);

const formatDuration = (ms: number): string => {
   const seconds = Math.floor(ms / 1000);
   return formatTime(seconds);
};

const formatTime = (seconds: number): string => {
   const mins = Math.floor(seconds / 60);
   const secs = Math.floor(seconds % 60);
   return `${mins}:${secs.toString().padStart(2, '0')}`;
};

const progressPercent = ref(0);

const updateProgress = () => {
   if (duration.value > 0) {
      progressPercent.value = (currentTime.value / duration.value) * 100;
   }
};

const handleSearch = async () => {
   const keyword = searchText.value.trim() || '热门';

   isSearching.value = true;
   try {
      const response = await searchMusic({
         s: keyword,
         type: 1,
         limit: 20
      });

      if (response.code === 200 && response.result) {
         songList.value = response.result.songs;
      }
   } catch (error) {
      console.error('搜索失败:', error);
   } finally {
      isSearching.value = false;
   }
};

const playSong = async (song: Song) => {
   currentSong.value = song;
   lyrics.value = [];
   currentLyricIndex.value = -1;

   try {
      const urlResponse = await getNeteaseUrl({
         id: String(song.id),
         quality: 'standard'
      });

      if (urlResponse.code === 200 && urlResponse.url) {
         playUrl.value = urlResponse.url.trim().replace(/^`|`$/g, '');

         try {
            const detailResponse = await getSongDetail({
               ids: `[${song.id}]`
            });
            if (
               detailResponse.code === 200 &&
               detailResponse.songs.length > 0
            ) {
               currentSong.value = detailResponse.songs[0];
            }
         } catch (detailError) {
            console.error('获取歌曲详情失败:', detailError);
         }

         await nextTick();

         const playPromise = new Promise<void>((resolve, reject) => {
            const audio = audioRef.value;
            if (!audio) {
               reject(new Error('音频元素不存在'));
               return;
            }

            const onCanPlay = () => {
               audio.removeEventListener('canplay', onCanPlay);
               audio.removeEventListener('error', onError);
               resolve();
            };

            const onError = (e: Event) => {
               audio.removeEventListener('canplay', onCanPlay);
               audio.removeEventListener('error', onError);
               reject(new Error('音频加载失败'));
            };

            audio.addEventListener('canplay', onCanPlay);
            audio.addEventListener('error', onError);
            audio.load();
         });

         try {
            await playPromise;
            await audioRef.value?.play();
            isPlaying.value = true;
         } catch (playError) {
            console.error('音频播放失败:', playError);
            isPlaying.value = false;
         }

         isLoadingLyrics.value = true;

         try {
            const lyricResponse = await getLyric({
               id: song.id,
               os: 'pc'
            });

            if (lyricResponse.code === 200 && lyricResponse.lrc?.lyric) {
               lyrics.value = parseLyric(lyricResponse.lrc.lyric);
            } else {
               lyrics.value = [];
            }
         } catch (lyricError) {
            console.error('获取歌词失败:', lyricError);
            lyrics.value = [];
         } finally {
            isLoadingLyrics.value = false;
         }
      }
   } catch (error) {
      console.error('播放失败:', error);
   }
};

const togglePlay = async () => {
   if (!audioRef.value) return;
   if (isPlaying.value) {
      audioRef.value.pause();
   } else {
      try {
         await audioRef.value.play();
      } catch (error) {
         console.error('播放失败:', error);
      }
   }
   isPlaying.value = !isPlaying.value;
};

const playPrev = () => {
   if (!currentSong.value || songList.value.length === 0) return;
   const currentIndex = songList.value.findIndex(
      s => s.id === currentSong.value!.id
   );
   const prevIndex =
      currentIndex > 0 ? currentIndex - 1 : songList.value.length - 1;
   playSong(songList.value[prevIndex]);
};

const playNext = () => {
   if (!currentSong.value || songList.value.length === 0) return;
   const currentIndex = songList.value.findIndex(
      s => s.id === currentSong.value!.id
   );
   const nextIndex =
      currentIndex < songList.value.length - 1 ? currentIndex + 1 : 0;
   playSong(songList.value[nextIndex]);
};

const toggleFullscreen = async () => {
   const playerPanel = document.querySelector('.player-panel') as HTMLElement;
   if (!document.fullscreenElement) {
      try {
         await playerPanel.requestFullscreen();
         isFullscreen.value = true;
      } catch (error) {
         console.error('全屏失败:', error);
      }
   } else {
      try {
         await document.exitFullscreen();
         isFullscreen.value = false;
      } catch (error) {
         console.error('退出全屏失败:', error);
      }
   }
};

const handleFullscreenChange = () => {
   isFullscreen.value = !!document.fullscreenElement;
};

const seekTo = (e: MouseEvent) => {
   if (!audioRef.value || !currentSong.value) return;
   const target = e.currentTarget as HTMLElement;
   const rect = target.getBoundingClientRect();
   const percent = (e.clientX - rect.left) / rect.width;
   const newTime = percent * duration.value;
   audioRef.value.currentTime = newTime;
   currentTime.value = newTime;
   updateProgress();
};

const onTimeUpdate = () => {
   if (audioRef.value) {
      currentTime.value = audioRef.value.currentTime;
      updateProgress();
      updateLyric();
   }
};

const onLoadedMetadata = () => {
   if (audioRef.value) {
      duration.value = audioRef.value.duration;
   }
};

const onEnded = () => {
   isPlaying.value = false;
   playNext();
};

const updateLyric = () => {
   if (lyrics.value.length === 0) return;
   for (let i = lyrics.value.length - 1; i >= 0; i--) {
      if (currentTime.value >= lyrics.value[i].time) {
         currentLyricIndex.value = i;
         scrollToCurrentLyric();
         break;
      }
   }
};

const scrollToCurrentLyric = () => {
   if (!lyricsContainer.value || currentLyricIndex.value < 0) return;
   const activeLine = lyricsContainer.value.children[currentLyricIndex.value];
   if (activeLine) {
      const containerTop = lyricsContainer.value.getBoundingClientRect().top;
      const lineTop = (activeLine as HTMLElement).getBoundingClientRect().top;
      const offset =
         lineTop - containerTop - lyricsContainer.value.clientHeight / 2;
      lyricsContainer.value.scrollTop += offset;
   }
};

watch(volume, newVal => {
   if (audioRef.value) {
      audioRef.value.volume = newVal / 100;
   }
});

onMounted(() => {
   handleSearch();
   document.addEventListener('fullscreenchange', handleFullscreenChange);
});

onUnmounted(() => {
   audioRef.value?.pause();
   document.removeEventListener('fullscreenchange', handleFullscreenChange);
});
</script>

<style lang="scss" scoped>
.music-player {
   margin-top: 50px;
   height: calc(100vh - 80px);
   display: flex;
   flex-direction: column;
   background: var(--cart-muisc-color);
   color: var(--bk-font-color);
   overflow: hidden;
}

:deep(:fullscreen) .player-panel {
   width: 100vw;
   height: 100vh;
   margin: 0;
   padding: 0;
}

:deep(:fullscreen) .current-player {
   width: 100%;
   max-width: none;
   background: transparent;
   border: none;
   justify-content: center;
}

:deep(:fullscreen) .cover-background,
:deep(:fullscreen) .album-cover,
:deep(:fullscreen) .song-detail {
   display: none !important;
}

:deep(:fullscreen) .lyric-container {
   margin-top: 0;
   height: 90vh;
   font-size: 32px;
   width: 90vw;
   max-width: none;
}

:deep(:fullscreen) .lyrics-panel {
   max-height: 100%;
   height: 100%;
   padding: 20px;
   margin-bottom: 0;
}

:deep(:fullscreen) .lyric-line {
   padding: 15px 20px;
   font-size: 32px;
   line-height: 1.8;

   &:hover {
      opacity: 1;
      color: var(--cart-border-color);
      font-size: 36px;
      transform: scale(1.1);
      font-weight: 600;
   }

   &.active {
      color: var(--cart-border-color);
      font-size: 40px;
      font-weight: 700;
      transform: scale(1.15);
      opacity: 1;
   }
}

.player-header {
   padding: 20px;
   background: var(--back-op-color);

   .search-box {
      display: flex;
      align-items: center;
      margin: 0 auto;
      background: var(--cart-back-color);
      border-radius: 30px;
      padding: 8px 15px;
      border: 1px solid var(--cart-border-color);

      .search-icon {
         color: var(--bk-font-color);
         opacity: 0.5;
         margin-right: 10px;
      }

      input {
         flex: 1;
         background: transparent;
         border: none;
         outline: none;
         color: var(--bk-font-color);
         font-size: 14px;

         &::placeholder {
            color: var(--bk-font-color);
            opacity: 0.5;
         }
      }

      .search-btn {
         width: 36px;
         height: 36px;
         border: none;
         background: var(--cart-border-color);
         border-radius: 50%;
         display: flex;
         align-items: center;
         justify-content: center;
         cursor: pointer;
         transition: transform 0.3s;

         el-icon {
            color: var(--cart-muisc-color);
         }

         &:hover {
            transform: scale(1.1);
         }
      }
   }
}

.player-body {
   flex: 1;
   display: flex;
   overflow: hidden;
}

.playlist-panel {
   width: 400px;
   background: var(--cart-back-color);
   border-right: 1px solid var(--cart-border-color);
   display: flex;
   flex-direction: column;

   .panel-title {
      display: flex;
      align-items: center;
      gap: 10px;
      padding: 15px 20px;
      font-size: 16px;
      font-weight: 600;
      background: var(--back-op-color);

      .song-count {
         margin-left: auto;
         font-size: 12px;
         color: var(--bk-font-color);
         opacity: 0.5;
      }
   }

   .song-list {
      flex: 1;
      overflow-y: auto;
      padding: 10px;

      &::-webkit-scrollbar {
         width: 4px;
      }

      &::-webkit-scrollbar-thumb {
         background: var(--cart-border-color);
         border-radius: 2px;
      }
   }
}

.song-item {
   display: flex;
   align-items: center;
   padding: 12px 15px;
   border-radius: 8px;
   cursor: pointer;
   transition: all 0.3s;

   &:hover {
      background: var(--back-op-color);
   }

   &.active {
      background: var(--cart-border-color);
      opacity: 0.4;
   }

   .song-index {
      width: 30px;
      text-align: center;
      font-size: 14px;
      color: var(--bk-font-color);
      opacity: 0.5;

      el-icon {
         color: var(--cart-border-color);
      }
   }

   .song-info {
      flex: 1;
      min-width: 0;

      .song-name {
         font-size: 14px;
         white-space: nowrap;
         overflow: hidden;
         text-overflow: ellipsis;
      }

      .song-artist {
         font-size: 12px;
         color: var(--bk-font-color);
         opacity: 0.5;
         white-space: nowrap;
         overflow: hidden;
         text-overflow: ellipsis;
         margin-top: 3px;
      }
   }

   .song-album {
      font-size: 12px;
      color: var(--bk-font-color);
      opacity: 0.4;
      width: 100px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      margin-right: 15px;
   }

   .song-duration {
      font-size: 12px;
      color: var(--bk-font-color);
      opacity: 0.5;
      width: 50px;
      text-align: right;
   }
}

.empty-state,
.loading-state {
   display: flex;
   flex-direction: column;
   align-items: center;
   justify-content: center;
   padding: 60px 20px;
   color: var(--bk-font-color);
   opacity: 0.5;

   .empty-icon,
   .loading-icon {
      font-size: 48px;
      margin-bottom: 15px;
      opacity: 0.5;
   }

   .loading-icon {
      animation: spin 1s linear infinite;
   }

   p {
      margin: 0;
   }
}

@keyframes spin {
   from {
      transform: rotate(0deg);
   }
   to {
      transform: rotate(360deg);
   }
}

.player-panel {
   flex: 1;
   display: flex;
   align-items: center;
   justify-content: center;

   background: var(--back-darw-liner);
   position: relative;
   overflow: hidden;
}

.player-panel::before {
   content: '';
   position: absolute;
   top: 0;
   left: 0;
   right: 0;
   bottom: 0;
   background-image: url('../static/lding.gif');
   background-size: cover;
   background-position: center;
   opacity: 0.3;
   z-index: 0;
}

.current-player {
   width: 100%;
   display: flex;
   height: 100%;
   flex-direction: column;
   align-items: center;
   background: var(--cart-back-color);
   padding: 60px;
   border: 1px solid var(--cart-border-color);
   position: relative;
   overflow: hidden;
   z-index: 1;
}

.cover-background {
   position: absolute;
   top: 0;
   left: 0;
   right: 0;
   bottom: 0;
   background-size: cover;
   background-position: center;
   filter: blur(40px) brightness(0.5);
   opacity: 0.6;
   z-index: 0;
}

.album-cover {
   position: relative;
   z-index: 1;

   .cover-image {
      width: 280px;
      height: 280px;
      border-radius: 50%;
      overflow: hidden;
      box-shadow: 0 20px 60px rgba(0, 0, 0, 0.4);
      border: 4px solid var(--cart-border-color);

      &.spinning {
         animation: spin 20s linear infinite;
      }

      img {
         width: 100%;
         height: 100%;
         object-fit: cover;
      }
   }

   .cover-overlay {
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background: rgba(0, 0, 0, 0.3);
      opacity: 0;
      transition: opacity 0.3s;
      display: flex;
      align-items: center;
      justify-content: center;
      border-radius: 50%;

      .play-btn {
         width: 60px;
         height: 60px;
         background: var(--cart-border-color);
         border: none;
         border-radius: 50%;
         display: flex;
         align-items: center;
         justify-content: center;
         cursor: pointer;
         transition: all 0.3s;

         el-icon {
            color: var(--cart-muisc-color);
            font-size: 24px;
         }

         &:hover {
            transform: scale(1.1);
         }
      }
   }

   &:hover .cover-overlay {
      opacity: 1;
   }
}

.song-detail {
   text-align: center;
   margin-bottom: 30px;
   position: relative;
   z-index: 1;

   .detail-title {
      font-size: 24px;
      font-weight: 600;
      margin-bottom: 8px;
      color: var(--bk-font-color);
   }

   .detail-artist {
      font-size: 16px;
      color: var(--bk-font-color);
      opacity: 0.7;
      margin-bottom: 5px;
   }

   .detail-album {
      font-size: 14px;
      color: var(--bk-font-color);
      opacity: 0.5;
   }
}

.lyrics-panel {
   width: 100%;
   max-height: 200px;
   overflow-y: auto;
   text-align: center;
   margin-bottom: 30px;
   padding: 10px;
   position: relative;
   z-index: 1;

   &::-webkit-scrollbar {
      width: 0;
      height: 0;
      background: transparent;
   }

   &::-webkit-scrollbar-thumb {
      display: none;
   }
}

.lyric-line {
   padding: 10px 20px;
   font-size: 14px;
   color: var(--bk-font-color);
   opacity: 0.6;
   transition: all 0.3s;
   cursor: pointer;

   &:hover {
      opacity: 1;
      color: var(--cart-border-color);
      transform: scale(1.02);
   }

   &.active {
      color: var(--cart-border-color);
      font-size: 18px;
      font-weight: 500;
      transform: scale(1.05);
      opacity: 1;
   }
}

.loading-lyrics {
   display: flex;
   flex-direction: column;
   align-items: center;
   color: var(--bk-font-color);
   opacity: 0.5;
   padding: 30px;

   .loading-icon {
      font-size: 24px;
      margin-bottom: 10px;
      animation: spin 1s linear infinite;
   }

   span {
      font-size: 14px;
   }
}

.no-lyrics {
   display: flex;
   flex-direction: column;
   align-items: center;
   color: var(--bk-font-color);
   opacity: 0.4;
   padding: 30px;

   el-icon {
      font-size: 32px;
      margin-bottom: 10px;
   }
}

.progress-bar {
   width: 100%;
   display: flex;
   align-items: center;
   gap: 15px;
   margin-bottom: 20px;
   margin-top: auto;
   position: relative;
   z-index: 1;

   .time-current,
   .time-total {
      font-size: 12px;
      color: var(--bk-font-color);
      opacity: 0.6;
      min-width: 45px;
   }

   .progress-track {
      flex: 1;
      height: 6px;
      background: var(--bk-font-color);
      opacity: 0.2;
      border-radius: 3px;
      position: relative;
      cursor: pointer;

      .progress-fill {
         height: 100%;
         background: var(--cart-border-color);
         border-radius: 3px;
         transition: width 0.1s linear;
      }

      .progress-thumb {
         position: absolute;
         top: 50%;
         transform: translate(-50%, -50%);
         width: 14px;
         height: 14px;
         background: var(--cart-border-color);
         border-radius: 50%;
         opacity: 0;
         transition: opacity 0.3s;
      }

      &:hover .progress-thumb {
         opacity: 1;
      }
   }
}

.player-controls {
   display: flex;
   align-items: center;
   gap: 20px;
   position: relative;
   z-index: 1;
   margin-top: auto;

   .control-btn {
      width: 40px;
      height: 40px;
      border: none;
      background: rgba(255, 255, 255, 0.1);
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      transition: all 0.3s;
      border: 1px solid var(--cart-border-color);
      opacity: 0.6;

      el-icon {
         color: var(--bk-font-color);
         font-size: 18px;
      }

      &:hover {
         background: rgba(255, 255, 255, 0.2);
         opacity: 1;
         transform: scale(1.1);
      }

      &.play-main {
         width: 56px;
         height: 56px;
         background: var(--cart-border-color);
         opacity: 1;
         border: none;

         el-icon {
            font-size: 24px;
            color: var(--cart-muisc-color);
         }

         &:hover {
            opacity: 0.9;
         }
      }
   }

   .volume-control {
      display: flex;
      align-items: center;
      gap: 10px;
      margin-left: 20px;

      el-icon {
         color: var(--bk-font-color);
         opacity: 0.6;
         font-size: 16px;
      }

      .volume-slider {
         width: 80px;
         height: 4px;
         -webkit-appearance: none;
         appearance: none;
         background: var(--bk-font-color);
         opacity: 0.2;
         border-radius: 2px;
         cursor: pointer;

         &::-webkit-slider-thumb {
            -webkit-appearance: none;
            appearance: none;
            width: 12px;
            height: 12px;
            background: var(--bk-draw-back-color);
            border-radius: 50%;
            cursor: pointer;
         }
      }
   }
}

.welcome-state {
   display: flex;
   flex-direction: column;
   align-items: center;
   color: var(--bk-font-color);
   opacity: 0.6;

   .welcome-icon {
      width: 120px;
      height: 120px;
      background: var(--bk-font-color);
      opacity: 0.1;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-bottom: 25px;
      border: 2px solid var(--cart-border-color);

      el-icon {
         font-size: 64px;
         color: var(--bk-font-color);
         opacity: 0.5;
      }
   }

   h3 {
      font-size: 28px;
      font-weight: 600;
      margin-bottom: 10px;
      color: var(--bk-font-color);
   }

   p {
      margin: 0;
      font-size: 16px;
   }
}

@media (max-width: 900px) {
   .player-body {
      flex-direction: column;
   }

   .playlist-panel {
      width: 100%;
      height: 300px;
      border-right: none;
      border-bottom: 1px solid var(--cart-border-color);
   }

   .player-panel {
      padding: 20px;
   }

   .album-cover .cover-image {
      width: 200px;
      height: 200px;
   }
}
</style>
