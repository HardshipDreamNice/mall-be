package com.torinosrc.model.entity.captcha;

import com.torinosrc.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="t_captcha")
@DynamicInsert
@DynamicUpdate
public class Captcha extends BaseEntity implements Serializable {

    @Column(name = "username")
    private String userName;

    @Column(name = "validate_code")
    private String validateCode;

    @Column(name = "type")
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

}
