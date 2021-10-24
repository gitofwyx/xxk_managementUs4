package com.xxk.management.registration_record.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.registration.entity.Registration;
import com.xxk.management.registration.service.RegistrationMService;
import com.xxk.management.registration.service.RegistrationService;
import com.xxk.management.registration_record.entity.Registration_record;
import com.xxk.management.registration_record.service.Registration_recordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
            listRecord = registration_recordService.getRegistration_recordMakeDate(CurrentUserId, status);
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
}