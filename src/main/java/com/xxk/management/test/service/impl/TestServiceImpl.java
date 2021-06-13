package com.xxk.management.test.service.impl;

import com.xxk.management.system.entity.Sysadmin;
import com.xxk.management.test.dao.TestDao;
import com.xxk.management.test.service.Test2Service;
import com.xxk.management.test.service.TestService;
import com.xxk.management.user.entity.RegUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class TestServiceImpl implements TestService {

    private static Logger log = Logger.getLogger(TestServiceImpl.class);

    @Autowired
    private TestDao dao;
    @Autowired
    private Test2Service test2Service;

    @Override
    public String test1(String v1){
        return  dao.test1(v1);
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public boolean addRegUserTest(RegUser user) throws Exception {
        int Rl=0;
        try {
            Rl=dao.updateRegUserTest(user);
            log.warn("Test1:"+Rl);
            Sysadmin sysadmin=test2Service.getSysUserByAccount("t3");
            String V2="t4";
            sysadmin.setAccount(V2);
            sysadmin.setPassword(V2);
            sysadmin.setName(V2);
            sysadmin.setPhone(V2);
            test2Service.addSysUserTest2(sysadmin);
            Rl=dao.addRegUserTest(user);
            log.warn("Test1:"+Rl);
            return true;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public boolean updateRegUserTest(RegUser user) {
        return dao.updateRegUserTest(user)==1?true:false;
    }

    @Override
    public boolean deleteListRegUserTest(List<String> listStr) {
        return dao.deleteListRegUserTest(listStr)>=1?true:false;
    }


}
