package com.xxk.management.bar_code.controller;

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
