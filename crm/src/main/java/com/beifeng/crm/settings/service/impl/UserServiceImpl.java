package com.beifeng.crm.settings.service.impl;

import com.beifeng.crm.exception.LoginException;
import com.beifeng.crm.settings.dao.UserDao;
import com.beifeng.crm.settings.domain.User;
import com.beifeng.crm.settings.service.UserService;
import com.beifeng.crm.utils.DateTimeUtil;
import com.beifeng.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);


    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {

        Map<String,Object> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);

        User user = userDao.login(map);

        if (user == null){

            throw new LoginException("账号密码错误");

        }

        // 如果程序能够成功执行到该行，说明账号密码正确
        // 需要验证其他信息

        // 验证失效时间
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(currentTime) < 0){

            throw new LoginException("账号已失效");

        }

        // 判断锁定状态
        String lockState = user.getLockState();
        if ("0".equals(lockState)){

            throw new LoginException("账号已锁定");

        }

        // 判断ip地址
        String allowips = user.getAllowIps();
        if (!allowips.contains(ip)){

            throw new LoginException("ip地址受限");

        }


        return user;
    }

    @Override
    public List<User> getUserList() {

        List<User> userList = userDao.getUserList();

        return userList;
    }
}
