package com.xxk.management.office.devices.controller;

import com.xxk.core.file.BaseController;
import com.xxk.management.bar_code.entity.Bar_code;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Controller
@RequestMapping("devicesm")
public class DevicesViewController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @RequestMapping("/BC_devices")
    public ModelAndView bar_code_devices() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/form/bar_code/bar_code_devices", "result", result);
    }

    @RequestMapping("/manage_devices")
    public ModelAndView manage_devices() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/form/bar_code/manage_devices", "result", result);
    }

}
