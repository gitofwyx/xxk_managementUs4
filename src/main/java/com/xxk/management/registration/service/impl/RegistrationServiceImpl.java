package com.xxk.management.registration.service.impl;

import com.xxk.management.registration.dao.RegistrationDao;
import com.xxk.management.registration.entity.Registration;
import com.xxk.management.registration.service.RegistrationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private RegistrationDao dao;


    @Override
    public List<Registration> listRegistration(int pageStart, int pageSize) {
        return dao.listRegistration((pageStart-1)*pageSize, pageSize);
    }

    @Override
    public boolean addRegistration(Registration registration) {
        return dao.addRegistration(registration)==1?true:false;
    }


}
