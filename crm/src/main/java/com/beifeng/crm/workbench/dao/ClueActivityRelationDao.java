package com.beifeng.crm.workbench.dao;

import com.beifeng.crm.workbench.domain.ClueActivityRelation;

public interface ClueActivityRelationDao {


    int unbund(String id);

    int bund(ClueActivityRelation car);
}
