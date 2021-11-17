package com.xxk.management.operation.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.office.offices.record.entity.Record;
import com.xxk.management.office.offices.record.service.RecordService;
import com.xxk.management.operation.entity.Operation;
import com.xxk.management.operation.service.OperationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("")
public class OperationMController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private OperationService operationService;

    @Autowired
    private RecordService recordService;

    @ResponseBody
    @RequestMapping("/addMobileOperation")
    public Map<String, Object> addMobileOperation(Operation operation,
                                                  @RequestParam(value = "registration_id") String registration_id) {
        Map<String, Object> result = new HashMap<>();
        String Date = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        String recId = UUIdUtil.getUUID();
        try {
            if("".equals(registration_id)||registration_id==null){
                result.put("hasError", true);
                result.put("error", "添加出错,登记Id为空！");
            }
            String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            operation.setOpe_registration_id(registration_id);
            operation.setCreateUserId(CurrentUserId);
            Boolean resultOpe = operationService.addOperation(operation);
            if (!(resultOpe)) {
                result.put("hasError", true);
                result.put("error", "添加出错");
                log.error("resultOpe:"+resultOpe);
            }else {
                result.put("hasError", false);
                log.info("resultOpe:"+resultOpe);
            }
        } catch (Exception e) {
            result.put("hasError", true);
            result.put("error", "添加出错");
            log.error(e);
        }
        return result;
        //return "system/index";
    }

}
