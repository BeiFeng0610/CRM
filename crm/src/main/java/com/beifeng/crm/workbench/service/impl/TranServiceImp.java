package com.beifeng.crm.workbench.service.impl;

import com.beifeng.crm.utils.SqlSessionUtil;
import com.beifeng.crm.workbench.dao.TranDao;
import com.beifeng.crm.workbench.dao.TranHistoryDao;
import com.beifeng.crm.workbench.service.TranService;

public class TranServiceImp implements TranService {

    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);


}
