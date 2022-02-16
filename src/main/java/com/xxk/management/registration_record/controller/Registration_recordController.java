package com.xxk.management.registration_record.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.management.registration.entity.Registration;
import com.xxk.management.registration.service.RegistrationService;
import com.xxk.management.registration_record.entity.Registration_record;
import com.xxk.management.registration_record.service.Registration_recordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class Registration_recordController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private Registration_recordService registration_recordService;

    @ResponseBody
    @RequestMapping("/listRegistration_record")
    public Map<String, Object> listRegistration_record(@RequestParam(value = "pageIndex") String pageIndex,
                                                @RequestParam(value = "limit") String limit,
                                                @RequestParam(value = "account") String office_name,
                                                @RequestParam(value = "name") String office_ident,
                                                @RequestParam(value = "startDate") String startDate,
                                                @RequestParam(value = "endDate") String endDate) {
        Map<String, Object> result = new HashMap<>();
        try {
            int pageNumber = Integer.parseInt(pageIndex) + 1;//因为pageindex 从0开始
            int pageSize = Integer.parseInt(limit);

            List<Registration_record> listRegistration = registration_recordService.listRegistration_record(pageNumber, pageSize);
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

    //处理状态
    @ResponseBody
    @RequestMapping(value = "/updateExecute_record_status", method = RequestMethod.POST)
    public Map<String, Object> acceptanceRegistration_record(@RequestParam(value = "reg_record_id") String reg_record_id,
                                                             @RequestParam(value = "execute_record_status") String execute_record_status) {
        Map<String, Object> result = new HashMap<>();
        try {
            String Date = DateUtil.getFullTime();
            String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            if (reg_record_id != null && !"".equals(reg_record_id)) {

                boolean Result=registration_recordService.updateRegistration_recordExeStatus(reg_record_id,"2",CurrentUserId,Date);
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
