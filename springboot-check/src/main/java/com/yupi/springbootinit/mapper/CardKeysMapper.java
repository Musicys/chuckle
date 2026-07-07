package com.yupi.springbootinit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.vo.CardVO;
import org.apache.ibatis.annotations.Param;

/**
* @author 黎旺
* @description 针对表【card_keys(卡密表)】的数据库操作Mapper
* @createDate 2026-06-24 14:26:02
* @Entity generator.domain.CardKeys
*/
public interface CardKeysMapper extends BaseMapper<CardKeys> {

    /**
     * 分页查询卡密（关联用户名）
     */
    Page<CardVO> pageWithUsername(Page<CardKeys> page,
                                  @Param("type") Integer type,
                                  @Param("status") Integer status,
                                  @Param("cardNo") String cardNo,
                                  @Param("userId") Long userId);
}




