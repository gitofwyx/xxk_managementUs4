package com.xxk.management.test.service.impl;

import com.xxk.management.test.dao.TestDao;
import com.xxk.management.test.service.TestService;
import com.xxk.management.user.dao.RegUserDao;
import com.xxk.management.user.entity.RegUser;
import com.xxk.management.user.service.RebUserService;
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

    @Override
    public String test1(String v1){
        return  dao.test1(v1);
    }


}
