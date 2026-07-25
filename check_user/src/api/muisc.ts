import axios from 'axios';

const neteaseRequest = axios.create({
   timeout: 20000
});

export interface Artist {
   id: number;
   name: string;
   img1v1Url?: string;
}

export interface Album {
   id: number;
   name: string;
   publishTime?: number;
   picId?: number;
   picUrl?: string;
   artist?: Artist;
}

export interface Song {
   id: number;
   name: string;
   alias: string[];
   duration: number;
   fee: number;
   status: number;
   mvid: number;
   artists: Artist[];
   album: Album;
}

export interface SearchResult {
   code: number;
   result: {
      songs: Song[];
      songCount: number;
   };
}

export interface NeteaseUrlResponse {
   code: number;
   msg: string;
   level: string;
   size: string;
   url: string;
   reserve: string;
   links: string;
   time: string;
}

export interface LyricLine {
   time: number;
   text: string;
}

export interface LyricResponse {
   code: number;
   lrc: {
      lyric: string;
   };
   tlyric?: {
      lyric: string;
   };
   romalrc?: {
      lyric: string;
   };
}

export const searchMusic = (params: {
   s: string;
   type?: number;
   limit?: number;
   offset?: number;
}): Promise<SearchResult> => {
   return neteaseRequest
      .get('/netease/api/search/get/web', { params })
      .then(res => res.data);
};

export const getNeteaseUrl = (params: {
   id: string;
   quality?: string;
}): Promise<NeteaseUrlResponse> => {
   return neteaseRequest
      .get('https://www.ffapi.cn/int/v1/netease_url', { params })
      .then(res => res.data);
};

export const getLyric = (params: {
   id: number;
   os?: string;
   lv?: number;
   kv?: number;
   tv?: number;
}): Promise<LyricResponse> => {
   const defaultParams = {
      os: 'pc',
      lv: -1,
      kv: -1,
      tv: -1,
      ...params
   };
   return neteaseRequest
      .get('/netease/api/song/lyric', { params: defaultParams })
      .then(res => res.data);
};

export interface SongDetailResponse {
   code: number;
   songs: Song[];
}

export const getSongDetail = (params: {
   ids: string;
}): Promise<SongDetailResponse> => {
   return neteaseRequest
      .get('/netease/api/song/detail/', { params })
      .then(res => res.data);
};

export const parseLyric = (lyricStr: string): LyricLine[] => {
   const lines: LyricLine[] = [];
   const regex = /\[(\d{2}):(\d{2})\.(\d{2,3})\](.*)/g;
   let match;

   while ((match = regex.exec(lyricStr)) !== null) {
      const minutes = parseInt(match[1]);
      const seconds = parseInt(match[2]);
      const milliseconds = parseInt(match[3]);
      const text = match[4].trim();

      if (text) {
         lines.push({
            time: minutes * 60 + seconds + milliseconds / 1000,
            text
         });
      }
   }

   return lines.sort((a, b) => a.time - b.time);
};
