package com.yupi.springbootinit.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "访问统计视图")
public class VisitStatsVO {

    @ApiModelProperty(value = "今日访问量（PV）")
    private Integer todayPv;

    @ApiModelProperty(value = "今日访客数（UV）")
    private Integer todayUv;

    @ApiModelProperty(value = "本月访问量")
    private Integer monthPv;

    @ApiModelProperty(value = "总访问量")
    private Integer totalPv;

    public Integer getTodayPv() {
        return todayPv;
    }

    public void setTodayPv(Integer todayPv) {
        this.todayPv = todayPv;
    }

    public Integer getTodayUv() {
        return todayUv;
    }

    public void setTodayUv(Integer todayUv) {
        this.todayUv = todayUv;
    }

    public Integer getMonthPv() {
        return monthPv;
    }

    public void setMonthPv(Integer monthPv) {
        this.monthPv = monthPv;
    }

    public Integer getTotalPv() {
        return totalPv;
    }

    public void setTotalPv(Integer totalPv) {
        this.totalPv = totalPv;
    }
}
