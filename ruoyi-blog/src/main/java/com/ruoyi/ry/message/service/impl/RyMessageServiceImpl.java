package com.ruoyi.ry.message.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.ry.blog.domain.RyBlog;
import com.ruoyi.ry.blog.mapper.RyBlogMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ry.message.mapper.RyMessageMapper;
import com.ruoyi.ry.message.domain.RyMessage;
import com.ruoyi.ry.message.service.IRyMessageService;

/**
 * 留言Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@Service
public class RyMessageServiceImpl implements IRyMessageService
{
    @Autowired
    private RyMessageMapper ryMessageMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    // @Autowired
    // private RyBlogMapper ryBlogMapper;

    /**
     * 查询留言
     *
     * @param id 留言主键
     * @return 留言
     */
    @Override
    public RyMessage selectRyMessageById(Long id)
    {
        return ryMessageMapper.selectRyMessageById(id);
    }

    /**
     * 查询留言列表
     *
     * @param ryMessage 留言
     * @return 留言
     */
    @Override
    public List<RyMessage> selectRyMessageList(RyMessage ryMessage)
    {

        List<RyMessage> ryMessages = new ArrayList<>();
        //判断用户权限
        String createBy = ryMessage.getCreateBy();
        List<RyMessage> allMessage = ryMessageMapper.selectRyMessageList(null);
        if(createBy != null && !"".equals(createBy)){
            SysUser user = sysUserMapper.selectUserByUserName(createBy);
            if(user != null){
                //存在此用户
                //找出此用户所有没有被删除的留言，以及给他留言的留言

                ryMessage.setDelFlag("0");
                List<RyMessage> ryMessageList = ryMessageMapper.selectRyMessageList(ryMessage);
                //查询子评论 (没有查询父留言 只从当前留言查询子留言)
                ryMessages = ryMessageList.stream().map(message -> {
                    //映射操作 设置子分类
                    message.setChildMessages(getChildMessage(message, allMessage));
                    return message;
                }).collect(Collectors.toList());
            }
        }else {
            //查询所有评论
            ryMessages = ryMessageMapper.selectRyMessageList(ryMessage);
        }
        //添加头像
        ryMessages.forEach(message -> {
            Long userId = message.getUserId();
            if(userId != null){
                SysUser user = sysUserMapper.selectUserById(userId);
                message.setAvatar(user.getAvatar());
            }
            //添加父留言
            Long parentId = message.getParentId();
            if(parentId != null){
                RyMessage parentMessage = ryMessageMapper.selectRyMessageById(parentId);
                message.setParentCreateBy(parentMessage.getCreateBy());
            }
        });

        return ryMessages;
    }

    /**
     * 新增留言
     *
     * @param ryMessage 留言
     * @return 结果
     */
    @Override
    public int insertRyMessage(RyMessage ryMessage)
    {
        String createBy = ryMessage.getCreateBy();
        if(createBy != null && !"".equals(createBy)){
            SysUser user = sysUserMapper.selectUserByUserName(createBy);
            if(user != null){
                ryMessage.setUserId(user.getUserId());
            }
        }
        ryMessage.setCreateTime(DateUtils.getNowDate());
        return ryMessageMapper.insertRyMessage(ryMessage);
    }

    /**
     * 修改留言
     *
     * @param ryMessage 留言
     * @return 结果
     */
    @Override
    public int updateRyMessage(RyMessage ryMessage)
    {
        ryMessage.setUpdateTime(DateUtils.getNowDate());
        return ryMessageMapper.updateRyMessage(ryMessage);
    }

    /**
     * 批量删除留言
     *
     * @param ids 需要删除的留言主键
     * @return 结果
     */
    @Override
    public int deleteRyMessageByIds(Long[] ids)
    {
        return ryMessageMapper.deleteRyMessageByIds(ids);
    }

    /**
     * 删除留言信息
     *
     * @param id 留言主键
     * @return 结果
     */
    @Override
    public int deleteRyMessageById(Long id)
    {
        return ryMessageMapper.deleteRyMessageById(id);
    }


    //递归查询该回复的所有子回复
    private List<RyMessage> getChildMessage(RyMessage root,List<RyMessage> allMessage){

        List<RyMessage> collect = allMessage.stream().filter(ryMessage -> {

            //过滤Long //类型是1代表是回复的
            return ryMessage.getParentId().equals(root.getId()) && ryMessage.getType().equals("1");
            //找出子分类
        }).map(ryMessage -> {
            //设置子留言
            //循环递归
            ryMessage.setChildMessages(getChildMessage(ryMessage, allMessage));
            return ryMessage;
        }).sorted((message1,message2) ->{//根据修改时间排序
            return message1.getUpdateTime().compareTo(message2.getUpdateTime());
        }).collect(Collectors.toList());
        return collect;
    }

    //找出根留言加入
    private RyMessage getRootMessage(RyMessage ryMessage,List<RyMessage> allMessage){
        //判断是否为根留言
        if(ryMessage.getParentId() == null && "".equals(ryMessage.getParentId())){
            //为根留言
            return ryMessage;
        }
        //不是根评论
        //找出根评论
        RyMessage rootMessage = allMessage.stream().filter(message -> {
            return message.getId().equals(ryMessage.getParentId());
        }).findFirst().orElse(null);
        return getRootMessage(rootMessage,allMessage);
    }
}
