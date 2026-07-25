# 客户端接口（博客前端）

## 博客信息

- [博主公开信息](./user_api/blogger-info.md#获取博主公开信息) `GET /api/blogger/info`
- [首页聚合数据](./user_api/blogger-info.md#获取首页聚合数据) `GET /api/blogger/home`

## 文章展示

- [分页文章列表](./user_api/articles.md#分页获取已发布文章列表) `POST /api/articles/list`

## 访问统计

- [获取访问统计](./user_api/visits.md#获取访问统计) `GET /api/visits/stats`
- [记录访问](./user_api/visits.md#记录访问) `POST /api/visits/record`
- [文章详情](./user_api/articles.md#获取文章详情) `GET /api/articles/{id}`
- [提交评论](./user_api/articles.md#提交评论或回复评论) `POST /api/articles/comment`
- [评论列表](./user_api/articles.md#获取文章评论列表) `GET /api/articles/{id}/comments`
