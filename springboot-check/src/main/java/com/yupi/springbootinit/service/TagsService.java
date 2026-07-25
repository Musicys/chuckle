package com.yupi.springbootinit.service;

import com.yupi.springbootinit.model.domain.Tags;
import com.yupi.springbootinit.model.vo.TagVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 黎旺
* @description 针对表【tags(标签表)】的数据库操作Service
* @createDate 2026-07-07 14:07:09
*/
public interface TagsService extends IService<Tags> {

    /**
     * 获取所有标签及其文章数量（仅统计已发布、未删除的文章）
     */
    List<TagVO> listTagCounts();
}
