package com.yupi.springbootinit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.vo.OssFileVO;
import org.apache.ibatis.annotations.Param;

/**
* @author 黎旺
* @description 针对表【oss_files(OSS文件表)】的数据库操作Mapper
* @createDate 2026-06-25 16:54:45
*/
public interface OssFilesMapper extends BaseMapper<OssFiles> {

    Page<OssFileVO> pageOssFiles(Page<?> page,
                                 @Param("category") String category,
                                 @Param("keyword") String keyword,
                                 @Param("startDate") String startDate,
                                 @Param("endDate") String endDate);
}
