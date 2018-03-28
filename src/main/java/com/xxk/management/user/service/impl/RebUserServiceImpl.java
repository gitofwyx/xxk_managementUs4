package com.xxk.management.user.service.impl;

import com.xxk.management.user.dao.RegUserDao;
import com.xxk.management.user.entity.RegUser;
import com.xxk.management.user.service.RebUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class RebUserServiceImpl implements RebUserService {

    private static Logger log = Logger.getLogger(RebUserServiceImpl.class);

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

    @Override
    public boolean addRegUser(RegUser user) {
        return dao.addRegUser(user)==1?true:false;
    }

    @Override
    public RegUser getRegUser(String id) {
        return  dao.getRegUser(id);
    }

    @Override
    public boolean updateRegUser(RegUser user) {
        return dao.updateRegUser(user)==1?true:false;
    }

    @Override
    public boolean deleteListRegUser(List<String> listStr) {
        return dao.deleteListRegUser(listStr)>=1?true:false;
    }
}
