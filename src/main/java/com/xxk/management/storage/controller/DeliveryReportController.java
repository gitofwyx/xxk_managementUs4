package com.xxk.management.storage.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.JsonUtils;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.entity.Storage;
import com.xxk.management.storage.service.DeliveryService;
import com.xxk.management.user.service.RebUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("/report")
public class DeliveryReportController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private RebUserService rebUserService;

    @ResponseBody
    @RequestMapping("/deliveryReport")
    public Map<String, Object> deliveryReport(HttpServletRequest request,
                                              HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String startDate=DateUtil.getMonthStartDate(createDate);
        String endDate=createDate;
        try {
            Map<String, String[]> pramMap = request.getParameterMap();
            Map<String, Object> objectMap = new HashMap<>();
            for (String key : pramMap.keySet()) {

                String[] pramChar = pramMap.get(key);
                StringBuffer pramBuffer = new StringBuffer();
                for (int i = 0; i < pramChar.length; i++) {
                    pramBuffer.append(pramChar[i]);
                }
                String pram = pramBuffer.toString();
                objectMap.put(key, pram);
            }
            if(!"".equals(objectMap.get("date"))&&objectMap.get("date")!=null){
                String Day=DateUtil.getFullDay();
                startDate=DateUtil.getPreMonthStartDate(Day)+" 00:00:00";
                endDate= DateUtil.getPreMonthEndDate(Day)+" 23:59:59";
            }
            if(!"".equals(objectMap.get("startdate"))&&objectMap.get("startdate")!=null){
                startDate=(String) objectMap.get("startdate");
            }
            if(!"".equals(objectMap.get("enddate"))&&objectMap.get("enddate")!=null){
                endDate=(String) objectMap.get("enddate");
            }

            resultMap = deliveryService.deliveryReport(startDate,endDate);
            if (resultMap == null) {
                log.error("listDelivery:获取分页出错");
                return resultMap;
            }

        } catch (Exception e) {
            log.error(e);
            resultMap.put("hasError", true);
            resultMap.put("error", "查询出错");
        }
        return resultMap;
    }

}
