package com.xxk.management.registration.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.core.util.build_ident.IdentUtil;
import com.xxk.management.office.offices.record.entity.Record;
import com.xxk.management.registration.dao.RegistrationDao;
import com.xxk.management.registration.entity.Registration;
import com.xxk.management.registration.entity.Registration_record;
import com.xxk.management.registration.service.RegistrationMService;
import com.xxk.management.registration.service.RegistrationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Override
    public List<Registration> listRegistration(int pageStart, int pageSize) {
        return dao.listRegistration((pageStart - 1) * pageSize, pageSize);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> addRegistration(Registration registration, Registration_record record)  throws Exception, RuntimeException{
        Map<String, Object> result = new HashMap<>();
        String Date = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        String recId = UUIdUtil.getUUID();
        int reg_count = 0;


        String reg_ident = IdentUtil.buildIdent("", reg_count, Date);
        registration.setId(id);
        registration.setReg_ident(reg_ident);
        registration.setCreateUserId("admin");
        registration.setCreateDate(Date);
        registration.setUpdateUserId("admin");
        registration.setUpdateDate(Date);
        registration.setDeleteFlag("0");
        Boolean resultReg = dao.addRegistration(registration) == 1 ? true : false;
        if (!(resultReg)) {
            log.error("addRegistration:dao.addRegistration出错！");
            throw new Exception("addRegistration:dao.addRegistration出错！");
        } else {
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
            if (!(resultReg)) {
                log.error("addRegistration:deliveryService.updateDeliveryStatus出错！");
                throw new Exception("addDepositoryWithStorage:deliveryService.updateDeliveryStatus出错！");
            }
        }
        return result;
        //return "system/index";
    }

}
