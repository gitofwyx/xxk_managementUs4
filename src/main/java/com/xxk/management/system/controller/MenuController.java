package com.xxk.management.system.controller;

import com.xxk.core.file.BaseController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

    private static Logger log = LogManager.getLogger();

    /*@RequestMapping("/index")
    public ModelAndView  index() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/index", "result", result);
    }*/

    @RequestMapping("/user_tab")
    public ModelAndView  listUser() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/search/user_dialog", "result", result);
    }

    @RequestMapping("/offices_tab")
    public ModelAndView  offices_tab() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/search/offices_dialog", "result", result);
    }

    @RequestMapping("/station_tab")
    public ModelAndView  station_tab() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/station/sysadmin/station_admin", "result", result);
    }

    @RequestMapping("/storage_tab/{stock_id}")
    public ModelAndView  storage_tab(@PathVariable("stock_id") String stock_id) {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/search/storage_dialog", "result", result);
    }

    @RequestMapping("/delivery_tab/{stock_id}")
    public ModelAndView  delivery_tab(@PathVariable("stock_id") String stock_id) {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/search/delivery_dialog", "result", stock_id);
    }

    @RequestMapping("/device_tab")
    public ModelAndView  device_tab() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/search/device_dialog", "result", result);
    }

    @RequestMapping("/stock_tab")
    public ModelAndView  stock_tab() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/search/stock_dialog", "result", result);
    }

    @RequestMapping("/material_tab")
    public ModelAndView  material_tab() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/search/material_dialog", "result", result);
    }

    @RequestMapping("/stock_devices_tab")
    public ModelAndView  stock_devices_tab() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/search/stock_devices_dialog", "result", result);
    }

    @RequestMapping("/registration_tab")
    public ModelAndView  registration_tab() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/search/registration_dialog", "result", result);
    }

    @RequestMapping("/operation_tab")
    public ModelAndView  operation_tab() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/search/operation_dialog", "result", result);
    }

    @ResponseBody
    @RequestMapping("/test")
    public  Map<String, Object>  test() {
        Map<String, Object> result = new HashMap<>();
        result.put("test","1");
        return result;
    }

}
