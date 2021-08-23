package com.xxk.management.bar_code.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.management.bar_code.entity.Bar_code;
import com.xxk.management.bar_code.service.Bar_codeService;
import com.xxk.management.bar_code.service.StartPrintCheckOutService;
import com.xxk.management.device.entity.Device;
import com.xxk.management.device.service.DeviceService;
import com.xxk.management.storage.service.DeliveryReportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Controller
@RequestMapping("")
public class Bar_codeViewController extends BaseController {

    private static Logger log = LogManager.getLogger();

    private Supplier<Bar_code> bar_codeStorySupplier = Bar_code::new;

    @RequestMapping("/storage_scan_code_tab")
    public ModelAndView depository_tab() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/bar_code/storage_scan_code", "result", result);
    }

}
