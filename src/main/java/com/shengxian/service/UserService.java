package com.shengxian.service;

import com.shengxian.common.util.Page;
import com.shengxian.entity.Business;
import com.shengxian.vo.BindingInfoVO;
import com.shengxian.vo.GoodsCategoryVO;

import java.util.HashMap;
import java.util.List;

/**
 * Description:
 *
 * @Author: yang
 * @Date: 2018-12-30
 * @Version: 1.0
 */
public interface UserService {

    /**
     * 查询版本号
     * @return
     */
    String version()throws Exception;

    /**
     * 协议书
     * @return
     * @throws Exception
     */
    HashMap agreement()throws Exception;

    Integer sendSms(String phone)throws NullPointerException ,Exception;

    /**
     * 用户注册
     * @param phone
     * @param password
     * @return
     */
    Integer register(String phone, String password)throws NullPointerException, Exception;

    /**
     * 快速登录
     * @param phone
     * @return
     */
    String fastLogin(String phone)throws NullPointerException ,Exception;

    /**
     * 忘记密码
     * @param phone
     * @param password
     * @return
     */
    boolean forgetPwd(String phone, String password)throws NullPointerException,Exception;


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
    Integer  updateEquipment(String token, String model, String system, String version, String platform, String SDKVersion)throws NullPointerException ,Exception;

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
    boolean appIsLogin(String phone, String model, String system, String version, String platform, String SDKVersion);


    /**
     * 在微信公众号是否有退出登录过
     * @param phone
     * @return
     * @throws NullPointerException
     * @throws Exception
     */
    Integer seleteUserStatus(String phone)throws NullPointerException ,Exception;

    /**
     * 在微信公众号退出登录修改状态
     * @param token
     * @return
     * @throws NullPointerException
     * @throws Exception
     */
    Integer updateUserStatus(String token)throws NullPointerException ,Exception;

    /**
     * 登录
     * @param phone
     * @param password
     * @return
     * @throws NullPointerException
     * @throws Exception
     */
    String login(String phone, String password)throws NullPointerException ,Exception;

    /**
     * 获取店铺名称和图片
     * @param token
     * @return
     */
    HashMap getBusinessNameAndImg(String token);

    /**
     * 店铺有活动，首次切换弹出活动窗口，关闭下次不用弹出
     * @param token
     * @return
     * @throws Exception
     */
    List<HashMap> activityIsRead(String token)throws NullPointerException, Exception;

    /**
     * 关闭弹出活动窗口
     * @param token
     * @return
     * @throws Exception
     */
    Integer updateCouponState(String token)throws Exception;


    /**
     * 首页轮播图
     * @return
     */
    List<HashMap> broadcastpicture()throws Exception;

    /**
     * 系统公告
     * @return
     */
    HashMap systemtBulletin()throws Exception ;

    /**
     * 用户最后登录的商家
     * @param token
     * @return
     * @throws Exception
     */
    HashMap userLastLoginBusiness(String token) throws NullPointerException, Exception;

    /**
     * 添加申请绑定商家记录
     * @param token
     * @param phone
     * @return
     * @throws NullPointerException
     * @throws Exception
     */
    Integer addApplyBindingBuseinss(String token, String phone)throws NullPointerException, Exception;

    /**
     * 扫描申请绑定店铺
     * @param token
     * @param business_id
     * @return
     */
    Integer scanBindingBusiness(String token, Integer business_id)throws NullPointerException ,Exception;

    /**
     * 用户绑定的商家列表
     * @param token
     * @param pageNo
     * @return
     */
    Page userBindingBusiness(String token, Integer pageNo)throws Exception;



    /**
     * 切换商家时修改最后登录的商家
     * @param token
     * @return
     */
    Integer updateUserBDId(String token, Integer binding_id)throws NullPointerException, Exception;

    /**
     * 用户收藏的产品
     * @param token
     * @param pageNo
     * @return
     * @throws Exception
     */
    Page userCollectionGoods(String token, Integer pageNo ,String name )throws Exception;

    /**
     * 通过id查询产品详情
     * @param goods_id
     * @return
     * @throws Exception
     */
    HashMap goodsDetali(String token, Integer goods_id)throws NullPointerException, Exception;

    /**
     * 获取当前的绑定用户是否添加产品到购物车了
     * @param token
     * @param goodsId
     * @return
     */
    HashMap getGoodsIsAddCart(String token, Integer goodsId);

    /**
     * 添加收藏
     * @param token
     * @param goods_id
     * @return
     * @throws Exception
     */
    Integer addCollection(String token, Integer goods_id,Integer business_id)throws NullPointerException, Exception;

    /**
     * 取消收藏
     * @param collection_id
     * @return
     * @throws Exception
     */
    Integer delectCollection(Integer collection_id)throws Exception;

    /**
     * 查询店铺类别
     * @param business_id 店铺id
     * @param level 二级类别时传一级类别id
     * @return
     */
    List<HashMap>  businessCategory(Integer business_id, Integer level);


    /**
     * 获取店铺类别集合（小程序）
     * @param businessId
     * @return
     */
    List<GoodsCategoryVO> getCategroyList( Integer businessId);

    /**
     * 店铺类别下的产品
     * @param pageNo
     * @param business_id
     * @param category_id
     * @param name
     * @return
     */
    Page businessGoods(String token, Integer pageNo, Integer business_id, Integer category_id, String name)throws NullPointerException, Exception;

    /**
     * 店铺满减活动
     * @param token
     * @return
     * @throws Exception
     */
    List<HashMap> businessActivity(String token)throws Exception;

    /**
     * 取消绑定
     * @param binding_id
     * @return
     */
    Integer cancelBinding(Integer binding_id)throws Exception;

    /**
     * 限时秒杀时间
     * @param token
     * @return
     * @throws Exception
     */
    List<HashMap> limitedSeckill(String token)throws Exception;

    /**
     * 限时秒杀时间段的产品
     * @param pageNo
     * @param seckill_id
     * @return
     * @throws Exception
     */
    Page limitedSeckillGoods(Integer pageNo, Integer seckill_id)throws Exception;

    /**
     * 申请商家
     * @param business
     * @return
     */
    Integer addBuseinss(Business business)throws NullPointerException ,Exception;

    /**
     * 领取优惠券
     * @param token
     * @param coupon_id
     * @return
     * @throws Exception
     */
    Integer addBindingCoupon(String token, Integer coupon_id)throws NullPointerException,Exception;

    /**
     * 店铺电话
     * @param token
     * @return
     */
    String busienssPhone(String token)throws NullPointerException ,Exception;


    BindingInfoVO getAllNeedAttribute(String token , Integer businessId);

    /**
     * 获取店铺满赠商品
     * @param token
     * @param pageNo
     * @return
     */
    Page getFullGiftGoodsList(String token , Integer pageNo);

    /**
     * 获取店铺限购活动商品
     * @param token
     * @param pageNo
     * @return
     */
    Page getRestrictionsGoodsList(String token , Integer pageNo);


}
