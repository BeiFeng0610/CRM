package com.beifeng.crm.workbench.service;

import com.beifeng.crm.exception.CRUDException;
import com.beifeng.crm.vo.PaginationVO;
import com.beifeng.crm.workbench.domain.Activity;

import java.util.Map;

public interface ActivityService {

    boolean save(Activity a) throws CRUDException;

    PaginationVO<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    boolean update(Activity a) throws CRUDException;

    Activity detail(String id);
}
