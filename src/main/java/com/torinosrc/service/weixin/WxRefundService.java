package com.torinosrc.service.weixin;

import com.torinosrc.model.entity.weixin.WxRefund;
import com.torinosrc.model.view.weixin.WxRefundView;
import com.torinosrc.service.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public interface WxRefundService extends BaseService<WxRefundView> {
    WxRefundView findByTransactionId(String transactionId);
}
