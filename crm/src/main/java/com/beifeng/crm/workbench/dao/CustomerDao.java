package com.beifeng.crm.workbench.dao;

import com.beifeng.crm.workbench.domain.Customer;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    int save(Customer customer);
}
