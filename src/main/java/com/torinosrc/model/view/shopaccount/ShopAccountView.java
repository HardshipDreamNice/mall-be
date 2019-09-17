/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.view.shopaccount;

import com.torinosrc.model.entity.shopaccount.ShopAccount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * <b><code>ShopAccountView</code></b>
 * <p/>
 * ShopAccount的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-12 16:33:53.
 *
 * @author ${model.author}
 * @version 1.0.0
 * @since torinosrc-spring-boot-be 1.0.0
 */
@ApiModel
public class ShopAccountView extends ShopAccount implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "全部收入")
    private Long incomeAmountAll;

    @ApiModelProperty(value = "本周收入")
    private Long incomeAmountWeek;

    @ApiModelProperty(value = "本月收入")
    private Long incomeAmountMonth;

    @ApiModelProperty(value = "本日收入")
    private Long incomeAmountDay;

    @ApiModelProperty(value = "总销售金额")
    private Integer salesAmountAll;

    @ApiModelProperty(value = "本周销售金额")
    private Integer salesAmountWeek;

    @ApiModelProperty(value = "本月销售金额")
    private Integer salesAmountMonth;

    @ApiModelProperty(value = "本日销售金额")
    private Integer salesAmountDay;

    @ApiModelProperty(value = "团队全部收入")
    private Long teamIncomeAmountAll;

    @ApiModelProperty(value = "团队本周收入")
    private Long teamIncomeAmountWeek;

    @ApiModelProperty(value = "团队本月收入")
    private Long teamIncomeAmountMonth;

    @ApiModelProperty(value = "团队本日收入")
    private Long teamIncomeAmountDay;

    public Integer getSalesAmountAll() {
        return salesAmountAll;
    }

    public void setSalesAmountAll(Integer salesAmountAll) {
        this.salesAmountAll = salesAmountAll;
    }

    public Integer getSalesAmountWeek() {
        return salesAmountWeek;
    }

    public void setSalesAmountWeek(Integer salesAmountWeek) {
        this.salesAmountWeek = salesAmountWeek;
    }

    public Integer getSalesAmountMonth() {
        return salesAmountMonth;
    }

    public void setSalesAmountMonth(Integer salesAmountMonth) {
        this.salesAmountMonth = salesAmountMonth;
    }

    public Integer getSalesAmountDay() {
        return salesAmountDay;
    }

    public void setSalesAmountDay(Integer salesAmountDay) {
        this.salesAmountDay = salesAmountDay;
    }

    public Long getIncomeAmountAll() {
        return incomeAmountAll;
    }

    public void setIncomeAmountAll(Long incomeAmountAll) {
        this.incomeAmountAll = incomeAmountAll;
    }

    public Long getIncomeAmountWeek() {
        return incomeAmountWeek;
    }

    public void setIncomeAmountWeek(Long incomeAmountWeek) {
        this.incomeAmountWeek = incomeAmountWeek;
    }

    public Long getIncomeAmountMonth() {
        return incomeAmountMonth;
    }

    public void setIncomeAmountMonth(Long incomeAmountMonth) {
        this.incomeAmountMonth = incomeAmountMonth;
    }

    public Long getIncomeAmountDay() {
        return incomeAmountDay;
    }

    public void setIncomeAmountDay(Long incomeAmountDay) {
        this.incomeAmountDay = incomeAmountDay;
    }

    public Long getTeamIncomeAmountAll() {
        return teamIncomeAmountAll;
    }

    public void setTeamIncomeAmountAll(Long teamIncomeAmountAll) {
        this.teamIncomeAmountAll = teamIncomeAmountAll;
    }

    public Long getTeamIncomeAmountWeek() {
        return teamIncomeAmountWeek;
    }

    public void setTeamIncomeAmountWeek(Long teamIncomeAmountWeek) {
        this.teamIncomeAmountWeek = teamIncomeAmountWeek;
    }

    public Long getTeamIncomeAmountMonth() {
        return teamIncomeAmountMonth;
    }

    public void setTeamIncomeAmountMonth(Long teamIncomeAmountMonth) {
        this.teamIncomeAmountMonth = teamIncomeAmountMonth;
    }

    public Long getTeamIncomeAmountDay() {
        return teamIncomeAmountDay;
    }

    public void setTeamIncomeAmountDay(Long teamIncomeAmountDay) {
        this.teamIncomeAmountDay = teamIncomeAmountDay;
    }
}
