package com.yupi.springbootinit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.vo.MaterialVO;
import com.yupi.springbootinit.model.vo.UserMaterialVO;
import org.apache.ibatis.annotations.Param;

/**
* @author 黎旺
* @description 针对表【materials(资料表)】的数据库操作Mapper
*/
public interface MaterialsMapper extends BaseMapper<Materials> {

    /**
     * 管理端分页查询资料（含上传者用户名）
     */
    Page<MaterialVO> pageMaterials(Page<Materials> page,
                                    @Param("province") String province,
                                    @Param("keyword") String keyword,
                                    @Param("status") Integer status);

    /**
     * 用户端分页查询资料（含 isUnlocked，不含 filePath）
     */
    Page<UserMaterialVO> pageMaterialsWithUnlock(Page<Materials> page,
                                                   @Param("province") String province,
                                                   @Param("keyword") String keyword,
                                                   @Param("userId") Long userId,
                                                   @Param("isFullAccess") Integer isFullAccess);
}
