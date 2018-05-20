package com.xxk.management.registration.controller;

import com.xxk.core.file.BaseController;
import com.xxk.management.registration.entity.Registration;
import com.xxk.management.registration.service.RegistrationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static Logger log = Logger.getLogger(RegistrationController.class);

    @Autowired
    private RegistrationService registrationService;

//    @Autowired
//    private OpeRecordService opeRecordService;


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

            List<Registration> listOperation = registrationService.listRegistration(pageNumber, pageSize);
            if (listOperation == null) {
                log.error("获取分页出错");
                result.put("success", false);
                return result;
            } else {
                result.put("rows", listOperation);
                result.put("results", 7);
            }
        } catch (Exception e) {
            log.error(e);
            result.put("success", false);
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
