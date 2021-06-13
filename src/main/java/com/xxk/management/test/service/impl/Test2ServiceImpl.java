package com.xxk.management.test.service.impl;

import com.xxk.management.system.entity.Sysadmin;
import com.xxk.management.test.dao.Test2Dao;
import com.xxk.management.test.service.Test2Service;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class Test2ServiceImpl implements Test2Service {

    private static Logger log = Logger.getLogger(Test2ServiceImpl.class);

    @Autowired
    private Test2Dao dao2;

    @Override
    public Sysadmin getSysUserByAccount(String account) {
        return dao2.getSysUserByAccount(account);
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public boolean addSysUserTest2(Sysadmin sysadmin) throws Exception {
        int Rl=0;
        try {
            Rl=dao2.updateSysUserTest2(sysadmin);
            log.warn("Test2:"+Rl);
            /*Rl=dao2.addSysUserTest2(sysadmin);
            log.warn("Test2:"+Rl);*/
            return true;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public boolean updateSysUserTest2(Sysadmin sysadmin) {
        return dao2.updateSysUserTest2(sysadmin)==1?true:false;
    }

    @Override
    public boolean deleteListSysTest2(List<String> listStr) {
        return dao2.deleteListSysTest2(listStr)>=1?true:false;
    }


}
