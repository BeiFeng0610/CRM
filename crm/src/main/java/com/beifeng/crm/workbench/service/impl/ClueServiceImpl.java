package com.beifeng.crm.workbench.service.impl;

import com.beifeng.crm.exception.CRUDException;
import com.beifeng.crm.utils.DateTimeUtil;
import com.beifeng.crm.utils.SqlSessionUtil;
import com.beifeng.crm.utils.UUIDUtil;
import com.beifeng.crm.workbench.dao.*;
import com.beifeng.crm.workbench.domain.*;
import com.beifeng.crm.workbench.service.ClueService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class ClueServiceImpl implements ClueService {

    // 线索相关表
    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    private ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);

    // 客户相关表
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);

    // 联系人相关表
    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    private ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);

    // 交易相关表
    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

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

    @Override
    public boolean unbund(String id) {

        boolean flag = true;

        int count = clueActivityRelationDao.unbund(id);

        if (count!=1){

            flag = false;

        }

        return flag;
    }

    @Override
    public boolean bund(String cid, String[] aids) {

        boolean flag = true;

        for (String aid : aids){

            // 取得每一个aid和cid做关联
            ClueActivityRelation car = new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setClueId(cid);
            car.setActivityId(aid);

            int count = clueActivityRelationDao.bund(car);

            if (count!=1){
                flag = false;
            }

        }

        return flag;
    }

    @Override
    public boolean convert(String clueId, Tran t, String createBy) {

        String createTime = DateTimeUtil.getSysTime();

        boolean flag = true;

        // 1、根据线索id取到线索对象
        Clue clue = clueDao.getById(clueId);

        // 2、通过线索对象提取客服信息，当前客户不存在的时候，新建客户（根据公司的名称精确匹配，判断客服是否存在）
        String company = clue.getCompany();
        Customer customer = customerDao.getCustomerByName(company);

        // 如果customer为空，说明以前没有这个客户，需要新建一个
        if (customer == null){

            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setAddress(clue.getAddress());
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setOwner(clue.getOwner());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setName(company);
            customer.setDescription(clue.getDescription());
            customer.setCreateTime(createTime);
            customer.setCreateBy(createBy);
            customer.setContactSummary(clue.getContactSummary());
            // 添加客服
            int count1 = customerDao.save(customer);
            if (count1!=1){
                flag = false;
            }

        }

        //---------------------------------------------------------
        //经过第二步处理后，客户的信息我们已经拥有
        //---------------------------------------------------------


        // 3、通过线索对象提取联系人信息
        Contacts con = new Contacts();
        con.setId(UUIDUtil.getUUID());
        con.setSource(clue.getSource());
        con.setOwner(clue.getOwner());
        con.setNextContactTime(clue.getNextContactTime());
        con.setMphone(clue.getMphone());
        con.setJob(clue.getJob());
        con.setFullname(clue.getFullname());
        con.setEmail(clue.getEmail());
        con.setDescription(clue.getDescription());
        con.setCustomerId(customer.getId());
        con.setCreateTime(createTime);
        con.setCreateBy(createBy);
        con.setContactSummary(clue.getContactSummary());
        con.setAppellation(clue.getAppellation());
        con.setAddress(clue.getAddress());

        // 添加联系人
        int count2 = contactsDao.save(con);
        if (count2!=1){
            flag = false;
        }

        //---------------------------------------------------------
        //经过第三步处理后，联系人的信息我们已经拥有
        //---------------------------------------------------------


        // 4、查询出与该线索关联的备注信息列表
        // 查询出与该线索关联的备注信息列表
        List<ClueRemark> clueRemarkList = clueRemarkDao.getListByClueId(clueId);
        for (ClueRemark clueRemark:clueRemarkList){

            // 取出备注信息（主要转换到客户备注和联系人备注的就是这个备注信息）
            String noteContent = clueRemark.getNoteContent();

            // 创建客户备注对象，添加客户备注
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setEditFlag("0");
            customerRemark.setNoteContent(noteContent);
            int count3 = customerRemarkDao.save(customerRemark);
            if (count3!=1){
                flag = false;
            }

            // 创建联系人备注对象，添加备注

            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setContactsId(con.getId());
            contactsRemark.setEditFlag("0");
            contactsRemark.setNoteContent(noteContent);
            int count4 = contactsRemarkDao.save(contactsRemark);
            if (count4!=1){
                flag = false;
            }

        }

        // (5) “线索和市场活动”的关系转换到“联系人和市场活动”的关系
        // 查询出与该条线索关联的市场活动，查询与市场活动的关联关系列表
        List<ClueActivityRelation> clueActvityRelationList = clueActivityRelationDao.getListByClueId(clueId);
        // 遍历
        for (ClueActivityRelation clueActivityRelation:clueActvityRelationList){

            // 从每一天遍历出来的记录中取出关联的市场活动id
            String activityId = clueActivityRelation.getActivityId();

            // 创建联系人与市场活动的关联关系对象，让第三步生成的联系人与市场活动做关联
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(con.getId());

            // 添加联系人与市场活动的关联关系
            int count5 = contactsActivityRelationDao.save(contactsActivityRelation);
            if (count5!=1){
                flag = false;
            }
        }

        // (6) 如果有创建交易需求，创建一条交易
        if (t != null){

            /*
                T对象在controller里面已经封装好了信息

                通过第一步生成的c对象，取出一些信息，己学完善对t对象的封装
             */
            t.setSource(clue.getSource());
            t.setOwner(clue.getOwner());
            t.setNextContactTime(clue.getNextContactTime());
            t.setDescription(clue.getDescription());
            t.setCustomerId(customer.getId());
            t.setContactSummary(clue.getContactSummary());
            t.setContactsId(con.getId());
            // 添加交易
            int count6 = tranDao.save(t);

            if (count6!=1){
                flag = false;
            }

            // (7) 如果创建了交易，则创建一条该交易下的交易历史
            TranHistory th = new TranHistory();
            th.setId(UUIDUtil.getUUID());
            th.setCreateBy(createBy);
            th.setCreateTime(createTime);
            th.setExpectedDate(t.getExpectedDate());
            th.setMoney(t.getMoney());
            th.setStage(t.getStage());
            th.setTranId(t.getId());
            // 添加交易历史
            int count7 = tranHistoryDao.save(th);
            if (count7!=1){
                flag = false;
            }

        }

        // (8) 删除线索备注
        for (ClueRemark clueRemark:clueRemarkList){

            int count8 = clueRemarkDao.delete(clueRemark);
            if (count8!=1){
                flag = false;
            }

        }

        // (9) 删除线索和市场活动的关系
        for (ClueActivityRelation clueActivityRelation:clueActvityRelationList){

            int count9 = clueActivityRelationDao.delete(clueActivityRelation);
            if (count9!=1){
                flag = false;
            }
        }

        // (10) 删除线索
        int count10 = clueDao.delete(clueId);
        if (count10!=1){
            flag = false;
        }


        return flag;

    }
}
