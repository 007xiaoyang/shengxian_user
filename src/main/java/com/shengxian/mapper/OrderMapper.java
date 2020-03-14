package com.shengxian.mapper;

import com.shengxian.entity.*;
import com.shengxian.vo.ShoppongCartDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface OrderMapper {


    /**
     * 产品是否限购
     * @param goods_id 产品id
     * @return
     */
    Double goodsIsRestricted(@Param("goods_id") Integer goods_id);

    /**
     * 产品是否满赠
     * @param goods_id 产品id
     * @return
     */
    HashMap goodsIsFullGift(@Param("goods_id") Integer goods_id);

    /**
     * 查询当前切换绑定的店铺是否有购物车
     * @param binding_id
     * @return
     */
    Integer selectBindingIsShoppingcart(@Param("binding_id") Integer binding_id);


    /**
     * 添加当前切换店铺购物车
     * @return
     */
    Integer addBindingShoppongcart(Shoppongcart shoppongcart);

    /**
     * 查询购物车详情里是否有该产品
     * @param goods_id
     * @return
     */
    Integer selectShoppingcartDetailIsGoods_id(@Param("sc_id") Integer scid  , @Param("goods_id") Integer goods_id);

    /**
     * 删除购物车详情id
     * @param id
     * @return
     */
    Integer deleteShoppingcartDetail(@Param("id") Integer id);

    /**
     * //通过购物车id查询购物车下是否还有产品
     * @param sc_id
     * @return
     */
    List<Integer> findShoppingcartIsGoods(@Param("sc_id") Integer sc_id);

    /**
     * 删除购物车
     * @param id
     * @return
     */
    Integer deleteShoppingcart(@Param("id") Integer id);



    /**
     * 修改购物车详情的产品数量
     * @param id 购物车详情id
     * @param num 购买数量
     * @return
     */
    Integer updateShoppingcartDetailIsGoods_id(@Param("id") Integer id, @Param("num") Double num, @Param("type") Integer type);

    /**
     * 添加购物车详情的产品数量
     * @return
     */
    Integer addShoppingcartDetailIsGoods_id(ShoppongcartDetail shoppongcartDetail);

    /**
     * 通过购物车详情id查询加入购物车的总数量是否大于限购数量
     * @param id 购物车详情id
     * @return
     */
    Double selectShoppingcartDetailIsGoods_idNum(@Param("id") Integer id);

    /**
     * 修改产品赠送数量
     * @param id 购物车详情id
     * @param give_num 赠送数量
     * @return
     */
    Integer updateShoppingcartDetailGoodsGiveNum(@Param("id") Integer id, @Param("give_num") Double give_num);

    /**
     * 计算加入购物车所有产品的总金额
     * @param sc_id
     * @param binding_id
     * @return
     */
    Double selectGoodsTatolMoney(@Param("sc_id") Integer sc_id, @Param("binding_id") Integer binding_id);

    /**
     * 修改购物车总金额
     * @param id 购物车id
     * @param money
     * @return
     */
    Integer updateShoppingcartTotalMoney(@Param("id") Integer id, @Param("money") Double money);

    /**
     * 减少购物车详情里的产品数量
     * @param dateil_id
     * @return
     */
    Integer updateShoppingcartDetailGoodsNum(@Param("id") Integer dateil_id);

    /**
     * 通过购物车详情ID查询原来的数量
     * @param id 购物车详情id
     * @return
     */
    HashMap shoppingcartDetailGoodsNum(@Param("id") Integer id);

    /**
     * 查询起送价,店铺id，店铺名称
     * @param binding_id
     * @return
     */
    ShoppingHashMap findStartingPrice(@Param("id") Integer binding_id);

    /**
     * 通过购物车id查询购物车下的产品总数
     * @param sc_id
     * @return
     */
    Integer shoppingcartGoodsDetailGoodsCount(@Param("sc_id") Integer sc_id);

    /**
     * 通过店铺id判断加入购物车的产品是否被下架了或者是删除了，如果有则直接删除购物车详情里的产品
     * @param business_id
     * @return
     */
    Integer deleteShoppingCartDetailByBusiness_id(Integer business_id);

    /**
     * 通过绑定id查询屏蔽的产品 ,如果有则删除购物车详情里的产品
     * @param binding_id
     * @return
     */
    Integer deleteShoppingcartDateilShieldingGoods(Integer binding_id);

    /**
     * 查询用户绑定id的屏蔽产品
     * @param binding_id
     * @return
     */
    List<Integer> shieldingGoods(@Param("binding_id") Integer binding_id);

    /**
     * 根据屏蔽的产品id删除购物车详情对应的产品
     * @param goods_id
     * @return
     */
    Integer deteleShoppingCartDetailByGoods_id(@Param("goods_id") Integer goods_id);

    /**
     * 通过购物车id查询购物车下的产品信息（无产品图片）
     * @param sc_id
     * @return
     */
    List<HashMap> shoppingcartGoodsDetail(@Param("sc_id") Integer sc_id, @Param("binding_id") Integer binding_id ,@Param("business_id") Integer business_id);

    /**
     * 通过购物车id查询购物车下的产品信息（有产品图片）
     * @param sc_id
     * @return
     */
    List<HashMap> shoppingcartDetail(@Param("sc_id") Integer sc_id, @Param("binding_id") Integer binding_id  ,@Param("business_id") Integer business_id);


    /**
     * 通过购物车id查询购物车下的产品信息(微信小程序）
     * @param cartId 购物车id
     * @param bindingId 绑定id
     * @param businessId 店铺id
     * @return
     */
    List<ShoppongCartDetailVO> getShoppingCartDetail(@Param("cartId") Integer cartId, @Param("bindingId") Integer bindingId  , @Param("businessId") Integer businessId);



    Integer getCartCount(@Param("bdId") Integer bdId);

    /**
     * 结算
     * @param scdId 购物车详情id
     * @param binding_id 绑定用户id
     * @return
     */
    List<HashMap> settlement(@Param("scId") Integer scId, @Param("scdId") String  scdId, @Param("bdId") Integer binding_id ,@Param("bId") Integer business_id);

    /**
     * 查询店铺满减活动优惠券
     * @param business_id 店铺ID
     * @param money 消费金额
     * @return
     */
    HashMap businessFullReduction(@Param("business_id") Integer business_id, @Param("money") Double money);

    /**
     * 通过绑定id查询符合金额的优惠券
     * @param bindingId 用户绑定ID
     * @param money     消费金额
     * @return
     */
    HashMap getAccordWithBindingCoupon(@Param("bindingId") Integer bindingId, @Param("money") Double money);
    /**
     * 通过用户优惠券ID查询符合金额的优惠券
     * @param coupon_id 用户优惠券ID
     * @param money     消费金额
     * @return
     */
    HashMap accordMoneyCouponByCoupon_id(@Param("coupon_id") Integer coupon_id, @Param("money") Double money);

    /**
     * 使用用户优惠券
     * @param coupon_id 优惠券id
     * @param binding_id 绑定用户id
     * @param money
     * @return
     */
    Integer selectUserCoupon(@Param("coupon_id") Integer coupon_id, @Param("binding_id") Integer binding_id, @Param("money") Double money);

    /**
     * 通过用户优惠券id查询优惠券信息
     * @param id
     * @return
     */
    HashMap userCouponInfoById(@Param("id") Integer id);

    /**
     * 通过店铺id查询店铺运费
     * @param businessId
     * @return
     */
    Double businessFreight(@Param("businessId") Integer businessId);

    /**
     * 获取绑定客户符合金额条件的优惠券
     * @param bindingId
     * @param money
     * @return
     */
    List<HashMap> getConformConponList(@Param("bindingId") Integer bindingId, @Param("money") Double money);



    /**
     * 通过token查询绑定用户信息和店铺id
     * @param bindingId
     * @return
     */
    HashMap bindingidAndBusinessidByToken(@Param("bindingId") Integer bindingId);

    /**
     * 查询绑定用户的信用额度
     * @param binding_id 绑定id
     * @return
     */
    Integer bindingCredit(@Param("id") Integer binding_id);

    /**
     * 查询绑定用户的限制天数
     * @param binding_id 绑定id
     * @return
     */
    Integer bindingLimited(@Param("id") Integer binding_id);



    /**
     * 添加订单
     * @param order
     * @return
     */
    Integer addOrder(Order order);

    /**
     * 根据产品id查询产品进价金额
     * @param goods_id
     * @return
     */
    double findGoodsCostPrice(@Param("id") Integer goods_id);

    /**
     * 减少产品虚拟库存
     * @param goods_id 产品id
     * @param num 采购数量
     * @return
     */
    Integer reduceGoodsFictitiousInventory(@Param("goods_id") Integer goods_id, @Param("num") Double num);

    /**
     * 添加报损记录
     * @param give
     * @return
     */
    Integer addLossGoods(Give give);

    /**
     * 添加订单详情
     * @param orderDetail
     * @return
     */
    Integer addOrderDateil(OrderDetail orderDetail);

    /**
     * 根据产品id删除购物车详情
     * @param sc_id 购物车id
     * @param goods_id 产品id
     * @return
     */
    Integer deteleShoppingCartDetail(@Param("sc_id") Integer sc_id, @Param("goods_id") Integer goods_id);

    /**
     * 推送通知总数
     * @param user_id
     * @return
     */
    Integer pushMessageCount(@Param("user_id") Integer user_id);

    /**
     * 推送通知
     * @param user_id
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<HashMap> pushMessage(@Param("user_id") Integer user_id, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 订单总数
     * @param binding_id 绑定用户ID
     * @param status 1商城待接单，2待送货(接受)，3派送中，4确认到货，5拒绝接单，6取消订单
     * @param state 0未付款，1申请欠款审核，2欠款，3已完成
     * @return
     */
    Integer orderListCount(@Param("binding_id") Integer binding_id, @Param("status") String status, @Param("state") Integer state);

    /**
     * 查询所有状态的推送订单总数 (小程序)-
     * @param binding_id
     * @param status
     * @param state
     * @return
     */
    Integer getOrderPushCount(@Param("id") Integer binding_id, @Param("status") String status, @Param("state") Integer state);

    /**
     * 订单总数(推送)
     * @param binding_id 绑定用户ID
     * @param status 1商城待接单，2待送货(接受)，3派送中，4确认到货，5拒绝接单，6取消订单
     * @param state 0未付款，1申请欠款审核，2欠款，3已完成
     * @return
     */
    Integer orderCount(@Param("binding_id") Integer binding_id, @Param("status") String status, @Param("state") Integer state);

    /**
     * 通知总数
     * @param user_id 注册用户ID
     * @return
     */
    Integer pushCount(@Param("user_id") Integer user_id);


    /**
     * 修改订单为已读
     * @param order_id 订单ID
     * @return
     */
    Integer updateOrderPush_state(@Param("id") Integer order_id);

    /**
     * 修改通知为已读
     * @param push_id 通知ID
     * @return
     */
    Integer updatePushPush_state(@Param("id") Integer push_id);

    /**
     * 通过绑定ID查询用户在当前店铺的第一条地址
     * @param binding_id 绑定用户ID
     * @return
     */
    HashMap bindingAddress(@Param("binding_id") Integer binding_id);

    /**
     *绑定用户地址集合
     * @param binding_id 绑定用户ID
     * @return
     */
    List<HashMap> bindingAddressList(@Param("binding_id") Integer binding_id);

    /**
     * 通过绑定ID修改绑定用户地址的取消以前的默认
     * @param binding_id 绑定ID
     * @return
     */
    Integer updateBindingAddressState(@Param("binding_id") Integer binding_id);

    /**
     *查询用户地址里code最大的一条
     * @param binding_id
     * @return
     */
    Integer selectBindingAddressLastCode(@Param("binding_id") Integer binding_id);

    /**
     * 添加用户收货地址
     * @param address
     * @return
     */
    Integer addBindingAddress(Address address);

    /**
     * 通过地址ID查询用户收货地址信息
     * @param address_id 地址ID
     * @return
     */
    HashMap bindingAddressById(@Param("id") Integer address_id);

    /**
     * 通过地址ID查询state
     * @param address_id
     * @return
     */
    Integer bindingAddressState(@Param("id") Integer address_id);

    /**
     * 修改用户收货地址
     * @param address
     * @return
     */
    Integer updateBindingAddress(Address address);

    /**
     * 删除用户收货地址
     * @param address_id
     * @return
     * @throws Exception
     */
    Integer deleteBindingAddress(@Param("id") Integer address_id);

}
