package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.*;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.vo.PaginationVo;

import java.util.List;
import java.util.Map;

public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);
    private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);
    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    private ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);

    public boolean save(Clue clue) {
        return clueDao.save(clue) == 1;
    }

    public PaginationVo<Clue> pagelist(Map<String, Object> map) {
        List<Clue> datalist = clueDao.getClueList(map);
        int total = clueDao.getCount(map);
        PaginationVo<Clue> vo = new PaginationVo<Clue>();
        vo.setTotal(total);
        vo.setDatalist(datalist);
        return vo;
    }

    public Clue detail(String id) {
        Clue clue = clueDao.detail(id);
        return clue;
    }

    public boolean unband(String id) {
        return clueActivityRelationDao.unband(id) == 1;
    }

    public boolean bund(String cId, String[] aIds) {
        boolean flag = true;
        for (String aid : aIds) {
            ClueActivityRelation car = new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setClueId(cId);
            car.setActivityId(aid);
            int result = clueActivityRelationDao.bund(car);
            if (result != 1) {
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public boolean convert(String clueId, Tran t, String createBy) {
        boolean flag = true;
        String createTime = DateTimeUtil.getSysTime();
        //??????????????????????????????
        //(1) ??????????????? id??????????????? id ???????????????????????????????????????????????????????????? ??????
        Clue c = clueDao.getClueById(clueId);
        //(2) ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        String company = c.getCompany();
        Customer customer = customerDao.getCustomerByName(company);
        if (customer == null) {
            customer = new Customer();
            customer.setAddress(c.getAddress());
            customer.setContactSummary(c.getContactSummary());
            customer.setCreateBy(createBy);
            customer.setDescription(c.getDescription());
            customer.setName(company);
            customer.setId(UUIDUtil.getUUID());
            customer.setNextContactTime(c.getNextContactTime());
            customer.setOwner(c.getOwner());
            customer.setWebsite(c.getWebsite());
            customer.setPhone(c.getPhone());
            customer.setCreateTime(createTime);
            int count = customerDao.save(customer);
            if (count != 1) {
                flag = false;
            }
        }
        //(3) ?????????????????????????????????????????????????????????
        Contacts contacts = new Contacts();
        contacts.setAddress(c.getAddress());
        contacts.setContactSummary(c.getContactSummary());
        contacts.setAppellation(c.getAppellation());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(createTime);
        contacts.setDescription(c.getDescription());
        contacts.setCustomerId(customer.getId());
        contacts.setEmail(c.getEmail());
        contacts.setSource(c.getSource());
        contacts.setFullname(c.getFullname());
        contacts.setOwner(c.getOwner());
        contacts.setNextContactTime(c.getNextContactTime());
        contacts.setJob(c.getJob());
        contacts.setMphone(c.getMphone());
        contacts.setId(UUIDUtil.getUUID());
        int count2 = contactsDao.save(contacts);
        if (count2 != 1) {
            flag = false;
        }
        //(4) ??????????????????????????????????????????????????????
        List<ClueRemark> clueRemarks = clueRemarkDao.getRemarksByClueId(clueId);
        for (ClueRemark clueRemark : clueRemarks) {
            String noteContent = clueRemark.getNoteContent();
            //??????????????????
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setEditFlag("0");
            customerRemark.setNoteContent(noteContent);
            customerRemark.setCustomerId(customer.getId());
            int count3 = customerRemarkDao.save(customerRemark);
            if (count3 != 1) {
                flag = false;
            }
            //?????????????????????
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setEditFlag("0");
            contactsRemark.setNoteContent(noteContent);
            contactsRemark.setContactsId(contacts.getId());
            int count4 = contactsRemarkDao.save(contactsRemark);
            if (count4 != 1) {
                flag = false;
            }
        }
        //(5) ????????????????????????????????????????????????????????????????????????????????????
        List<ClueActivityRelation> clueActivityRelations = clueActivityRelationDao.getRelationList(clueId);
        for (ClueActivityRelation clueActivityRelation : clueActivityRelations) {
            String activityId = clueActivityRelation.getActivityId();
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(contacts.getId());
            int count5 = contactsActivityRelationDao.save(contactsActivityRelation);
            if (count5 != 1) {
                flag = false;
            }
        }
        //(6) ????????????????????????????????????????????????
        if (t != null){
            //?????????????????? id createBy createTime expectedDate money name stage activityId
            t.setSource(c.getSource());
            t.setOwner(c.getOwner());
            t.setCustomerId(customer.getId());
            t.setContactsId(contacts.getId());
            t.setContactSummary(c.getContactSummary());
            t.setDescription(c.getDescription());
            t.setNextContactTime(c.getNextContactTime());
            int count6 = tranDao.save(t);
            if (count6 != 1) {
                flag = false;
            }
            //(7) ?????????????????????????????????????????????????????????????????? ?????????
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(createTime);
            tranHistory.setExpectedDate(t.getExpectedDate());
            tranHistory.setMoney(t.getMoney());
            tranHistory.setStage(t.getStage());
            tranHistory.setTranId(t.getId());
            int count7 = tranHistoryDao.save(tranHistory);
            if (count7 != 1) {
                flag = false;
            }
        }

        //(8) ??????????????????
        for (ClueRemark clueRemark : clueRemarks){
            int count8 = clueRemarkDao.delete(clueRemark);
            if (count8 != 1) {
                flag = false;
            }
        }
        //(9) ????????????????????????????????????
        for (ClueActivityRelation clueActivityRelation : clueActivityRelations) {
            int count9 = clueActivityRelationDao.delete(clueActivityRelation);
            if (count9 != 1) {
                flag = false;
            }
        }
        //(10) ????????????
        int count10 = clueDao.delete(clueId);
        if (count10 != 1) {
            flag = false;
        }
        return flag;
    }
}
