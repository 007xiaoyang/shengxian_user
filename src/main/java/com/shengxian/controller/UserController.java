package com.shengxian.controller;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.shengxian.common.Message;
import com.shengxian.common.Sendsms;
import com.shengxian.common.WebSocketUtil;
import com.shengxian.common.util.Global;
import com.shengxian.common.util.IntegerUtils;
import com.shengxian.common.util.Page;
import com.shengxian.entity.Business;
import com.shengxian.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Description:
 *
 * @Author: yang
 * @Date: 2018-12-30
 * @Version: 1.0
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private static Logger log = Logger.getLogger(UserController.class);

    /**
     * 查询APP版本号
     * @return
     */
    @RequestMapping("/version.do")
    public Message version(){
        Message message = Message.non();
        try {
            String vesion = userService.version();
            return message.code(Message.codeSuccessed).data(vesion).message("获取成功");
        }catch (Exception e){
            log.error("user/version"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 协议书
     * @return
     */
    @RequestMapping("/agreement.do")
    public Message agreement(){
        Message message = Message.non();
        try {
            HashMap agreement = userService.agreement();
            return message.code(Message.codeSuccessed).data(agreement).message("获取成功");
        }catch (Exception e){
            log.error("user/agreement"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 获取验证码
     * @param phone
     * @return
     */
    @RequestMapping("/sendSms.do")
    public Message sendSms(String phone){
        Message message = Message.non();
        if (StringUtils.isEmpty(phone)){
            return message.code(Message.codeFailured).message("请输入手机号");
        }
        try {
            Integer code = userService.sendSms(phone);
            if (IntegerUtils.isEmpty(code)){
                return message.code(Message.codeFailured).message("获取验证码失败");
            }
            return message.code(Message.codeSuccessed).data(code).message("获取验证码成功");
        } catch (NullPointerException e) {
            return message.code(Message.codeFailured).message(e.getMessage());
        } catch (Exception e) {
            log.error("user/sendSms"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 用户注册
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping("/register.do")
    public Message register(String phone,String password){
        Message message = Message.non();

        try {
            if (StringUtils.isEmpty(password)){
                return message.code(Message.codeFailured).message("请输入密码");
            }
            if (StringUtils.isEmpty(phone)){
                return message.code(Message.codeFailured).message("请输入手机号码");
            }

            Integer count = userService.register(phone,password);
            if ( IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("注册失败");
            }
            return message.code(Message.codeSuccessed).message("注册成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("user/register"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 快速登录
     * @param phone
     * @return
     */
    @RequestMapping("/fastLogin.do")
    public Message fastLogin(String phone){
        Message message = Message.non();
        if (StringUtils.isEmpty(phone)){
            return message.code(Message.codeFailured).message("请输入手机号码");
        }
        try {
            String token = userService.fastLogin(phone);
            return message.code(Message.codeSuccessed).data(token).message("登录成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("user/fastLogin"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 忘记密码
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping("/forgetPwd.do")
    public Message forgetPwd(String phone,String password){
        Message message = Message.non();
        try {
            if (StringUtils.isEmpty(phone)){
                return message.code(Message.codeFailured).message("手机号不能为空");
            }
            if (StringUtils.isEmpty(password)){
                return message.code(Message.codeFailured).message("密码不能为空");
            }
            boolean bool = userService.forgetPwd(phone,password);
            if (!bool){
                return message.code(Message.codeFailured).message("修改失败");
            }
            return message.code(Message.codeSuccessed).message("修改成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("user/forgetPwd"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 修改手机设备
     * @param token
     * @param model
     * @param system
     * @param version
     * @return
     */
    @RequestMapping("/updateEquipment.do")
    public Message updateEquipment(String token,String model,String system,String version,String platform ,String SDKVersion){
        Message message = Message.non();
        try {
            Integer count = userService.updateEquipment(token,model,system,version,platform,SDKVersion);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("修改手机设备失败");
            }
            return message.code(Message.codeSuccessed).message("修改成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("user/updateEquipment"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * app中判断在是否有退出登录过
     * @param phone
     * @return
     */
    @RequestMapping("/appIsLogin.do")
    public Message appIsLogin(String phone ,String model,String system,String version ,String platform ,String SDKVersion){
        Message message = Message.non();
        try {
            boolean is_boolean = userService.appIsLogin(phone,model,system,version,platform,SDKVersion);
            return message.code(Message.codeSuccessed).data(is_boolean).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("user/appIsLogin"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }



    /**
     * 在是否有退出登录过
     * @param phone
     * @return
     */
    @RequestMapping("/seleteUserStatus.do")
    public Message seleteUserStatus(String phone){
        Message message = Message.non();
        try {
            Integer status = userService.seleteUserStatus(phone);
            return message.code(Message.codeSuccessed).data(status).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("user/seleteUserStatus"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }
    /**
     * 退出登录修改状态
     * @param token
     * @return
     */
    @RequestMapping("/updateUserStatus.do")
    public Message updateUserStatus(String token){
        Message message = Message.non();
        try {
            Integer status = userService.updateUserStatus(token);
            if (IntegerUtils.isEmpty(status)){
                return message.code(Message.codeSuccessed).data(status).message("退出失败");
            }
            return message.code(Message.codeSuccessed).data(status).message("退出成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("user/updateUserStatus"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }




    /**
     * 登录
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping("/login.do")
    public Message login(String phone,String password){
        Message message = Message.non();
        if (StringUtils.isEmpty(phone)){
            return message.code(Message.codeFailured).message("请输入手机号码");
        }
        if (StringUtils.isEmpty(password)){
            return message.code(Message.codeFailured).message("请输入密码");
        }
        try {
            String token = userService.login(phone,password);
            return message.code(Message.codeSuccessed).data(token).message("登录成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("user/updateUserStatus"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }




    /**
     * 登录查询是否有最后登录的店铺，如果有，查询店铺是否有活动，首次登录弹出活动窗口，关闭下次不用弹出
     * @return
     */
    @RequestMapping("/activityIsRead.do")
    public Message activityIsRead(String token){
        Message message =Message.non();
        try {
            List<HashMap> hashMaps = userService.activityIsRead(token);
            return message.code(Message.codeSuccessed).data(hashMaps).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("user/activityIsRead"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 关闭弹出活动窗口
     * @return
     */
    @RequestMapping("/updateCouponState.do")
    public Message updateCouponState(String token){
        Message message = Message.non();
        try {
            Integer count = userService.updateCouponState(token);
            if (IntegerUtils.isEmpty(count)) {
                return message.code(Message.codeFailured).message("修改失败");
            }
            return message.code(Message.codeSuccessed).message("修改成功");
        }catch (Exception e){
            log.error("user/updateCouponState"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 首页轮播图
     * @return
     */
    @RequestMapping("/broadcastpicture.do")
    public Message broadcastpicture(){
        Message message = Message.non();
        try {
            List<HashMap> hashMaps = userService.broadcastpicture();
            return message.code(Message.codeSuccessed).data(hashMaps).message("获取成功");
        }catch (Exception e){
            log.error("user/broadcastpicture"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 系统公告
     * @return
     */
    @RequestMapping("systemtBulletin.do")
    public Message systemtBulletin(String token){
        Message message = Message.non();
        try {
            HashMap hashMap = userService.systemtBulletin();
            return message.code(Message.codeSuccessed).data(hashMap).message("获取成功");
        }catch (Exception e){
            log.error("user/systemtBulletin"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 用户最后登录的商家
     * @param token
     * @return
     */
    @RequestMapping("/userLastLoginBusiness.do")
    public Message userLastLoginBusiness(String token){
        Message message = Message.non();
        try {
            HashMap hashMap = userService.userLastLoginBusiness(token);
            return message.code(Message.codeSuccessed).data(hashMap).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("user/userLastLoginBusiness"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 手动添加申请绑定店铺
     * @param phone
     * @return
     */
    @RequestMapping("/addApplyBindingBuseinss.do")
    public Message addApplyBindingBuseinss(String token,String phone){
        Message message = Message.non();
        if (StringUtils.isEmpty(phone)){
            return message.code(Message.codeFailured).message("店铺手机号不能为空");
        }
        try {
            Integer count = userService.addApplyBindingBuseinss( token, phone);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("申请失败");
            }
            return message.code(Message.codeSuccessed).message("申请成功，等待审核");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("user/addApplyBindingBuseinss"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 扫描申请绑定店铺
     * @param token
     * @param business_id
     * @return
     */
    @RequestMapping("/scanBindingBusiness.do")
    public Message scanBindingBusiness(String token,Integer business_id){
        Message message = Message.non();
        if (IntegerUtils.isEmpty(business_id)){
            return message.code(Message.codeFailured).message("店铺id不能为空");
        }
        try {
            Integer count = userService.scanBindingBusiness( token, business_id);
            if ( IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("申请失败");
            }
            return message.code(Message.codeSuccessed).message("申请成功，等待审核");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("user/scanBindingBusiness"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 用户绑定的商家列表
     * @param token
     * @return
     */
    @RequestMapping("/userBindingBusiness.do")
    public Message userBindingBusiness(String token,Integer pageNo){
        Message message = Message.non();
        try {
            Page page = userService.userBindingBusiness(token,pageNo);
            return message.code(Message.codeSuccessed).data(page).message("获取成功");
        }catch (Exception e){
            log.error("user/userBindingBusiness"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 切换商家时修改最后登录的商家
     * @param token
     * @return
     */
    @RequestMapping("/updateUserBDId.do")
    public Message updateUserBDId(String token ,Integer binding_id){
        Message message = Message.non();
        if (IntegerUtils.isEmpty(binding_id)){
            return message.code(Message.codeFailured).message("绑定店铺id不能为空");
        }
        try {
            Integer count = userService.updateUserBDId(token ,binding_id);
            if ( IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("切换失败");
            }
            return message.code(Message.codeSuccessed).message("切换成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("user/updateUserBDId"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 用户收藏的产品
     * @param token
     * @param pageNo
     * @return
     */
    @RequestMapping("/userCollectionGoods.do")
    public Message userCollectionGoods(String token,Integer pageNo){
        Message message = Message.non();
        try {
            Page page = userService.userCollectionGoods(token,pageNo);
            return message.code(Message.codeSuccessed).data(page).message("获取成功");
        }catch (Exception e){
            log.error("user/userCollectionGoods"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 通过id查询产品详情
     * @param token
     * @param goods_id
     * @return
     */
    @RequestMapping("/goodsDetali.do")
    public Message goodsDetali(String token ,Integer goods_id){
        Message message = Message.non();
        if (IntegerUtils.isEmpty(goods_id) ){
            return message.code(Message.codeFailured).message("产品id不能为空");
        }
        try {
            HashMap hashMap = userService.goodsDetali(token,goods_id);
            return message.code(Message.codeSuccessed).data(hashMap).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("user/goodsDetali"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 收藏产品
     * @param token
     * @param goods_id
     * @return
     */
    @RequestMapping("/addCollection.do")
    public Message addCollection(String token,Integer goods_id,Integer business_id){
        Message message = Message.non();
        if (goods_id == null || goods_id == 0){
            return message.code(Message.codeFailured).message("产品id不能为空");
        }
        if (business_id == null || business_id == 0){
            return message.code(Message.codeFailured).message("店铺id不能为空");
        }
        try {
            Integer count = userService.addCollection(token,goods_id ,business_id);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("收藏失败");
            }
            return message.code(Message.codeSuccessed).data(count).message("收藏成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("user/addCollection"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 取消收藏
     * @param token
     * @param collection_id
     * @return
     */
    @RequestMapping("/delectCollection.do")
    public Message delectCollection(String token,Integer collection_id){
        Message message = Message.non();
        if (collection_id == null || collection_id == 0){
            return message.code(Message.codeFailured).message("收藏id不能为空");
        }
        try {
            Integer count = userService.delectCollection(collection_id);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("取消失败");
            }
            return message.code(Message.codeSuccessed).message("取消成功");
        }catch (Exception e){
            log.error("user/delectCollection"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 查询店铺类别
     * @param token
     * @param business_id 店铺id
     * @param level 二级类别时传一级类别id
     * @return
     */
    @RequestMapping("/businessCategory.do")
    public Message businessCategory(String token,Integer business_id,Integer level){
        Message message = Message.non();
        try {
            List<HashMap> hashMaps = userService.businessCategory(business_id, level);
            return message.code(Message.codeSuccessed).data(hashMaps).message("获取成功");
        }catch (Exception e){
            log.error("user/businessCategory"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 店铺类别下的产品
     * @param token
     * @param business_id 店铺id
     * @param category_id 类别id
     * @param name 产品名称
     * @return
     */
    @RequestMapping("/businessGoods.do")
    public Message businessGoods(String token,Integer pageNo,Integer business_id,Integer category_id,String name){
        Message message = Message.non();
        if (IntegerUtils.isEmpty(business_id)){
            return message.code(Message.codeFailured).message("店铺id不能为空");
        }
        try {
            Page page = userService.businessGoods(token,pageNo, business_id, category_id, name);
            return message.code(Message.codeSuccessed).data(page).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("user/businessGoods"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 店铺满减活动优惠券
     * @param token
     * @return
     */
    @RequestMapping("/businessActivity.do")
    public Message businessActivity(String token){
        Message message = Message.non();
        try {
            List<HashMap> hashMaps = userService.businessActivity(token);
            return message.code(Message.codeSuccessed).data(hashMaps).message("获取成功");
        }catch (Exception e){
            log.error("user/businessActivity"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 取消绑定
     * @param token
     * @param binding_id
     * @return
     */
    @RequestMapping("/cancelBinding.do")
    public Message cancelBinding(String token,Integer binding_id){
        Message message = Message.non();
        if (IntegerUtils.isEmpty(binding_id)){
            return message.code(Message.codeFailured).message("绑定id不能为空");
        }
        try {
            Integer count = userService.cancelBinding(binding_id);
            if (IntegerUtils.isEmpty(count)){
                return message.code(Message.codeFailured).message("解绑失败");
            }
            return message.code(Message.codeSuccessed).message("解绑成功");
        }catch (Exception e){
            log.error("user/cancelBinding"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 限时秒杀时间
     * @param token
     * @return
     */
    @RequestMapping("/limitedSeckill.do")
    public Message limitedSeckill(String token){
        Message message = Message.non();
        try {
            List<HashMap> hashMaps = userService.limitedSeckill(token);
            return message.code(Message.codeSuccessed).data(hashMaps).message("获取成功");
        }catch (Exception e){

            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 限时秒杀时间段产品
     * @param token
     * @return
     */
    @RequestMapping("/limitedSeckillGoods.do")
    public Message limitedSeckillGoods(String token ,Integer pageNo,Integer seckill_id){
        Message message = Message.non();
        try {
            Page page = userService.limitedSeckillGoods(pageNo,seckill_id);
            return message.code(Message.codeSuccessed).data(page).message("获取成功");
        }catch (Exception e){
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     * 申请成为商家
     * @param token
     * @param business
     * @return
     */
    @RequestMapping("/addBuseinss.do")
    public Message addBuseinss(String token, Business business){
        Message message = Message.non();
        if (StringUtils.isEmpty(business.getPhone())){
            return message.code(Message.codeFailured).message("手机号不能为空");
        }
        if (StringUtils.isEmpty(business.getStore_name())){
            return message.code(Message.codeFailured).message("店铺名称不能为空");
        }
        try {
            Integer count = userService.addBuseinss(business);
            if (IntegerUtils.isEmpty(count)) {
                return message.code(Message.codeFailured).message("申请失败");
            }
            return message.code(Message.codeSuccessed).message("申请成功，等待审核");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("user/addBuseinss"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }


    /**
     * 领取满减优惠券
     * @param token
     * @param coupon_id
     * @return
     */
    @RequestMapping("/addBindingCoupon.do")
    public Message addBindingCoupon(String token ,Integer coupon_id){
        Message message =Message.non();
        try {
            Integer count = userService.addBindingCoupon(token, coupon_id);
            if (IntegerUtils.isEmpty(count)) {
                return message.code(Message.codeFailured).message("领取失败");
            }
            return message.code(Message.codeSuccessed).message("领取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("user/addBindingCoupon"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }

    /**
     *查询店铺电话
     * @param token
     * @return
     */
    @RequestMapping("/busienssPhone.do")
    public Message busienssPhone(String token ){
        Message message = Message.non();
        try {
            String phone = userService.busienssPhone(token);
            return message.code(Message.codeSuccessed).data(phone).message("获取成功");
        }catch (NullPointerException e){
            return message.code(Message.codeFailured).message(e.getMessage());
        }catch (Exception e){
            log.error("user/busienssPhone"+e.getMessage());
            return message.code(Message.codeFailured).message(Global.ERROR);
        }
    }




    @RequestMapping("/find.do")
    public Message find(){
        Message message = Message.non();
        WebSocketUtil ws = new WebSocketUtil();
        JSONObject jo = new JSONObject();
        jo.put("message", "推送");
        jo.put("bid",4);
        System.out.println("开始测试");
        try {
            ws.onMessage(jo.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message.code(Message.codeSuccessed).message("dfs");
    }


    @RequestMapping("/ERROR_URL1.do")
    public Message error(){
        Message message = Message.non();
        return message.code(Message.codeOutOfLogin).message("请先登陆");
    }

    @RequestMapping("/ERROR_URL3.do")
    public Message error2(){
        Message message = Message.non();
        return message.code(Message.codeFailured).message("您的账号在另一台设备登录");
    }
    @RequestMapping("/ERROR_URL4.do")
    public Message error3(){
        Message message = Message.non();
        return message.code(Message.codeFailured).message("您的账号是否出现违反行为导致总平台禁用了？如需解冻，则联系总平台");
    }

}
