package com.xxk.management.stock.controller;

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
@RequestMapping("stockm")
public class StockViewController extends BaseController {

    private static Logger log = LogManager.getLogger();

    private Supplier<Bar_code> bar_codeStorySupplier = Bar_code::new;

    @RequestMapping("/BC_delivery")
    public ModelAndView depository_tab() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/form/bar_code/bar_code_delivery", "result", result);
    }

}
