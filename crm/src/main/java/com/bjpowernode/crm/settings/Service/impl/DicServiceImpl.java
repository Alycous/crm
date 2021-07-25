package com.bjpowernode.crm.settings.Service.impl;

import com.bjpowernode.crm.settings.Service.DicService;
import com.bjpowernode.crm.settings.dao.DicTypeDao;
import com.bjpowernode.crm.settings.dao.DicValueDao;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImpl implements DicService {
    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    public Map<String, List<DicValue>> getDic() {

        Map<String,List<DicValue>> map = new HashMap<String, List<DicValue>>();
        //1 获取codetype

        List<DicType> dicTypes = dicTypeDao.getDicTypeList();

        //2 获取dicvalue
        for (DicType d : dicTypes){
            String code = d.getCode();
            List<DicValue> dicValues = dicValueDao.getDicValuesByCode(code);
            map.put(code,dicValues);
        }

        return map;
    }
}
