package com.xxk.management.bar_code.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.bar_code.entity.Bar_code;
import com.xxk.management.bar_code.service.Bar_codeService;
import com.xxk.management.device.service.DeviceService;
import com.xxk.management.storage.service.DeliveryReportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Controller
@RequestMapping("sys_bar_code")
public class AdminBar_codeController extends BaseController {

    private static Logger log = LogManager.getLogger();

    private Supplier<Bar_code> bar_codeStorySupplier = Bar_code::new;

    @Autowired
    private Bar_codeService bar_codeService;

    @ResponseBody
    @RequestMapping("/addBar_code")
    public Map<String, Object> addBar_code(Bar_code bar_code,
                                           @RequestParam(value = "bar_code") String code,
                                           @RequestParam(value = "entity_id") String entity_id,
                                           @RequestParam(value = "bar_code_type") String bar_code_type) {
        Map<String, Object> result = new HashMap<>();

        String recId = UUIdUtil.getUUID();
        int reg_count = 0;
        try {
            if("".equals(entity_id)||entity_id==null){
                result.put("hasError", true);
                result.put("error", "更新数据出错");
                return result;
            }
            String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            String CurrentUser = (String) SecurityUtils.getSubject().getSession().getAttribute("userName");

            bar_code.setBar_code(code);
            bar_code.setBar_code_by(CurrentUserId);
            bar_code.setBar_code_type(bar_code_type);
            result=bar_codeService.addBar_code(bar_code);

        } catch (DuplicateKeyException e) {
            result.put("hasError", true);
            result.put("error", "重复值异常");
            log.error(e);
        } catch (Exception e) {
            result.put("hasError", true);
            result.put("error", "更新数据出错");
            log.error(e);
        }
        return result;
        //return "system/index";
    }

}
