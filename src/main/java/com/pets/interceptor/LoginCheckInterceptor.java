package com.pets.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.pets.utils.base.JwtUtils;
import com.pets.utils.base.ResponseData;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String path = request.getRequestURI();
//        System.out.println("拦截请求路径: " + path);
//
//        //获得请求头令牌
//        String jwt = request.getHeader("token");
//        //解析令牌是否存在(如果不存在)
//        if(!StringUtils.hasLength(jwt)){
//            ResponseData error = ResponseData.NoLogin("请先登录!");
//            String notLogin = JSONObject.toJSONString(error);
//            response.getWriter().write(notLogin);
//            return false;
//        }
//        //解析token（如果不存在）
//        try {
//            JwtUtils.parseJWT(jwt);
//        } catch (Exception e) {
//            e.printStackTrace();
//            ResponseData error =ResponseData.JwtTimeout("请先登录!");
//            String notLogin = JSONObject.toJSONString(error);
//            response.getWriter().write(notLogin);
//            return false;
//        }
//        //一切正常
//        return true;
//    }


//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String path = request.getRequestURI();
//        System.out.println("拦截请求路径: " + path);
//
//        // 获取 Authorization 请求头
//        String authorizationHeader = request.getHeader("Authorization");
//
//        // 判断 Authorization 头是否为空
//        if (!StringUtils.hasLength(authorizationHeader)) {
//            ResponseData error = ResponseData.NoLogin("请先登录!");
//            String notLogin = JSONObject.toJSONString(error);
//            response.getWriter().write(notLogin);
//            return false;
//        }
//
//        // 检查 Authorization 头是否包含 Bearer
//        if (authorizationHeader.startsWith("Bearer ")) {
//            // 提取 Bearer 后面的 token
//            String jwt = authorizationHeader.substring(7); // 获取 "Bearer " 后面的部分
//            try {
//                JwtUtils.parseJWT(jwt);  // 解析 token
//            } catch (Exception e) {
//                e.printStackTrace();
//                ResponseData error = ResponseData.JwtTimeout("请重新登录!");
//                String notLogin = JSONObject.toJSONString(error);
//                response.getWriter().write(notLogin);
//                return false;
//            }
//        } else {
//            // 如果 Authorization 头没有 Bearer 前缀
//            ResponseData error = ResponseData.NoLogin("无效的 token 格式!");
//            String notLogin = JSONObject.toJSONString(error);
//            response.getWriter().write(notLogin);
//            return false;
//        }
//
//        // 如果 token 正常，允许请求继续
//        return true;
//    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        System.out.println("拦截请求路径: " + path);

        // 获取请求头中的令牌
        String jwt = request.getHeader("token");

        try {
            // 如果令牌为空
            if (!StringUtils.hasLength(jwt)) {
                writeSuccessResponse(response, ResponseData.NoLogin("请先登录!"));
                return false;
            }

            // 解析令牌
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            // 处理解析失败情况
            writeSuccessResponse(response, ResponseData.JwtTimeout("请重新登录!"));
            return false;
        }

        // 验证成功，继续后续处理
        return true;
    }

    // 通用的错误响应写入方法
    private void writeErrorResponse(HttpServletResponse response, ResponseData error) throws IOException, java.io.IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 设置 HTTP 状态码
        response.getWriter().write(JSONObject.toJSONString(error)); // 写入错误信息
    }

    // 通用的成功响应写入方法
    private void writeSuccessResponse(HttpServletResponse response, ResponseData successData) throws IOException, java.io.IOException {
        response.setContentType("application/json;charset=UTF-8");  // 设置响应的内容类型
        response.setStatus(HttpServletResponse.SC_OK);  // 设置 HTTP 状态码为 200（成功）
        response.getWriter().write(JSONObject.toJSONString(successData));  // 写入响应数据
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
