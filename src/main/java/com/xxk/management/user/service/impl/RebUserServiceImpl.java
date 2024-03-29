package com.xxk.management.user.service.impl;

import com.xxk.management.user.dao.RegUserDao;
import com.xxk.management.user.entity.RegUser;
import com.xxk.management.user.service.RebUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class RebUserServiceImpl implements RebUserService {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private RegUserDao dao;

    @Override
    public boolean login(String account, String password) {
        return false;
    }

    @Override
    public List<RegUser> listRegUser(int pageStart, int pageSize) {
        return dao.listRegUser((pageStart-1)*pageSize, pageSize);
    }

    public  List<Map<String, Object>> listRegUserByIds(List<String> listStr){
        return dao.listRegUserByIds(listStr);
    }

    @Override
    public boolean addRegUser(RegUser user) {
        return dao.addRegUser(user)==1?true:false;
    }

    public boolean addListRegUser(List<RegUser> listRegUser){
        return dao.addListRegUser(listRegUser)>=1?true:false;
    }

    @Override
    public RegUser getRegUser(String id) {
        return  dao.getRegUser(id);
    }

    @Override
    public RegUser getUserByAccount(String account) {
        return dao.getUserByAccount(account);
    }

    @Override
    public String getRoleByAccount(String account) {
        return dao.getRoleByAccount(account);
    }

    @Override
    public boolean updateRegUser(RegUser user) {
        return dao.updateRegUser(user)==1?true:false;
    }

    @Override
    public int countRegUser() {
        return dao.countRegUser();
    }

    @Override
    public boolean deleteListRegUser(List<String> listStr) {
        return dao.deleteListRegUser(listStr)>=1?true:false;
    }

    @Transactional
    public Boolean addTest(RegUser user){
        int count = dao.addRegUser(user);

        if(count != 1){
            log.error("数据库更新不为1");
        }

        log.error("添加角色成功");

        if(1 == 1) {
            log.error("测试事务是否回滚");
        }
        return true;
    }
}
