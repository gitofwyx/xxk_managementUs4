package com.xxk.management.storage.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.JsonUtils;
import com.xxk.management.device.service.DeviceService;
import com.xxk.management.material.service.MaterialService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("/mobile")
public class DeliveryMController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private RebUserService rebUserService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private MaterialService materialService;


    @ResponseBody
    @RequestMapping("/listDelivery")
    public Map<String, Object> listDelivery(@RequestParam(value = "pageIndex") String pageIndex,
                                            @RequestParam(value = "limit") String limit,
                                            @RequestParam(value = "account") String account,
                                            @RequestParam(value = "name") String name,
                                            @RequestParam(value = "out_confirmed_date") String out_confirmed_date) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<String> userIdList = new ArrayList<>();
        List<Map<String, Object>> userMapList = new ArrayList<>();
        try {
            int pageNumber = Integer.parseInt(pageIndex) + 1;//页数，因为pageindex 从0开始要加1
            int pageSize = Integer.parseInt(limit);         //单页记录数
            List<Delivery> listDelivery = deliveryService.listDelivery(pageNumber, pageSize);
            if (listDelivery == null) {
                log.error("listDelivery:获取分页出错");
                result.put("error", false);
                return result;
            } else {

            }
            result.put("rows", resultList);
            result.put("results", deliveryService.countDelivery());
            result.put("userMapList", userMapList);

        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", "查询出错");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/listDeliveryByHandler")
    public Map<String, Object> listDeliveryByHandler() {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            List<Delivery> listDelivery = deliveryService.listDeliveryByHandler(CurrentUserId);
            if (listDelivery == null) {
                log.error("listDeliveryByOffice:获取分页出错");
                result.put("error", false);
                return result;
            } else {
                for (Delivery delivery : listDelivery) {
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.putAll(JsonUtils.toMap(delivery));
                    resultList.add(resultMap);
                }
            }
            result.put("code", 0);
            result.put("msg", "OK");
            result.put("count", 8);
            result.put("data", resultList);

        } catch (Exception e) {
            log.error(e);
            result.put("code", 200);
            result.put("msg", "查询出错");
            result.put("count", 8);
            result.put("data", resultList);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/listDeliveryByOffice")
    public Map<String, Object> listDeliveryByOffice(@RequestParam(value = "office_id") String office_id) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            List<Delivery> listDelivery = deliveryService.listDeliveryByOffice(1, 9, office_id);
            if (listDelivery == null) {
                log.error("listDeliveryByOffice:获取分页出错");
                result.put("error", false);
                return result;
            } else {
                for (Delivery delivery : listDelivery) {
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.putAll(JsonUtils.toMap(delivery));
                    resultList.add(resultMap);
                }
            }
            result.put("entityData", resultList);
            result.put("results", 9);

        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", "查询出错");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/backwardDelivery", method = RequestMethod.POST)
    public Map<String, Object> backwardDelivery(Storage storage,Delivery delivery,
                                                @RequestParam(value = "delivery_id") String delivery_id,
                                                @RequestParam(value = "stock_no") String stock_no,
                                                @RequestParam(value = "out_former_total") String out_total) {
        Map<String, Object> result = new HashMap<>();
        try {

            String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            delivery.setUpdateUserId(CurrentUserId);
            if ( CurrentUserId != null&&!CurrentUserId.equals(delivery.getOut_confirmed_by()) ) {
                result.put("hasError", true);
                result.put("error", "不是出库记录人");
                return result;

            }
            if ( !"".equals(delivery_id)&&delivery_id != null ) {
                delivery.setId(delivery_id);
                result = deliveryService.backwardDelivery( storage,delivery,stock_no,out_total);

            } else {
                result.put("hasError", true);
                result.put("error", "backwardDelivery:更新出错->delivery_id为空");
            }

        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", e.getLocalizedMessage());
        }
        return result;
        //return "system/index";
    }
}
