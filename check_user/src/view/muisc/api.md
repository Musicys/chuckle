🎵 网易云音乐搜索接口文档

1. 接口概述
   该接口用于在网易云音乐平台搜索歌曲、专辑、歌手等内容。
   接口地址: https://music.163.com/api/search/get/web
   请求方式: GET 或 POST
2. 请求参数
   调用此接口时，需要传递以下参数。
   表格
   参数名 类型 必填 描述 示例值
   s String 是 搜索关键词，如歌曲名、歌手名。 周杰伦
   type Integer 否 搜索类型。1=单曲, 10=专辑, 100=歌手, 1000=歌单, 1002=用户。默认为 1。 1
   limit Integer 否 每页返回的数量。 10
   offset Integer 否 分页偏移量，用于翻页。 0
   total Boolean 否 是否返回总数。 true
   csrf_token String 否 防止跨站请求伪造的令牌，通常可为空。 ""
3. 响应参数
   接口成功调用后，会返回一个 JSON 格式的数据。
   code (Integer): 状态码。200 表示请求成功。
   result (Object): 包含搜索结果的核心对象。
   songs (Array): 歌曲列表，包含多个歌曲对象。
   songCount (Integer): 搜索到的歌曲总数。
   trp (Object): 包含一些内部规则或追踪信息，通常对普通开发者不关键。
4. songs 数组中的歌曲对象结构
   songs 数组中的每个对象代表一首歌曲，其详细结构如下：
   表格
   字段名 类型 描述
   id Integer 歌曲的唯一ID。
   name String 歌曲名称。
   alias Array 歌曲别名或附加信息。
   duration Integer 歌曲时长，单位为毫秒。
   fee Integer 歌曲费用类型。0=免费, 1=会员, 8=付费单曲。
   status Integer 歌曲状态。0=正常, -1=下架。
   mvid Integer 关联的MV ID，若无则为0。
   artists Array 歌手列表，每个元素是一个歌手对象。
   album Object 专辑对象，包含专辑的详细信息。
5. 歌手对象 (artists 数组元素)
   表格
   字段名 类型 描述
   id Integer 歌手的唯一ID。
   name String 歌手名称。
   img1v1Url String 歌手头像图片的URL。
6. 专辑对象 (album 对象)
   表格
   字段名 类型 描述
   id Integer 专辑的唯一ID。
   name String 专辑名称。
   publishTime Integer 专辑发行时间，为Unix时间戳（毫秒）。
   picId Integer 专辑图片的ID。
   artist Object 专辑的艺术家对象，结构与歌手对象类似。
7. 请求示例
   搜索关键词为“周杰伦”的单曲：
   文本

编辑

GET https://music.163.com/api/search/get/web?s=周杰伦&type=1&limit=10&offset=0 8. 响应示例
以下是根据你提供的数据整理的简化版成功响应示例：
json

编辑

{
"code": 200,
"result": {
"songs": [
{
"id": 509781655,
"name": "想你就写信 (Live)",
"duration": 238698,
"fee": 1,
"status": 0,
"artists": [
{
"id": 6452,
"name": "周杰伦"
},
{
"id": 12010120,
"name": "李硕"
}
],
"album": {
"id": 36412633,
"name": "中国新歌声第二季 第13期",
"publishTime": 1507046400000,
"artist": {
"id": 0,
"name": ""
}
}
}
],
"songCount": 273
}
}

根据你提供的信息，这是关于第三方网易云音乐歌曲解析接口的文档。
🎵 网易云音乐歌曲解析接口文档
这是一个第三方提供的接口，用于解析网易云音乐的歌曲ID，获取其真实的音频文件播放地址。

1. 接口概述
   接口地址: https://www.ffapi.cn/int/v1/netease_url
   请求方式: GET 或 POST
   返回格式: JSON
2. 请求参数
   调用此接口时，需要传递以下参数。
   表格
   参数名 类型 必填 说明
   id string 是 网易云音乐歌曲的ID。可以从歌曲播放页面的URL中获取，例如 https://music.163.com/song?id=210049 中的 210049。
   quality string 否 指定输出音质。默认为标准音质。
   quality 参数可选值：
   表格
   值 说明
   standard 标准
   higher 较高
   exhigh 极高
   lossless 无损
   hires Hi-Res
   jyeffect 高清环绕声
   sky 沉浸环绕声
   jymaster 超清母带
3. 响应参数
   接口成功调用后，会返回一个 JSON 格式的数据。
   表格
   字段名 类型 说明
   code int 状态码。200 表示解析成功，-1 表示解析失败。
   msg string 状态信息，如“解析成功”或“解析失败”。
   level string/int 解析出的音质等级。成功时为字符串（如“超清母带(Master)”），失败时可能为整数。
   size string 音频文件的大小，如“143.82MB”。
   url string 解析出的音频文件直链地址。
   reserve string 备用播放地址。
   links string 歌曲在网易云音乐的原始页面链接。
   time string 接口响应时间。
   耗时 string 解析所花费的时间。
4. 请求示例
   解析歌曲ID为 210049 的歌曲，并指定音质为超清母带：
   文本

编辑

GET https://www.ffapi.cn/int/v1/netease_url?id=210049&quality=jymaster 5. 响应示例
成功响应示例：
json

编辑

{
"code": 200,
"msg": "解析成功",
"level": "超清母带(Master)",
"size": "143.82MB",
"url": "https://m801.music.126.net/.../0ccacd3ee24357e17869d3d90d07574e.flac",
"reserve": "https://music.163.com/song/media/outer/url?id=25714102.mp3",
"links": "https://music.163.com/song?id=25714102",
"time": "2026-04-02 16:19:21",
"耗时": "0.61 秒"
}
失败响应示例：
json

编辑

{
"code": -1,
"msg": "解析失败！"
}

是的，网易云音乐确实提供了官方的歌词API，可以获取到原文歌词、翻译歌词和罗马音歌词。
不过，这个接口对请求参数有要求，如果参数不完整，就会返回 wrong params 错误。
✅ 正确的请求方式
你需要提供 id（歌曲ID）和 os（操作系统类型）这两个核心参数。
接口地址：
https://music.163.com/api/song/lyric
请求示例：
https://music.163.com/api/song/lyric?id=22677434&os=pc
📝 返回的数据格式
成功请求后，接口会返回一个JSON对象，其中包含了不同版本的歌词：
lrc: 包含时间轴的原文歌词。
tlyric: 翻译歌词（如果有）。
romalrc: 罗马音歌词（如果有）。
klyric: 逐字歌词，用于卡拉OK效果。
返回示例：
json

编辑

{
"code": 200,
"lrc": {
"lyric": "[00:19.84]あなたもきっとそうでしょう\n[00:24.67]足早に通り過ぎていく風"
},
"tlyric": {
"lyric": "[00:19.84]你一定也是如此吧\n[00:24.67]快步走过的风"
},
"romalrc": {
"lyric": "[00:19.840]a na ta mo ki tto so u de sho u"
}
}
💡 使用注意事项
请求方式：支持 GET 和 POST 请求。
缓存机制：接口存在缓存，短时间内对同一URL的重复请求可能直接返回缓存结果。
请求频率：避免过于频繁地调用，否则可能会触发风控，导致请求失败。
