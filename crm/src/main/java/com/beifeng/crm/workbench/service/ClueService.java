package com.beifeng.crm.workbench.service;

import com.beifeng.crm.exception.CRUDException;
import com.beifeng.crm.workbench.domain.Clue;
import com.beifeng.crm.workbench.domain.Tran;

import javax.servlet.http.HttpServletRequest;

public interface ClueService {

    boolean save(Clue c) throws CRUDException;

    Clue detail(String id);

    boolean unbund(String id);

    boolean bund(String cid, String[] aids);

    boolean convert(String clueId, Tran t, String createBy);
}
