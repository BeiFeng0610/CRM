package com.beifeng.crm.workbench.service;

import com.beifeng.crm.exception.CRUDException;
import com.beifeng.crm.workbench.domain.Clue;

public interface ClueService {

    boolean save(Clue c) throws CRUDException;

    Clue detail(String id);
}
