package com.yupi.springbootinit.controller.user;

import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.model.domain.Images;
import com.yupi.springbootinit.service.PortfolioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/portfolio")
@Api(tags = "作品展示")
public class PortfolioController {

    @Resource
    private PortfolioService portfolioService;

    @GetMapping("/list")
    @ApiOperation(value = "获取已发布的作品列表")
    public BaseResponse<List<Images.Portfolio>> listPortfolio() {
        List<Images.Portfolio> list = portfolioService.lambdaQuery()
                .eq(Images.Portfolio::getStatus, 1)
                .orderByAsc(Images.Portfolio::getSortOrder)
                .list();
        return ResultUtils.success(list);
    }
}
