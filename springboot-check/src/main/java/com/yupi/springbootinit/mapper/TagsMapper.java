package com.yupi.springbootinit.mapper;

import com.yupi.springbootinit.model.domain.Tags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
* @author 黎旺
* @description 针对表【tags(标签表)】的数据库操作Mapper
* @createDate 2026-07-07 14:07:09
* @Entity com.yupi.springbootinit.model.domain.Tags
*/
public interface TagsMapper extends BaseMapper<Tags> {

    /**
     * 获取所有标签及其文章数量（仅统计已发布、未删除的文章）
     */
    List<Map<String, Object>> listTagCounts();
}




