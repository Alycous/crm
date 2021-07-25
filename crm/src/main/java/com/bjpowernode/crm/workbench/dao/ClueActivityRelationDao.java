package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    int unband(String id);

    int bund(ClueActivityRelation car);

    List<ClueActivityRelation> getRelationList(String clueId);

    int delete(ClueActivityRelation clueActivityRelation);
}
