package com.xxk.management.test.controller;

import com.xxk.core.file.BaseController;
import com.xxk.management.registration_record.service.Registration_recordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("/test")
public class TestViewController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private Registration_recordService registration_recordService;

    /*@RequestMapping("/index")
    public ModelAndView  index() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/index", "result", result);
    }*/

    @RequestMapping("/test_dialog")
    public ModelAndView  test_dialog() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/search/test-dialog", "result", result);
    }

    @RequestMapping("/test1")
    public ModelAndView  test1() {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> listRecord=new ArrayList<>();
        try {

            String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            listRecord = registration_recordService.getRegistration_recordMakeDate(CurrentUserId, "0");
            if (listRecord == null) {
                log.error("获取分页出错");
            } else {
                result.put("RecordMap", listRecord);
            }
        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", "获取数据出错");
        }
        List<String> list=new ArrayList<>();
        list.add("123");
        return new ModelAndView("/form/bootstrap_form", "result", result);
    }

    @RequestMapping("/testReg")
    public ModelAndView  testReg() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/layui/form", "result", result);
    }

}
