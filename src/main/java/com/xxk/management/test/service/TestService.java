package com.xxk.management.test.service;

import com.xxk.management.user.entity.RegUser;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface TestService {

    public String test1(String v1);

    public boolean addRegUserTest(RegUser user) throws Exception;

    public boolean updateRegUserTest(RegUser user);

    public boolean deleteListRegUserTest(List<String> listStr);

}
