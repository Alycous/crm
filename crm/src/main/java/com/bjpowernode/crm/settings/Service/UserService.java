package com.bjpowernode.crm.settings.Service;

import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.settings.domain.User;

public interface UserService {

    User login(String logAct, String logPwd, String ip) throws LoginException, LoginException;
}
