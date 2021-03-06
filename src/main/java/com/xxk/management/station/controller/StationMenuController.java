package com.xxk.management.station.controller;

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
@RequestMapping("/station_menu")
public class StationMenuController extends BaseController {

    private static Logger log = LogManager.getLogger();

    /*@RequestMapping("/index")
    public ModelAndView  index() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/index", "result", result);
    }*/

    @RequestMapping("/station_tab")
    public ModelAndView  depository_tab() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/station/search/station_dialog", "result", result);
    }

    @RequestMapping("/devices_tab")
    public ModelAndView  material_tab() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/offices/search/devices_dialog", "result", result);
    }

}
