package com.torinosrc.model.entity.weixin;

import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="t_wx_payment_config")
@DynamicInsert
@DynamicUpdate
public class WxPaymentConfig extends BaseEntity implements Serializable {
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    public WxPaymentConfig(){
        super();
    }


    @Column(name = "title")
    private String  title;

    @Column(name = "app_id")
    private String  appId;

    @Column(name = "app_secret")
    private String  appSecret;

    @Column(name = "mch_id")
    private String  mchId;

    @Column(name = "sslcert_path")
    private String  sslcertPath;

    @Column(name = "sslkey_path")
    private String  sslkeyPath;

    @Column(name = "wechat_key")
    private String  wechatKey;

    @Column(name = "p12_path")
    private String p12Path;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getSslcertPath() {
        return sslcertPath;
    }

    public void setSslcertPath(String sslcertPath) {
        this.sslcertPath = sslcertPath;
    }

    public String getSslkeyPath() {
        return sslkeyPath;
    }

    public void setSslkeyPath(String sslkeyPath) {
        this.sslkeyPath = sslkeyPath;
    }

    public String getWechatKey() {
        return wechatKey;
    }

    public void setWechatKey(String wechatKey) {
        this.wechatKey = wechatKey;
    }

    public String getP12Path() {
        return p12Path;
    }

    public void setP12Path(String p12Path) {
        this.p12Path = p12Path;
    }
}
