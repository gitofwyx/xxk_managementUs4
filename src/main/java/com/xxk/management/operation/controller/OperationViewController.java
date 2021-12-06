package com.xxk.management.operation.controller;

import com.xxk.core.file.BaseController;
import com.xxk.management.operation.service.OperationService;
import com.xxk.management.registration.service.RegistrationMService;
import com.xxk.management.registration_record.service.Registration_recordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("/ope")
public class OperationViewController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private RegistrationMService registrationMService;

    @Autowired
    private Registration_recordService registration_recordService;

    @Autowired
    private OperationService operationService;

    @RequestMapping(value="/operation_record_part",method = RequestMethod.POST)
    public ModelAndView  operation_form_part(@RequestParam Map<String, Object> mapParam) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> listRecord=new ArrayList<>();
        try {
            String[] status={"0","1"};
            String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            //String userName = (String) SecurityUtils.getSubject().getSession().getAttribute("userName");
            listRecord = operationService.listOperationAccordingDate((String) mapParam.get("id"), status);
            if (listRecord == null) {
                log.error("获取分页出错");
            } else {
                result.put("regRecord", mapParam);
                result.put("RecordMap", listRecord);
                //result.put("userName",userName);
            }
        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", "获取数据出错");
        }
        List<String> list=new ArrayList<>();
        list.add("123");
        return new ModelAndView("form/layui_html_part/operation_record_part", "result", result);
    }

}
