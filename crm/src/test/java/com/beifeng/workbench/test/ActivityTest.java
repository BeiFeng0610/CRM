package com.beifeng.workbench.test;

import com.beifeng.crm.exception.CRUDException;
import com.beifeng.crm.utils.ServiceFactory;
import com.beifeng.crm.utils.UUIDUtil;
import com.beifeng.crm.workbench.domain.Activity;
import com.beifeng.crm.workbench.service.ActivityService;
import com.beifeng.crm.workbench.service.impl.ActivityServiceImpl;
import junit.framework.Assert;
import org.junit.Test;

public class ActivityTest {

    @Test
    public void testSave() throws CRUDException {

        Activity a = new Activity();
        a.setId(UUIDUtil.getUUID());
        a.setName("宣传推广");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.save(a);
        System.out.println(flag);

        Assert.assertEquals(flag,true);

    }

    @Test
    public void testUpdate(){

        System.out.println("234");

    }

    @Test
    public void testUpdate1(){

        System.out.println("345");

    }

}
