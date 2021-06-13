package com.xxk.management.test.dao;

import com.xxk.management.user.entity.RegUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface TestDao {

    public String test1(String v1);

    public List<RegUser> listRegUser(int pageStart, int pageSize);

    public  List<Map<String, Object>> listRegUserByIds(List<String> listStr);

    public int addRegUserTest(RegUser user);

    public int updateRegUserTest(RegUser user);

    public int deleteListRegUserTest(List<String> listStr);


}
