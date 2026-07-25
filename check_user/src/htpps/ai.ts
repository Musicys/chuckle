import axios from 'axios';
import config from '@/config';
import { useUserStore } from '@/store/user';

// 获取配置，优先使用后端配置，其次使用默认配置
const getConfig = () => {
   const userStore = useUserStore();
   return {
      aiurl: userStore.config.aiurl || config.aiurl,
      miyao: userStore.config.miyao || config.miyao
   };
};

export const tileSai: (string) => any = Str => {
   const { aiurl, miyao } = getConfig();

   return axios.post(
      aiurl,
      {
         max_tokens: 1200,
         model: 'gpt-3.5-turbo',
         temperature: 0.8,
         top_p: 1,
         presence_penalty: 1,
         messages: [
            {
               role: 'user',
               content: Str
            }
         ],
         stream: false
      },
      {
         headers: {
            'Content-Type': 'application/json',
            Authorization: miyao
         }
      }
   );
};

export interface StreamOptions {
   onChunk: (chunk: string) => void;
   onEnd?: () => void;
   onError?: (error: Error) => void;
   signal?: AbortSignal;
}

export const tileSaiStream = async (Str: string, options: StreamOptions) => {
   const { onChunk, onEnd, onError, signal } = options;
   const { aiurl, miyao } = getConfig();

   try {
      const response = await fetch(aiurl, {
         method: 'POST',
         headers: {
            'Content-Type': 'application/json',
            Authorization: miyao
         },
         body: JSON.stringify({
            max_tokens: 1200,
            model: 'gpt-3.5-turbo',
            temperature: 0.8,
            top_p: 1,
            presence_penalty: 1,
            messages: [
               {
                  role: 'user',
                  content: Str
               }
            ],
            stream: true
         }),
         signal
      });

      if (!response.ok) {
         throw new Error(`HTTP error! status: ${response.status}`);
      }

      const reader = response.body?.getReader();
      if (!reader) {
         throw new Error('Response body is not readable');
      }

      const decoder = new TextDecoder();
      let buffer = '';

      while (true) {
         const { done, value } = await reader.read();
         if (done) break;

         buffer += decoder.decode(value, { stream: true });
         const lines = buffer.split('\n');
         buffer = lines.pop() || '';

         for (const line of lines) {
            if (!line.startsWith('data: ')) continue;

            const data = line.substring(6);
            if (data === '[DONE]') {
               if (onEnd) onEnd();
               return;
            }

            try {
               const parsed = JSON.parse(data);
               const content = parsed.choices?.[0]?.delta?.content;
               if (content) {
                  onChunk(content);
               }
            } catch {
               continue;
            }
         }
      }

      if (onEnd) onEnd();
   } catch (error) {
      if (onError) {
         onError(error instanceof Error ? error : new Error(String(error)));
      }
   }
};
