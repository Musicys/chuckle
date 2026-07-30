package com.yupi.springbootinit.controller.user;

import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.model.domain.FriendLinks;
import com.yupi.springbootinit.model.dto.friendLink.FriendLinkAddRequest;
import com.yupi.springbootinit.service.FriendLinksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/friendLinks")
@Api(tags = "友链展示")
public class FriendLinkController {

    @Resource
    private FriendLinksService friendLinksService;

    @GetMapping("/list")
    @ApiOperation(value = "获取已发布的友链列表")
    public BaseResponse<List<FriendLinks>> listFriendLinks() {
        List<FriendLinks> list = friendLinksService.lambdaQuery()
                .eq(FriendLinks::getStatus, 1)
                .orderByAsc(FriendLinks::getSortOrder)
                .list();
        return ResultUtils.success(list);
    }

    @PostMapping("/apply")
    @ApiOperation(value = "提交友链申请（公开接口）")
    public BaseResponse<Boolean> applyFriendLink(@RequestBody FriendLinkAddRequest request) {
        FriendLinks entity = new FriendLinks();
        entity.setName(request.getName());
        entity.setUrl(request.getUrl());
        entity.setAvatar(request.getAvatar());
        entity.setDescription(request.getDescription());
        entity.setEmail(request.getEmail());
        entity.setSortOrder(0);
        entity.setStatus(0); // 待审核
        boolean saved = friendLinksService.save(entity);
        return ResultUtils.success(saved);
    }
}
