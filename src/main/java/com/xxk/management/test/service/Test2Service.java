package com.xxk.management.test.service;

import com.xxk.management.system.entity.Sysadmin;
import com.xxk.management.user.entity.RegUser;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface Test2Service {

    public Sysadmin getSysUserByAccount(String account);

    public boolean addSysUserTest2(Sysadmin sysadmin) throws Exception;

    public boolean updateSysUserTest2(Sysadmin sysadmin);

    public boolean deleteListSysTest2(List<String> listStr);

}
