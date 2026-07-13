package com.yupi.springbootinit.controller.user;

import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.domain.BloggerInfo;
import com.yupi.springbootinit.model.vo.BloggerInfoVO;
import com.yupi.springbootinit.service.BloggerInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/blogger")
@Api(tags = "博客信息")
public class BloggerInfoController {

    @Resource
    private BloggerInfoService bloggerInfoService;

    @GetMapping("/info")
    @ApiOperation(value = "获取博主公开信息（当前启用配置）")
    public BaseResponse<BloggerInfoVO> getBloggerInfo() {
        BloggerInfo info = bloggerInfoService.lambdaQuery()
                .eq(BloggerInfo::getIsUsed, 1)
                .one();
        BloggerInfoVO vo = new BloggerInfoVO();
        if (info != null) {
            BeanUtils.copyProperties(info, vo);
        }
        return ResultUtils.success(vo);
    }
}
