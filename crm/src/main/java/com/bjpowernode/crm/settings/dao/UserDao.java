package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {

    User login(@Param("logAct") String logAct, @Param("logPwd") String logPwd);
}
