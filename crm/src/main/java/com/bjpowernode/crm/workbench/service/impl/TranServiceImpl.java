package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.dao.TranDao;
import com.bjpowernode.crm.workbench.dao.TranHistoryDao;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.service.TranService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranServiceImpl implements TranService {
    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public boolean save(Tran t, String customerName) {
        boolean flag = true;
        //1 判断是否存在customer
        Customer customer = customerDao.getCustomerByName(customerName);
        if (customer == null) {
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setContactSummary(t.getContactSummary());
            customer.setNextContactTime(t.getNextContactTime());
            customer.setCreateBy(t.getCreateBy());
            customer.setDescription(t.getDescription());
            customer.setName(customerName);
            customer.setOwner(t.getOwner());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            int count = customerDao.save(customer);
            if (count != 1) {
                flag = false;
            }
        }
        //2 获取id
        t.setCustomerId(customer.getId());
        //3添加交易
        int count2 = tranDao.save(t);
        if (count2 != 1) {
            flag = false;
        }
        //4添加交易历史记录
        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setCreateBy(t.getCreateBy());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setMoney(t.getMoney());
        tranHistory.setStage(t.getStage());
        tranHistory.setTranId(t.getId());
        tranHistory.setExpectedDate(t.getExpectedDate());
        int count3 = tranHistoryDao.save(tranHistory);
        if (count3 != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public Tran detail(String id) {
        return tranDao.detail(id);
    }

    @Override
    public List<TranHistory> getHistorysByTranId(String tranId) {
        return tranHistoryDao.getHistorysByTranId(tranId);
    }

    @Override
    public boolean changeStage(Tran t) {
        boolean flag = true;
        //1 修改交易
        int count = tranDao.update(t);
        //2 添加交易历史
        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setCreateBy(t.getEditBy());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setStage(t.getStage());
        tranHistory.setTranId(t.getId());
        tranHistory.setExpectedDate(t.getExpectedDate());
        tranHistory.setStage(t.getStage());
        int count2 = tranHistoryDao.save(tranHistory);
        if (count != 1 || count2 !=1){
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getCharts() {
        Map<String, Object> map = new HashMap<>();
        //1 total
        int total = tranDao.total();
        //2 datalist
        List<Map<String,Object>> datalist = tranDao.getDatalist();
        //3 put
        map.put("total",total);
        map.put("datalist",datalist);

        return map;
    }
}
