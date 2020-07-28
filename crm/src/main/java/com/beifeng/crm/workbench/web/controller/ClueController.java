package com.beifeng.crm.workbench.web.controller;

import com.beifeng.crm.exception.CRUDException;
import com.beifeng.crm.settings.domain.User;
import com.beifeng.crm.settings.service.UserService;
import com.beifeng.crm.settings.service.impl.UserServiceImpl;
import com.beifeng.crm.utils.DateTimeUtil;
import com.beifeng.crm.utils.PrintJson;
import com.beifeng.crm.utils.ServiceFactory;
import com.beifeng.crm.utils.UUIDUtil;
import com.beifeng.crm.vo.PaginationVO;
import com.beifeng.crm.workbench.domain.Activity;
import com.beifeng.crm.workbench.domain.ActivityRemark;
import com.beifeng.crm.workbench.domain.Clue;
import com.beifeng.crm.workbench.service.ActivityService;
import com.beifeng.crm.workbench.service.ClueService;
import com.beifeng.crm.workbench.service.impl.ActivityServiceImpl;
import com.beifeng.crm.workbench.service.impl.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到线索控制器");

        String path = request.getServletPath();

        if ("/workbench/clue/getUserList.do".equals(path)){

            getUserList(request,response);

        }else if ("/workbench/clue/save.do".equals(path)){

            save(request,response);

        }else if ("/workbench/clue/detail.do".equals(path)){

            detail(request,response);

        }else if ("/workbench/clue/getActivityListByClueId.do".equals(path)){

            getActivityListByClueId(request,response);

        }

    }

    private void getActivityListByClueId(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("根据线索id查询关联的市场活动列表");

        String clueId = request.getParameter("clueId");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<Activity> aList = as.getActivityListByClueId(clueId);

        PrintJson.printJsonObj(response,aList);

    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("跳转到线索的详细信息页");

        String id = request.getParameter("id");
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        Clue c = cs.detail(id);

        request.setAttribute("c",c);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);


    }

    private void save(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行线索的添加操作");

        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");

        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");

        Clue c = new Clue();
        c.setId(id);
        c.setFullname(fullname);
        c.setAppellation(appellation);
        c.setOwner(owner);
        c.setCompany(company);
        c.setJob(job);
        c.setEmail(email);
        c.setPhone(phone);
        c.setWebsite(website);
        c.setMphone(mphone);
        c.setState(state);
        c.setSource(source);
        c.setCreateTime(createTime);
        c.setCreateBy(createBy);
        c.setDescription(description);
        c.setContactSummary(contactSummary);
        c.setNextContactTime(nextContactTime);
        c.setAddress(address);

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        try {

            boolean flag = cs.save(c);

            PrintJson.printJsonFlag(response,flag);

        } catch (CRUDException e) {
            e.printStackTrace();

            Map<String,Object> map = new HashMap<>();
            String msg = e.getMessage();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }


    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得用户信息列表");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        PrintJson.printJsonObj(response,uList);

    }


}
