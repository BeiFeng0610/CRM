package com.beifeng.crm.workbench.service.impl;

import com.beifeng.crm.utils.SqlSessionUtil;
import com.beifeng.crm.workbench.dao.ClueDao;
import com.beifeng.crm.workbench.service.ClueService;

public class ClueServiceImpl implements ClueService {

    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);

}
