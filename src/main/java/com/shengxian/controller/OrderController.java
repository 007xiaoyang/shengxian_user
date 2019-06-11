package com.shengxian.controller;

import com.alibaba.fastjson.JSONObject;
import com.shengxian.common.Message;
import com.shengxian.common.WebSocketUtil;
import com.shengxian.common.util.Global;
import com.shengxian.common.util.IntegerUtils;
import com.shengxian.common.util.Page;
import com.shengxian.entity.Address;
import com.shengxian.entity.Order;
import com.shengxian.entity.ShoppingHashMap;
import com.shengxian.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Description: 订单
 *
 * @Author: yang
 * @Date: 2019-01-15
 * @Version: 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {


    private static Logger log = Logger.getLogger(OrderController.class);

    @Resource
    private OrderService orderService;

    /**
     * 产品是否限购
     * @param token
     * @param goods_id 产品id
     * @return
     */
    @RequestMapping("/goodsIsRestricted.do")
    public Message goodsIsRestricted(String token,Integer goods_id){
        Message message = Message.non();
        try {
            Double num = orderService.goodsIsRestricted(goods_id);
            return message.code(Message.codeSuccessed).data(num).message("查询成功");
        }catch (Exception e){
            log.error("order/goodsIsRestricted"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 产品是否满赠
     * @param token
     * @param goods_id 产品id
     * @return
     */
    @RequestMapping("/goodsIsFullGift.do")
    public Message goodsIsFullGift(String token,Integer goods_id){
        Message message = Message.non();
        try {
            HashMap hashMap = orderService.goodsIsFullGift(goods_id);
            return message.code(Message.codeSuccessed).data(hashMap).message("查询成功");
        }catch (Exception e){
            log.error("order/goodsIsFullGift"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 加入购物车
     * @param token
     * @param goods_id
     * @param num
     * @return
     */
    @RequestMapping("/addShoppingCart.do")
    public Message addShoppingCart(String token,Integer goods_id,double num ,Integer type){
        Message message = Message.non();
        if (IntegerUtils.isEmpty(goods_id)){
            return message.code(Message.codeFailured).message("要加入购物车的产品id不能为空");
        }
        if (num == 0.0){
            return message.code(Message.codeFailured).message("数量不能为空");
        }
        try {
            Integer count = orderService.addShoppingCart(token, goods_id, num ,type);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("加入购物车失败");
            }
            return message.code(Message.codeSuccessed).message("加入购物车成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("order/addShoppingCart"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }
    /**
     * 减掉购物车
     * @param token
     * @param dateil_id
     * @return
     */
    @RequestMapping("/reduceShoppingCart.do")
    public Message reduceShoppingCart(String token,Integer dateil_id){
        Message message = Message.non();
        if (IntegerUtils.isEmpty(dateil_id)){
            return message.code(Message.codeFailured).message("详情id不能为空");
        }
        try {
            Integer count = orderService.reduceShoppingCart(token, dateil_id);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("加入购物车失败");
            }
            return message.code(Message.codeSuccessed).message("加入购物车成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("order/reduceShoppingCart"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 当前切换店铺的购物车产品
     * @param token
     * @return
     */
    @RequestMapping("/bindingShoppingcart.do")
    public synchronized Message bindingShoppingcart(String token ){
        Message message = Message.non();
        try {
            ShoppingHashMap hashMap = orderService.bindingShoppingcart(token);
            return message.code(Message.codeSuccessed).data(hashMap).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("order/bindingShoppingcart"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 购物车
     * @param token
     * @return
     */
    @RequestMapping("/shoppingcart.do")
    public Message shoppingcart(String token ){
        Message message = Message.non();
        try {
            ShoppingHashMap hashMap = orderService.shoppingcart(token);
            return message.code(Message.codeSuccessed).data(hashMap).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("order/shoppingcart"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 结算
     * @param token
     * @param scd_id 购物车详情id
     * @param coupon_id 用户优惠券id
     * @return
     */
    @RequestMapping("/settlement.do")
    public Message settlement(String token , String scd_id,Integer coupon_id,Integer business_id){
        Message message = Message.non();
        try{
            HashMap hashMap = orderService.settlement(token,scd_id ,coupon_id ,business_id);
            return message.code(Message.codeSuccessed).data(hashMap).message("结算成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("order/settlement"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 下订单
     * @param token
     * @param json
     * @return
     */
    @RequestMapping("/addOrdre.do")
    public synchronized Message addOrder(String token,String json){
        Message message = Message.non();

        try {
            Order order = JSONObject.parseObject(json, Order.class);
            Integer count = orderService.addOrder(token,order);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("下单失败");
            }
            //webSocket 推送
            WebSocketUtil ws = new WebSocketUtil();

            System.out.println("------------------------------推送----------------------");

            //通过店铺id查询店铺token
            String businessToken = orderService.businessToken(order.getBusiness_id());
            JSONObject jo = new JSONObject();
            jo.put("message", " 您有新的订单，请注意及时查看哟！！！");
            jo.put("token",businessToken);

            try {
                ws.onMessage(jo.toString());
            } catch (IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
            return message.code(Message.codeSuccessed).data(count).message("下单成功");

        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("order/addOrdre"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 推送通知
     * @param token
     * @return
     */
    @RequestMapping("/pushMessage.do")
    public Message pushMessage(String token ,Integer pageNo){
        Message message = Message.non();
        try {
            Page page = orderService.pushMessage(token, pageNo);
            return message.code(Message.codeSuccessed).data(page).message("获取成功");
        }catch (Exception e){
            log.error("order/pushMessage"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 查询订单总数
     * @param token
     * @return
     */
    @RequestMapping("/orderListCount.do")
    public Message orderListCount(String token,String status,Integer state){
        Message message = Message.non();
        try {
            Integer count = orderService.orderListCount(token,status,state);
            return message.code(Message.codeSuccessed).data(count).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("order/orderListCount"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 查询订单总数(推送)
     * @param token
     * @return
     */
    @RequestMapping("/orderCount.do")
    public Message orderCount(String token,String status,Integer state){
        Message message = Message.non();
        try {
            Integer count = orderService.orderCount(token,status,state);
            return message.code(Message.codeSuccessed).data(count).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("order/orderCount"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     *  通知总数
     * @param token
     * @return
     */
    @RequestMapping("/pushCount.do")
    public Message pushCount(String token){
        Message message = Message.non();
        try {
            Integer count = orderService.pushCount(token);
            return message.code(Message.codeSuccessed).data(count).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("order/pushCount"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     *  查询订单和通知总数
     * @param token
     * @return
     */
    @RequestMapping("/totalCount.do")
    public Message totalCount(String token){
        Message message = Message.non();
        try {
            Integer count = orderService.totalCount(token);
            return message.code(Message.codeSuccessed).data(count).message("查询成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("order/totalCount"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 修改订单或通知为已读
     * @param token
     * @param id
     * @param type 1订单，2通知
     * @return
     */
    @RequestMapping("/updateCount.do")
    public Message updateCount(String token ,String id ,Integer type){
        Message message = Message.non();

        try {
            Integer count = orderService.updateCount(token,id,type);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("修改失败");
            }
            return message.code(Message.codeSuccessed).message("修改成功");
        }catch (Exception e){
            log.error("order/updateCount"+e.getMessage());
            return message.code(Message.codeFailured).message("修改失败");
        }
    }

    /**
     * 删除购物车产品
     * @param token
     * @param dateil_id
     * @return
     */
    @RequestMapping("/deteleShoppingcartDateil.do")
    public Message deteleShoppingcartDateil(String token ,Integer sc_id ,String dateil_id){
        Message message = Message.non();
        if (dateil_id == null || dateil_id.equals("")){
            return message.code(Message.codeFailured).message("详情ID不能为空");
        }
        try {
            Integer count = orderService.deteleShoppingcartDateil(sc_id ,dateil_id);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("删除购物车产品失败");
            }
            return message.code(Message.codeSuccessed).message("删除成功");
        }catch (Exception e){
            log.error("order/deteleShoppingcartDateil"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 绑定用户默认地址
     * @param token
     * @return
     */
    @RequestMapping("/bindingAddress.do")
    public Message bindingAddress(String token ){
        Message message = Message.non();
        try {
            HashMap hashMap = orderService.bindingAddress(token );
            return message.code(Message.codeSuccessed).data(hashMap).message("获取成功");
        }catch (Exception e){
            log.error("order/bindingAddress"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 绑定用户地址集合
     * @param token
     * @return
     */
    @RequestMapping("/bindingAddressList.do")
    public Message bindingAddressList(String token){
        Message message = Message.non();
        try {
            List<HashMap> hashMap = orderService.bindingAddressList(token);
            return message.code(Message.codeSuccessed).data(hashMap).message("获取成功");
        }catch (Exception e){
            log.error("order/bindingAddressList"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 添加用户收货地址
     * @param token
     * @return
     */
    @RequestMapping("/addBindingAddress.do")
    public Message addBindingAddress(String token , Address address){
        Message message = Message.non();
        try {
            Integer count = orderService.addBindingAddress(token ,address);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("添加地址失败");
            }
            return message.code(Message.codeSuccessed).message("添加地址成功");
        }catch (Exception e){
            log.error("order/addBindingAddress"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 通过地址ID查询用户收货地址信息
     * @param token
     * @return
     */
    @RequestMapping("/bindingAddressById.do")
    public Message bindingAddressById(String token , Integer address_id){
        Message message = Message.non();
        try {
            HashMap hashMap = orderService.bindingAddressById(address_id);
            return message.code(Message.codeSuccessed).data(hashMap).message("获取成功");
        }catch (Exception e){
            log.error("order/bindingAddressById"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 修改用户收货地址
     * @param token
     * @return
     */
    @RequestMapping("/updateBindingAddress.do")
    public Message updateBindingAddress(String token , Address address){
        Message message = Message.non();
        try {
            Integer count = orderService.updateBindingAddress(token, address);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("修改地址失败");
            }
            return message.code(Message.codeSuccessed).message("修改地址成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("order/updateBindingAddress"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 删除用户收货地址
     * @param token
     * @return
     */
    @RequestMapping("/deleteBindingAddress.do")
    public Message deleteBindingAddress(String token ,Integer address_id){
        Message message = Message.non();
        try {
            Integer count = orderService.deleteBindingAddress( address_id);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("删除地址失败");
            }
            return message.code(Message.codeSuccessed).message("删除地址成功");
        }catch (Exception e){
            log.error("order/deleteBindingAddress"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


}
