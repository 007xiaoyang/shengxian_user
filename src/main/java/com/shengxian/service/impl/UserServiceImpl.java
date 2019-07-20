package com.shengxian.service.impl;

import com.shengxian.common.Sendsms;
import com.shengxian.common.util.*;
import com.shengxian.entity.*;
import com.shengxian.mapper.OrderMapper;
import com.shengxian.mapper.UserMapper;
import com.shengxian.service.UserService;
import org.dom4j.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Description:
 *
 * @Author: yang
 * @Date: 2018-12-30
 * @Version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private OrderMapper orderMapper;

    //查询版本号
    @Override
    public String version()throws Exception {
        return userMapper.version();
    }

    //协议书
    @Override
    public HashMap agreement() throws Exception {
        return userMapper.agreement();
    }

    @Override
    public Integer sendSms(String phone)throws NullPointerException ,Exception {
        //判断手机是否已经注册过了
        Integer uid = userMapper.phoneIfExistence(phone);
        if (uid == null){
            throw new NullPointerException("手机号码未注册，不能发验证码");
        }

        return Sendsms.sendSMS(phone);
    }

    //用户注册
    @Override
    @Transactional
    public Integer register(String phone, String password) throws NullPointerException, Exception{
        //判断手机是否已经注册过了
        Integer uid = userMapper.phoneIfExistence(phone);
        if (uid != null){
            throw new NullPointerException("手机号码已经注册过了");
        }
        //密码加密
        String pwd = PasswordMD5.EncoderByMd5(password + Global.passwordKey);
        return userMapper.register(phone,pwd ,UUID.randomUUID().toString());
    }

    //快速登录
    @Override
    @Transactional
    public String fastLogin(String phone)throws NullPointerException ,Exception {
        HashMap user = userMapper.selectUser(phone);
        if (user == null){
            throw new NullPointerException("手机号码不正确或未注册");
        }
        //判断用户是否被禁用户了
        if (user.get("is_disable").toString().equals("1")){
            throw new NullPointerException("您的账号是否出现违反行为导致总平台禁用了?如需解冻，则联系总平台");
        }
        String token =  user.get("token").toString();

        //判断是否是单人登录,等于0，则修改token
        if (user.get("power").toString().equals("0")){
            //通过用户id修改token
            token = UUID.randomUUID().toString();
            userMapper.udateUserToken(Integer.valueOf(user.get("id").toString()), token);
        }
        return token;
    }

    //忘记密码
    @Override
    @Transactional
    public boolean forgetPwd(String phone, String password) throws NullPointerException, Exception {
        //判断手机号是否注册了
        Integer uid = userMapper.phoneIfExistence(phone);
        if (uid == null){
            throw new NullPointerException("手机号未注册！");
        }
        //根据用户id修改密码
        String pwd = PasswordMD5.EncoderByMd5(password + Global.passwordKey);
        Integer count = userMapper.updatePassword(uid,pwd);
        if (count != null && !count.equals(0)){
            return true;
        }
        return false;
    }


    //修改手机设备
    @Override
    @Transactional
    public Integer updateEquipment(String token, String model, String system, String version, String platform, String SDKVersion) throws NullPointerException, Exception {
        return userMapper.updateEquipment(token,model,system,version,platform,SDKVersion);
    }

    //app中判断在是否有退出登录过
    @Override
    public boolean appIsLogin(String phone, String model, String system, String version, String platform, String SDKVersion) {
        Integer bool = userMapper.appIsLogin(phone, model, system, version, platform, SDKVersion);
        if (bool != null){
            return true;
        }
        return false;
    }

    //在微信公众号是否有退出登录过
    @Override
    public Integer seleteUserStatus(String phone) throws NullPointerException, Exception {
        return userMapper.seleteUserStatus(phone);
    }

    //在微信公众号退出登录修改状态
    @Override
    @Transactional
    public Integer updateUserStatus(String token) throws NullPointerException, Exception {
        return userMapper.updateUserStatus(token);
    }

    //登录
    @Override
    @Transactional
    public String login(String phone, String password) throws NullPointerException, Exception {
        HashMap user = userMapper.selectUser(phone);

        if (user == null){
            throw new NullPointerException("手机号码不正确或未注册");
        }
        //密码加密
        String pawd = PasswordMD5.EncoderByMd5(password + Global.passwordKey);
        if (!pawd.equals(user.get("password").toString())){
            throw new NullPointerException("密码不正确");
        }
        //判断用户是否被禁用户了
        if (user.get("is_disable").toString().equals("1")){
            throw new NullPointerException("您的账号是否出现违反行为导致总平台禁用了?如需解冻，则联系总平台");
        }

        String token =  user.get("token").toString();

        //判断是否是单人登录,等于0，则修改token
        if (user.get("power").toString().equals("0")){
            //通过用户id修改token
           token = UUID.randomUUID().toString();
            userMapper.udateUserToken(Integer.valueOf(user.get("id").toString()), token);
        }
        return token;
    }

    //店铺有活动，首次切换弹出活动窗口，关闭下次不用弹出
    @Override
    public List<HashMap> activityIsRead(String token) throws NullPointerException, Exception {
        Integer binding_id = userMapper.userBDIdByToken(token);
        //查询最后登录的店铺，店铺有活动，首次切换弹出活动窗口，关闭下次不用弹出
        return userMapper.activityIsRead(binding_id);
    }

    //关闭弹出活动窗口
    @Override
    @Transactional
    public Integer updateCouponState(String token) throws Exception {

        //通过token查询用户绑定的店铺id
        Integer business_id = userMapper.userBDBusiness_id(token);
        return userMapper.updateCouponState(business_id);
    }

    //首页轮播图
    @Override
    public List<HashMap> broadcastpicture() throws Exception {
        return userMapper.broadcastpicture();
    }

    //系统公告
    @Override
    public HashMap systemtBulletin() throws Exception {
        return userMapper.systemtBulletin();
    }

    //用户最后登录的商家
    @Override
    public HashMap userLastLoginBusiness(String token) throws NullPointerException, Exception {

        HashMap hashMap = null;

        if (token != null && !token.equals("")){
            Integer binding_id = userMapper.userBDIdByToken(token);
            //用户最后登录的商家
             hashMap = userMapper.userLastLoginBusiness(binding_id);

        }else {
            hashMap = userMapper.acquiesceBusiness();
        }

        if (hashMap != null){
            //用户最后登录的商家满减活动
            List<HashMap> acticity = userMapper.buserLastLoginBusinessActivity( Integer.valueOf(hashMap.get("id").toString()) );
            hashMap.put("activity",acticity);
        }

        return hashMap;
    }

    //添加申请绑定商家记录
    @Override
    @Transactional
    public Integer addApplyBindingBuseinss(String token, String phone) throws NullPointerException, Exception {

        //判断申请绑定的商家手机号是否存在
        Integer business_id = userMapper.phoneOrBusiness_idIfExist(null,phone);
        if (business_id == null){
            throw new NullPointerException("您要申请添加的店铺不存在");
        }
        //通过token查询注册id
        Integer uid = userMapper.userIdByToken(token);

        //查询注册id和店铺id是否绑定过
        Integer binding_id  = userMapper.useridAndbusinessidIfBinding(uid,business_id);
        if (binding_id != null){
            throw new NullPointerException("您已经绑定该店铺了，不能再进行申请了");
        }

        //通过商家id查询是否被禁用了或者转换成线下了
        HashMap businessState = userMapper.findBusinessState(business_id);

        if (businessState.get("is_disable").toString().equals("1")){
            throw new NullPointerException("店铺已被禁用了，暂时不能绑定");
        }else if (Integer.valueOf(businessState.get("duration").toString()) < 0){
            throw new NullPointerException("店铺已过期，暂时不能绑定");
        }else if (businessState.get("source").toString().equals("1")){
            throw new NullPointerException("此店铺暂时不支持商城功能");
        }

        //通过注册ID和店铺ID查询是否有提交过申请在待审核中
        Integer examineUser = userMapper.businessExamineUser(uid, business_id);
        if (examineUser != null){
            throw new NullPointerException("您已提交绑定了，不能再进行申请了");
        }




        return userMapper.addApplyBindingBuseinss(uid,business_id,new Date());
    }

    //扫描申请绑定店铺
    @Override
    @Transactional
    public Integer scanBindingBusiness(String token, Integer business_id) throws NullPointerException, Exception {
        //通过token查询注册id
        Integer uid = userMapper.userIdByToken(token);

        //判断申请绑定的商家id是否存在
        Integer buseinss_id = userMapper.phoneOrBusiness_idIfExist(business_id,null);
        if (buseinss_id == null){
            throw new NullPointerException("您要申请添加的店铺不存在");

        }

        //查询注册id和商家id是否绑定过
        Integer binding_id  = userMapper.useridAndbusinessidIfBinding(uid,buseinss_id);
        if (binding_id != null){
            throw new NullPointerException("您已经绑定该店铺了，不能再进行申请了");
        }

        //通过注册ID和店铺ID查询是否有提交过申请在待审核中
        Integer examineUser = userMapper.businessExamineUser(uid, business_id);
        if (examineUser != null){
            throw new NullPointerException("您已提交绑定了，不能再进行申请了");
        }

        //通过商家id查询是否被禁用了或者转换成线下了
        HashMap businessState = userMapper.findBusinessState(buseinss_id);
        if (businessState.get("is_disable").toString().equals("1")){
            throw new NullPointerException("店铺已被禁用了，暂时不能绑定");
        }else   if (businessState.get("source").toString().equals("1")){
            throw new NullPointerException("此店铺暂时不支持商城功能");
        }else if (Integer.valueOf(businessState.get("duration").toString()) < 0){
            throw new NullPointerException("店铺已过期，暂时不能绑定");
        }


        return userMapper.addApplyBindingBuseinss(uid,buseinss_id,new Date());
    }

    //用户绑定的商家列表
    @Override
    public Page userBindingBusiness(String token, Integer pageNo)throws Exception {
        //通过token查询id
        Integer uid =  userMapper.userIdByToken(token);
        int pageNum = 1;
        if (pageNo != null && pageNo != 0){
            pageNum = pageNo;
        }
        Integer totalCount = userMapper.userBindingBusinessCount(uid);
        Page page = new Page(pageNum,totalCount);
        List<HashMap> hashMaps = userMapper.userBindingBusiness(uid,page.getStartIndex(),page.getPageSize());
        for (HashMap hash: hashMaps   ) {
            //是否有活动
            Integer acticity = userMapper.ifExistActivity(Integer.valueOf(hash.get("id").toString()));
            hash.put("acticity",acticity);
        }
        page.setRecords(hashMaps);
        return page;
    }

    //切换商家时修改最后登录的商家
    @Override
    @Transactional
    public Integer updateUserBDId(String token ,Integer binding_id) throws NullPointerException, Exception {
        //通过绑定id查询店铺id
        Integer business_id = userMapper.busienss_id(binding_id);
        //通过商家id查询是否被禁用了或者转换成线下了
        HashMap businessState = userMapper.findBusinessState(business_id);
        if (businessState.get("is_disable").toString().equals("1")){
            throw new NullPointerException("店铺已被禁用了，不能切换了");
        }
        if (businessState.get("source").toString().equals("1")){
            throw new NullPointerException("此店铺暂时不支持商城功能了");
        }
        if (Integer.valueOf(businessState.get("duration").toString()) < 0){
            throw new NullPointerException("店铺已过期，不能切换了");
        }

        return userMapper.updateUserBDId(token ,binding_id);
    }

    //用户收藏的产品
    @Override
    public Page userCollectionGoods(String token, Integer pageNo) throws Exception {
        //通过token查询用户绑定id
        Integer bd_id = userMapper.userBDIdByToken(token);
        int pageNum = 1;
        if (IntegerUtils.Empty(pageNo)){
            pageNum = pageNo;
        }
        Integer business_id = userMapper.busienss_id(bd_id);

        Integer totalCount = userMapper.userCollectionGoodsCount(bd_id ,business_id);
        Page page = new Page(pageNum,totalCount);
        List<HashMap> hashMaps = userMapper.userCollectionGoods(bd_id,business_id  ,page.getStartIndex(), page.getPageSize());
        page.setRecords(hashMaps);
        return page;
    }

    //通过id查询产品详情
    @Override
    public HashMap goodsDetali(String token ,Integer goods_id) throws NullPointerException, Exception {

        //通过token查询用户绑定id
        Integer bd_id = -1;
        HashMap hashMap = null;

        if (token !=  null && !token.equals("")){
            bd_id = userMapper.userBDIdByToken(token);

        }
        hashMap = userMapper.goodsDetali(goods_id, bd_id);
        if (hashMap== null){
            throw new NullPointerException("产品不存在");
        }

        //通过产品ID和绑定客户ID查询产品是否收藏
        Integer count = userMapper.selectGoodsIsCollection(goods_id, bd_id);
        if (count == null){
            hashMap.put("count",0);
        }else {
            hashMap.put("count",count);
        }
        //通过产品id查询产品图片
        List<HashMap> hashMaps = userMapper.goodsImg(goods_id);
        hashMap.put("imgs",hashMaps);
        return hashMap;
    }

    //添加收藏
    @Override
    @Transactional
    public Integer addCollection(String token, Integer goods_id ,Integer business_id) throws NullPointerException, Exception {
        Integer binding_id = userMapper.userBDIdByToken(token);
        if (binding_id== null || binding_id == 0){
            throw new NullPointerException("您还未绑定店铺，不能收藏");
        }
        //判断当前店铺是否有这件产品
        Integer id = userMapper.findIdByGoodsIdAndBusinessId(goods_id, business_id);
        if (id == null){
            throw new NullPointerException("店铺没有这件产品，不能收藏");
        }
        Collect collect = new Collect(binding_id, goods_id, new Date());
        userMapper.addCollection(collect);
        return collect.getId();
    }

    //取消收藏
    @Override
    @Transactional
    public Integer delectCollection(Integer collection_id) throws Exception {
        return userMapper.delectCollection(collection_id);
    }

    //查询店铺类别
    @Override
    public List<HashMap> businessCategory(Integer business_id, Integer level) {
        return userMapper.businessCategory(business_id,level);
    }

    //店铺类别下的产品
    @Override
    public Page businessGoods(String token, Integer pageNo, Integer business_id, Integer category_id, String name)throws NullPointerException, Exception {
        int pageNum = 1;
        if( IntegerUtils.Empty(pageNo)){
            pageNum=pageNo;
        }

        Integer binding_id = -1;
        if (token != null && !token.equals("")){
            //查询登录店铺的绑定id
             binding_id = userMapper.userBDIdByToken(token);
             if (binding_id == null || binding_id == 0){
                 throw new NullPointerException("未切换店铺哟");
             }
        }

        Integer cid = null;
        Integer level = null;
        //判断是大类别id还是小类别id
        Integer le = userMapper.largeClassOrSmalClass(category_id);
        if (le == null || le == 0){ //判断le如果等于null，就代表是大类下的产品
            level = category_id;
        }else {
            cid = category_id;
        }
        Integer totalCount = userMapper.businessGoodsCount(new Search(business_id,cid,binding_id,name,level));
        Page page = new Page(pageNum,totalCount);
        List<HashMap> hashMaps = userMapper.businessGoods(new Search(business_id,cid,binding_id,name,level,page.getStartIndex(),page.getPageSize()));
        for (HashMap hash: hashMaps ) {

            //根据产品id查询产品是否限购
            Double restricted = orderMapper.goodsIsRestricted(Integer.valueOf(hash.get("id").toString()));
            hash.put("restricted" ,restricted) ;

            //产品是否满赠
            HashMap hashMap = orderMapper.goodsIsFullGift(Integer.valueOf(hash.get("id").toString()));
            hash.put("fullGift" ,hashMap);
        }
        page.setRecords(hashMaps);
        return page;
    }

    //店铺满减活动
    @Override
    public List<HashMap> businessActivity(String token) throws Exception {
        //店铺ID
        Integer business_id = userMapper.userBDBusiness_id(token);
        return userMapper.businessActivity(business_id);
    }

    //取消绑定
    @Override
    @Transactional
    public Integer cancelBinding(Integer binding_id) throws Exception {

        //判断取消绑定ID是否是最后一次登录的绑定ID
        Integer user_id = userMapper.userBDidByBinding_id(binding_id);
        if (user_id != null){
            //取消user表里记录最后一次登录的店铺id
            userMapper.updateBDIdByUser_id(user_id);
        }
        return userMapper.cancelBinding(binding_id);
    }


    @Override
    public List<HashMap> limitedSeckill(String token) throws Exception {
        Integer binding_id = userMapper.userBDIdByToken(token);
        return userMapper.limitedSeckill(binding_id);
    }


    @Override
    public Page limitedSeckillGoods(Integer pageNo, Integer seckill_id) throws Exception {

        int pageNun = 1;
        if (pageNo != null && pageNo != 0){
            pageNun =pageNo;
        }
        Integer tatolCount = userMapper.limitedSeckillGoodsCount(seckill_id);
        Page page = new Page(pageNun,tatolCount);
        List<HashMap> hashMaps = userMapper.limitedSeckillGoods(seckill_id, page.getStartIndex(), page.getPageSize());
        page.setRecords(hashMaps);
        return page;
    }

    //申请商家
    @Override
    @Transactional
    public Integer addBuseinss(Business business)throws NullPointerException ,Exception {

        //判断手机号是否注册商家过了
        Integer bid = userMapper.phoneIfRegister(business.getPhone());
        if (bid != null){
            throw new NullPointerException("手机号已经注册过了，不能申请了哟");
        }
        //判断手机号是否注册商家过了
        Integer staffiId = userMapper.phoneIfStaffRegister(business.getPhone());
        if (staffiId != null){
            throw new NullPointerException("手机号已经注册员工账号了，不能申请了哟");
        }

        //判断邀请码是否存在
        if (business.getInvitation() != null && !business.getInvitation().equals("")){
            //判断邀请码是否存在（包括客户，员工，商家）
            Integer phone = userMapper.ifBusinessUserStaffPhone(business.getPhone());
            if (phone == null){
                throw new NullPointerException("邀请码不存在");
            }
        }
        //查询最后一条编号
        String number = "";
        number = userMapper.selectLastNumber();
        if (number == null){
            number = "1001";
        }else {
            number = GroupNumber.getNumber(Integer.valueOf(number));
        }

        String pwd = PasswordMD5.EncoderByMd5(123456 + Global.passwordKey);
        business.setToken(UUID.randomUUID().toString());
        business.setCreate_time(new Date());
        business.setNumber(number);
        //注册商家
        Integer count = userMapper.addBuseinss(business);

        //添加商家盘点状态
        userMapper.addClerk(business.getId());

        //添加商家采购模板
        userMapper.addTemplate(new Template(business.getId(),"采购单","供应商名称","供应商电话",0));
        //添加商家采购退货模板
        userMapper.addTemplate(new Template(business.getId(),"采购退货单","供应商名称","供应商电话",1));
        //添加商家销售模板
        userMapper.addTemplate(new Template(business.getId(),"销售单","客户名称","客户电话",2));
        //添加商家销售退货模板
        userMapper.addTemplate(new Template(business.getId(),"销售退货单","客户名称","客户电话",3));

        //添加店铺模板2
        userMapper.addTemplateTwo(business.getId() , "采购单" ,0 ,null  );
        userMapper.addTemplateTwo(business.getId() , "采购退货单" ,1 ,null  );
        userMapper.addTemplateTwo(business.getId() , "销售单" ,2 ,null  );
        userMapper.addTemplateTwo(business.getId() , "销售退货单" ,3 ,null  );

        //添加店铺模板3
        userMapper.addTemplateThree(business.getId() , "采购单" ,0 ,null  );
        userMapper.addTemplateThree(business.getId() , "采购退货单" ,1 ,null  );
        userMapper.addTemplateThree(business.getId() , "销售单" ,2 ,null  );
        userMapper.addTemplateThree(business.getId() , "销售退货单" ,3 ,null  );

        //添加店铺模板5
        userMapper.addTemplateFive(business.getId() , "采购单" ,0 ,null  );
        userMapper.addTemplateFive(business.getId() , "采购退货单" ,1 ,null  );
        userMapper.addTemplateFive(business.getId() , "销售单" ,2 ,null  );
        userMapper.addTemplateFive(business.getId() , "销售退货单" ,3 ,null  );

        //注册商家默认添加15种方案名称
        for (int i = 1;i <16 ; i++){
            userMapper.addBusinessScheme(i,business.getId(),String.valueOf(i));
        }

        return count;
    }

    //领取优惠券
    @Override
    @Transactional
    public Integer addBindingCoupon(String token, Integer coupon_id) throws NullPointerException, Exception {
        //通过店铺满减优惠券id查信息
        HashMap hashMap = userMapper.selectCoupon(coupon_id);
        if (hashMap == null){
            throw new NullPointerException("店铺满减优惠券不存在");
        }
        Integer binding_id = userMapper.userBDIdByToken(token);

        Coupon coupon = new Coupon();
        coupon.setBinding_id(binding_id);
        coupon.setCoupon_id(coupon_id);
        coupon.setFull(Double.valueOf(hashMap.get("full").toString()));
        coupon.setReduce(Double.valueOf(hashMap.get("reduce").toString()));
        coupon.setStartTime(hashMap.get("startTime").toString());
        coupon.setEndTime(hashMap.get("endTime").toString());
        return userMapper.addBindingCoupon(coupon);
    }

    //店铺电话
    @Override
    public String busienssPhone(String token) throws NullPointerException ,Exception{
        //获取绑定ID
        Integer bdid = userMapper.userBDIdByToken(token);
        if (bdid == null || bdid == 0){
            throw new NullPointerException("您还未切换店铺呢");
        }
        return userMapper.busienssPhone(bdid);
    }
}
