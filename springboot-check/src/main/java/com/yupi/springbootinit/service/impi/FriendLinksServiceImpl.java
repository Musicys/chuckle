package com.yupi.springbootinit.service.impi;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.domain.FriendLinks;
import com.yupi.springbootinit.service.FriendLinksService;
import com.yupi.springbootinit.mapper.FriendLinksMapper;
import org.springframework.stereotype.Service;

/**
* @author 黎旺
* @description 针对表【friend_links(友情链接表)】的数据库操作Service实现
* @createDate 2026-07-07 14:07:09
*/
@Service
public class FriendLinksServiceImpl extends ServiceImpl<FriendLinksMapper, FriendLinks>
    implements FriendLinksService{

}




