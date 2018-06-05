package com.xxk.management.registration.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.offices.record.entity.Record;
import com.xxk.management.offices.record.service.RecordService;
import com.xxk.management.registration.entity.Registration;
import com.xxk.management.registration.service.RegistrationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RegistrationController extends BaseController {

    private static Logger log = Logger.getLogger(RegistrationController.class);

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private RecordService recordService;


    @ResponseBody
    @RequestMapping("/listRegistration")
    public Map<String, Object> listRegistration(@RequestParam(value = "pageIndex") String pageIndex,
                                           @RequestParam(value = "limit") String limit,
                                           @RequestParam(value = "account") String office_name,
                                           @RequestParam(value = "name") String office_ident,
                                           @RequestParam(value = "startDate") String startDate,
                                           @RequestParam(value = "endDate") String endDate) {
        Map<String, Object> result = new HashMap<>();
        try {
            int pageNumber = Integer.parseInt(pageIndex) + 1;//因为pageindex 从0开始
            int pageSize = Integer.parseInt(limit);

            List<Registration> listRegistration = registrationService.listRegistration(pageNumber, pageSize);
            if (listRegistration == null) {
                log.error("获取分页出错");
                result.put("hasError", true);
                result.put("error", "获取数据出错");
                return result;
            } else {
                result.put("rows", listRegistration);
                result.put("results", 7);
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
    public Map<String, Object> addRegistration(Registration registration,Record record) {
        Map<String, Object> result = new HashMap<>();
        String Date = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        String recId = UUIdUtil.getUUID();
        try {
            registration.setId(id);
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
            }else{
                record.setRec_office_id(registration.getReg_office_id());
                record.setRec_starting_date(DateUtil.getStrYearM(Date));
                record.setUpdateUserId("admin");
                record.setUpdateDate(Date);
                Boolean resultRec = recordService.plusRegCount(record);
                if(!(resultRec)){
                    record.setId(recId);
                    record.setRec_office_id(registration.getReg_office_id());
                    record.setReg_count(1);
                    record.setOpe_count(0);
                    record.setRec_cycle("M");
                    record.setCreateUserId("admin");
                    record.setCreateDate(Date);
                    resultRec = recordService.addRecord(record);
                    if(!(resultRec)){
                        result.put("hasError", true);
                        result.put("error", "添加出错");
                        log.error("resultRec:"+resultRec);
                    }
                }
            }
            //Boolean resultOpe = opeRecordService.plusOpeRecord(operation.getOpe_office_id());
        } catch (Exception e) {
            result.put("success", false);
            log.error(e);
        }
        return result;
        //return "system/index";
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
