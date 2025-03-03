import config from "@/config";

import axios from "axios";


export const tileSai: (string) => any = (Str) => {
    // 函数体内的代码

    return axios.post(
        config.aiurl,
        {
            "max_tokens": 1200,
            "model": "gpt-3.5-turbo",
            "temperature": 0.8,
            "top_p": 1,
            "presence_penalty": 1,
            "messages": [

                {
                    "role": "user",
                    "content": Str
                }


            ]
            , "stream": false
        }, {
        headers: {
            'Content-Type': 'application/json', // 如果需要发送 JSON 数据
            "Authorization": config.miyao
        }
    });
};