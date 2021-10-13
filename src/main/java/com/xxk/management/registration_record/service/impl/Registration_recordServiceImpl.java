package com.xxk.management.registration_record.service.impl;

import com.xxk.management.registration_record.dao.Registration_recordDao;
import com.xxk.management.registration_record.entity.Registration_record;
import com.xxk.management.registration_record.service.Registration_recordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class Registration_recordServiceImpl implements Registration_recordService {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private Registration_recordDao dao;


    @Override
    public List<Registration_record> listRegistration_record(int pageStart, int pageSize) {
        return dao.listRegistration_record((pageStart-1)*pageSize, pageSize);
    }

    public Registration_record getRegistration_recordForRegStatus(String registrationId,String status){
        return dao.getRegistration_recordForRegStatus(registrationId,status);
    }

    @Override
    public boolean addRegistration_record(Registration_record registration_record) {
        return dao.addRegistration_record(registration_record)==1?true:false;
    }


}
