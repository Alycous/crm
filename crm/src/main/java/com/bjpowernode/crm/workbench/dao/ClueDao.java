package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {


    int save(Clue clue);

    List<Clue> getClueList(Map<String, Object> map);

    int getCount(Map<String, Object> map);

    Clue detail(String id);

    Clue getClueById(String clueId);

    int delete(String clueId);
}
