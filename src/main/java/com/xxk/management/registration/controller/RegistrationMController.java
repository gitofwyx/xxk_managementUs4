package com.xxk.management.registration.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.core.util.build_ident.IdentUtil;
import com.xxk.management.office.offices.record.entity.Record;
import com.xxk.management.office.offices.record.service.RecordService;
import com.xxk.management.registration.entity.Registration;
import com.xxk.management.registration.service.RegistrationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("")
public class RegistrationMController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private RecordService recordService;

    @ResponseBody
    @RequestMapping("/addMobileRegistration")
    public Map<String, Object> addRegistration(Registration registration, Record record) {
        Map<String, Object> result = new HashMap<>();
        String Date = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        String recId = UUIdUtil.getUUID();
        int reg_count = 0;
        try {
            Map<String, Object> resultRecord = recordService.getRecordByOffices(registration.getReg_office_id(), DateUtil.getStrYM(Date));
            if (resultRecord != null && !resultRecord.isEmpty()) {
                reg_count = (int) resultRecord.get("reg_count");
            }
            String reg_ident = IdentUtil.buildIdent("", reg_count, Date);
            registration.setId(id);
            registration.setReg_ident(reg_ident);
            registration.setCreateUserId("admin");
            registration.setCreateDate(Date);
            registration.setUpdateUserId("admin");
            registration.setUpdateDate(Date);
            registration.setDeleteFlag("0");
            Boolean resultReg = registrationService.addRegistration(registration);
            if (!(resultReg)) {
                result.put("hasError", true);
                result.put("error", "更新数据出错");
                return result;
            } else {
                record.setRec_office_id(registration.getReg_office_id());
                record.setRec_starting_date(DateUtil.getStrYM(Date));
                record.setUpdateUserId("admin");
                record.setUpdateDate(Date);
                Boolean resultRec = recordService.plusRegCount(record);
                if (!(resultRec)) {
                    record.setId(recId);
                    record.setRec_office_id(registration.getReg_office_id());
                    record.setReg_count(1);
                    record.setOpe_count(0);
                    record.setRec_cycle("M");
                    record.setCreateUserId("admin");
                    record.setCreateDate(Date);
                    record.setDeleteFlag("0");
                    resultRec = recordService.addRecord(record);
                    if (!(resultRec)) {
                        result.put("hasError", true);
                        result.put("error", "添加出错");
                        log.error("resultRec:" + resultRec);
                    }
                }
            }
        } catch (DuplicateKeyException e) {
            result.put("hasError", true);
            result.put("error", "重复值异常");
            log.error(e);
        } catch (Exception e) {
            result.put("hasError", true);
            result.put("error", "更新数据出错");
            log.error(e);
        }
        return result;
        //return "system/index";
    }

}
