package com.shengxian.mapper;

import com.shengxian.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserMapper {


    /**
     * 查询版本号
     * @return
     */
    String version();

    /**
     * 协议书
     * @return
     * @throws Exception
     */
    HashMap agreement();

    /**
     * 通过token查询注册id
     * @param token
     * @return
     */
    Integer userIdByToken(@Param("token") String token);

    /**
     * 查询token查询是否被禁用（此接口只在拦截器使用）
     * @param token
     * @return
     */
    HashMap userByToken(@Param("token") String token);

    /**
     * 查询手机号是否存在了
     * @param phone
     * @return
     */
    Integer phoneIfExistence(@Param("phone") String phone);

    /**
     * 用户注册
     * @param phone
     * @param password
     * @return
     */
    Integer register(@Param("phone") String phone, @Param("password") String password, @Param("token") String token);

    /**
     * 修改密码
     * @param uid 用户id
     * @param password 密码
     * @return
     */
    Integer updatePassword(@Param("id") Integer uid, @Param("password") String password);

    /**
     * 修改手机设备
     * @param token
     * @param model
     * @param system
     * @param version
     * @param platform
     * @param SDKVersion
     * @return
     * @throws NullPointerException
     * @throws Exception
     */
    Integer  updateEquipment(@Param("token") String token, @Param("model") String model, @Param("system") String system, @Param("version") String version, @Param("platform") String platform, @Param("SDKVersion") String SDKVersion)throws NullPointerException ,Exception;


    /**
     * app中判断在是否有退出登录过
     * @param phone
     * @param model
     * @param system
     * @param version
     * @param platform
     * @param SDKVersion
     * @return
     */
    Integer appIsLogin(@Param("phone") String phone, @Param("model") String model, @Param("system") String system, @Param("version") String version, @Param("platform") String platform, @Param("SDKVersion") String SDKVersion);


    /**
     * 在微信公众号是否有退出登录过
     * @param phone
     * @return
     * @throws NullPointerException
     * @throws Exception
     */
    Integer seleteUserStatus(@Param("phone") String phone);

    /**
     * 在微信公众号退出登录修改状态
     * @param token
     * @return
     * @throws NullPointerException
     * @throws Exception
     */
    Integer updateUserStatus(@Param("token") String token);

    /**
     * 通过手机号查询用户信息
     * @param phone
     * @return
     */
    HashMap selectUser(@Param("phone") String phone);

    /**
     * 通过用户id修改token
     * @param uid
     * @param token
     * @return
     */
    Integer udateUserToken(@Param("id") Integer uid, @Param("token") String token);

    /**
     * 查询店铺状态
     * @param buseinss_id
     * @return
     */
    HashMap findBusinessState(@Param("id") Integer buseinss_id);


    /**
     * 店铺有活动，首次切换弹出活动窗口，关闭下次不用弹出
     * @param binding_id
     * @return
     * @throws Exception
     */
    List<HashMap> activityIsRead(@Param("binding_id") Integer binding_id);

    /**
     * 关闭弹出活动窗口
     * @param business_id 商家id
     * @return
     * @throws Exception
     */
    Integer updateCouponState(@Param("business_id") Integer business_id);

    /**
     * 首页轮播图
     * @return
     */
    List<HashMap> broadcastpicture();

    /**
     * 系统公告
     * @return
     */
    HashMap systemtBulletin();

    /**
     * 用户最后登录的商家
     * @param binding_id
     * @return
     * @throws Exception
     */
    HashMap userLastLoginBusiness(@Param("binding_id") Integer binding_id);

    /**
     * 默认店铺
     * @return
     */
    HashMap acquiesceBusiness();

    /**
     * 是否有活动
     * @param busienss_id
     * @return
     */
    Integer ifExistActivity(Integer busienss_id);


    /**
     * 商家的手机号或id是否存在
     * @param business_id 商家id
     * @param phone 商家手机号
     * @return
     */
    Integer phoneOrBusiness_idIfExist(@Param("business_id") Integer business_id, @Param("phone") String phone);

    /**
     * 通过注册ID和商家ID查询是否有提交过申请在待审核中
     * @param user_id 注册ID
     * @param business_id 店铺ID
     * @return
     */
    Integer businessExamineUser(@Param("user_id") Integer user_id, @Param("business_id") Integer business_id);

    /**
     * 查询注册id和商家id是否绑定过
     * @param user_id 注册id
     * @param business_id 商家id
     * @return
     */
    Integer useridAndbusinessidIfBinding(@Param("user_id") Integer user_id, @Param("business_id") Integer business_id);

    /**
     * 添加申请绑定商家记录
     * @param user_id 注册id
     * @param business_id 商家id
     * @param applyTime 申请时间
     * @return
     */
    Integer addApplyBindingBuseinss(@Param("uid") Integer user_id, @Param("bid") Integer business_id, @Param("applyTime") Date applyTime);


    /**
     * 用户绑定的商家列表总数
     * @param id
     * @return
     */
    Integer userBindingBusinessCount(@Param("id") Integer id);

    /**
     * 用户绑定的商家列表
     * @param id
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<HashMap> userBindingBusiness(@Param("id") Integer id, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 通过绑定id查询店铺id
     * @param binding_id
     * @return
     */
    Integer busienss_id(@Param("id") Integer binding_id);

    /**
     * 通过绑定id查询店铺id和会员方案
     * @param binding_id
     * @return
     */
    HashMap bidAndSchemeid(@Param("id") Integer binding_id);

    /**
     * 切换商家时修改最后登录的绑定id
     * @param token
     * @return
     */
    Integer updateUserBDId(@Param("token") String token, @Param("binding_id") Integer binding_id);

    /**
     * 通过token查询用户绑定id
     * @param token
     * @return
     */
    Integer userBDIdByToken(@Param("token") String token);

    /**
     * 通过token查询用户绑定的店铺id
     * @param token
     * @return
     */
    Integer userBDBusiness_id(@Param("token") String token);

    /**
     * 用户收藏的产品总数
     * @param bd_id
     * @return
     * @throws Exception
     */
    Integer userCollectionGoodsCount(@Param("bd_id") Integer bd_id ,@Param("business_id") Integer business_id);

    /**
     * 用户收藏的产品
     * @param bd_id
     * @return
     * @throws Exception
     */
    List<HashMap> userCollectionGoods(@Param("bd_id") Integer bd_id  ,@Param("business_id") Integer business_id, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 通过id查询产品详情
     * @param goods_id
     * @param bd_id
     * @return
     * @throws Exception
     */
    HashMap goodsDetali(@Param("goods_id") Integer goods_id, @Param("bd_id") Integer bd_id);

    /**
     * 通过产品ID和绑定客户ID查询产品是否收藏
     * @param goods_id 产品ID
     * @param bd_id 绑定客户ID
     * @return
     */
    Integer selectGoodsIsCollection(@Param("goods_id") Integer goods_id, @Param("binding_id") Integer bd_id);

    /**
     * 查询产品图片
     * @param goods_id
     * @return
     */
    List<HashMap> goodsImg(@Param("goods_id") Integer goods_id);

    /**
     * 判断当前店铺是否有这件产品
     * @param goods_id
     * @param business_id
     * @return
     */
    Integer findIdByGoodsIdAndBusinessId(@Param("id") Integer goods_id ,@Param("business_id") Integer business_id);

    /**
     * 添加收藏
     * @return
     * @throws Exception
     */
    Integer addCollection(Collect collect );

    /**
     * 取消收藏
     * @param collection_id 收藏id
     * @return
     * @throws Exception
     */
    Integer delectCollection(@Param("id") Integer collection_id);

    /**
     * 查询店铺类别
     * @param business_id 店铺id
     * @param level 二级类别时传一级类别id
     * @return
     */
    List<HashMap>  businessCategory(@Param("business_id") Integer business_id, @Param("level") Integer level);

    /**
     * 判断是大类别还是小类别
     * @param category_id 类别id
     * @return
     */
    Integer largeClassOrSmalClass(@Param("id") Integer category_id);

    /**
     * 店铺类别下的产品总数
     * @param search
     * @return
     */
    Integer businessGoodsCount(Search search);

    /**
     * 店铺类别下的产品
     * @param search
     * @return
     */
    List<HashMap> businessGoods(Search search);

    /**
     * 用户最后登录的商家满减活动
     * @param business_id 店铺id
     * @return
     * @throws Exception
     */
    List<HashMap> buserLastLoginBusinessActivity(@Param("business_id") Integer business_id);

    /**
     * 店铺满减活动
     * @param business_id 店铺ID
     * @return
     * @throws Exception
     */
    List<HashMap> businessActivity(@Param("business_id") Integer business_id);

    /**
     * 取消绑定
     * @param binding_id 绑定id
     * @return
     */
    Integer cancelBinding(@Param("id") Integer binding_id);

    /**
     * 查询注册用户表里最后一次登录的绑定店铺
     * @param binding_id
     * @return
     */
    Integer userBDidByBinding_id(@Param("binding_id") Integer binding_id);

    /**
     * 通过注册ID修改最后一次记录登录的店铺ID
     * @param user_id 注册ID
     * @return
     */
    Integer updateBDIdByUser_id(@Param("id") Integer user_id);

    /**
     * 限时秒杀时间
     * @param binding_id
     * @return
     */
    List<HashMap> limitedSeckill(@Param("binding_id") Integer binding_id);

    /**
     * 限时秒杀时间段下的产品总数
     * @param seckill_id
     * @return
     */
    Integer  limitedSeckillGoodsCount(@Param("seckill_id") Integer seckill_id);

    /**
     * 限时秒杀时间段下的产品
     * @param seckill_id
     * @return
     */
    List<HashMap>  limitedSeckillGoods(@Param("seckill_id") Integer seckill_id, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 查询手机号是否申请过商家
     * @param phone
     * @return
     */
    Integer phoneIfRegister(@Param("phone") String phone);

    /**
     * 查询手机号是否申请过商家
     * @param phone
     * @return
     */
    Integer phoneIfStaffRegister(@Param("phone") String phone);

    /**
     * //判断邀请码是否存在（包括客户，员工，商家）
     * @param phone
     * @return
     */
    Integer ifBusinessUserStaffPhone(@Param("phone") String phone);

    /**
     * 查询最后一条商家编号
     * @return
     */
    String selectLastNumber();

    /**
     * 申请商家
     * @param business
     * @return
     */
    Integer addBuseinss(Business business);

    /**
     * 添加商家盘点
     * @param business_id
     * @return
     */
    Integer addClerk(@Param("business_id") Integer business_id);

    /**
     * 添加模板
     * @param template
     * @return
     */
    Integer addTemplate(Template template);

    /**
     * 添加模板2
     * @param business_id
     * @param title
     * @param type
     * @param one
     * @return
     */
    Integer addTemplateTwo( @Param("business_id") Integer business_id,@Param("title") String title ,@Param("type") Integer type ,@Param("one") String one );

    /**
     * 添加模板3
     * @param business_id
     * @param title
     * @param type
     * @param one
     * @return
     */
    Integer addTemplateThree( @Param("business_id") Integer business_id,@Param("title") String title ,@Param("type") Integer type ,@Param("one") String one);

    /**
     * 添加模板5
     * @param business_id
     * @param title
     * @param type
     * @param one
     * @return
     */
    Integer addTemplateFive( @Param("business_id") Integer business_id,@Param("title") String title ,@Param("type") Integer type ,@Param("one") String one);



    /**
     * 添加商家菜单方案
     * @param bid
     * @param name
     * @return
     */
    Integer addBusinessScheme(@Param("scheme_id") Integer scheme_id, @Param("bid") Integer bid, @Param("name") String name);

    /**
     * 查询店铺优惠券信息
     * @param coupon_id 优惠券id
     * @return
     */
    HashMap selectCoupon(Integer coupon_id);

    /**
     * 领取优惠券
     * @param coupon
     * @return
     * @throws Exception
     */
    Integer addBindingCoupon(Coupon coupon);

    /**
     * 通过绑定ID查询店铺电话
     * @param binding_id
     * @return
     */
    String busienssPhone(@Param("binding_id") Integer binding_id);


    String businessToken(@Param("id") Integer business_id);

    /**
     * 通过用户优惠券id修改优惠券使用状态
     * @param coupon_id
     * @return
     */
    Integer updateUserCouponState(@Param("id") Integer coupon_id, @Param("state") Integer state);

}
