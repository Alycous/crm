package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.vo.PaginationVo;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    List<User> getOwner();

    boolean saveActivity(Activity ac);

    PaginationVo<Activity> pagelist(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> getUlistAndActivity(String id);

    boolean updateActivity(Activity ac);

    Activity detail(String id);

    List<ActivityRemark> getRemarkList(String activityId);

    boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark ar);

    boolean updateRemark(ActivityRemark ar);

    List<Activity> getActivitysByClueId(String id);

    List<Activity> getActivitysByName(Map<String, String> map);

    List<Activity> getActivitysByLikeName(String name);
}
