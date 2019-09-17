package com.torinosrc.commons.constants;

import io.swagger.models.auth.In;

/**
 * <b><code>MallConstant</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>13:07
 *
 * @author PanXin
 * @version 1.0.0
 * @since mall-be  1.0.0
 */
public class MallConstant {

    public final static String PERCENT_DEFAULT = "default";

    /**
     * 商品类型 0 是普通商品
     */
    public final static Integer PRODUCT_TYPE_GENERAL = 0;

    /**
     * 商品类型 1 是拼团商品
     */
    public final static Integer PRODUCT_TYPE_TEAM = 1;

    /**
     * 商品类型 2 是普通与拼团兼有
     */
    public final static Integer PRODUCT_TYPE_GENERAL_AND_TEAM = 2;

    /**
     * 商品类型 3 是助力购商品
     */
    public final static Integer PRODUCT_TYPE_BOOST = 3;
    public final static Integer PRODUCT_TYPE_99 = 99;

    public final static Long CHECK_REFUND_MS=60001L;

    public final static String TEAM_TIME="teamTime";

    /**
     * 订单类型 0:普通商品订单，1：拼团类型订单，2：会员商品订单 3：免费领订单 4：助力购订单
     */
    public final static Integer ORDER_TYPE_GENERAL = 0;
    public final static Integer ORDER_TYPE_TEAM = 1;
    public final static Integer ORDER_TYPE_MEMBER = 2;
    public final static Integer ORDER_TYPE_FREE = 3;
    public final static Integer ORDER_TYPE_BOOST = 4;


    /**
     * 提现状态：提现成功
     */
    public final static Integer WITHDRAW_MONEY_STATUS_WITHDRAW_SUCCESS = 1;

    /**
     * 提现状态：提现失败
     */
    public final static Integer WITHDRAW_MONEY_STATUS_WITHDRAW_FAIL = 2;

    /**
     * 提现状态：审核成功/提现进行中
     */
    public final static Integer WITHDRAW_MONEY_STATUS_AUDIT_SUCCESS = 0;

    /**
     * 提现状态：审核进行中
     */
    public final static Integer WITHDRAW_MONEY_STATUS_AUDIT_GOING = 3;

    /**
     * 提现状态：审核失败
     */
    public final static Integer WITHDRAW_MONEY_STATUS_AUDIT_FAIL = 4;

    /**
     * 小程序授权页跳转的 redirectId
     */
    public final static Integer REDIRECT_ID_TO_ORDERDETAIL = 6;

    public final static Integer REDIRECT_TYPE_NO_TABBAR = 0;

    public final static Integer REDIRECT_TYPE_TABBAR = 1;

    /**
     * 首页商品类型ID 热门活动
     */
    public final static Long INDEX_PRODUCT_TYPE_HOT = 1L;

    /**
     * 首页商品类型ID 真爱新品
     */
    public final static Long INDEX_PRODUCT_TYPE_NEW = 2L;

    /**
     * 首页商品类型ID 精选优品
     */
    public final static Long INDEX_PRODUCT_TYPE_CHOSEN = 3L;

    /**
     * 签到成功可领取优惠券分类id
     */
    public final static Long SIGN_COUPON_CATEGORY_ID = 1L;

    /**
     * 助签成功可领取优惠券分类id
     */
    public final static Long HELP_SIGN_COUPON_CATEGORY_ID = 2L;

    /**
     * 快照表商品类型 普通商品
     */
    public final static Integer PRODUCT_SNAPSHOT_PRODUCT_TYPE_GENERAL = 0;

    /**
     * 快照表商品类型 拼团商品
     */
    public final static Integer PRODUCT_SNAPSHOT_PRODUCT_TYPE_TEAM = 1;

    /**
     * 快照表商品类型 购买会员
     */
    public final static Integer PRODUCT_SNAPSHOT_PRODUCT_TYPE_MEMBER = 2;

    /**
     * 快照表商品类型 免费领商品
     */
    public final static Integer PRODUCT_SNAPSHOT_PRODUCT_TYPE_FREE = 3;

    /**
     * 快照表商品类型 助力购商品
     */
    public final static Integer PRODUCT_SNAPSHOT_PRODUCT_TYPE_BOOST = 4;

    /**
     * 订单状态 待支付
     */
    public final static Integer ORDER_STATUS_WAIT_PAY = 0;

    /*
    商品分类状态：应用
     */
    public final static Integer CATEGORY_ENABLED_STATUS_SUCCESS=1;
    /*
   商品分类状态：禁用
    */
    public final static Integer CATEGORY_ENABLED_STATUS_FALL=0;


}
