package com.beifeng.crm.workbench.service.impl;

import com.beifeng.crm.utils.DateTimeUtil;
import com.beifeng.crm.utils.SqlSessionUtil;
import com.beifeng.crm.utils.UUIDUtil;
import com.beifeng.crm.workbench.dao.CustomerDao;
import com.beifeng.crm.workbench.dao.TranDao;
import com.beifeng.crm.workbench.dao.TranHistoryDao;
import com.beifeng.crm.workbench.domain.Customer;
import com.beifeng.crm.workbench.domain.Tran;
import com.beifeng.crm.workbench.domain.TranHistory;
import com.beifeng.crm.workbench.service.TranService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TranServiceImp implements TranService {

    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public boolean save(Tran t, String customerName) {

        /*
            交易添加业务：
                添加之前，确定客户是否存在
         */

        // 判断customerName是否在客户表中存在，如果有取出id
        // 如果没有，新建一条客户信息，取出新建的id，封装到t对象

        boolean flag = true;

        Customer cus = customerDao.getCustomerByName(customerName);

        if (cus == null){

            // 创建客户
            cus = new Customer();
            cus.setId(UUIDUtil.getUUID());
            cus.setName(customerName);
            cus.setCreateBy(t.getCreateBy());
            cus.setCreateTime(t.getCreateTime());
            cus.setContactSummary(t.getContactSummary());
            cus.setNextContactTime(t.getNextContactTime());
            cus.setDescription(t.getDescription());
            cus.setOwner(t.getOwner());
            // 添加客服
            int count1 = customerDao.save(cus);

            if (count1 != 1){
                flag = false;
            }

        }

        // 通过对于客户的处理，取出客户的id，可以新建交易了
        t.setCustomerId(cus.getId());

        int count2 = tranDao.save(t);

        if (count2 != 1){
            flag = false;
        }

        // 添加交易历史
        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setTranId(t.getId());
        th.setStage(t.getStage());
        th.setMoney(t.getMoney());
        th.setExpectedDate(t.getExpectedDate());
        th.setCreateBy(t.getCreateBy());
        th.setCreateTime(DateTimeUtil.getSysTime());

        int count3 = tranHistoryDao.save(th);
        if (count3 != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public Tran detail(String id) {

        Tran t = tranDao.detail(id);

        return t;
    }

    @Override
    public List<TranHistory> getHistoryListByTranId(String tranId) {

        List<TranHistory> thList = tranHistoryDao.getHistoryListByTranId(tranId);

        return thList;
    }

    @Override
    public boolean changeStage(Tran t) {

        boolean flag = true;

        // 改变交易阶段
        int count1 = tranDao.changeStage(t);

        if (count1 !=1){
            flag = false;
        }

        // 交易阶段改变后，生成一条交易历史
        TranHistory th = new TranHistory();

        th.setId(UUIDUtil.getUUID());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setCreateBy(t.getCreateBy());
        th.setMoney(t.getMoney());
        th.setStage(t.getStage());
        th.setTranId(t.getId());
        th.setExpectedDate(t.getExpectedDate());

        // 添加交易历史
        int count2 = tranHistoryDao.save(th);
        if (count2 !=1){
            flag = false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> getCharts() {

        // 取得total
        int total = tranDao.getTotal();

        // 取得dataList
        List<Map<String ,Object>> dataList = tranDao.getCharts();

        Map<String,Object> map = new HashMap<>();

        map.put("total",total);
        map.put("dataList",dataList);


        // 将数据放到map中
        return map;
    }
}




























