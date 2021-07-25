package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    List<User> getOwner();

    int saveActivity(Activity ac);

    List<Activity> getActivityList(Map<String, Object> map);

    int getCount(Map<String, Object> map);

    int deleteByids(String[] ids);

    Activity getActivityById(String id);

    int update(Activity ac);

    Activity detail(String id);

    List<Activity> getActivitysByClueId(String id);

    List<Activity> getActivitysByName(Map<String, String> map);

    List<Activity> getActivitysByLikeName(String name);
}
