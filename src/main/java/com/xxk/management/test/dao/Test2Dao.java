package com.xxk.management.test.dao;

import com.xxk.management.system.entity.Sysadmin;
import com.xxk.management.user.entity.RegUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface Test2Dao {

    public Sysadmin getSysUserByAccount(String account);

    public int addSysUserTest2(Sysadmin sysadmin);

    public int updateSysUserTest2(Sysadmin sysadmin);

    public int deleteListSysTest2(List<String> listStr);


}
