package com.beifeng.settings.test;

import com.beifeng.crm.utils.DateTimeUtil;
import com.beifeng.crm.utils.MD5Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test1 {

    public static void main(String[] args) {

        /*// 验证失效时间
        String expireTime = "2019-10-10 10:10:10";

        // 当前系统时间
        String currentTime = DateTimeUtil.getSysTime();
        int count = expireTime.compareTo(currentTime);
        System.out.println(count);*/

        /*String lockState = "0";
        if ("0".equals(lockState)){
            System.out.println("账号已锁定");
        }*/

        /*// 浏览器端得ip地址
        String ip = "192.168.1.3";
        // 允许访问得ip地址群
        String allowIps = "192.168.1.1,192.168.1.2";
        if (allowIps.contains(ip)){
            System.out.println("有效的ip地址");
        }else {
            System.out.println("ip地址受限");
        }*/

        /*String pwd = "nikankanwodemimashisha";
        String md5 = MD5Util.getMD5(pwd);
        System.out.println(md5);
*/
    }

}
