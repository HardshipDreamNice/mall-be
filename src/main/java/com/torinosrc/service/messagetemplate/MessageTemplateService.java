package com.torinosrc.service.messagetemplate;

import com.torinosrc.model.entity.team.Team;
import com.torinosrc.model.entity.teamuser.TeamUser;
import com.torinosrc.model.entity.withdrawmoney.WithdrawMoney;
import com.torinosrc.model.view.order.OrderView;
import com.torinosrc.model.view.team.TeamView;
import com.torinosrc.model.view.teamuser.TeamUserView;
import com.torinosrc.model.view.withdrawmoney.WithdrawMoneyView;
import com.torinosrc.response.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * 推送小程序模板消息
 */
@Service
public interface MessageTemplateService {

    /**
     * 推送小程序模板消息
     * 发货提醒
     * @param orderView
     * @return
     */
    JSONObject sendShipMessage(OrderView orderView);

    /**
     * 推送小程序模板消息
     * 待支付提醒
     * @param orderView
     * @return
     */
    JSONObject sendWaitingForYouToPayMessage(OrderView orderView);

    /**
     * 推送小程序模板消息
     * 退款成功提醒
     * @param orderView
     * @return
     */
    JSONObject sendRefundSuccessMessage(OrderView orderView);

    /**
     * 推送小程序模板消息
     * 拼团待成团提醒
     * @param teamUserView
     * @param formId
     * @return
     */
    JSONObject sendWaitGroupCompleteMessage(TeamUserView teamUserView, String formId);

    /**
     * 推送小程序模板消息
     * 拼团成功提醒
     * @param teamUserView
     * @param formId
     * @return
     */
    JSONObject sendGroupSuccessMessage(TeamUserView teamUserView, String formId);

    /**
     * 推送小程序模板消息
     * 拼团失败提醒
     * @param teamUserView
     * @param formId
     * @return
     */
    JSONObject sendGroupFailMessage(TeamUserView teamUserView, String formId);

}
