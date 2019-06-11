package com.shengxian.controller;

import com.alibaba.fastjson.JSONObject;
import com.shengxian.common.HttpClientManager;
import com.shengxian.common.util.Global;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Description:
 *
 * @Author: yang
 * @Date: 2019-05-06
 * @Version: 1.0
 */
@Controller
public class WXController {


    @RequestMapping("loginInit.do")
    public String loginInit(HttpServletRequest request, HttpServletResponse response)  {

        try {
            //回调地址,要跟下面的地址能调通(getWechatGZAccessToken.do)
            String backUrl = "http://3261045722cwy.vicp.io/wx/getWechatGZAccessToken.do";
            /**
             *这儿一定要注意！！首尾不能有多的空格（因为直接复制往往会多出空格），其次就是参数的顺序不能变动
             **/
            //AuthUtil.APPID微信公众号的appId
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Global.APPID +
                    "&redirect_uri=" + URLEncoder.encode(backUrl, "UTF-8") +
                    "&response_type=code" +
                    "&scope=snsapi_userinfo" +
                    "&state=STATE#wechat_redirect";
            return "redirect:" + url;
        }catch (Exception e){
            return null;
        }
    }


    @RequestMapping("getWechatGZAccessToken.do")
    public String getWechatGZAccessToken(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //微信公众号的APPID和APPSECRET
        String code=request.getParameter("code");
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Global.APPID+
                "&secret=" +Global.APPSECRET+
                "&code=" +code+
                "&grant_type=authorization_code";
        String result = HttpClientManager.getUrlData(url);
        Map<String,Object> data = JSONObject.parseObject(result);
        String openid=data.get("openid").toString();
        String token=data.get("access_token").toString();
        //获取信息
        String infoUrl="https://api.weixin.qq.com/sns/userinfo?access_token=" +token+
                "&openid=" +openid+
                "&lang=zh_CN";
        String infoResult = HttpClientManager.getUrlData(infoUrl);
        return infoResult;

    }


}
