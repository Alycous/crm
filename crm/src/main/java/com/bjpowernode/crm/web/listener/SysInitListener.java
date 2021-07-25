package com.bjpowernode.crm.web.listener;

import com.bjpowernode.crm.settings.Service.DicService;
import com.bjpowernode.crm.settings.Service.impl.DicServiceImpl;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SysInitListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        System.out.println("服务器缓存数据字典加载开始");

        //1 service 接受 数据字典
        DicService dicService = (DicService) ServiceFactory.getService(new DicServiceImpl());
        Map<String, List<DicValue>> map = dicService.getDic();

        //2 将数据字典保存到上下文作用域
        // map  codetype,dicvaluelist
        ServletContext application = event.getServletContext();
        Set<String> set = map.keySet();
        for (String s : set){
            application.setAttribute(s,map.get(s));
        }




        System.out.println("服务器缓存数据字典加载结束");

    }
}
