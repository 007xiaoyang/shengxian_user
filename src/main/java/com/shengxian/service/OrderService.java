package com.shengxian.service;

import com.shengxian.common.util.Page;
import com.shengxian.entity.Address;
import com.shengxian.entity.Order;
import com.shengxian.entity.ShoppingHashMap;
import io.swagger.models.auth.In;

import java.util.HashMap;
import java.util.List;

public interface OrderService {


    /**
     * 产品是否限购
     * @param goods_id 产品id
     * @return
     */
    Double goodsIsRestricted(Integer goods_id)throws Exception;

    /**
     * 产品是否满赠
     * @param goods_id 产品id
     * @return
     */
    HashMap goodsIsFullGift(Integer goods_id)throws Exception;

    /**
     * 加入购物车
     * @param token
     * @param goods_id
     * @param num
     * @return
     */
    Integer addShoppingCart(String token, Integer goods_id, Double num, Integer type)throws NullPointerException ,Exception;

    /**
     * 减掉购物车
     * @param token
     * @param dateil_id 购物车详情ID
     * @return
     */
    Integer reduceShoppingCart(String token, Integer dateil_id)throws NullPointerException ,Exception;

    /**
     * 删除购物车商品
     * @param id
     * @return
     * @throws Exception
     */
    Integer deleteShoppingCart(Integer id ) throws Exception;

    /**
     * 当前切换店铺的购物车产品
     * @param token
     * @return
     */
    ShoppingHashMap bindingShoppingcart(String token)throws NullPointerException ,Exception;

    /**
     * 购物车
     * @param token
     * @return
     */
    ShoppingHashMap shoppingcart(String token)throws NullPointerException ,Exception;



    /**
     * 小程序购物车
     * @param token
     * @return
     */
    ShoppingHashMap wxGetShoppingcart(String token)throws Exception;
    /**
     * 获取购物车总数
     * @param token
     * @return
     */
    Integer cartCount(String token);

    /**
     * 结算
     * @param token
     * @param scd_id 购物车详情id
     * @param coupon_id 用户优惠券id
     * @param business_id 店铺ID
     * @return
     * @throws NullPointerException
     * @throws Exception
     */
    HashMap settlement(String token, String scd_id, Integer coupon_id, Integer business_id)throws NullPointerException ,Exception;

    /**
     * 获取绑定客户符合金额条件的优惠券
     * @param bindingId
     * @return
     */
    List<HashMap> getConformConponList(Integer bindingId ,Double money);

    /**
     * 下订单
     * @param token
     * @param order
     * @return
     * @throws NullPointerException
     * @throws Exception
     */
    Integer addOrder(String token, Order order)throws NullPointerException ,Exception;

    /**
     * 推送通知
     * @param token
     * @param pageNo
     * @return
     * @throws Exception
     */
    Page pushMessage(String token, Integer pageNo)throws NullPointerException, Exception;

    /**
     * 订单总数
     * @param token
     * @return
     * @throws NullPointerException
     * @throws Exception
     */
    Integer orderListCount(String token, String status, Integer state)throws NullPointerException,Exception;

    /**
     * 查询所有状态的订单总数
     */
    HashMap getOrderStatis(String token );

    /**
     * 查询所有状态的(推送)订单总数
     */
    HashMap getOrderPushCount(String token );
    /**
     * 订单总数(推送)
     * @param token
     * @return
     * @throws NullPointerException
     * @throws Exception
     */
    Integer orderCount(String token, String status, Integer state)throws NullPointerException,Exception;

    /**
     * 通知总数
     * @param token
     * @return
     * @throws NullPointerException
     * @throws Exception
     */
    Integer pushCount(String token)throws NullPointerException,Exception;

    /**
     * 查询订单和通知总数
     * @param token
     * @return
     * @throws NullPointerException
     * @throws Exception
     */
    Integer totalCount(String token)throws NullPointerException ,Exception;

    /**
     * 修改订单或通知为已读
     * @param token
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    Integer updateCount(String token, String id, Integer type)throws Exception;

    /**
     * 删除购物车产品
     * @param dateil_id
     * @return
     * @throws Exception
     */
    Integer deteleShoppingcartDateil(Integer sc_id, String dateil_id)throws Exception;

    /**
     * 绑定用户默认地址
     * @param token
     * @return
     * @throws Exception
     */
    HashMap bindingAddress(String token)throws Exception;

    /**
     * 绑定用户地址集合
     * @param token
     * @return
     * @throws Exception
     */
    List<HashMap> bindingAddressList(String token)throws Exception;

    /**
     * 添加用户收货地址
     * @param token
     * @param address
     * @return
     */
    Integer  addBindingAddress(String token, Address address)throws Exception;

    /**
     * 通过地址ID查询用户收货地址信息
     * @param address_id
     * @return
     */
    HashMap bindingAddressById(Integer address_id);

    /**
     * 修改用户收货地址
     * @param address
     * @return
     * @throws Exception
     */
    Integer updateBindingAddress(String token, Address address)throws NullPointerException, Exception;

    /**
     * 删除用户收货地址
     * @param address_id
     * @return
     * @throws Exception
     */
    Integer deleteBindingAddress(Integer address_id)throws Exception;

    String businessToken(Integer business_id);

}
