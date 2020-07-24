package com.beifeng.crm.web.filter;

import com.beifeng.crm.settings.domain.User;

import javax.servlet.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        System.out.println("进入到验证有没有登录过的过滤器");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String path = request.getServletPath();

        HttpSession serssion = request.getSession();
        User user = (User) serssion.getAttribute("user");

        if (path.contains("login")){

            chain.doFilter(req,resp);

        }else {

            // 如果user不为null，说明登录过
            if (user != null){

                chain.doFilter(req,resp);

                // 没有登录过
            }else {

            /*
                这里需要使用重定向，而不是请求转发
             */

                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }

        }


    }
}
