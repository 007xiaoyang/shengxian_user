package com.shengxian.service;

import com.shengxian.common.util.Page;
import com.shengxian.entity.Complaints;
import com.shengxian.entity.SalesService;

import java.util.HashMap;
import java.util.List;

public interface MyService {

    /**
     * 店铺详情
     * @param token
     * @return
     */
    HashMap businessDateil(String token);

    /**
     * 通过店铺id查询店铺详情信息
     * @param bindingId
     * @return
     */
    HashMap getBusinessInfoByBid(Integer bindingId);

    /**
     * 我要投诉
     * @param complaints
     * @return
     */
    Integer addIWantComplaints(String token, Complaints complaints)throws NullPointerException,Exception;

    /**
     * 投诉记录
     * @param token
     * @return
     */
    List<HashMap> complaintsNotes(String token, Integer type)throws Exception;

    /**
     * 修改投诉状态
     * @param id
     * @return
     */
    Integer updateComlaintsState(Integer id)throws Exception;


    HashMap getUnpaidAndArrearsAndConsumed(String token) throws Exception;

    /**
     * 公告未付款欠款消费
     * @param token
     * @return
     * @throws Exception
     */
    HashMap myNUAC(String token)throws NullPointerException, Exception;

    /**
     * 我的订单
     * @param token
     * @param status 1商城待接单，2待送货(接受)，3派送中，4确认到货，5拒绝接单，6取消订单
     * @param state 0未付款，1申请欠款审核，2欠款，3已完成，4拒绝欠款
     * @param type 全部，1一天，2一周，3半个夜 ，4一个月
     * @return
     */
    Page myOrder(String token, Integer pageNo, String status, Integer state, Integer type, String startTime, String endTime, Integer mold)throws NullPointerException,Exception;

    /**
     * 删除订单
     * @param order_id
     * @return
     */
    Integer deteleOrder(Integer order_id)throws NullPointerException;

    /**
     * 通过订单id查询订单明细
     * @param id 订单id
     * @return
     * @throws Exception
     */
    HashMap orderDateilListById(Integer id)throws Exception;


    /**
     *分享有奖
     * @param token
     * @return
     * @throws Exception
     */
    HashMap sharingAwards(String token)throws Exception;


    /**
     * 我的优惠券
     * @param token
     * @return
     */
    List<HashMap>  myCoupon(String token, Integer business_id)throws NullPointerException ,Exception;

    Page couponList(String token , Integer pageNo);

    /**
     * 积分商城
     * @param token
     * @param pageNo
     * @return
     * @throws NullPointerException
     * @throws Exception
     */
    Page integralMall(String token, Integer pageNo)throws NullPointerException,Exception;

    /**
     * 通过积分产品id查询产品信息
     * @param goods_id
     * @return
     */
    HashMap integraGoodsById(String token, Integer goods_id)throws NullPointerException ,Exception;

    /**
     * 查询用户积分明细
     * @param token
     * @param startTime
     * @return
     * @throws NullPointerException
     * @throws Exception
     */
    Page integraDetail(String token, Integer pageNo, String startTime)throws NullPointerException ,Exception;

    /**
     * 通过明细id查询积分明细
     * @param detail_id
     * @return
     */
    HashMap integraDetailById(Integer detail_id);

    /**
     * 用户手机号
     * @param token
     * @return
     */
    String userPhone(String token);

    /**
     * 修改密码
     * @param token
     * @param password
     * @return
     * @throws Exception
     */
    Integer updateUserPWD(String token, String password, String password2)throws NullPointerException,Exception;

    /**
     *商城推荐产品
     * @param token
     * @param pageNo
     * @return
     */
    Page businessRecommendGoodsList(String token, Integer pageNo);


    /**
     * 未付款或欠款
     * @param token
     * @param pageNo
     * @param type 0 未付款 ，1欠款
     * @return
     * @throws Exception
     */
    Page unpaidOrArrearsList(String token, Integer pageNo, Integer type)throws Exception;

    /**
     * 消费
     * @param token
     * @param pageNo
     * @param type 1日，2月，3年
     * @return
     * @throws Exception
     */
    Page consumeList(String token, Integer pageNo, Integer type)throws Exception;

    /**
     * 积分兑换
     * @param token
     * @param goods_id 积分产品ID
     * @param integra 兑换的积分
     * @return
     * @throws Exception
     */
    Integer addExchangeIntegraGoods(String token, Integer goods_id, Double integra)throws  Exception;


    /**
     * 积分兑换订单列表
     * @param token
     * @param pageNo
     * @return
     * @throws Exception
     */
    Page exchangeIntegraGoodsList(String token, Integer pageNo, Integer state)throws Exception;


    /**
     * 我要售货服务
     * @param salesService
     * @return
     */
    Integer addSalesService(String token, SalesService salesService)throws NullPointerException,Exception;

    /**
     * 售货服务记录
     * @param token
     * @return
     */
    List<HashMap> slesService(String token, Integer type)throws Exception;

    /**
     * 修改投诉状态
     * @param id
     * @return
     */
    Integer updateslesServiceState(Integer id)throws Exception;

    /**
     * 注销号码
     * @param token
     * @param password
     * @return
     */
    Integer  deleteUser(String token, String password)throws NullPointerException ,Exception;

}
