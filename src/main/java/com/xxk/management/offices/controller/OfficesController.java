package com.xxk.management.offices.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.offices.entity.Offices;
import com.xxk.management.offices.service.OfficesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("")
public class OfficesController extends BaseController {

    private static Logger log = Logger.getLogger(OfficesController.class);

    @Autowired
    private OfficesService officesService;


    @ResponseBody
    @RequestMapping("/listOffices")
    public Map<String, Object> listOffices(@RequestParam(value = "pageIndex") String pageIndex,
                                           @RequestParam(value = "limit") String limit,
                                           @RequestParam(value = "office_name") String office_name,
                                           @RequestParam(value = "office_ident") String office_ident,
                                           @RequestParam(value = "startDate") String startDate,
                                           @RequestParam(value = "endDate") String endDate) {
        Map<String, Object> result = new HashMap<>();
        try {
            int pageNumber = Integer.parseInt(pageIndex) + 1;//因为pageindex 从0开始
            int pageSize = Integer.parseInt(limit);

            List<Offices> listDevice = officesService.listOffices(pageNumber, pageSize);
            if (listDevice == null) {
                log.error("获取分页出错");
                result.put("success", false);
                return result;
            } else {
                result.put("rows", listDevice);
                result.put("results", 7);
            }
        } catch (Exception e) {
            log.error(e);
            result.put("success", false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/addOffices")
    public Map<String, Boolean> addOffices(Offices offices) {
        Map<String, Boolean> result = new HashMap<>();
        String Date = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        try {
            offices.setId(id);
            offices.setCreateUserId("admin");
            offices.setCreateDate(Date);
            offices.setUpdateUserId("admin");
            offices.setUpdateDate(Date);
            offices.setDeleteFlag("0");
            Boolean resultBl = officesService.addOffices(offices);
            if (!(resultBl)) {
                result.put("error", false);
                return result;
            }
        } catch (Exception e) {
            result.put("success", false);
            log.error(e);
        }
        return result;
        //return "system/index";
    }

}
