package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.vo.PaginationVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);


    public List<User> getOwner() {
        List<User> userList = activityDao.getOwner();
        return userList;
    }

    public boolean saveActivity(Activity ac) {
        boolean flag = false;
        int result = activityDao.saveActivity(ac);
        if (result == 1) {
            flag = true;
        }
        return flag;
    }

    public PaginationVo<Activity> pagelist(Map<String, Object> map) {
        List<Activity> datalist = activityDao.getActivityList(map);
        int total = activityDao.getCount(map);
        PaginationVo<Activity> vo = new PaginationVo<Activity>();
        vo.setTotal(total);
        vo.setDatalist(datalist);
        return vo;
    }

    public boolean delete(String[] ids) {
        boolean flag = false;
        //1 查询出所需要删除的备注总数
        int count1 = activityRemarkDao.getCount(ids);
        //2 删除备注返回实际删除数量
        int count2 = activityRemarkDao.deleteByAids(ids);
        //3 删除活动数
        int count3 = activityDao.deleteByids(ids);

        if (count1 == count2 && count3 == ids.length) {
            flag = true;
        }
        return flag;
    }

    public Map<String, Object> getUlistAndActivity(String id) {
        //1 获取ulist
        List<User> ulist = activityDao.getOwner();
        //2 获取activity
        Activity activity = activityDao.getActivityById(id);
        //3 新建map
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ulist", ulist);
        map.put("activity", activity);
        return map;
    }

    public boolean updateActivity(Activity ac) {
        boolean flag = false;
        int result = activityDao.update(ac);
        if (result == 1) {
            flag = true;
        }
        return flag;
    }

    public Activity detail(String id) {
        return activityDao.detail(id);
    }

    public List<ActivityRemark> getRemarkList(String activityId) {
        List<ActivityRemark> list = activityRemarkDao.getRemarkList(activityId);
        return list;
    }

    public boolean deleteRemark(String id) {
        int result = activityRemarkDao.deleteById(id);
        return result == 1;
    }

    public boolean saveRemark(ActivityRemark ar) {

        return activityRemarkDao.saveRemark(ar) == 1;
    }

    public boolean updateRemark(ActivityRemark ar) {

        return activityRemarkDao.updateRemark(ar) == 1;
    }

    public List<Activity> getActivitysByClueId(String id) {
        return activityDao.getActivitysByClueId(id);
    }

    public List<Activity> getActivitysByName(Map<String, String> map) {
        return activityDao.getActivitysByName(map);
    }

    public List<Activity> getActivitysByLikeName(String name) {
        return activityDao.getActivitysByLikeName(name);
    }

}
