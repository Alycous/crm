package com.bjpowernode.crm;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ClueDao;
import com.bjpowernode.crm.workbench.domain.Clue;
import org.junit.Test;

public class ClueTest {
    @Test
    public void getClueByIdTest(){
        ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
        Clue c = clueDao.getClueById("f45f54efde704468815b51557086b4bb");
        System.out.println(c.getFullname());
    }
}
