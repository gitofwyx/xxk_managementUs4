package com.xxk.management.registration.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.core.util.build_ident.IdentUtil;
import com.xxk.management.office.offices.record.entity.Record;
import com.xxk.management.office.offices.record.service.RecordService;
import com.xxk.management.registration.entity.Registration;
import com.xxk.management.registration.service.RegistrationService;
import com.xxk.management.registration_record.service.Registration_recordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("")
public class RegistrationController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private Registration_recordService registration_recordService;

    @ResponseBody
    @RequestMapping("/listRegistration")
    public Map<String, Object> listRegistration(@RequestParam(value = "pageIndex") String pageIndex,
                                                @RequestParam(value = "limit") String limit,
                                                @RequestParam(value = "reg_record_ident") String reg_record_ident,
                                                @RequestParam(value = "reg_office_id",required = false) String reg_office_id,
                                                @RequestParam(value = "reg_record_status",required = false) String reg_record_status,
                                                @RequestParam(value = "execute_record_status",required = false) String execute_record_status,
                                                @RequestParam(value = "startDate") String startDate,
                                                @RequestParam(value = "endDate") String endDate) {
        Map<String, Object> result = new HashMap<>();
        try {
            int pageNumber = Integer.parseInt(pageIndex) + 1;//因为pageindex 从0开始
            int pageSize = Integer.parseInt(limit);

            List<Map<String, Object>> listRegistration = registrationService.listRegistrationMap(
                    reg_record_ident,
                    reg_office_id,
                    reg_record_status,
                    execute_record_status,
                    pageNumber,
                    pageSize,"","");
            if (listRegistration == null) {
                log.error("获取分页出错");
                result.put("hasError", true);
                result.put("error", "获取数据出错");
                return result;
            } else {
                result.put("rows", listRegistration);
                result.put("results", registration_recordService.countRegistration_record());
            }
        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", "获取数据出错");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/addRegistration")
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

    @ResponseBody
    @RequestMapping("/getRecordByOffices")
    public Map<String, Object> getRecordByOffices(String officeId) {
        String Date = DateUtil.getFullTime();
        Map<String, Object> result = new HashMap<>();
        try {
            result = recordService.getRecordByOffices(officeId, DateUtil.getStrYM(Date));
            if (result.isEmpty()) {
                log.error("获取出错");
                result.put("hasError", true);
                result.put("error", "获取科室记录出错");
            }
        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", "获取科室记录出错");
        }
        return result;
    }

    /*@ResponseBody
    @RequestMapping("/getOperation")
    public Map<String, Object> getOfficeSelect() {
        int id = 0;
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> listOffice = new ArrayList<>();
        try {
            listOffice = registrationService.getOfficeSelect();
            if (listOffice == null) {
                log.error("获取出错");
                return null;
            }
            result.put("offices_select", listOffice);
            *//*result.put("dev_count", dev_count);*//*
        } catch (Exception e) {
            log.error(e);
            return null;
        }
        return result;
    }*/

}
