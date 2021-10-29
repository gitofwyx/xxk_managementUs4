package com.xxk.management.registration_record.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.management.registration.service.RegistrationMService;
import com.xxk.management.registration_record.dao.Registration_recordDao;
import com.xxk.management.registration_record.entity.Registration_record;
import com.xxk.management.registration_record.service.Registration_recordService;
import com.xxk.management.stock.entity.Stock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private RegistrationMService registrationMService;


    @Override
    public List<Registration_record> listRegistration_record(int pageStart, int pageSize) {
        return dao.listRegistration_record((pageStart - 1) * pageSize, pageSize);
    }

    public List<Registration_record> getRecordAccordRegistration(String registrationId, String officeId, String status) {
        return dao.getRecordAccordRegistration(registrationId, officeId, status);
    }

    public List<Map<String, Object>> getRegistration_recordMakeDate(String registrationId, String[] status) {

        List<Map<String, Object>> resultList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        try {
            List<Registration_record> listRegistration_record = dao.getRegistration_recordByOffice(registrationId, status);
            for (Registration_record record : listRegistration_record) {
                if (record.getReg_record_date() == null) {
                    continue;
                }
                String recordDate = record.getReg_record_date().substring(0, 10);
                if (dateList.contains(recordDate)) {
                    continue;
                }
                dateList.add(recordDate);
                Map<String, Object> resultReg = new HashMap<>();
                List<Registration_record> recordList = new ArrayList<>();
                for (Registration_record r : listRegistration_record) {
                    if (recordDate.equals(r.getReg_record_date().substring(0, 10))) {
                        recordList.add(r);
                    }
                }
                resultReg.put(recordDate, recordList);
                resultList.add(resultReg);
            }
        } catch (Exception e) {
            log.error(e);
            return null;
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> getRegistration_recordById(String recordId) {
        return dao.getRegistration_recordById(recordId);
    }

    @Override
    public boolean addRegistration_record(Registration_record registration_record) {
        return dao.addRegistration_record(registration_record) == 1 ? true : false;
    }

    @Override
    public boolean addListRegistration_record(List<Registration_record> ListRecord) {
        return dao.addListRegistration_record(ListRecord) == 1 ? true : false;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public boolean acceptanceRegistration_record(String registration_id,String reg_record_id, String receiver_id, String registration_py) throws Exception, RuntimeException{
        String createDate = DateUtil.getFullTime();
        boolean result = false;
        List<Registration_record> listRecord = new ArrayList();

        listRecord = dao.getRecordByRegistrationId(registration_id, "0");
        if (listRecord.size() == 1) {
            result=registrationMService.acceptanceRegistration(listRecord.get(0).getRegistration_id(), receiver_id);
            if (!(result)) {
                log.error("acceptanceRegistration_record:registrationMService.acceptanceRegistration:" + result);
                throw new Exception("acceptanceRegistration_record:registrationMService.acceptanceRegistration出错！");
            }

        }

        result = dao.updateRegistration_recordStatus(reg_record_id, "1", receiver_id, createDate) == 1 ? true : false;
        if (!(result)) {
            log.error("acceptanceRegistration_record:dao.updateRegistration_recordStatus:" + result);
            throw new Exception("acceptanceRegistration_record:dao.updateRegistration_recordStatus出错！");
        }

        return result;
    }


}
