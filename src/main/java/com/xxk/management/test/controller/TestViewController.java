package com.xxk.management.test.controller;

import com.xxk.core.file.BaseController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("/test")
public class TestViewController extends BaseController {

    private static Logger log = LogManager.getLogger();

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
        return new ModelAndView("/form/bootstrap_form", "result", result);
    }

    @RequestMapping("/testReg")
    public ModelAndView  testReg() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/layui/form", "result", result);
    }

}
