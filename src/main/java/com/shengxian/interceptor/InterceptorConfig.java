package com.shengxian.interceptor;

import com.shengxian.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * Description: controller方法前拦截器
 *
 * @Author: yang
 * @Date: 2019-03-27
 * @Version: 1.0
 */
@Component
public class InterceptorConfig implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(InterceptorConfig.class);

    @Resource
    private UserMapper userMapper;

    //前置
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        log.info("---------------------开始进入请求地址拦截----------------------------");

        //查询该登录用户是否上线，如果上线，挤掉上一个
        //检测token是否正确
        String token = request.getParameter("token");
        if(token == null || token.trim().equals("")){
            request.getRequestDispatcher(ERROR_URL1).forward(request, response);
            return false;
        }
        HashMap user = userMapper.userByToken(token);
        if(user ==null){
            request.getRequestDispatcher(ERROR_URL3).forward(request, response);
            return false;
        }else {
            if (user.get("is_disable").toString().equals("1")){
                request.getRequestDispatcher(ERROR_URL4).forward(request, response);
                return false;
            }
        }
        return true;
    }

    //后置
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    //环绕
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


    /**
     * token为空
     */

    private static final String  ERROR_URL1 = "/user/ERROR_URL1.do";

    /**
     * 角色不能为空
     */

    private static final String ERROR_URL2 = "/user/ERROR_URL2.do";

    /**
     * token失效url
     */

    private static final String ERROR_URL3 = "/user/ERROR_URL3.do";

    /**
     * 冻结url
     */

    private static final String ERROR_URL4 = "/user/ERROR_URL4.do";

    /**
     * 店铺使用权限
     */

    private static final String ERROR_URL5 = "/user/ERROR_URL5.do";
}
