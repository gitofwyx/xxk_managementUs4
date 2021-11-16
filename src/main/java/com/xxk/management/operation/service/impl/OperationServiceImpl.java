package com.xxk.management.operation.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.core.util.build_ident.IdentUtil;
import com.xxk.management.operation.dao.OperationDao;
import com.xxk.management.operation.entity.Operation;
import com.xxk.management.operation.service.OperationService;
import com.xxk.management.registration.entity.Registration;
import com.xxk.management.registration.service.RegistrationService;
import com.xxk.management.registration_record.entity.Registration_record;
import com.xxk.management.registration_record.service.Registration_recordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OperationServiceImpl implements OperationService {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private OperationDao dao;

    @Autowired
    private Registration_recordService registration_recordService;

    @Autowired
    private RegistrationService registrationService;

    @Override
    public List<Operation> listOperation(int pageStart, int pageSize) {
        return dao.listOperation((pageStart - 1) * pageSize, pageSize);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public boolean addOperation(Operation operation) throws Exception, RuntimeException {

        Map<String, Object> result = new HashMap<>();
        Boolean resultReg = false;
        String Date = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        String recId = UUIdUtil.getUUID();
        List<Registration_record> listRecord = new ArrayList();
        int reg_count = 0;
        Registration reg =registrationService.getRegistrationByRecordId(operation.getOpe_registration_id());
        operation.setOpe_office_id("NO");
        if(reg!=null){
            operation.setOpe_office_id(reg.getExe_office_id());
        }

        operation.setId(recId);
        operation.setOpe_ident("NO");
        operation.setOpe_staff_id(operation.getCreateUserId());
        operation.setOpe_confirm_date(Date);
        operation.setUpdateUserId(operation.getCreateUserId());
        operation.setUpdateDate(Date);
        operation.setDeleteFlag("0");
        resultReg = dao.addOperation(operation)==1 ? true : false;
        if (!(resultReg)) {
            log.error("addRegistration:registration_recordService.addRegistration_record出错！");
            throw new Exception("addRegistration:registration_recordService.addRegistration_record出错！");
        }else {
            resultReg = registration_recordService.updateRegistration_recordStatus(operation.getOpe_registration_id(), "2", operation.getCreateUserId(), Date);
        }

        return resultReg;
    }

    @Override
    public List<Map<String, Object>> getOfficeSelect() {
        return dao.getOfficeSelect();
    }

    @Override
    public int getUnderlingCount(String belong_to_id) {
        return dao.getUnderlingCount(belong_to_id);
    }

    @Override
    public int geRootCount(String belong_to_id) {
        return dao.geRootCount(belong_to_id);
    }
}
