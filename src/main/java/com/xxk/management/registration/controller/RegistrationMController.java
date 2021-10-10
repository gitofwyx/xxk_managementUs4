package com.xxk.management.registration.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.core.util.build_ident.IdentUtil;
import com.xxk.management.office.offices.record.entity.Record;
import com.xxk.management.office.offices.record.service.RecordService;
import com.xxk.management.registration.entity.Registration;
import com.xxk.management.registration.entity.Registration_record;
import com.xxk.management.registration.service.RegistrationMService;
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
    private RegistrationMService registrationMService;

    @ResponseBody
    @RequestMapping("/addMobileRegistration")
    public Map<String, Object> addRegistration(Registration registration, Registration_record record) {
        Map<String, Object> result = new HashMap<>();
        String Date = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        String recId = UUIdUtil.getUUID();
        int reg_count = 0;
        try {


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
