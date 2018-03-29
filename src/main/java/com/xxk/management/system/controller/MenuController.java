package com.xxk.management.system.controller;

import com.xxk.core.file.BaseController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("")
public class MenuController extends BaseController {

    private static Logger log = Logger.getLogger(MenuController.class);


    @RequestMapping("/user_tab")
    public ModelAndView  listUser() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/search/user_dialog", "result", result);
    }

    @RequestMapping("/delivery_tab")
    public ModelAndView  delivery_tab() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/search/delivery-dialog", "result", result);
    }

    @RequestMapping("/offices_tab")
    public ModelAndView  offices_tab() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/search/offices-dialog", "result", result);
    }

    @RequestMapping("/storage_tab")
    public ModelAndView  storage_tab() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/search/example-dialog", "result", result);
    }

    @RequestMapping("/device_tab")
    public ModelAndView  device_tab() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/search/device_dialog", "result", result);
    }

    @ResponseBody
    @RequestMapping("/test")
    public  Map<String, Object>  test() {
        Map<String, Object> result = new HashMap<>();
        result.put("test","1");
        return result;
    }

}
