package com.shengxian.mapper;

import com.shengxian.entity.Complaints;
import com.shengxian.entity.Integra;
import com.shengxian.entity.Parameter;
import com.shengxian.entity.SalesService;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface MyMapper {

    /**
     * 店铺详情
     * @param binding_id
     * @return
     */
    HashMap businessDateil(@Param("binding_id") Integer binding_id);

    /**
     * 店铺营业执照
     * @param business_id
     * @return
     */
    List<HashMap> businessLicense(@Param("business_id") Integer business_id);

    /**
     * 我要投诉
     * @param complaints
     * @return
     */
    Integer addIWantComplaints(Complaints complaints);

    /**
     * 我要投诉的图片
     * @param complaints_id 投诉id
     * @param img 投诉图片
     * @return
     */
    Integer addIWantComplaintsImg(@Param("complaints_id") Integer complaints_id, @Param("img") String img);

    /**
     * 未处理和处理中的投诉记录
     * @param business_id 投诉店铺id
     * @return
     */
    List<HashMap> NotCompleteComplaintsNotes(@Param("business_id") Integer business_id);

    /**
     * 投诉图片集合
     * @param id 投诉id
     * @return
     */
    List<HashMap> complaintsNotesImg(@Param("id") Integer id);

    /**
     * 完成的投诉处理，只显示完成8天
     * @param business_id
     * @return
     */
    List<HashMap> completeComplaintsNotes(@Param("business_id") Integer business_id);


    /**
     * 修改投诉状态（相当于重新投诉）
     * @param id 投诉id
     * @param create_time 投诉时间
     * @return
     */
    Integer updateComlaintsState(@Param("id") Integer id, @Param("create_time") Date create_time);


    HashMap getUnpaidAndArrearsAndConsumed(@Param("bindingId") Integer bindingId);

    /**
     * 公告未付款欠款消费
     * @param binding_id
     * @return
     * @throws Exception
     */
    HashMap myNUAC(@Param("binding_id") Integer binding_id) ;

    /**
     * 我的订单总数
     * @param parameter
     * @return
     */
    Integer myOrderCount(Parameter parameter);

    /**
     * 我的订单
     * @param parameter
     * @return
     */
    List<HashMap>  myOrder(Parameter parameter);

    /**
     * 查询订单优惠券
     * @param order_id
     * @return
     */
    HashMap selectOrderCoupon(Integer order_id);



    /**
     * 删除订单
     * @param order_id
     * @return
     */
    Integer deteleOrder(Integer order_id);

    /**
     * 通过订单id查询订单运费和差价
     * @param id
     * @return
     */
    HashMap orderFreightAndDifferential(@Param("id") Integer id);

    /**
     *通过订单id查询订单明细
     * @param id 订单id
     * @return
     */
    List<HashMap> orderDateilListById(@Param("id") Integer id);

    /**
     *分享有奖
     * @param token
     * @return
     */
    HashMap sharingAwards(@Param("token") String token);

    /**
     * 我的优惠券
     * @param binding_id 绑定id
     * @param business_id 店铺id
     * @return
     */
    List<HashMap> myCoupon(@Param("binding_id") Integer binding_id, @Param("business_id") Integer business_id);

    Integer couponListCount(@Param("bdid") Integer bindingId , @Param("bid") Integer businessId );

    List<HashMap> couponList(@Param("bdid") Integer bindingId , @Param("bid") Integer businessId , @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 查询绑定用户积分
     * @param binding_id
     * @return
     */
    Double bindingIntegra(@Param("binding_id") Integer binding_id);

    /**
     * 积分商城列表总数
     * @param binding_id
     * @return
     */
    Integer integralMallCount(@Param("binding_id") Integer binding_id);

    /**
     * 积分商城列表
     * @param binding_id
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<HashMap> integralMall(@Param("binding_id") Integer binding_id, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 通过积分产品id查询积分信息
     * @param goods_id
     * @return
     */
    HashMap integraGoodsById(@Param("id") Integer goods_id);

    /**
     * 通过绑定id查询积分总数
     * @param binding_id
     * @return
     */
    Double getBindingIntegarNum(@Param("bindingId") Integer binding_id);
    /**
     * 通过绑定用户id查询积分id
     * @param binding_id
     * @return
     */
    Integer selectIntegraId(@Param("binding_id") Integer binding_id);

    /**
     * 用户积分明细总数
     * @param integra_id 积分id
     * @param startTime
     * @return
     */
    Integer integraDetailCount(@Param("integra_id") Integer integra_id, @Param("time") String startTime);

    /**
     * 用户积分明细
     * @param integra_id 积分id
     * @param startTime
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<HashMap> integraDetail(@Param("integra_id") Integer integra_id, @Param("time") String startTime, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 用户积分收入
     * @param integra_id
     * @param startTime
     * @return
     */
    Double integraDetailIncome(@Param("integra_id") Integer integra_id, @Param("time") String startTime);

    /**
     * 用户积分支出
     * @param integra_id
     * @param startTime
     * @return
     */
    Double integraDetailExpenditure(@Param("integra_id") Integer integra_id, @Param("time") String startTime);

    /**
     * 通过明细id查询积分明细
     * @param detail_id
     * @return
     */
    HashMap integraDetailById(@Param("id") Integer detail_id);

    /**
     * 用户手机号
     * @param token
     * @return
     */
    String userPhone(@Param("token") String token);

    /**
     * 通过token查询数据库的密码和注册id
     * @param token
     * @return
     */
    HashMap findUserIdAndPwd(@Param("token") String token);

    /**
     * 修改密码
     * @param user_id
     * @param password
     * @return
     */
    Integer updateUserPWD(@Param("id") Integer user_id, @Param("password") String password);

    /**
     * 店铺推荐产品总数
     * @param binding_id 绑定ID
     * @param business_id 店铺ID
     * @param scheme_id 绑定用户方案ID
     * @return
     */
    Integer businessRecommendGoodsListCount(@Param("binding_id") Integer binding_id, @Param("business_id") Integer business_id, @Param("scheme_id") Integer scheme_id);

    /**
     * 店铺推荐产品列表
     * @param binding_id 绑定ID
     * @param business_id 店铺ID
     * @param scheme_id 绑定用户方案ID
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<HashMap>  businessRecommendGoodsList(@Param("binding_id") Integer binding_id, @Param("business_id") Integer business_id, @Param("scheme_id") Integer scheme_id, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize) ;

    /**
     * 客户的未付款订单总数
     * @param binding_id 客户绑定ID
     * @return
     */
    Integer unpaidListCount(@Param("binding_id") Integer binding_id);

    /**
     * 客户的未付款订单
     * @param binding_id 客户绑定ID
     * @return
     */
    List<HashMap> unpaidList(@Param("binding_id") Integer binding_id, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 客户的欠款订单总数
     * @param binding_id 客户绑定ID
     * @return
     */
    Integer arrearsListCount(@Param("binding_id") Integer binding_id);

    /**
     * 客户的欠款订单
     * @param binding_id 客户绑定ID
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<HashMap> arrearsList(@Param("binding_id") Integer binding_id, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 消费总数
     * @param binding_id 客户绑定id
     * @param type 1日，2月，3年
     * @return
     */
    Integer consumeListCount(@Param("binding_id") Integer binding_id, @Param("type") Integer type);


    /**
     * 消费
     * @param binding_id 客户绑定id
     * @param type 1日，2月，3年
     * @return
     */
    List<HashMap> consumeList(@Param("binding_id") Integer binding_id, @Param("type") Integer type, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 通过产品id查询是否下架了
     * @param goods_id
     * @return
     */
    Integer selectIntegraGoodsIdStatus(@Param("id") Integer goods_id);

    /**
     * 通过积分产品id查询产品剩余库存
     * @param goods_id
     * @return
     */
    Double selectIntegraGoodNum(@Param("id") Integer goods_id);

    /**
     * 兑换产品
     * @param integra
     * @return
     */
    Integer addExchangeIntegraGoods(Integra integra);

    /**
     * 修改积分产品库存
     * @param goods_id
     * @return
     */
    Integer updateIntegraGoodNum(@Param("id") Integer goods_id);

    /**
     * 库存已经没有了 ，下架产品
     * @param goods_id
     * @return
     */
    Integer updateIntegraGoodsStatus(@Param("id") Integer goods_id);

    /**
     * 修改绑定用户的积分
     * @param id 用户积分id
     * @param integar_num 积分数量
     * @return
     */
    Integer updateUserIntegra(@Param("id") Integer id, @Param("integar_num") Double integar_num);

    /**
     * 添加用户积分明细
     * @param integra_id 用户积分id
     * @param integra 产品积分数
     * @param type 3兑换
     * @param create_time 创建时间
     * @param number 订单号
     * @return
     */
    Integer addUserIntegraDetail(@Param("integra_id") Integer integra_id, @Param("integra") Double integra, @Param("type") Integer type, @Param("create_time") Date create_time, @Param("number") String number);

    /**
     * 积分兑换订单列表总数
     * @param binding_id
     * @return
     */
    Integer exchangeIntegraGoodsListCount(@Param("binding_id") Integer binding_id, @Param("state") Integer state);

    /**
     * 积分兑换订单列表
     * @param binding_id
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<HashMap> exchangeIntegraGoodsList(@Param("binding_id") Integer binding_id, @Param("state") Integer state, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);


    /**
     * 我要售货服务
     * @param salesService
     * @return
     */
    Integer addSalesService(SalesService salesService);

    /**
     * 添加售货服务图片
     * @param ss_id 售货id
     * @param img 售货图片
     * @return
     */
    Integer addSalesServiceImg(@Param("ss_id") Integer ss_id, @Param("img") String img);

    /**
     * 未处理和处理中的售货服务记录
     * @param business_id 售货店铺id
     * @return
     */
    List<HashMap> NotslesService(@Param("business_id") Integer business_id);

    /**
     * 售货服务图片集合
     * @param id 售货id
     * @return
     */
    List<HashMap> slesServiceImg(@Param("id") Integer id);

    /**
     * 完成的售货服务记录，只显示完成8天
     * @param business_id 店铺ID
     * @return
     */
    List<HashMap> slesService(@Param("business_id") Integer business_id);

    /**
     * 修改售货服务状态（相当于重新售货）
     * @param id 售货id
     * @param create_time 售货时间
     * @return
     */
    Integer updateslesServiceState(@Param("id") Integer id, @Param("create_time") Date create_time);

    /**
     * 通过注册id查询登录密码
     * @param user_id
     * @return
     */
    String selectUserPassword(@Param("id") Integer user_id);

    /**
     * 注销号码同时把该号码绑定所有的店铺也取消掉
     * @param user_id
     * @return
     */
    Integer updateBindingUser(@Param("user_id") Integer user_id);

    /**
     * 注销号码
     * @param user_id
     * @return
     */
    Integer deleteUser(@Param("id") Integer user_id);

}
