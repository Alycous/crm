package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.vo.PaginationVo;

import java.util.Map;

public interface ClueService {
    boolean save(Clue clue);

    PaginationVo<Clue> pagelist(Map<String, Object> map);

    Clue detail(String id);

    boolean unband(String id);

    boolean bund(String cId, String[] aIds);

    boolean convert(String clueId, Tran t, String createBy);
}
