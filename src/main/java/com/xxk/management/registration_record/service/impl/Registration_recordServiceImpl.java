package com.xxk.management.registration_record.service.impl;

import com.xxk.management.registration_record.dao.Registration_recordDao;
import com.xxk.management.registration_record.entity.Registration_record;
import com.xxk.management.registration_record.service.Registration_recordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Registration_record> getRegistration_recordForRegStatus(String registrationId,String status){
        return dao.getRegistration_recordForRegStatus(registrationId,status);
    }

    public List<Map<String, Object>> getRegistration_recordMakeDate(String registrationId,String status){

        List<Map<String, Object>> resultList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        try {
            List<Registration_record> listRegistration_record=dao.getRegistration_recordForRegStatus(registrationId,status);
            for (Registration_record record : listRegistration_record) {
                if(record.getReg_record_date()==null){
                    continue;
                }
                String recordDate=record.getReg_record_date().substring(0,10);
                if(dateList.contains(recordDate)){
                    continue;
                }
                dateList.add(recordDate);
                for (Registration_record r : listRegistration_record) {
                    if(recordDate.equals(r.getReg_record_date().substring(0,10))){
                        Map<String, Object> resultReg = new HashMap<>();
                        resultReg.put(recordDate,r);
                        resultList.add(resultReg);
                    }
                }
            }
        }catch (Exception e) {
            log.error(e);
            return null;
        }

        return resultList;
    }

    @Override
    public boolean addRegistration_record(Registration_record registration_record) {
        return dao.addRegistration_record(registration_record)==1?true:false;
    }

    @Override
    public boolean addListRegistration_record(List<Registration_record> ListRecord) {
        return dao.addListRegistration_record(ListRecord)==1?true:false;
    }


}
