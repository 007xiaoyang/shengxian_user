package com.shengxian.controller;


import com.alibaba.fastjson.JSONObject;
import com.shengxian.common.Message;
import com.shengxian.common.util.Global;
import com.shengxian.common.util.IntegerUtils;
import com.shengxian.common.util.Page;
import com.shengxian.entity.Complaints;
import com.shengxian.entity.SalesService;
import com.shengxian.service.MyService;
import io.swagger.models.auth.In;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Description: 我的模块
 *
 * @Author: yang
 * @Date: 2019-01-12
 * @Version: 1.0
 */
@RestController
@RequestMapping("/my")
public class MyController {

    private static Logger log = Logger.getLogger(MyController.class);

    @Resource
    private MyService myService;

    /**
     * 小程序未付款欠款消费
     * @param token
     * @return
     */
    @RequestMapping("/getUnpaidAndArrearsAndConsumed.do")
    public Message getUnpaidAndArrearsAndConsumed(String token){
        Message message = Message.non();
        try {
            HashMap hashMap = myService.getUnpaidAndArrearsAndConsumed(token);
            return message.code(Message.codeSuccessed).data(hashMap).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("my/myNUAC"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 公告未付款欠款消费
     * @param token
     * @return
     */
    @RequestMapping("/myNUAC.do")
    public Message myNUAC(String token){
        Message message = Message.non();
        try {
            HashMap hashMap = myService.myNUAC(token);
            return message.code(Message.codeSuccessed).data(hashMap).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("my/myNUAC"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 店铺详情
     * @param token
     * @return
     */
    @RequestMapping("/businessDateil.do")
    public Message businessDateil(String token){
        Message message = Message.non();
        try {
            HashMap hashMap = myService.businessDateil(token);
            return message.code(Message.codeSuccessed).data(hashMap).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("my/businessDateil"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 通过店铺id查询店铺详情信息
     * @param businessId
     * @return
     */
    @RequestMapping("/getBusinessInfoByBid.do")
    public Message getBusinessInfoByBid(Integer businessId){
        Message message = Message.non();
        try {
            HashMap hashMap = myService.getBusinessInfoByBid(businessId);
            return message.code(Message.codeSuccessed).data(hashMap).message("获取成功");
        }catch (Exception e){
            log.error("user/userBindingBusiness"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 我要投诉
     * @param token
     * @param json
     * @return
     */
    @RequestMapping("/IWantComplaints.do")
    public Message IWantComplaints(String token, String json){
        Message message = Message.non();
        try {
            Complaints complaints = JSONObject.parseObject(json, Complaints.class);
            Integer count = myService.addIWantComplaints(token,complaints);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("举报失败");
            }
            return message.code(Message.codeSuccessed).message("举报成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("my/IWantComplaints"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 投诉记录
     * @param token
     * @return
     */
    @RequestMapping("/complaintsNotes.do")
    @ResponseBody
    public Message complaintsNotes(String token,Integer type){
        Message message = Message.non();
        try {
            List<HashMap> hashMaps = myService.complaintsNotes(token , type);
            return message.code(Message.codeSuccessed).data(hashMaps).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("my/complaintsNotes"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 修改投诉状态
     * @param token
     * @param id 投诉id
     * @return
     */
    @RequestMapping("/updateComlaintsState.do")
    public Message updateComlaintsState(String token,Integer id){
        Message message = Message.non();
        if (IntegerUtils.isEmpty(id)){
            return message.code(Message.codeFailured).message("投诉id不存在");
        }
        try {
            Integer count = myService.updateComlaintsState(id);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("重新投诉失败");
            }
            return message.code(Message.codeSuccessed).message("投诉成功");
        }catch (Exception e){
            log.error("my/updateComlaintsState"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }



    /**
     * 我的订单
     * @param token
     * @param status 1商城待接单，2待送货(接受)，3派送中，4确认到货，5拒绝接单，6取消订单
     * @param state 0未付款，1申请欠款审核，2欠款，3已完成，4拒绝欠款
     * @param type 全部，1一天，2一周，3半个夜 ，4一个月
     * @return
     */
    @RequestMapping("/myOrder.do")
    public Message myOrder(String token ,Integer pageNo,String status ,Integer state,Integer type ,String startTime ,String endTime ,Integer mold){
        Message message = Message.non();
        try {

            Page page = myService.myOrder(token,pageNo, status, state,type ,startTime ,endTime ,mold);
            return message.code(Message.codeSuccessed).data(page).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("my/myOrder"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 删除订单
     * @param token
     * @param order_id
     * @return
     */
    @RequestMapping("/deteleOrder.do")
    public Message deteleOrder(String token ,Integer order_id){
        Message message = Message.non();
        try {
            Integer count = myService.deteleOrder(order_id);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("删除订单失败");
            }
            return message.code(Message.codeSuccessed).message("删除订单成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("my/deteleOrder"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 通过订单id查询订单明细
     * @param token
     * @param id
     * @return
     */
    @RequestMapping("/orderDateilById.do")
    public Message orderDateilById(String token ,Integer id){
        Message message = Message.non();
        if (IntegerUtils.isEmpty(id)){
            return message.code(Message.codeFailured).message("订单id不能为空");
        }
        try {
            HashMap hashMap = myService.orderDateilListById(id);
            return message.code(Message.codeSuccessed).data(hashMap).message("获取成功");
        }catch (Exception e){
            log.error("my/orderDateilById"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }




    /**
     * 分享有奖
     * @param token
     * @return
     */
    @RequestMapping("/sharingAwards.do")
    public Message sharingAwards(String token){
        Message message = Message.non();
        try {
            HashMap hashMap = myService.sharingAwards(token);
            return message.code(Message.codeSuccessed).data(hashMap).message("获取成功");
        }catch (Exception e){
            log.error("my/sharingAwards"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 我的优惠券
     * @param token
     * @return
     */
    @RequestMapping("/myCoupon.do")
    public Message myCoupon(String token ,Integer business_id){
        Message message = Message.non();
        try {
            List<HashMap> hashMaps = myService.myCoupon(token , business_id);
            return message.code(Message.codeSuccessed).data(hashMaps).message("获取成功");
        }catch (Exception e){
            log.error("my/myCoupon"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 我的优惠券（分页）小程序
     * @param token
     * @return
     */
    @RequestMapping("/couponList.do")
    public Message couponList(String token , Integer pageNo ){
        Message message = Message.non();
        try {
            Page page = myService.couponList(token ,pageNo);
            return message.code(Message.codeSuccessed).data(page).message("获取成功");
        }catch (Exception e){
            log.error("my/myCoupon"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 积分商城列表
     * @param token
     * @return
     */
    @RequestMapping("/integralMall.do")
    public Message integralMall(String token,Integer pageNo){
        Message message = Message.non();
        try {
            Page page = myService.integralMall(token, pageNo);
            return message.code(Message.codeSuccessed).data(page).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("my/integralMall"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 通过积分产品id查询积分信息
     * @param token
     * @param goods_id
     * @return
     */
    @RequestMapping("/integraGoodsById.do")
    public Message integraGoodsById(String token,Integer goods_id){
        Message message = Message.non();
        try {
            HashMap hashMap = myService.integraGoodsById(token, goods_id);
            return message.code(Message.codeSuccessed).data(hashMap).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("my/integraGoodsById"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 积分明细
     * @param token
     * @param startTime
     * @return
     */
    @RequestMapping("/integraDetail.do")
    public Message integraDetail(String token,Integer pageNo, String startTime){
        Message message = Message.non();
        try {
            Page page = myService.integraDetail(token,pageNo, startTime);
            return message.code(Message.codeSuccessed).data(page).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("my/integraDetail"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     *通过明细id查询积分明细
     * @param token
     * @param detail_id
     * @return
     */
    @RequestMapping("/integraDetailById.do")
    public Message integraDetailById(String token,Integer detail_id){
        Message message = Message.non();
        try {
            HashMap hashMap = myService.integraDetailById( detail_id);
            return message.code(Message.codeSuccessed).data(hashMap).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("my/integraDetailById"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 用户手机号
     * @param token
     * @return
     */
    @RequestMapping("/userPhone.do")
    public Message userPhone(String token){
        Message message = Message.non();
        try {
            String phone = myService.userPhone(token);
            if (phone == null || phone.equals("")) {
                return message.code(Message.codeFailured).message("登录失效");
            }
            return message.code(Message.codeSuccessed).data(phone).message("获取成功");
        }catch (Exception e){
            log.error("my/userPhone"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 修改密码
     * @param token
     * @param password
     * @return
     */
    @RequestMapping("/updateUserPWD.do")
    public Message updateUserPWD(String token,String password ,String password2){
        Message message = Message.non();
        if (password == null || password.equals("")){
            return message.code(Message.codeFailured).message("旧密码不能为空");
        }
        try {
            Integer count = myService.updateUserPWD(token,password ,password2);
            if (count == null || count ==0){
                return message.code(Message.codeFailured).message("修改失败");
            }
            return message.code(Message.codeSuccessed).message("修改成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("my/updateUserPWD"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 商城推荐产品
     * @param token
     * @param pageNo
     * @return
     */
    @RequestMapping("/businessRecommendGoodsList.do")
    public Message businessRecommendGoodsList(String token ,Integer pageNo){
        Message message = Message.non();
        try {
            Page page = myService.businessRecommendGoodsList(token,pageNo);
            return message.code(Message.codeSuccessed).data(page).message("获取成功");
        }catch (Exception e){
            log.error("my/businessRecommendGoodsList"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 未付款或欠款
     * @param token
     * @param pageNo
     * @param type 0 未付款 ，1欠款
     * @return
     */
    @RequestMapping("/unpaidOrArrearsList.do")
    public Message unpaidOrArrearsList(String token,Integer pageNo ,Integer type ){
        Message message = Message.non();
        try {
            Page page = myService.unpaidOrArrearsList(token, pageNo ,type);
            return message.code(Message.codeSuccessed).data(page).message("获取成功");
        }catch (Exception e){
            log.error("my/unpaidOrArrearsList"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 消费
     * @param token
     * @param pageNo
     * @param type 1日，2月，3年
     * @return
     */
    @RequestMapping("/consumeList.do")
    public Message consumeList(String token ,Integer pageNo ,Integer type){
        Message message = Message.non();
        try {
            Page page = myService.consumeList(token ,pageNo ,type);
            return message.code(Message.codeSuccessed).data(page).message("获取成功");
        }catch (Exception e){
            log.error("my/consumeList"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 积分兑换
     * @param token
     * @param goods_id 积分产品ID
     * @param integra 兑换的积分
     * @return
     */
    @RequestMapping("/addExchangeIntegraGoods.do")
    public Message addExchangeIntegraGoods(String token ,Integer goods_id,Double integra){
        Message message = Message.non();
        try {
            Integer count = myService.addExchangeIntegraGoods(token,goods_id,integra);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("兑换失败");
            }
            return message.code(Message.codeSuccessed).message("兑换成功");
        }catch (NullPointerException e){
            return  message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("my/addExchangeIntegraGoods"+e.getMessage());
            return  message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 积分兑换订单列表
     * @param token
     * @return
     */
    @RequestMapping("/exchangeIntegraGoodsList.do")
    public Message exchangeIntegraGoodsList(String token ,Integer pageNo ,Integer state){
        Message message = Message.non();
        try {
            Page page = myService.exchangeIntegraGoodsList(token, pageNo ,state);
            return message.code(Message.codeSuccessed).data(page).message("获取成功");
        }catch (Exception e){
            log.error("my/exchangeIntegraGoodsList"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }




    /**
     * 我要售货服务
     * @param token
     * @param json
     * @return
     */
    @RequestMapping("/addSalesService.do")
    public Message addSalesService(String token, String json){
        Message message = Message.non();
        try {
            SalesService salesService = JSONObject.parseObject(json, SalesService.class);
            Integer count = myService.addSalesService(token,salesService);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("售货失败");
            }
            return message.code(Message.codeSuccessed).message("售货成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("my/addSalesService"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 售货服务记录
     * @param token
     * @return
     */
        @RequestMapping("/slesService.do")
    @ResponseBody
    public Message slesService(String token,Integer type){
        Message message = Message.non();
        try {
            List<HashMap> hashMaps = myService.slesService(token , type);
            return message.code(Message.codeSuccessed).data(hashMaps).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("my/slesService"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 修改售货服务状态
     * @param token
     * @param id 售货id
     * @return
     */
    @RequestMapping("/updateslesServiceState.do")
    public Message updateslesServiceState(String token,Integer id){
        Message message = Message.non();
        if (IntegerUtils.isEmpty(id)){
            return message.code(Message.codeFailured).message("投诉id不存在");
        }
        try {
            Integer count = myService.updateslesServiceState(id);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("重新投诉失败");
            }
            return message.code(Message.codeSuccessed).message("投诉成功");
        }catch (Exception e){
            log.error("my/updateslesServiceState"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 注销号码
     * @param token
     * @return
     */
    @RequestMapping("/deleteUser.do")
    public Message deleteUser(String token ,String password){
        Message message = Message.non();
        try {
            Integer count = myService.deleteUser(token ,password);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("注销失败");
            }
            return message.code(Message.codeSuccessed).message("注销成功");
        }catch (Exception e){
            log.error("my/deleteUser"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }

    }





}
