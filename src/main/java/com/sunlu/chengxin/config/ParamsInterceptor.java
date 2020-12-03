package com.sunlu.chengxin.config;

import com.sunlu.chengxin.common.Utils;
import com.sunlu.chengxin.dao.UserRepository;
import com.sunlu.chengxin.entity.UserEntity;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ParamsInterceptor implements HandlerInterceptor {
    @Resource
    UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //通过referer 防止csrf(跨域请求伪造)
        //request内含有请求的接口地址request.getRequestURI()
        String nowUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
        String referer = request.getHeader("Referer");
        //比对HTTP请求发送的网站(referer字段)和请求的接口网站(request.getScheme())+”://”+request.getServerName())是不是一个网站。
        //如：从swagger请求接口：是允许访问的
        //nowUrl:http://localhost:9998/chengxin/user/getAddress
        //referer:http://localhost:9998/swagger-ui.html
        //referer==null 是指通过postman或者浏览器直接请求的，不是从其他网站跳转的
        //referer==null(如浏览器/postman访问过来 是null)，通过其他地址访问过来时显示来源网址， 下边判断不允许除本网站外调用接口。

        //先注释掉，允许用postman和浏览器测试接口
//        if (referer==null || referer.equals("") || !referer.startsWith(nowUrl)){
//            //返回403
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            System.out.println("非本网站请求地址(比对地址：http://ip+port)---："+"调用的接口域名："+nowUrl+"发起请求来源域名："+referer);
//            return false;
//        }

        // 过滤
        //如果token上传了，继续到下一个控制器
        Map<String, String[]> requestMap = request.getParameterMap();
        response.setCharacterEncoding("utf-8");
        if (requestMap.containsKey("token")  && (!Utils.isEmpty(requestMap.get("token")[0]))){
            //token存在于表中 继续到下一个控制器 不存在 返回token无效
            UserEntity userEntity = userRepository.findByToken(requestMap.get("token")[0]);
            if (userEntity == null){
                System.out.println(request.getRequestURI()+"token无效");
                try {
                    response.getWriter().println("{\"code\":\"200204\",\"msg\":\"token无效\",\"data\":[]}");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }else{
                //toekn 有效并且用户为启用和未删除状态下，继续到下一个控制器
                if (userEntity.getIsDelete()==1 && userEntity.getState() == 1){
                    return true;
                }else{
                    try {
                        response.getWriter().println("{\"code\":\"200205\",\"msg\":\"用户未启用或被删除\",\"data\":[]}");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            }
        }else{
            System.out.println(request.getRequestURI()+"token未传");
            response.getWriter().println("{\"code\":\"200204\",\"msg\":\"token未上传\",\"data\":[]}");
            return false;
        }
    }
}
