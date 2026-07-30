import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import type { Song } from '@/api/muisc';

export const useMusicStore = defineStore('music', () => {
   // 当前播放歌曲
   const currentSong = ref<Song | null>(null);
   // 是否正在播放
   const isPlaying = ref(false);
   // 当前播放进度
   const currentTime = ref(0);
   // 歌曲总时长
   const duration = ref(0);
   // 播放列表
   const playlist = ref<Song[]>([]);
   // 当前歌曲在列表中的索引
   const currentIndex = ref(-1);

   // 计算属性：是否有歌曲在播放
   const hasSong = computed(() => currentSong.value !== null);

   // 设置当前播放歌曲
   const setCurrentSong = (song: Song) => {
      currentSong.value = song;
      const index = playlist.value.findIndex(s => s.id === song.id);
      if (index !== -1) {
         currentIndex.value = index;
      }
   };

   // 设置播放状态
   const setPlaying = (playing: boolean) => {
      isPlaying.value = playing;
   };

   // 设置播放进度
   const setCurrentTime = (time: number) => {
      currentTime.value = time;
   };

   // 设置总时长
   const setDuration = (time: number) => {
      duration.value = time;
   };

   // 设置播放列表
   const setPlaylist = (songs: Song[]) => {
      playlist.value = songs;
   };

   // 播放下一首
   const playNext = () => {
      if (playlist.value.length === 0) return;
      const nextIndex =
         currentIndex.value < playlist.value.length - 1
            ? currentIndex.value + 1
            : 0;
      currentIndex.value = nextIndex;
      currentSong.value = playlist.value[nextIndex];
   };

   // 播放上一首
   const playPrev = () => {
      if (playlist.value.length === 0) return;
      const prevIndex =
         currentIndex.value > 0
            ? currentIndex.value - 1
            : playlist.value.length - 1;
      currentIndex.value = prevIndex;
      currentSong.value = playlist.value[prevIndex];
   };

   // 暂停播放
   const pause = () => {
      isPlaying.value = false;
   };

   // 继续播放
   const resume = () => {
      isPlaying.value = true;
   };

   // 切换播放状态
   const togglePlay = () => {
      isPlaying.value = !isPlaying.value;
   };

   // 清空播放状态
   const clear = () => {
      currentSong.value = null;
      isPlaying.value = false;
      currentTime.value = 0;
      duration.value = 0;
      currentIndex.value = -1;
   };

   return {
      currentSong,
      isPlaying,
      currentTime,
      duration,
      playlist,
      currentIndex,
      hasSong,
      setCurrentSong,
      setPlaying,
      setCurrentTime,
      setDuration,
      setPlaylist,
      playNext,
      playPrev,
      pause,
      resume,
      togglePlay,
      clear
   };
});
