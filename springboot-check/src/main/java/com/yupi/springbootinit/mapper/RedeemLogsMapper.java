package com.yupi.springbootinit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.vo.RedeemLogVO;
import org.apache.ibatis.annotations.Param;

public interface RedeemLogsMapper extends BaseMapper<RedeemLogs> {

    Page<RedeemLogVO> pageRedeemLogs(Page<RedeemLogs> page,
                                      @Param("username") String username,
                                      @Param("cardNo") String cardNo,
                                      @Param("cardType") Integer cardType,
                                      @Param("isFullAccess") Integer isFullAccess,
                                      @Param("startDate") String startDate,
                                      @Param("endDate") String endDate);

    Page<RedeemLogVO> pageMyRedeemLogs(Page<RedeemLogs> page,
                                       @Param("userId") Long userId);
}
