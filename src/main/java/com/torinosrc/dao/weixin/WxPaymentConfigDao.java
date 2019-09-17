package com.torinosrc.dao.weixin;

import com.torinosrc.model.entity.user.User;
import com.torinosrc.model.entity.weixin.WxPaymentConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WxPaymentConfigDao extends JpaRepository<WxPaymentConfig, Long>, JpaSpecificationExecutor<WxPaymentConfig> {
}
