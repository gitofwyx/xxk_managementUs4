package com.xxk.management.test.controller;

import com.xxk.core.file.BaseController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
public class TestViewController extends BaseController {

    private static Logger log = Logger.getLogger(TestViewController.class);

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

}
