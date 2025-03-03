package com.ruoyi.ry.message.mapper;

import java.util.List;
import com.ruoyi.ry.message.domain.RyMessage;

/**
 * 留言Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-26
 */
public interface RyMessageMapper 
{
    /**
     * 查询留言
     * 
     * @param id 留言主键
     * @return 留言
     */
    public RyMessage selectRyMessageById(Long id);

    /**
     * 查询留言列表
     * 
     * @param ryMessage 留言
     * @return 留言集合
     */
    public List<RyMessage> selectRyMessageList(RyMessage ryMessage);

    /**
     * 新增留言
     * 
     * @param ryMessage 留言
     * @return 结果
     */
    public int insertRyMessage(RyMessage ryMessage);

    /**
     * 修改留言
     * 
     * @param ryMessage 留言
     * @return 结果
     */
    public int updateRyMessage(RyMessage ryMessage);

    /**
     * 删除留言
     * 
     * @param id 留言主键
     * @return 结果
     */
    public int deleteRyMessageById(Long id);

    /**
     * 批量删除留言
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRyMessageByIds(Long[] ids);
}
