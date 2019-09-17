package com.torinosrc.dao.weixin;

import com.torinosrc.model.entity.weixin.WxRefund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WxRefundDao extends JpaRepository<WxRefund, Long>, JpaSpecificationExecutor<WxRefund> {
    WxRefund findByTransactionId(String transactionId);
}
