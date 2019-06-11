package com.shengxian.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Description: 继承WebMvcConfigurerAdapter类并重写addInterceptors方法；
 *
 * @Author: yang
 * @Date: 2019-03-27
 * @param
 * @Version: 1.0
 */
@SpringBootConfiguration
public class MyStringMVCConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private InterceptorConfig loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**")
                .excludePathPatterns("/user/ERROR_URL1.do" ,"/user/ERROR_URL3.do" ,"/user/ERROR_URL4.do" ) //异常跳转
                //查询APP版本号,协议书,获取验证码,用户注册 ,快速登录 ,忘记密码
                .excludePathPatterns("/user/version.do" ,"/user/agreement.do" ,"/user/sendSms.do" ,"/user/register.do" ,"/user/fastLogin.do","/user/forgetPwd.do")
                //修改手机设备,app中判断在是否有退出登录过,在是否有退出登录过,登录 ,首页轮播图
                .excludePathPatterns("/user/updateEquipment.do" ,"/user/appIsLogin.do" ,"/user/seleteUserStatus.do" ,"/user/login.do","/user/broadcastpicture.do")
                //系统公告
                .excludePathPatterns("/user/systemtBulletin.do" ,"/user/find.do")
                //用户收藏的产品 ,用户绑定的商家列表 ,用户最后登录的商家
                .excludePathPatterns("/user/userCollectionGoods.do" ,"/user/userBindingBusiness.do" ,"/user/userLastLoginBusiness.do")
                //查询店铺类别 ,店铺类别下的产品
                .excludePathPatterns("/user/businessCategory.do" ,"/user/businessGoods.do" ,"/user/goodsDetali.do");
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
