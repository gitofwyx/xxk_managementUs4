package com.xxk.management.registration_record.controller;

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
@RequestMapping("/reg")
public class Registration_recordViewController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private RegistrationMService registrationMService;

    @Autowired
    private Registration_recordService registration_recordService;

    @Autowired
    private OperationService operationService;

    /*@RequestMapping("/index")
    public ModelAndView  index() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/index", "result", result);
    }*/

    @RequestMapping("")
    public ModelAndView  application_workstation(@RequestParam(value = "reg_id",required = false) String reg_id) {
        Map<String, Object> result = new HashMap<>();
        result.put("reg_id",reg_id);

        String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");

        return new ModelAndView("/form/bootstrap_form", "result", result);
    }

    @RequestMapping(value="/registration_record_part",method = RequestMethod.POST)
    public ModelAndView  registration_record_part(@RequestParam(value = "office_id",required = false) String office_id,
                                                  @RequestParam(value = "status[]") String[] status) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> listRecord=new ArrayList<>();
        try {
            //String[] status={"0","1"};
            String CurrentUserId ="";//前端业务需求：科室不为空时申请人要为空
            if("".equals(office_id)||office_id==null){
                CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            }//前端业务需求：科室为空时申请人为当前登录
            //String userName = (String) SecurityUtils.getSubject().getSession().getAttribute("userName");
            listRecord = registration_recordService.getRegistration_recordMakeDate(CurrentUserId,office_id, status);
            if (listRecord == null) {
                log.error("获取分页出错");
            } else {
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
        return new ModelAndView("/form/layui_html_part/registration_record_part", "result", result);
    }

    @RequestMapping(value="/registration_record_part1",method = RequestMethod.POST)
    public ModelAndView  registration_record_part1(@RequestParam(value = "office_id",required = false) String office_id,
                                                   @RequestParam(value = "status[]") String[] status,
                                                   @RequestParam(value = "e_status[]") String[] e_status) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> listRecord=new ArrayList<>();
        try {
            String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            //String userName = (String) SecurityUtils.getSubject().getSession().getAttribute("userName");
            listRecord = registrationMService.listRegistrationMapAccordingDate(office_id,"","", status,e_status);
            if (listRecord == null) {
                log.error("获取分页出错");
            } else {
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
        return new ModelAndView("form/layui_html_part/registration_record_part1", "result", result);
    }

}
