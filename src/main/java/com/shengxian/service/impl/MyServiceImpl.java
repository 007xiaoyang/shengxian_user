package com.shengxian.service.impl;

import com.shengxian.common.util.*;
import com.shengxian.entity.*;
import com.shengxian.mapper.MyMapper;
import com.shengxian.mapper.UserMapper;
import com.shengxian.service.MyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Description:
 *
 * @Author: yang
 * @Date: 2019-01-12
 * @Version: 1.0
 */
@Service
public class MyServiceImpl implements MyService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MyMapper myMapper;


    //店铺详情
    @Override
    public HashMap businessDateil(String token) {
        Integer binding_id = userMapper.userBDIdByToken(token);
        HashMap hashMap = myMapper.businessDateil(binding_id);
        if (hashMap != null){
            List<HashMap> hashMaps = myMapper.businessLicense(Integer.valueOf(hashMap.get("business_id").toString()));
            hashMap.put("license" ,hashMaps);
        }
        return hashMap;
    }

    @Override
    public HashMap getBusinessInfoByBid(Integer bindingId) {
        HashMap hashMap = myMapper.businessDateil(bindingId);
        if (hashMap != null){
            List<HashMap> hashMaps = myMapper.businessLicense(Integer.valueOf(hashMap.get("business_id").toString()));
            hashMap.put("license" ,hashMaps);
        }
        return hashMap;
    }

    //我要投诉
    @Override
    @Transactional
    public Integer addIWantComplaints(String token, Complaints complaints)throws NullPointerException,Exception {

        Integer user_id = userMapper.userIdByToken(token);
        if (user_id == null ){
            throw new NullPointerException("登录失效");
        }
        complaints.setUser_id(user_id);
        complaints.setCreate_time(new Date());
        Integer count = myMapper.addIWantComplaints(complaints);
        if (count != null){
            //添加投诉图片
            ComplaintsImg[] complaintsImgs = complaints.getComplaintsImgs();
            for (ComplaintsImg complaintsImg: complaintsImgs   ) {
                myMapper.addIWantComplaintsImg(complaints.getId(),complaintsImg.getImg());
            }
        }
        return count;
    }

    //投诉记录
    @Override
    public List<HashMap> complaintsNotes(String token ,Integer type)throws Exception {
        Integer business_id = userMapper.userBDBusiness_id(token);
        if (business_id == null || business_id.equals(0)){
            throw new NullPointerException("未切换店铺");
        }
        if (type ==1){ //未处理和处理中的投诉记录
            List<HashMap> hashMaps = myMapper.NotCompleteComplaintsNotes(business_id);
            for (HashMap hash: hashMaps  ) {
                List<HashMap> imgs = myMapper.complaintsNotesImg(Integer.valueOf(hash.get("id").toString()));
                hash.put("img" ,imgs);
            }
            return hashMaps;
        }else { //完成的投诉处理，只显示完成8天
            List<HashMap> hashMaps = myMapper.completeComplaintsNotes(business_id);
            for (HashMap hash: hashMaps  ) {
                List<HashMap> imgs = myMapper.complaintsNotesImg(Integer.valueOf(hash.get("id").toString()));
                hash.put("img" ,imgs);
            }
            return hashMaps;
        }
    }

    //修改投诉状态
    @Override
    @Transactional
    public Integer updateComlaintsState(Integer id) throws Exception {
        return myMapper.updateComlaintsState(id,new Date());
    }

    @Override
    public HashMap getUnpaidAndArrearsAndConsumed(String token) throws Exception {
        Integer bindingId = userMapper.userBDIdByToken(token);
        return myMapper.getUnpaidAndArrearsAndConsumed(bindingId);
    }

    //公告未付款欠款消费
    @Override
    public HashMap myNUAC(String token) throws NullPointerException, Exception {
        Integer binding_id = userMapper.userBDIdByToken(token);
        if (binding_id == null){
            throw new NullPointerException("您还未切换店铺呢");
        }
        return myMapper.myNUAC(binding_id);
    }

    //我的订单
    @Override
    public Page myOrder(String token, Integer pageNo, String status, Integer state, Integer type , String startTime , String endTime , Integer mold)throws NullPointerException,Exception {
        int pageNum = 1;
        if (pageNo != null && pageNo != 0){
            pageNum = pageNo;
        }
        Integer binding_id = userMapper.userBDIdByToken(token);
        if (binding_id == null || binding_id.equals(0)){
            throw new NullPointerException("您还未切换店铺呢");
        }
        Integer tatolCount = myMapper.myOrderCount(new Parameter(binding_id,status,state,type ,startTime ,endTime ,mold));
        Page page = new Page(pageNum,tatolCount);
        List<HashMap> hashMaps = myMapper.myOrder(new Parameter(binding_id,status,state,type ,startTime ,endTime ,mold ,page.getStartIndex(),page.getPageSize()));
        page.setRecords(hashMaps);
        return page;
    }

    //删除订单
    @Override
    @Transactional
    public Integer deteleOrder(Integer order_id) throws NullPointerException{

        //通过订单id查询订单是否有使用用户优惠券
        HashMap order  = myMapper.selectOrderCoupon(order_id);
        if (order == null){
            throw new NullPointerException("订单不存在");
        }
        if (order.get("status").toString().equals("2") || order.get("status").toString().equals("3")){
            throw new NullPointerException("订单已经接了，如需不要此订单，则联系业务员");
        }

        if (order.get("coupon_id") != null && Integer.valueOf(order.get("coupon_id").toString()) != 0){
            userMapper.updateUserCouponState(Integer.valueOf(order.get("coupon_id").toString()) , 0 );
        }

        return  myMapper.deteleOrder(order_id);
    }

    //通过订单id查询订单明细
    @Override
    public HashMap orderDateilListById(Integer id) throws Exception {

        //通过订单id查询订单运费和差价
        HashMap hashMap = myMapper.orderFreightAndDifferential(id);

        //通过订单id查询订单明细
        List<HashMap> dateil = myMapper.orderDateilListById(id);
        hashMap.put("dateil" ,dateil);
        return hashMap;
    }

    //分享有奖
    @Override
    public HashMap sharingAwards(String token) throws Exception {
        return myMapper.sharingAwards(token);
    }

    //我的优惠券
    @Override
    public  List<HashMap>  myCoupon(String token ,Integer business_id ) {
        Integer binding_id = userMapper.userBDIdByToken(token);
        if (binding_id == null || binding_id.equals(0)){
            throw new NullPointerException("您还未切换店铺呢");
        }
        return  myMapper.myCoupon(binding_id ,business_id);
    }

    @Override
    public Page couponList(String token, Integer pageNo) {
        Integer bindingId = userMapper.userBDIdByToken(token);
        Integer businessId = userMapper.busienss_id(bindingId);
        int pageNum = 1;
        if (pageNo != null && pageNo !=0){
            pageNum = pageNo;
        }
        Integer tatolCount = myMapper.couponListCount(bindingId , businessId);
        Page page = new Page(pageNum,tatolCount);
        List<HashMap> hashMaps = myMapper.couponList(bindingId , businessId , page.getStartIndex() , page.getPageSize());
        page.setRecords(hashMaps);
        return page;
    }

    //积分商城
    @Override
    public Page integralMall(String token, Integer pageNo) throws NullPointerException, Exception {
        Integer binding_id = userMapper.userBDIdByToken(token);
        if (binding_id == null || binding_id.equals(0)){
            throw new NullPointerException("您还未切换店铺呢");
        }
        int pageNum = 1;
        if (pageNo != null && pageNo !=0){
            pageNum = pageNo;
        }
        //通过绑定id查询用户剩余积分
        Double integra = myMapper.bindingIntegra(binding_id);
        Integer tatolCount = myMapper.integralMallCount(binding_id);
        Page page = new Page(pageNum,tatolCount);
        List<HashMap> hashMaps = myMapper.integralMall(binding_id,page.getStartIndex() ,page.getPageSize());
        page.setRecords(hashMaps);
        page.setPrice(new BigDecimal(integra));
        return page;
    }

    //通过积分产品id查询产品信息
    @Override
    public HashMap integraGoodsById( String token, Integer goods_id)throws NullPointerException ,Exception {
        Integer binding_id = userMapper.userBDIdByToken(token);
        if (binding_id == null || binding_id.equals(0)){
            throw new NullPointerException("您还未切换店铺呢");
        }
        //通过绑定id查询用户剩余积分
        Double integra = myMapper.bindingIntegra(binding_id);
        HashMap hashMap = myMapper.integraGoodsById(goods_id);
        if (hashMap ==  null){
            throw new NullPointerException("积分产品不存在");
        }
        hashMap.put("integra" ,integra);
        return hashMap;
    }

    //查询用户积分明细
    @Override
    public Page integraDetail(String token, Integer pageNo, String startTime) throws NullPointerException, Exception {
        HashMap hashMap = new HashMap();

        Integer binding_id = userMapper.userBDIdByToken(token);
        if (binding_id == null ||binding_id.equals(0)){
            throw  new NullPointerException("您还未切换店铺呢");
        }
        //通过绑定id查询积分id
        Integer integra_id = myMapper.selectIntegraId(binding_id);
        if (integra_id == null){
            throw new NullPointerException("用户积分id不存在");
        }
        int pageNum=1;
        if (pageNo != null && pageNo != 0){
            pageNum = pageNo;
        }
        //用户积分明细总数
        Integer tatolCount = myMapper.integraDetailCount(integra_id ,startTime );
        Page page = new Page(pageNum,tatolCount);
        //用户积分明细总数
        List<HashMap> hashMaps = myMapper.integraDetail(integra_id,startTime  ,page.getStartIndex(),page.getPageSize());
        //用户积分收入income
        Double income = myMapper.integraDetailIncome(integra_id ,startTime);
        //用户积分支出expenditure
        Double expenditure = myMapper.integraDetailExpenditure(integra_id ,startTime);
        hashMap.put("income" , new BigDecimal(income).setScale(2 ,BigDecimal.ROUND_CEILING));
        hashMap.put("expenditure" , new BigDecimal(expenditure).setScale(2 ,BigDecimal.ROUND_CEILING));
        page.setHashMap(hashMap);
        page.setRecords(hashMaps);
        return page;
    }

    //通过明细id查询积分明细
    @Override
    public HashMap integraDetailById(Integer detail_id) {
        return myMapper.integraDetailById(detail_id);
    }

    //用户手机号
    @Override
    public String userPhone(String token) {
        return myMapper.userPhone(token);
    }

    //修改密码
    @Override
    @Transactional
    public Integer updateUserPWD(String token, String password  ,String password2) throws NullPointerException, Exception {

        //旧密码
        String pwd = PasswordMD5.EncoderByMd5(password + Global.passwordKey);

        //通过token查询数据库的密码和注册id
        HashMap userIdAndPwd = myMapper.findUserIdAndPwd(token);

        if (!pwd.equals(userIdAndPwd.get("password").toString())){
            throw new NullPointerException("旧密码不匹配哟");
        }

        //新密码
        String pwd2 = PasswordMD5.EncoderByMd5(password2 + Global.passwordKey);

        return myMapper.updateUserPWD(Integer.valueOf(userIdAndPwd.get("id").toString()),pwd2);
    }

    //商城推荐产品
    @Override
    public Page businessRecommendGoodsList(String token, Integer pageNo) {

        //获取绑定ID
        Integer binding_id = userMapper.userBDIdByToken(token);
        int pageNum = 1;
        if(IntegerUtils.isNotEmpty(pageNo)){
            pageNum = pageNo;
        }

        //通过绑定id查询店铺id和会员方案
        Scheme scheme = userMapper.bidAndSchemeid(binding_id);
        if(scheme == null){
            scheme = new Scheme();
            scheme.setBusinessId(0);
            scheme.setSchemeId(0);
        }
        Integer totalCount = myMapper.businessRecommendGoodsListCount(binding_id ,scheme.getBusinessId() , scheme.getSchemeId());
        Page page = new Page(pageNum ,totalCount);
        List<HashMap> hashMaps = myMapper.businessRecommendGoodsList(binding_id ,scheme.getBusinessId() , scheme.getSchemeId() ,page.getStartIndex() ,page.getPageSize());
        page.setRecords(hashMaps);
        return page;
    }


    //未付款或欠款
    @Override
    public Page unpaidOrArrearsList(String token, Integer pageNo, Integer type) throws Exception {
        int pageNun = 1;
        if(pageNo != null && pageNo != 0){
            pageNun = pageNo ;
        }
        Integer totalCount = null;
        List<HashMap> hashMaps = null;
        Page page = null;
        //客户绑定ID
        Integer binding_id = userMapper.userBDIdByToken(token);
        if (type == 0 ){
            totalCount = myMapper.unpaidListCount(binding_id);
            page = new Page(pageNun ,totalCount);
            hashMaps = myMapper.unpaidList(binding_id ,page.getStartIndex() ,page.getPageSize());

        }else if (type == 1){
            totalCount = myMapper.arrearsListCount(binding_id);
            page = new Page(pageNun ,totalCount);
            hashMaps = myMapper.arrearsList(binding_id ,page.getStartIndex() ,page.getPageSize());
        }
        page.setRecords(hashMaps);
        return page;
    }

    //消费
    @Override
    public Page consumeList(String token, Integer pageNo, Integer type) throws Exception {
        int pageNun = 1;
        if(pageNo != null && pageNo != 0){
            pageNun = pageNo ;
        }
        //客户绑定ID
        Integer binding_id = userMapper.userBDIdByToken(token);

        Integer totalCount = myMapper.consumeListCount(binding_id ,type);
        Page page = new Page( pageNun ,totalCount);
        List<HashMap> hashMaps = myMapper.consumeList(binding_id ,type ,page.getStartIndex() ,page.getPageSize());
        page.setRecords(hashMaps);
        return page;
    }

    //积分兑换
    @Override
    @Transactional
    public Integer addExchangeIntegraGoods(String token, Integer goods_id, Double integra) throws NullPointerException, Exception {

        //通过产品id查询是否下架了
        Integer status = myMapper.selectIntegraGoodsIdStatus(goods_id);
        if (status == null){
            throw new NullPointerException("产品已经下架了");
        }

        //通过积分产品id查询产品剩余库存
        Double num = myMapper.selectIntegraGoodNum(goods_id);
        if (num  == null || num <= 0.0 ){
            throw new NullPointerException("抱歉 ，您兑换的产品库存已经没有了");
        }

        //客户绑定ID
        Integer binding_id = userMapper.userBDIdByToken(token);
        Integer business_id = userMapper.busienss_id(binding_id);
        String number = OrderCodeFactory.getOnlineOrderCode((long) 0, 5);

        Integra integra1 = new Integra(number, business_id, binding_id, goods_id, integra, 1, new Date());

        //通过绑定用户id查询积分id
        Integer integraId = myMapper.selectIntegraId(binding_id);
        if (integraId == null ){
            throw new NullPointerException("用户注册失败，丢失用户积分id");
        }

        //兑换产品订单
        myMapper.addExchangeIntegraGoods(integra1);

        //修改积分产品库存
        myMapper.updateIntegraGoodNum(goods_id);



        double v = num - 1;
        if (v <= 0.0 ){
            //库存已经没有了 ，下架产品
            myMapper.updateIntegraGoodsStatus(goods_id);
        }

        //通过用户积分id查询修改用户积分
        myMapper.updateUserIntegra(integraId , integra);

        //添加用户积分明细
        return myMapper.addUserIntegraDetail(integraId ,integra ,3 ,new Date() ,number);
    }

    //积分兑换订单列表
    @Override
    public Page exchangeIntegraGoodsList(String token, Integer pageNo , Integer state) throws Exception {

        int pageNum = 1;
        if (pageNo != null && pageNo != 0){
            pageNum = pageNo ;
        }
        //通过token查询用户绑定id
        Integer binding_id = userMapper.userBDIdByToken(token);

        Integer totalCount = myMapper.exchangeIntegraGoodsListCount(binding_id ,state);
        Page page = new Page(pageNum ,totalCount);
        List<HashMap>  hashMaps = myMapper.exchangeIntegraGoodsList(binding_id ,state , page.getStartIndex() ,page.getPageSize());
        page.setRecords(hashMaps);
        return page;
    }

    //我要售货服务
    @Override
    @Transactional
    public Integer addSalesService(String token, SalesService salesService) throws NullPointerException, Exception {
        Integer binding_id = userMapper.userBDIdByToken(token);
        Integer business_id = userMapper.busienss_id(binding_id);
        if (binding_id == null ){
            throw new NullPointerException("登录失效");
        }
        salesService.setBinding_id(binding_id);
        salesService.setBusiness_id(business_id);
        salesService.setCreate_time(new Date());
        Integer count = myMapper.addSalesService(salesService);
        if (count != null){
            //添加售货服务图片
            SalesServiceImg[] salesServiceImgs = salesService.getSalesServiceImgs();
            for (SalesServiceImg salesServiceImg: salesServiceImgs   ) {
                myMapper.addSalesServiceImg(salesService.getId(),salesServiceImg.getImg());
            }
        }
        return count;
    }

    //售货服务记录
    @Override
    public List<HashMap> slesService(String token, Integer type) throws Exception {
        Integer business_id = userMapper.userBDBusiness_id(token);
        if (business_id == null || business_id.equals(0)){
            throw new NullPointerException("未切换店铺");
        }
        if (type ==1){ //未处理和处理中的售货服务记录
            List<HashMap> hashMaps = myMapper.NotslesService(business_id);
            for (HashMap hash: hashMaps  ) {
                List<HashMap> imgs = myMapper.slesServiceImg(Integer.valueOf(hash.get("id").toString()));
                hash.put("img" ,imgs);
            }
            return hashMaps;
        }else { //完成的售货服务记录，只显示完成8天
            List<HashMap> hashMaps = myMapper.slesService(business_id);
            for (HashMap hash: hashMaps  ) {
                List<HashMap> imgs = myMapper.slesServiceImg(Integer.valueOf(hash.get("id").toString()));
                hash.put("img" ,imgs);
            }
            return hashMaps;
        }
    }

    //修改投诉状态
    @Override
    @Transactional
    public Integer updateslesServiceState(Integer id) throws Exception {
        return myMapper.updateslesServiceState(id,new Date());
    }

    //注销号码
    @Override
    @Transactional
    public Integer deleteUser(String token, String password) throws NullPointerException, Exception {
        Integer user_id = userMapper.userIdByToken(token);

        //通过注册id查询登录密码
        String pwd = myMapper.selectUserPassword(user_id);

        String PWD = PasswordMD5.EncoderByMd5(password + Global.passwordKey);
        //判断密码是否匹配
        if (!PWD.equals(pwd)){
            throw new NullPointerException("密码不匹配");
        }

        //注销号码同时把该号码绑定所有的店铺也取消掉
        myMapper.updateBindingUser(user_id);

        return myMapper.deleteUser(user_id);
    }
}
