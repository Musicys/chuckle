package com.yupi.springbootinit.controller.user;

import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.domain.Comments;
import com.yupi.springbootinit.model.dto.message.MessageAddRequest;
import com.yupi.springbootinit.model.vo.CommentUserVO;
import com.yupi.springbootinit.service.CommentsService;
import com.yupi.springbootinit.utils.NetUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/messages")
@Api(tags = "留言板")
public class MessageController {

    @Resource
    private CommentsService commentsService;

    @GetMapping("/list")
    @ApiOperation(value = "获取留言列表（树状层级结构）")
    public BaseResponse<List<CommentUserVO>> listMessages() {
        // 查询所有已通过的留言（article_id = 0 表示留言板留言）
        List<Comments> allMessages = commentsService.lambdaQuery()
                .eq(Comments::getArticleId, 0)
                .eq(Comments::getStatus, 1)
                .list();

        // 分离一级留言和子回复
        List<Comments> parentMessages = allMessages.stream()
                .filter(c -> c.getParentId() == null)
                .sorted(Comparator.comparing(Comments::getCreatedAt).reversed())
                .collect(Collectors.toList());

        List<Comments> childMessages = allMessages.stream()
                .filter(c -> c.getParentId() != null)
                .sorted(Comparator.comparing(Comments::getCreatedAt))
                .collect(Collectors.toList());

        // 按 parentId 分组子回复
        Map<Integer, List<CommentUserVO>> childrenMap = childMessages.stream()
                .collect(Collectors.groupingBy(
                        Comments::getParentId,
                        LinkedHashMap::new,
                        Collectors.mapping(this::toCommentUserVO, Collectors.toList())
                ));

        // 组装树状结构
        List<CommentUserVO> voList = parentMessages.stream().map(c -> {
            CommentUserVO vo = toCommentUserVO(c);
            vo.setChildren(childrenMap.getOrDefault(c.getId(), new ArrayList<>()));
            return vo;
        }).collect(Collectors.toList());

        return ResultUtils.success(voList);
    }

    @PostMapping("/comment")
    @ApiOperation(value = "提交留言（或回复留言）")
    public BaseResponse<Integer> addMessage(@RequestBody MessageAddRequest request, HttpServletRequest httpRequest) {
        // 参数校验
        if (!StringUtils.isNotBlank(request.getNickname())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "留言者昵称不能为空");
        }
        if (!StringUtils.isNotBlank(request.getContent())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "留言内容不能为空");
        }

        // 如果是回复，校验被回复的留言是否存在且属于留言板
        if (request.getReplyToCommentId() != null) {
            Comments parent = commentsService.getById(request.getReplyToCommentId());
            if (parent == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "被回复的留言不存在");
            }
            if (parent.getArticleId() != 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "不能回复文章评论");
            }
        }

        Comments comment = new Comments();
        comment.setArticleId(0);
        comment.setParentId(request.getReplyToCommentId());
        comment.setReplyToCommentId(request.getReplyToCommentId());
        comment.setNickname(request.getNickname());
        comment.setEmail(request.getEmail() != null ? request.getEmail() : "");
        comment.setAvatar(request.getAvatar() != null ? request.getAvatar() : "");
        comment.setContent(request.getContent());
        comment.setStatus(1);
        comment.setIpAddress(NetUtils.getIpAddress(httpRequest));
        comment.setUserAgent(httpRequest.getHeader("User-Agent") != null ? httpRequest.getHeader("User-Agent") : "");

        commentsService.save(comment);

        return ResultUtils.success(comment.getId());
    }

    private CommentUserVO toCommentUserVO(Comments comment) {
        CommentUserVO vo = new CommentUserVO();
        BeanUtils.copyProperties(comment, vo);
        return vo;
    }
}
