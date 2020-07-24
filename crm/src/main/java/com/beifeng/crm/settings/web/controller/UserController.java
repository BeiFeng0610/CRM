package com.beifeng.crm.settings.web.controller;

import com.beifeng.crm.settings.domain.User;
import com.beifeng.crm.settings.service.UserService;
import com.beifeng.crm.settings.service.impl.UserServiceImpl;
import com.beifeng.crm.utils.MD5Util;
import com.beifeng.crm.utils.PrintJson;
import com.beifeng.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到用户控制器");

        String path = request.getServletPath();

        if ("/settings/user/login.do".equals(path)){

            login(request,response);

        }else if ("/settings/user/xxx.do".equals(path)){


        }

    }

    private void login(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入验证登录");
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        // 将密码得明文形式转换为md5
        loginPwd = MD5Util.getMD5(loginPwd);
        // 接收ip地址
        String ip = request.getRemoteAddr();
        System.out.println("ip-----" + ip);

        // 业务层开发统一使用代理类得接口对象
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        try {

            User user = us.login(loginAct,loginPwd,ip);

            request.getSession().setAttribute("user",user);

            // 如果程序执行到此处，说明业务层没有为controller抛出任何异常

            PrintJson.printJsonFlag(response,true);

        }catch (Exception e){
            e.printStackTrace();

            // 一旦程序执行到此处，说明业务层验证登录失败，为controller抛出了异常
            String msg = e.getMessage();
            /*
                作为controller，需要为ajax请求提供多项信息

                两种手段：
                    将多项信息打包为map，将map解析为json串

                    创建一个vo
                        private boolean success;
                        private String msg;
             */
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);

        }

    }
}
