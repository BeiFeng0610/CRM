package com.beifeng.crm.workbench.web.controller;

import com.beifeng.crm.exception.CRUDException;
import com.beifeng.crm.settings.domain.User;
import com.beifeng.crm.settings.service.UserService;
import com.beifeng.crm.settings.service.impl.UserServiceImpl;
import com.beifeng.crm.utils.DateTimeUtil;
import com.beifeng.crm.utils.PrintJson;
import com.beifeng.crm.utils.ServiceFactory;
import com.beifeng.crm.utils.UUIDUtil;
import com.beifeng.crm.workbench.domain.Activity;
import com.beifeng.crm.workbench.domain.Clue;
import com.beifeng.crm.workbench.domain.Tran;
import com.beifeng.crm.workbench.domain.TranHistory;
import com.beifeng.crm.workbench.service.ActivityService;
import com.beifeng.crm.workbench.service.ClueService;
import com.beifeng.crm.workbench.service.CustomerService;
import com.beifeng.crm.workbench.service.TranService;
import com.beifeng.crm.workbench.service.impl.ActivityServiceImpl;
import com.beifeng.crm.workbench.service.impl.ClueServiceImpl;
import com.beifeng.crm.workbench.service.impl.CustomerServiceImp;
import com.beifeng.crm.workbench.service.impl.TranServiceImp;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到交易控制器");

        String path = request.getServletPath();

        if ("/workbench/transaction/add.do".equals(path)){

            add(request,response);

        }else if ("/workbench/transaction/getCustomerName.do".equals(path)){

            getCustomerName(request,response);

        }else if ("/workbench/transaction/save.do".equals(path)){

            save(request,response);

        }else if ("/workbench/transaction/detail.do".equals(path)){

            detail(request,response);

        }else if ("/workbench/transaction/getHistoryListByTranId.do".equals(path)){

            getHistoryListByTranId(request,response);

        }else if ("/workbench/transaction/changeStage.do".equals(path)){

            changeStage(request,response);

        }else if ("/workbench/transaction/getCharts.do".equals(path)){

            getCharts(request,response);

        }

    }

    private void getCharts(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得交易阶段数量统计图表的数据");

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImp());

        Map<String,Object> map = ts.getCharts();
        PrintJson.printJsonObj(response,map);

    }

    private void changeStage(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行改变阶段的操作");
        String id = request.getParameter("id");
        String stage = request.getParameter("stage");
        String money = request.getParameter("money");
        String expectedDate = request.getParameter("expectedDate");

        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)request.getSession().getAttribute("user")).getName();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        Tran t = new Tran();
        t.setId(id);
        t.setStage(stage);
        t.setMoney(money);
        t.setExpectedDate(expectedDate);
        t.setEditBy(editBy);
        t.setEditTime(editTime);
        t.setCreateBy(createBy);

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImp());

        boolean flag = ts.changeStage(t);

        Map<String, String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pMap");
        String possibility = pMap.get(stage);
        t.setPossibility(possibility);

        Map<String , Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("t",t);

        PrintJson.printJsonObj(response,map);

    }

    private void getHistoryListByTranId(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("根据交易id取得相应的历史列表");

        String tranId = request.getParameter("tranId");

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImp());

        List<TranHistory> thList = ts.getHistoryListByTranId(tranId);


        Map<String, String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pMap");
        // 将交易历史列表遍历
        for (TranHistory th : thList){

            // 根据每一条历史取出每一个阶段
            String stage = th.getStage();
            String possibility = pMap.get(stage);
            th.setPossibility(possibility);

        }

        PrintJson.printJsonObj(response,thList);

    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("跳转到详细信息页");
        String id = request.getParameter("id");

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImp());

        Tran t = ts.detail(id);

        // 处理可能性
        String stage = t.getStage();
        Map<String, String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pMap");

        String possibility = pMap.get(stage);
        t.setPossibility(possibility);

        request.setAttribute("t",t);


        request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request,response);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("执行添加交易的操作");

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String money = request.getParameter("money");
        String name = request.getParameter("name");
        String expectedDate = request.getParameter("expectedDate");

        // 暂时只有名称
        String customerName = request.getParameter("customerName");

        String stage = request.getParameter("stage");
        String type = request.getParameter("type");
        String source = request.getParameter("source");
        String activityId = request.getParameter("activityId");
        String contactsId = request.getParameter("contactsId");

        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");

        Tran t = new Tran();
        t.setId(id);
        t.setOwner(owner);
        t.setMoney(money);
        t.setName(name);
        t.setExpectedDate(expectedDate);
        t.setStage(stage);
        t.setType(type);
        t.setSource(source);
        t.setActivityId(activityId);
        t.setContactsId(contactsId);
        t.setCreateTime(createTime);
        t.setCreateBy(createBy);
        t.setDescription(description);
        t.setContactSummary(contactSummary);
        t.setNextContactTime(nextContactTime);

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImp());

        boolean flag = ts.save(t,customerName);

        if (flag){

            // 添加成功，跳转到列表页
            response.sendRedirect(request.getContextPath() + "/workbench/transaction/index.jsp");

        }

    }

    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得客户名称列表，（按照名称模糊查询）");
        String name = request.getParameter("name");

        CustomerService cs = (CustomerService) ServiceFactory.getService(new CustomerServiceImp());

        List<String> sList = cs.getCustomerName(name);

        PrintJson.printJsonObj(response,sList);

    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到跳转交易添加页的操作");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        request.setAttribute("uList",uList);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request,response);

    }


}
