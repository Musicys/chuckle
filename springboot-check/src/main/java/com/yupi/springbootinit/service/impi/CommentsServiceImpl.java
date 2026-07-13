package com.yupi.springbootinit.service.impi;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.domain.Comments;
import com.yupi.springbootinit.service.CommentsService;
import com.yupi.springbootinit.mapper.CommentsMapper;
import org.springframework.stereotype.Service;

/**
* @author 黎旺
* @description 针对表【comments(评论表（树状嵌套）)】的数据库操作Service实现
* @createDate 2026-07-07 14:07:09
*/
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments>
    implements CommentsService{

}




