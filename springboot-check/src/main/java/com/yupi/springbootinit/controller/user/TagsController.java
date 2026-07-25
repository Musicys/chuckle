package com.yupi.springbootinit.controller.user;

import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.model.vo.TagVO;
import com.yupi.springbootinit.service.TagsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/tags")
@Api(tags = "标签展示")
public class TagsController {

    @Resource
    private TagsService tagsService;

    @GetMapping("/list")
    @ApiOperation(value = "获取标签列表（含文章数量）")
    public BaseResponse<List<TagVO>> listTags() {
        List<TagVO> tagVOs = tagsService.listTagCounts();
        return ResultUtils.success(tagVOs);
    }
}
