package com.bjpowernode.crm.settings.Service.impl;

import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.settings.Service.UserService;
import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;

public class UserServiceImpl implements UserService {

    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    public User login(String logAct, String logPwd, String ip) throws LoginException {
        User user =  userDao.login(logAct,logPwd);
        if (user == null){
            throw new LoginException("账号密码不正确！");
        }
        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)){
            throw new LoginException("ip不正确！");
        }
        String expireTime = user.getExpireTime();
        String sysTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(sysTime) < 0){
            throw new LoginException("账号已失效！");
        }
        String lockState = user.getLockState();
        if ("0".equals(lockState)){
            throw new LoginException("账户已冻结");
        }
        return user;
    }
}
