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
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RequestMapping("/listOperationAccordingDate")
    public List<Map<String, Object>> listOperationAccordingDate(@RequestParam(value = "registration_id") String registration_id) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> listRecord=new ArrayList<>();
        try {
            String[] status={"0","1"};
            //String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            listRecord = operationService.listOperationAccordingDate(registration_id, status);
            if (listRecord == null) {
                log.error("获取分页出错");
                result.put("hasError", true);
                result.put("error", "获取数据出错");

            } else {
                result.put("resultRecord", listRecord);
            }
        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", "获取数据出错");
        }
        return listRecord;
    }

    @ResponseBody
    @RequestMapping(value = "/addMobileOperation",method = RequestMethod.POST)
    public Map<String, Object> addMobileOperation(Operation operation,
                                                  @RequestParam(value = "registration_id") String registration_id,
                                                  @RequestParam(value = "reg_record_ident") String reg_record_ident,
                                                  @RequestParam(value = "reg_record_name") String reg_record_name,
                                                  @RequestParam(value = "reg_record_date") String reg_record_date,
                                                  @RequestParam(value = "reg_record_content") String reg_record_content,
                                                  @RequestParam(value = "WXRobot",required = false) String WXRobot) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> reg_recordMap=new HashMap<>();
        String Date = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        String recId = UUIdUtil.getUUID();
        try {
            if("".equals(registration_id)||registration_id==null){
                result.put("hasError", true);
                result.put("error", "添加出错,登记Id为空！");
                return result;
            }
            String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            String CurrentUser = (String) SecurityUtils.getSubject().getSession().getAttribute("userName");
            operation.setOpe_registration_id(registration_id);
            operation.setCreateUserId(CurrentUserId);
            reg_recordMap.put("reg_record_ident",reg_record_ident);
            reg_recordMap.put("reg_record_name",reg_record_name);
            reg_recordMap.put("reg_record_date",reg_record_date);
            reg_recordMap.put("reg_record_content",reg_record_content);
            reg_recordMap.put("CurrentUser",CurrentUser);
            reg_recordMap.put("WXRobot",WXRobot);
            Boolean resultOpe = operationService.addOperation(operation,reg_recordMap);
            if (!(resultOpe)) {
                result.put("hasError", true);
                result.put("error", "添加出错");
                log.error("resultOpe:"+resultOpe);
            }else {
                result.put("hasError", false);
                log.info("resultOpe:"+resultOpe);
            }
        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error",e.getLocalizedMessage());
        }
        return result;
        //return "system/index";
    }

}
