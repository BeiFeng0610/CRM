package com.beifeng.crm.workbench.dao;

public interface ActivityRemarkDao {


    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

}
