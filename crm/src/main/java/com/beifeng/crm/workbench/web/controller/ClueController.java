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
import com.beifeng.crm.workbench.service.ActivityService;
import com.beifeng.crm.workbench.service.impl.ActivityServiceImpl;

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

        if ("/workbench/activity/xxx.do".equals(path)){

            // xxx(request,response);

        }else if ("/workbench/activity/xxx.do".equals(path)){

            // xxx(request,response);

        }

    }


}
