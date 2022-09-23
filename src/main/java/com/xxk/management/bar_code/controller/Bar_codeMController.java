package com.xxk.management.bar_code.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.management.bar_code.entity.Bar_code;
import com.xxk.management.bar_code.service.Bar_codeService;
import com.xxk.management.device.entity.Device;
import com.xxk.management.device.service.DeviceService;
import com.xxk.management.stock.entity.Stock;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Controller
@RequestMapping("")
public class Bar_codeMController extends BaseController {

    private static Logger log = LogManager.getLogger();

    private Supplier<Bar_code> bar_codeStorySupplier = Bar_code::new;

    @Autowired
    private Bar_codeService bar_codeService;

    @ResponseBody
    @RequestMapping("/getBar_codeByBar_code_ident")
    public Map<Object, Object> getBar_codeByBar_code_ident(@RequestParam(value = "bar_code_ident") String bar_code_ident) {
        Map<Object, Object> result = new HashMap<>();
        List<Map<String, Object>> listOffice = new ArrayList<>();
        try {
            Bar_code bar_code = bar_codeService.getBar_codeByBar_code_ident(bar_code_ident);
            if (bar_code == null) {
                log.error("获取为空");
                return null;
            }
            result.put("bar_code", bar_code);
        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", "获取出错");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/lisBar_codeByINSTRBar_code_ident", method = RequestMethod.POST)
    public Map<String, Object> lisBar_codeByINSTRBar_code_ident(@RequestParam(value = "bar_code_ident") String bar_code_ident) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Bar_code> listBar_code = bar_codeService.lisBar_codeByINSTRBar_code_ident(bar_code_ident);
            if (listBar_code == null||listBar_code.size()==0) {
                log.error("获取出错");
                return null;
            }
            result.put("entityData", listBar_code);
            /*result.put("dev_count", dev_count);*/
        } catch (Exception e) {
            log.error(e);
            return null;
        }
        return result;
    }

}
