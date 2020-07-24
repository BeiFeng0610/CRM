package com.beifeng.crm.settings.dao;

import com.beifeng.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    User login(Map<String, Object> map);

    List<User> getUserList();
}
