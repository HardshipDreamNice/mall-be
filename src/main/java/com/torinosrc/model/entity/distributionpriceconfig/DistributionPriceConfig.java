/**
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.model.entity.distributionpriceconfig;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>DistributionPriceConfig</code></b>
 * <p/>
 * DistributionPriceConfig的具体实现
 * <p/>
 * <b>Creation Time:</b> 2018-07-12 15:13:23.
 *
 * @author ${model.author}
 * @version 2.0.0
 * @since torinosrc-spring-boot-be 2.0.0
 */
@Entity
@Table(name="t_distribution_price_config")
@DynamicInsert
@DynamicUpdate
public class DistributionPriceConfig extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public DistributionPriceConfig(){
        super();
    }

    @Column(name = "percent_config")
    private String percentConfig;

    @Column(name = "percent_1")
    private Integer percent1;

    @Column(name = "percent_2")
    private Integer percent2;

    @Column(name = "percent_3")
    private Integer percent3;

    public String getPercentConfig() {
        return percentConfig;
    }

    public void setPercentConfig(String percentConfig) {
        this.percentConfig = percentConfig;
    }

    public Integer getPercent1() {
        return percent1;
    }

    public void setPercent1(Integer percent1) {
        this.percent1 = percent1;
    }

    public Integer getPercent2() {
        return percent2;
    }

    public void setPercent2(Integer percent2) {
        this.percent2 = percent2;
    }

    public Integer getPercent3() {
        return percent3;
    }

    public void setPercent3(Integer percent3) {
        this.percent3 = percent3;
    }
}
