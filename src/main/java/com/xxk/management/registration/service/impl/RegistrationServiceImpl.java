package com.xxk.management.registration.service.impl;

import com.xxk.management.registration.dao.RegistrationDao;
import com.xxk.management.registration.entity.Registration;
import com.xxk.management.registration.service.RegistrationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public List<Map<String, Object>> listRegistrationMap(String reg_record_ident,
                                                         String reg_office_id,
                                                         String reg_record_status,
                                                         String execute_record_status,
                                                         int pageStart,
                                                         int pageSize,
                                                         String startDate,
                                                         String endDate) {
        String[] status={"0","1","2"};
        String[] e_status={"0","1","2"};
        return dao.listRegistrationUnionMap(reg_record_ident,
                reg_office_id,
                null,
                null,
                status,e_status,
                (pageStart-1)*pageSize,
                pageSize,
                startDate,
                endDate);
    }

    @Override
    public Registration getRegistrationByRecordId(String recordId) {
        return dao.getRegistrationByRecordId(recordId);
    }

    @Override
    public boolean addRegistration(Registration registration) {
        return dao.addRegistration(registration)==1?true:false;
    }


}
