package com.beifeng.crm.workbench.service.impl;

import com.beifeng.crm.utils.SqlSessionUtil;
import com.beifeng.crm.workbench.dao.CustomerDao;
import com.beifeng.crm.workbench.service.CustomerService;

import java.util.List;

public class CustomerServiceImp implements CustomerService {

    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<String> getCustomerName(String name) {

        List<String> sList = customerDao.getCustomerName(name);

        return sList;
    }
}
