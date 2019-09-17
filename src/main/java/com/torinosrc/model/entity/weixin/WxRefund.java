package com.torinosrc.model.entity.weixin;

import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="t_wx_refund")
@DynamicInsert
@DynamicUpdate
public class WxRefund extends BaseEntity implements Serializable {
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    public WxRefund(){
        super();
    }

    @Column(name = "open_id")
    private String openId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "out_refund_no")
    private String outRefundNo;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "refund_fee")
    private int refundFee;

    @Column(name = "wx_refund_id")
    private String wxRefundId;

    @Column(name = "wx_result")
    private String wxResult;

    @Column(name = "wx_result_detail")
    private String wxResultDetail;

    @Column(name = "wx_refund_fee")
    private int wxRefundFee;

    @Column(name = "wx_success_time")
    private String wxSuccessTime;

    public String getWxSuccessTime() {
        return wxSuccessTime;
    }

    public void setWxSuccessTime(String wxSuccessTime) {
        this.wxSuccessTime = wxSuccessTime;
    }

    public int getWxRefundFee() {
        return wxRefundFee;
    }

    public void setWxRefundFee(int wxRefundFee) {
        this.wxRefundFee = wxRefundFee;
    }

    public String getWxResultDetail() {
        return wxResultDetail;
    }

    public void setWxResultDetail(String wxResultDetail) {
        this.wxResultDetail = wxResultDetail;
    }

    public String getWxRefundId() {
        return wxRefundId;
    }

    public void setWxRefundId(String wxRefundId) {
        this.wxRefundId = wxRefundId;
    }

    public String getWxResult() {
        return wxResult;
    }

    public void setWxResult(String wxResult) {
        this.wxResult = wxResult;
    }

    public int getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(int refundFee) {
        this.refundFee = refundFee;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
