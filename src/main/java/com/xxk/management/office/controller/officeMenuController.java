package com.xxk.management.office.controller;

import com.xxk.core.file.BaseController;
import org.apache.log4j.Logger;
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
@RequestMapping("/office_menu")
public class officeMenuController extends BaseController {

    private static Logger log = Logger.getLogger(officeMenuController.class);

    /*@RequestMapping("/index")
    public ModelAndView  index() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/index", "result", result);
    }*/

    @RequestMapping("/devices_tab")
    public ModelAndView  devices_tab() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/office_station/search/devices_dialog", "result", result);
    }

    @RequestMapping("/materials_tab")
    public ModelAndView  material_tab() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/search/material_dialog", "result", result);
    }

}
