package com.beifeng.crm.workbench.service.impl;

import com.beifeng.crm.exception.CRUDException;
import com.beifeng.crm.utils.SqlSessionUtil;
import com.beifeng.crm.workbench.dao.ClueDao;
import com.beifeng.crm.workbench.domain.Clue;
import com.beifeng.crm.workbench.service.ClueService;

public class ClueServiceImpl implements ClueService {

    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);

    @Override
    public boolean save(Clue c) throws CRUDException {

        if ("" == c.getCompany() || c.getCompany() == null){

            throw new CRUDException("公司不能为空");
        }

        if ("" == c.getFullname() || c.getFullname() == null){

            throw new CRUDException("姓名不能为空");
        }

        int count = clueDao.save(c);

        if (count!=1){

            throw new CRUDException("添加失败");

        }

        return true;
    }

    @Override
    public Clue detail(String id) {

        Clue c = clueDao.detail(id);

        return c;
    }
}
