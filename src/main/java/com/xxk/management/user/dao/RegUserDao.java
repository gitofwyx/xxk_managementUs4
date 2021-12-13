package com.xxk.management.user.dao;

import com.xxk.management.user.entity.RegUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface RegUserDao {

    public List<RegUser> listRegUser(int pageStart, int pageSize);

    public  List<Map<String, Object>> listRegUserByIds(List<String> listStr);

    public int addRegUser(RegUser user);

    public int addListRegUser(List<RegUser> listRegUser);

    public RegUser getRegUser(String id);

    public RegUser getUserByAccount(String account);

    //根据账号获取角色信息
    public String getRoleByAccount(String account);

    public int updateRegUser(RegUser user);

    public int countRegUser();

    public int deleteListRegUser(List<String> listStr);


}
