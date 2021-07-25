package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.settings.Service.UserService;
import com.bjpowernode.crm.settings.Service.impl.UserServiceImpl;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.MD5Util;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;

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
        String path = request.getServletPath();
        if ("/settings/user/login.do".equals(path)){
            login(request,response);

        }/*else if ("/settings/user/**.do".equals(path)){

        }*/
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        String logAct = (String) request.getParameter("logAct");
        String logPwd = (String) request.getParameter("logPwd");
        //expireTime  lockState  allowIps
        logPwd = MD5Util.getMD5(logPwd);
        String ip = request.getRemoteAddr();
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());

        try {
            User user = userService.login(logAct,logPwd,ip);
            request.getSession().setAttribute("user",user);
            PrintJson.printJsonFlag(response,true);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }
    }


}
