package com.bjpowernode.crm.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest res = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String servletPath = res.getServletPath();
        if ("/login.jsp".equals(servletPath) || "/settings/user/login.do".equals(servletPath)){
            chain.doFilter(request, response);
        }else {
            Object user = res.getSession().getAttribute("user");
            if (user == null){
                resp.sendRedirect(res.getContextPath() + "/login.jsp");
            }else {
                chain.doFilter(request, response);
            }
        }
    }
}
