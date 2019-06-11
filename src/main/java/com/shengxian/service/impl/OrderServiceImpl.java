package com.shengxian.service.impl;

import com.shengxian.common.util.OrderCodeFactory;
import com.shengxian.common.util.Page;
import com.shengxian.entity.*;
import com.shengxian.mapper.OrderMapper;
import com.shengxian.mapper.UserMapper;
import com.shengxian.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Description:
 *
 * @Author: yang
 * @Date: 2019-01-15
 * @Version: 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private OrderMapper orderMapper;

    private static Logger log = Logger.getLogger(OrderServiceImpl.class);

    //产品是否限购
    @Override
    public Double goodsIsRestricted(Integer goods_id)throws Exception {
        return orderMapper.goodsIsRestricted(goods_id);
    }

    //产品是否满赠
    @Override
    public HashMap goodsIsFullGift(Integer goods_id) throws Exception{
        return orderMapper.goodsIsFullGift(goods_id);
    }

    //加入购物车
    @Override
    @Transactional
    public Integer addShoppingCart(String token, Integer goods_id, Double num ,Integer type) throws NullPointerException ,Exception {
        //通过token查询当前切换的店铺
            Integer binding_id = userMapper.userBDIdByToken(token);
        if ( binding_id == null || binding_id.equals(0)){
            throw new NullPointerException("您还未切换店铺呢");
        }

        //查询当前切换绑定的店铺是否有购物车
        Integer scid = orderMapper.selectBindingIsShoppingcart(binding_id); //scid 购物车id

        //没有购物车
        if (scid == null){
            //添加购物车
            Shoppongcart shoppongcart = new Shoppongcart(binding_id,new Date());
            orderMapper.addBindingShoppongcart(shoppongcart);
            scid = shoppongcart.getId();
        }
        //通过购物车id和产品id查询购物车详情里是否该产品
        Integer dateil_id = orderMapper.selectShoppingcartDetailIsGoods_id(scid , goods_id); //dateil_id购物车详情id
        if (dateil_id != null){

            //通过购物车详情id修改购买数量
            orderMapper.updateShoppingcartDetailIsGoods_id(dateil_id,num ,type);

        }else {

            //添加购物车详情的产品数量
            ShoppongcartDetail shoppongcartDetail = new ShoppongcartDetail(scid,goods_id,num,new Date());
            orderMapper.addShoppingcartDetailIsGoods_id(shoppongcartDetail);
            dateil_id = shoppongcartDetail.getId();
        }
        //查询该产品是否有限购
        Double restrictedNum = orderMapper.goodsIsRestricted(goods_id);
        if (restrictedNum != null){
            //通过购物车详情ID查询加入购物车的产品数量
            Double number = orderMapper.selectShoppingcartDetailIsGoods_idNum(dateil_id);
            //通过产品id查询购物车里的产品数量
            //判断加入购物车的数量是否大于限购
            if (number > restrictedNum){
                throw  new NullPointerException("亲,该宝贝不能购买更多哦～");
            }
        }

        //计算店铺加入购物车的总金额
        Double money = orderMapper.selectGoodsTatolMoney( scid, binding_id);
        //修改购物车总金额
        return orderMapper.updateShoppingcartTotalMoney(scid, money);
    }

    //减掉购物车
    @Override
    @Transactional
    public Integer reduceShoppingCart(String token, Integer dateil_id) throws NullPointerException, Exception {

        //通过token查询当前切换的店铺
        Integer binding_id = userMapper.userBDIdByToken(token);
        if ( binding_id == null || binding_id.equals(0)){
            throw new NullPointerException("您还未切换店铺呢");
        }

        //通过购物车详情ID减数量
        Integer count = orderMapper.updateShoppingcartDetailGoodsNum(dateil_id);
        if (count == null || count == 0){
            throw new NullPointerException("执行失败");
        }
        HashMap dateil = orderMapper.shoppingcartDetailGoodsNum(dateil_id);
        //判断如果购物车里的数量等于0.0或小于0.0 ，则删除购物车详情
        if (Double.valueOf(dateil.get("num").toString()) <=0.0){
            //删除购物车详情id
            orderMapper.deleteShoppingcartDetail(dateil_id);
            //通过购物车id查询购物车下是否还有产品
            List<Integer> shoppingcartIsGoods = orderMapper.findShoppingcartIsGoods(Integer.valueOf(dateil.get("sc_id").toString()) );
            if (shoppingcartIsGoods.size() <= 0){
                //因购物车下没有产品则删除购物车
                return  orderMapper.deleteShoppingcart(Integer.valueOf(dateil.get("sc_id").toString()));
            }
        }
        //计算店铺加入购物车的总金额
        Double money = orderMapper.selectGoodsTatolMoney( Integer.valueOf(dateil.get("sc_id").toString()), binding_id);
        //修改购物车总金额
        return orderMapper.updateShoppingcartTotalMoney(Integer.valueOf(dateil.get("sc_id").toString()), money);
    }

    //当前切换店铺的购物车产品
    @Override
    @Transactional
    public  ShoppingHashMap bindingShoppingcart(String token)throws NullPointerException ,Exception {

        Integer binding_id = userMapper.userBDIdByToken(token);
        if (binding_id == null || binding_id.equals(0)){
            throw new NullPointerException("您还未切换店铺呢");
        }
        //查询起送价,店铺id，店铺名称
        ShoppingHashMap hashMap = orderMapper.findStartingPrice(binding_id);
        if (hashMap == null ){
            throw new NullPointerException("店铺信息不存在");
        }

        //通过店铺id判断加入购物车的产品是否被下架了或者是删除了，如果有则直接删除购物车详情里的产品
        orderMapper.deleteShoppingCartDetailByBusiness_id(hashMap.getBusiness_id());

        //通过绑定id查询屏蔽的产品 ,如果有则删除购物车详情里的产品
        orderMapper.deleteShoppingcartDateilShieldingGoods(binding_id);

        //通过绑定id查询购物车id
        Integer shoppingcart_id = orderMapper.selectBindingIsShoppingcart(binding_id);
        hashMap.setSc_id(shoppingcart_id);

        //通过购物车id查询购物车下是否还有产品
        List<Integer> shoppingcartIsGoods = orderMapper.findShoppingcartIsGoods(shoppingcart_id );
        if (shoppingcartIsGoods.size() <= 0){
            //因购物车下没有产品则删除购物车
            orderMapper.deleteShoppingcart(shoppingcart_id);
            hashMap.setSc_id(0);
        }

        //通过购物车ID查询购物车详情产品总数
        Integer count = orderMapper.shoppingcartGoodsDetailGoodsCount(hashMap.getSc_id());
        hashMap.setCount(count);

        //通过购物车id查询购物车下的产品
        List<HashMap> hashMaps = orderMapper.shoppingcartGoodsDetail(hashMap.getSc_id(),binding_id ,hashMap.getBusiness_id());
        hashMap.setGoodsDetail(hashMaps);


        //查询购物车下所有产品的总金额
        Double tatolMoney = orderMapper.selectGoodsTatolMoney(shoppingcart_id, binding_id);
        hashMap.setTatolMoney(new BigDecimal(tatolMoney).setScale(2,BigDecimal.ROUND_CEILING));
        return hashMap;

    }


    //购物车
    @Override
    @Transactional
    public ShoppingHashMap shoppingcart(String token) throws NullPointerException, Exception {

        Integer binding_id = userMapper.userBDIdByToken(token);
        if (binding_id == null || binding_id == 0){
            throw new NullPointerException("您还未切换店铺呢");
        }

        //查询起送价,店铺id，店铺名称
        ShoppingHashMap hashMap1 = orderMapper.findStartingPrice(binding_id);


        //通过绑定id查询购物车id
        Integer shoppingcart_id = orderMapper.selectBindingIsShoppingcart(binding_id);
        hashMap1.setSc_id(shoppingcart_id);


        //通过店铺id判断加入购物车的产品是否被下架了或者是删除了，如果有则直接删除购物车详情里的产品
        orderMapper.deleteShoppingCartDetailByBusiness_id(hashMap1.getBusiness_id());

        //通过绑定id查询屏蔽的产品 ,如果有则删除购物车详情里的产品
        orderMapper.deleteShoppingcartDateilShieldingGoods(binding_id);


        //通过购物车id查询购物车下是否还有产品
        List<Integer> shoppingcartIsGoods = orderMapper.findShoppingcartIsGoods(shoppingcart_id );
        if (shoppingcartIsGoods.size() <= 0){
            //因购物车下没有产品则删除购物车
            orderMapper.deleteShoppingcart(shoppingcart_id);
            hashMap1.setSc_id(0);
        }


        //店铺满减活动
        List<HashMap> activity = userMapper.businessActivity(hashMap1.getBusiness_id());
        hashMap1.setActivity(activity);

        //查询购物车下所有产品的总金额
        Double tatolMoney = orderMapper.selectGoodsTatolMoney(shoppingcart_id, binding_id);
        hashMap1.setTatolMoney(new BigDecimal(tatolMoney).setScale(2,BigDecimal.ROUND_CEILING));

        //通过购物车id查询购物车下的产品
        List<HashMap> hashMaps = orderMapper.shoppingcartDetail(shoppingcart_id,binding_id ,hashMap1.getBusiness_id());
        hashMap1.setGoodsDetail(hashMaps);

        return hashMap1;
    }

    //结算
    @Override
    @Transactional
    public HashMap settlement(String token, String scd_id ,Integer coupon_id ,Integer business_id) throws NullPointerException, Exception {
        Integer binding_id = userMapper.userBDIdByToken(token);
        if (binding_id == null || binding_id.equals(0)){
            throw new NullPointerException("您还未切换店铺呢");
        }

        HashMap hashMap = new HashMap();
        List<HashMap> list = new ArrayList<HashMap>();
        double tatolMoney = 0;
        String[] scdid = scd_id.split(",");
        for (String id: scdid  ) {

            //结算
            HashMap detail = orderMapper.settlement(Integer.valueOf(id), binding_id ,business_id);

            if (detail.get("price").toString().equals("无会员价格")){
                throw new NullPointerException("有产品无会员价格，不能结算");
            }
            tatolMoney += Double.valueOf(detail.get("num").toString()) * Double.valueOf(detail.get("price").toString());

            //查询是否是满赠产品
            HashMap hashMap1 = orderMapper.goodsIsFullGift(Integer.valueOf(detail.get("goods_id").toString()));
            if (hashMap1 != null){
                if (Double.valueOf(detail.get("num").toString()) >= Double.valueOf(hashMap1.get("full").toString()) ){
                    detail.put("activity",Double.valueOf(hashMap1.get("bestow").toString()));//赠送数量
                }else {
                    detail.put("activity",0);//赠送数量
                }
            }else {
                detail.put("activity",0);//赠送数量
            }
            list.add(detail);
        }
        hashMap.put("detail",list);

        HashMap reduce = null;

        //根据coupon_id来判断是使用用户优惠券合适店铺满减活动优惠券
        if (coupon_id== null || coupon_id.equals(0) ){
            //查询店铺满减活动优惠券
             reduce = orderMapper.businessFullReduction(business_id, tatolMoney);
            if (reduce == null){
                hashMap.put("conpon_id",0);
                hashMap.put("name","无");
                hashMap.put("reduce",0.0);
            }else {
                hashMap.put("conpon_id",Integer.valueOf(reduce.get("id").toString()));
                hashMap.put("name","店铺满减活动");
                hashMap.put("reduce",new BigDecimal(Double.valueOf(reduce.get("reduce").toString())).setScale( 2 ,BigDecimal.ROUND_CEILING));
            }

        }else {
            //通过用户优惠券ID查询符合金额的用户优惠券
            reduce = orderMapper.accordMoneyCouponByCoupon_id(coupon_id ,tatolMoney);
            if (reduce == null){
                hashMap.put("conpon_id",0);
                hashMap.put("name","无");
                hashMap.put("reduce",0.0);
            }else {
                hashMap.put("conpon_id",Integer.valueOf(reduce.get("id").toString()));
                hashMap.put("name",reduce.get("name").toString());
                hashMap.put("reduce",new BigDecimal(Double.valueOf(reduce.get("reduce").toString())).setScale( 2 ,BigDecimal.ROUND_CEILING));
            }

        }

        //通过绑定id查询店铺运费
        Double freight = orderMapper.businessFreight(binding_id);
        hashMap.put("freight",freight);
        hashMap.put("money",tatolMoney - Double.valueOf(hashMap.get("reduce").toString()) + freight);
        return hashMap;
    }

    //下订单
    @Override
    @Transactional
    public Integer addOrder(String token, Order order) throws NullPointerException, Exception {
        System.out.println("--------------------下订单---------------------");

        //通过token查询绑定用户信息和店铺id
        HashMap info = orderMapper.bindingidAndBusinessidByToken(token);
        if (info == null){
            throw new NullPointerException("登录失效");
        }

        //判断用户是否有做credit信用额度或limited限制天数
        if (info.get("credit") != null ){ //信用额度

            //查询用户未付款和欠款的总金额
            Integer credit = orderMapper.bindingCredit(Integer.valueOf(info.get("id").toString()));
            if (credit != null){
                throw new NullPointerException("您的未付款和欠款已超过信用额度，不能再下单");
            }

        }else if (info.get("limited") != null){ //限制天数
            //查询用户未付款或欠款最后的下单时间和当前时间的天数
            Integer limited = orderMapper.bindingLimited(Integer.valueOf(info.get("id").toString()));
            if (limited != null){
                throw new NullPointerException("您的未付款和欠款已超过限制天数，不能再下单");
            }
        }

        //判断是什么优惠券
        if (order.getName().equals("无")){
            order.setCoupon_id(null);
        }else if (order.getName().equals("店铺满减活动")){
            order.setActivity(order.getCoupon_id());
            order.setCoupon_id(0);
        }else if (!order.getName().equals("无") && !order.getName().equals("店铺满减活动") && !order.getName().equals("")){

            //通过用户优惠券id修改优惠券使用状态
            userMapper.updateUserCouponState(order.getCoupon_id() , 1);
        }

        order.setOrder_number(OrderCodeFactory.getOnlineOrderCode(Long.valueOf(info.get("id").toString()),5));//订单号
        order.setNo(OrderCodeFactory.getStringRandom(3,3));//标识码
        order.setMaking("客户"); //制单人
        order.setBusiness_id(Integer.valueOf(info.get("business_id").toString())); //店铺id
        order.setBinding_id(Integer.valueOf(info.get("id").toString()));  //绑定用户id
        order.setPart(1); //商城订单

        order.setStaff_id(0);

        order.setCreate_time(new Date());
        orderMapper.addOrder(order);

        //查询当前切换绑定的店铺的购物车id
        Integer scid = orderMapper.selectBindingIsShoppingcart(Integer.valueOf(info.get("id").toString())); //scid 购物车id

        //订单详情
        OrderDetail[] orderDetail = order.getOrderDetail();
        for (OrderDetail detail : orderDetail){
            //订单id
            detail.setOrder_id(order.getId());

            //判断是否有赠送
            if (detail.getActivity()!= 0.0){
                detail.setType(1);
                detail.setOrder_number(detail.getActivity());
                orderMapper.addOrderDateil(detail);

                //添加报损记录
                Give give = new Give(detail.getGoods_id(),Integer.valueOf(info.get("id").toString()),detail.getOrder_number(),new Date(),1,detail.getId());
                orderMapper.addLossGoods(give);
            }

            //根据产品id查询产品进价
            double costPrice = orderMapper.findGoodsCostPrice(detail.getGoods_id());
            //计算每销售一件产品的纯盈利 //用销售价格减去产品进价乘以数量等于纯盈利润
            detail.setProfit((detail.getOrder_price()-costPrice)*detail.getOrder_number());
            detail.setType(0);
            detail.setCost_price(costPrice);
            //添加订单详情
            orderMapper.addOrderDateil(detail);

            //根据购物车id和产品id删除购物车详情
            orderMapper.deteleShoppingCartDetail( scid , detail.getGoods_id());

        }
        //最后判断绑定用户的购物车里是否还有产品

        //通过购物车id查询购物车下是否还有产品
        List<Integer> shoppingcartIsGoods = orderMapper.findShoppingcartIsGoods(scid);
        if (shoppingcartIsGoods.size() <= 0){
            //因购物车下没有产品则删除购物车
            return  orderMapper.deleteShoppingcart(scid );
        }

        return order.getId();
    }

    //推送通知
    @Override
    public Page pushMessage(String token, Integer pageNo) throws NullPointerException, Exception {

        //通过token查询用户注册id
        Integer user_id = userMapper.userIdByToken(token);
        if (user_id == null){
            throw new NullPointerException("注册id不存在");
        }
        int pageNum = 1;
        if(pageNo != null && pageNo != 0){
            pageNum=pageNo;
        }
        Integer tatolCount = orderMapper.pushMessageCount(user_id);
        Page page = new Page(pageNum,tatolCount);
        List<HashMap> hashMaps = orderMapper.pushMessage(user_id, page.getStartIndex(), page.getPageSize());
        page.setRecords(hashMaps);
        return page;
    }

    //订单总数
    @Override
    public Integer orderListCount(String token, String status, Integer state) throws NullPointerException, Exception {
        //获取用户绑定ID
        Integer binding_id = userMapper.userBDIdByToken(token);
        if (binding_id== null){
            throw new NullPointerException("登录失效或未切换店铺");
        }

        return orderMapper.orderListCount(binding_id ,status ,state);
    }

    //订单总数(推送)
    @Override
    public Integer orderCount(String token, String status, Integer state) throws NullPointerException, Exception {
        //获取用户绑定ID
        Integer binding_id = userMapper.userBDIdByToken(token);
        if (binding_id== null){
            throw new NullPointerException("登录失效或未切换店铺");
        }
        return orderMapper.orderCount(binding_id,status,state);
    }

    //通知总数
    @Override
    public Integer pushCount(String token) throws NullPointerException, Exception {
        //获取用户注册ID
        Integer user_id = userMapper.userIdByToken(token);
        if (user_id== null){
            throw new NullPointerException("登录失效");
        }
        return orderMapper.pushCount(user_id);
    }



    //查询订单和通知总数
    @Override
    public Integer totalCount(String token) throws NullPointerException, Exception {
        Integer count=null;

        //获取用户绑定ID
        Integer binding_id = userMapper.userBDIdByToken(token);
        Integer ordercount = orderMapper.orderCount(binding_id, null, null);

        //获取用户注册ID
        Integer user_id = userMapper.userIdByToken(token);
        Integer pushcount = orderMapper.pushCount(user_id);

        return ordercount + pushcount;
    }


    //修改订单或通知为已读
    @Override
    @Transactional
    public Integer updateCount(String token, String id, Integer type) throws Exception {
        Integer count = null;

        if (type == 1){ //订单的
            String[] order_id = id.split(",");
            for ( String oid: order_id ) {
                count =orderMapper.updateOrderPush_state(Integer.valueOf(oid));
            }


        }else if (type == 2){ //通知的
            String[] push_id = id.split(",");
            for ( String pid: push_id ) {
                count = orderMapper.updatePushPush_state(Integer.valueOf(pid));
            }
        }
        return count;
    }

    //删除购物车产品
    @Override
    @Transactional
    public Integer deteleShoppingcartDateil( Integer sc_id ,String dateil_id) throws Exception {
        Integer count = null;

        String[] did = dateil_id.split(",");
        for (String id: did ) {
            //删除购物车详情id
            count = orderMapper.deleteShoppingcartDetail(Integer.valueOf(id));
        }
        //通过购物车id查询购物车下是否还有产品
        List<Integer> shoppingcartIsGoods = orderMapper.findShoppingcartIsGoods(sc_id );
        if (shoppingcartIsGoods.size() <= 0){
            //因购物车下没有产品则删除购物车
            count  = orderMapper.deleteShoppingcart(sc_id);
        }
        return count;
    }

    //绑定用户默认地址
    @Override
    public HashMap bindingAddress(String token ) throws Exception {
        Integer binding_id = userMapper.userBDIdByToken(token);
        return orderMapper.bindingAddress(binding_id );
    }

    //绑定用户地址集合
    @Override
    public List<HashMap> bindingAddressList(String token) throws Exception {
        Integer binding_id = userMapper.userBDIdByToken(token);
        return orderMapper.bindingAddressList(binding_id);
    }

    //添加用户收货地址
    @Override
    @Transactional
    public Integer addBindingAddress(String token, Address address) {
        Integer binding_id = userMapper.userBDIdByToken(token);
        address.setBinding_id(binding_id);
        //判断添加的地址是否为默认地址
        if (address.getState() == 1){ // 等于1为默认地址

            //通过绑定ID修改绑定用户地址的取消以前的默认
            orderMapper.updateBindingAddressState(binding_id);
            //查询用户地址里code最大的一条
            Integer code = orderMapper.selectBindingAddressLastCode(binding_id);
            if (code != null){
                address.setCode(code + 1);
            }else {
                address.setCode( 1);
            }

        }
        return orderMapper.addBindingAddress(address);
    }

    //通过地址ID查询用户收货地址信息
    @Override
    public HashMap bindingAddressById(Integer address_id) {
        return orderMapper.bindingAddressById(address_id);
    }

    //修改用户收货地址
    @Override
    @Transactional
    public Integer updateBindingAddress(String token , Address address) throws NullPointerException, Exception {
        //获取地址state默认地址
        Integer state = orderMapper.bindingAddressState(address.getId());
        if (state == null){
            throw new NullPointerException("用户地址不存在");
        }
        if (state != address.getState() && address.getState() == 1){
            Integer binding_id = userMapper.userBDIdByToken(token);
            //通过绑定ID修改绑定用户地址的取消以前的默认
            orderMapper.updateBindingAddressState(binding_id);
            //查询用户地址里code最大的一条
            Integer code = orderMapper.selectBindingAddressLastCode(binding_id);
            address.setCode(code + 1);
        }
        return orderMapper.updateBindingAddress(address);
    }

    //删除用户收货地址
    @Override
    @Transactional
    public Integer deleteBindingAddress(Integer address_id) throws Exception {
        return orderMapper.deleteBindingAddress(address_id);
    }

    @Override
    public String businessToken(Integer business_id) {
        return userMapper.businessToken(business_id);
    }
}
