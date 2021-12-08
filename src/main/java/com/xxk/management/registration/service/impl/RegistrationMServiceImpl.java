package com.xxk.management.registration.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.core.util.build_ident.IdentUtil;
import com.xxk.management.registration.dao.RegistrationDao;
import com.xxk.management.registration.entity.Registration;
import com.xxk.management.registration.service.RegistrationMService;
import com.xxk.management.registration_record.entity.Registration_record;
import com.xxk.management.registration_record.service.Registration_recordService;
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
public class RegistrationMServiceImpl implements RegistrationMService {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private RegistrationDao dao;

    @Autowired
    private Registration_recordService registration_recordService;


    @Override
    public List<Registration> listRegistration(int pageStart, int pageSize) {
        return dao.listRegistration((pageStart - 1) * pageSize, pageSize);
    }

    @Override
    public List<Map<String, Object>> listRegistrationMapAccordingDate(String office_id,
                                                                      String reg_record_py,
                                                                      String reg_receiver_id,
                                                                      String[] status) {

        List<Map<String, Object>> resultList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        try {
            List<Map<String, Object>> listReg = dao.listRegistrationUnionMap(null,office_id,reg_record_py,reg_receiver_id, status,0,0);
            for (Map<String, Object> reg : listReg) {
                if (reg.get("reg_record_date") == null) {
                    continue;
                }
                String recordDate = reg.get("reg_record_date").toString().substring(0, 10);
                if (dateList.contains(recordDate)) {
                    continue;
                }
                dateList.add(recordDate);
                Map<String, Object> resultReg = new HashMap<>();
                List<Map<String, Object>> recordList = new ArrayList<>();
                for (Map<String, Object> regMap : listReg) {
                    if (recordDate.equals(regMap.get("reg_record_date").toString().substring(0, 10))) {
                        recordList.add(regMap);
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
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> addRegistrationAccordingRegStatus(Registration registration, Registration_record record) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        Boolean resultReg = false;
        String Date = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        String recId = UUIdUtil.getUUID();
        List<Registration_record> listRecord = new ArrayList();
        int reg_count = 0;

        listRecord = registration_recordService.getRecordAccordRegistration(registration.getRegistration_py(), registration.getReg_office_id(), "0");
        if (listRecord.size() == 0) {
            String reg_ident = IdentUtil.buildIdent("", reg_count, Date);
            registration.setId(id);
            registration.setReg_ident(reg_ident);
            registration.setReg_status("0");
            registration.setExecute_status("0");
            registration.setExamine_status("0");
            registration.setCreateUserId(registration.getRegistration_py());
            registration.setCreateDate(Date);
            registration.setUpdateUserId(registration.getRegistration_py());
            registration.setUpdateDate(Date);
            registration.setDeleteFlag("0");
            resultReg = dao.addRegistration(registration) == 1 ? true : false;
        } else {
            registration.setId(listRecord.get(0).getRegistration_id());
            resultReg = true;
        }
        if (!(resultReg)) {
            log.error("addRegistration:dao.addRegistration出错！");
            throw new Exception("addRegistration:dao.addRegistration出错！");
        } else {
            record.setId(recId);
            record.setRegistration_id(registration.getId());
            record.setReg_record_py(registration.getRegistration_py());
            record.setReg_record_status("0");
            record.setReg_record_date(Date);
            record.setExecute_record_status("0");
            record.setExamine_record_status("0");
            record.setUpdateUserId(registration.getRegistration_py());
            record.setUpdateDate(Date);
            record.setCreateUserId(registration.getRegistration_py());
            record.setCreateDate(Date);
            record.setDeleteFlag("0");
            resultReg = registration_recordService.addRegistration_record(record);
            if (!(resultReg)) {
                log.error("addRegistration:registration_recordService.addRegistration_record出错！");
                throw new Exception("addRegistration:registration_recordService.addRegistration_record出错！");
            }
        }
        return result;
        //return "system/index";
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public boolean acceptanceRegistration(String id, String receiver_id) throws Exception, RuntimeException {
        String createDate = DateUtil.getFullTime();
        boolean devicesResult = false;
        List<Registration_record> listRecord = new ArrayList();


        devicesResult = dao.updateRegistrationStatus(id, receiver_id, createDate, "1") == 1 ? true : false;
        if (!(devicesResult)) {
            log.error("acceptanceRegistration->updateRegistration_recordStatus:" + devicesResult);
            throw new Exception("acceptanceRegistration:dao.updateRegistrationStatus出错！");
        }

        return devicesResult;
    }

}
