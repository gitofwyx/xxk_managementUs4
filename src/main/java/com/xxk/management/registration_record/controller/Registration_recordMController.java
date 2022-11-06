package com.xxk.management.registration_record.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.registration.entity.Registration;
import com.xxk.management.registration.service.RegistrationMService;
import com.xxk.management.registration.service.RegistrationService;
import com.xxk.management.registration_record.entity.Registration_record;
import com.xxk.management.registration_record.service.Registration_recordService;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.storage.entity.Delivery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("")
public class Registration_recordMController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private Registration_recordService registration_recordService;

    @ResponseBody
    @RequestMapping("/showMobileRegistration_record")
    public List<Map<String, Object>> showMobileRegistration_record() {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> listRecord=new ArrayList<>();
        try {
            String[] status={"0"};
            String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            String Day= DateUtil.getFullDay();
            String startDate= DateUtil.getManyMonthsAgoStartDate(Day,-12)+" 00:00:00";
            String endDate= DateUtil.getFullTime();
            listRecord = registration_recordService.getRegistration_recordMakeDate(CurrentUserId,CurrentUserId, status,startDate,endDate);
            if (listRecord == null) {
                log.error("获取分页出错");
                result.put("hasError", true);
                result.put("error", "获取数据出错");

            } else {
                result.put("resultRecord", listRecord);
            }
        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", "获取数据出错");
        }
        return listRecord;
    }

    @ResponseBody
    @RequestMapping(value = "/getRegistration_recordById", method = RequestMethod.POST)
    public Map<String, Object> getRegistration_recordById(@RequestParam(value = "record_id") String recordId) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> listReg_record = new ArrayList<>();
        try {
            listReg_record=registration_recordService.getRegistration_recordById(recordId);
            if (listReg_record.isEmpty()) {
                result.put("hasError", true);
                result.put("error", "记录为空！");
            }
            result.put("listReg_record", listReg_record);
        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", "获取出错");
        }
        return result;
    }

    //受理
    @ResponseBody
    @RequestMapping(value = "/acceptanceRegistration_record", method = RequestMethod.POST)
    public Map<String, Object> acceptanceRegistration_record(@RequestParam(value = "registration_id") String registration_id,
                                                             @RequestParam(value = "reg_record_id") String reg_record_id,
                                                             @RequestParam(value = "registration_py") String registration_py) {
        Map<String, Object> result = new HashMap<>();
        try {

            String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            if(!registration_py.equals(CurrentUserId)){
                result.put("hasError", true);
                result.put("error", "不是登记者");
                return result;
            }
            if (reg_record_id != null && !"".equals(reg_record_id)) {

                boolean Result = registration_recordService.acceptanceRegistration_record(registration_id,reg_record_id,CurrentUserId,registration_py);
                if (!(Result)) {
                    result.put("hasError", true);
                    result.put("error", "更新出错");
                } else {
                    result.put("hasError", false);

                }
            }

        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error",e.getLocalizedMessage());
            /*Optional.ofNullable(e).ifPresent(e1 -> {
                result.put("error",e1.getLocalizedMessage());
            });*/
        }
        return result;
        //return "system/index";
    }

    //撤销
    @ResponseBody
    @RequestMapping(value = "/repealRegistration_record", method = RequestMethod.POST)
    public Map<String, Object> repealRegistration_record(@RequestParam(value = "registration_id") String registration_id,
                                                             @RequestParam(value = "reg_record_id") String reg_record_id,
                                                             @RequestParam(value = "exe_status") String exe_status,
                                                             @RequestParam(value = "registration_py") String registration_py) {
        Map<String, Object> result = new HashMap<>();
        try {

            String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            if(!registration_py.equals(CurrentUserId)){
                result.put("hasError", true);
                result.put("error", "不是登记者");
                return result;
            }
            if(!"0".equals(exe_status)){
                result.put("hasError", true);
                result.put("error", "该登记已受理");
                return result;
            }
            if (reg_record_id != null && !"".equals(reg_record_id)) {

                boolean Result = registration_recordService.repealRegistration_record(registration_id,reg_record_id,CurrentUserId,registration_py);
                if (!(Result)) {
                    result.put("hasError", true);
                    result.put("error", "更新出错");
                } else {
                    result.put("hasError", false);

                }
            }

        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error",e.getLocalizedMessage());
            /*Optional.ofNullable(e).ifPresent(e1 -> {
                result.put("error",e1.getLocalizedMessage());
            });*/
        }
        return result;
        //return "system/index";
    }
}
