package com.beifeng.crm.workbench.service;

import com.beifeng.crm.workbench.domain.Tran;

public interface TranService {


    boolean save(Tran t, String customerName);
}
