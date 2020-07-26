package com.beifeng.crm.workbench.service.impl;

import com.beifeng.crm.exception.CRUDException;
import com.beifeng.crm.settings.dao.UserDao;
import com.beifeng.crm.settings.domain.User;
import com.beifeng.crm.utils.SqlSessionUtil;
import com.beifeng.crm.vo.PaginationVO;
import com.beifeng.crm.workbench.dao.ActivityDao;
import com.beifeng.crm.workbench.dao.ActivityRemarkDao;
import com.beifeng.crm.workbench.domain.Activity;
import com.beifeng.crm.workbench.domain.ActivityRemark;
import com.beifeng.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public boolean save(Activity a) throws CRUDException {

        if (a.getName() == null || "" == a.getName()){

            throw new CRUDException("活动名称不能为空");

        }

        int count = activityDao.save(a);

        if (count != 1){

            throw new CRUDException("市场活动添加失败");

        }

        return true;
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {

        // 取得total
        int total = activityDao.getTotalBycondition(map);

        // 取得total和dateList封装到vo中
        List<Activity> dataList = activityDao.getActivityListByCondition(map);

        // 创建一个vo对象
        PaginationVO vo = new PaginationVO();
        vo.setTotal(total);
        vo.setDataList(dataList);

        // 将vo返回
        return vo;
    }

    @Override
    public boolean delete(String[] ids) {

        boolean flag = true;

        // 先查询出需要删除的备注的数量
        int count1 = activityRemarDao.getCountByAids(ids);

        // 删除备注，返回一个受到影响的条数（实际删除的数量）
        int count2 = activityRemarDao.deleteByAids(ids);

        if (count1 != count2){
            flag = false;
        }
        // 删除市场活动
        int count3 = activityDao.delete(ids);
        if (count3!=ids.length){
            flag = false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {

        // 取ulist
        List<User> uList = userDao.getUserList();

        // 取a
        Activity a = activityDao.getById(id);

        // 封装到map集合中
        Map<String,Object> map = new HashMap<>();
        map.put("uList",uList);
        map.put("a",a);

        // 返回map
        return map;
    }

    @Override
    public boolean update(Activity a) throws CRUDException {

        if (a.getName() == null || "" == a.getName()){

            throw new CRUDException("活动名称不能为空");

        }
        if (a.getOwner() == null || "" == a.getOwner()){

            throw new CRUDException("所有者不能为空");

        }

        int count = activityDao.update(a);

        if (count != 1){

            throw new CRUDException("市场活动添加失败");

        }

        return true;
    }

    @Override
    public Activity detail(String id) {

        Activity a = activityDao.detail(id);
        return a;

    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String activityId) {

        List<ActivityRemark> arList = activityRemarDao.getRemarkListByAid(activityId);

        return arList;
    }
}
