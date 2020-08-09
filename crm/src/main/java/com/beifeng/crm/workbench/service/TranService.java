package com.beifeng.crm.workbench.service;

import com.beifeng.crm.workbench.domain.Tran;
import com.beifeng.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranService {


    boolean save(Tran t, String customerName);

    Tran detail(String id);

    List<TranHistory> getHistoryListByTranId(String tranId);

    boolean changeStage(Tran t);
}
