package com.xxk.management.operation.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.offices.record.entity.Record;
import com.xxk.management.offices.record.service.RecordService;
import com.xxk.management.operation.entity.Operation;
import com.xxk.management.operation.service.OperationService;
import org.apache.log4j.Logger;
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
public class OperationController extends BaseController {

    private static Logger log = Logger.getLogger(OperationController.class);

    @Autowired
    private OperationService operationService;

    @Autowired
    private RecordService recordService;


    @ResponseBody
    @RequestMapping("/listOperation")
    public Map<String, Object> listOperation(@RequestParam(value = "pageIndex") String pageIndex,
                                           @RequestParam(value = "limit") String limit,
                                           @RequestParam(value = "account") String office_name,
                                           @RequestParam(value = "name") String office_ident,
                                           @RequestParam(value = "startDate") String startDate,
                                           @RequestParam(value = "endDate") String endDate) {
        Map<String, Object> result = new HashMap<>();
        try {
            int pageNumber = Integer.parseInt(pageIndex) + 1;//因为pageindex 从0开始
            int pageSize = Integer.parseInt(limit);

            List<Operation> listOperation = operationService.listOperation(pageNumber, pageSize);
            if (listOperation == null) {
                log.error("获取分页出错");
                result.put("success", false);
                return result;
            } else {
                result.put("rows", listOperation);
                result.put("results", 7);
            }
        } catch (Exception e) {
            result.put("hasError", true);
            result.put("error", "获取列表出错");
            log.error(e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/addOperation")
    public Map<String, Object> addOperation(Operation operation,Record record) {
        Map<String, Object> result = new HashMap<>();
        String Date = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        String recId = UUIdUtil.getUUID();
        try {
            operation.setId(id);
            operation.setCreateUserId("admin");
            operation.setCreateDate(Date);
            operation.setUpdateUserId("admin");
            operation.setUpdateDate(Date);
            operation.setDeleteFlag("0");
            Boolean resultOpe = operationService.addOperation(operation);
            if (!(resultOpe)) {
                result.put("hasError", true);
                result.put("error", "添加出错");
                log.error("resultOpe:"+resultOpe);
            }else{
                record.setRec_office_id(operation.getOpe_office_id());
                record.setRec_starting_date(DateUtil.getStrYM(Date));
                record.setUpdateUserId("admin");
                record.setUpdateDate(Date);
                Boolean resultRec = recordService.plusOpeCount(record);
                if(!(resultRec)){
                    record.setId(recId);
                    record.setRec_office_id(operation.getOpe_office_id());
                    record.setReg_count(0);
                    record.setOpe_count(1);
                    record.setRec_cycle("M");
                    record.setCreateUserId("admin");
                    record.setCreateDate(Date);
                    resultRec = recordService.addRecord(record);
                    if(!(resultRec)){
                        result.put("hasError", true);
                        result.put("error", "添加出错");
                        log.error("resultRec:"+resultRec);
                    }
                }
            }
        } catch (Exception e) {
            result.put("success", false);
            log.error(e);
        }
        return result;
        //return "system/index";
    }

    @ResponseBody
    @RequestMapping("/getOperation")
    public Map<String, Object> getOperation() {
        int id = 0;
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> listOffice = new ArrayList<>();
        try {
            listOffice = operationService.getOfficeSelect();
            if (listOffice == null) {
                log.error("获取出错");
                return null;
            }
            result.put("offices_select", listOffice);
            /*result.put("dev_count", dev_count);*/
        } catch (Exception e) {
            log.error(e);
            return null;
        }
        return result;
    }

}
