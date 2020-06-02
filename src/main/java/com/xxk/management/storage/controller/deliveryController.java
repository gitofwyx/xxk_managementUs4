package com.xxk.management.storage.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.JsonUtils;
import com.xxk.core.util.UUIdUtil;
import com.xxk.core.util.build_ident.IdentUtil;
import com.xxk.management.device.service.DeviceService;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.stock.service.StockService;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.entity.Storage;
import com.xxk.management.storage.service.DeliveryService;
import com.xxk.management.storage.service.StorageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
public class deliveryController extends BaseController {

    private static Logger log = Logger.getLogger(deliveryController.class);

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private StockService stockService;

    @ResponseBody
    @RequestMapping("/listDelivery")
    public Map<String, Object> listDelivery(@RequestParam(value = "pageIndex") String pageIndex,
                                            @RequestParam(value = "limit") String limit,
                                            @RequestParam(value = "account") String account,
                                            @RequestParam(value = "name") String name,
                                            @RequestParam(value = "out_confirmed_date") String out_confirmed_date) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            int pageNumber = Integer.parseInt(pageIndex) + 1;//页数，因为pageindex 从0开始要加1
            int pageSize = Integer.parseInt(limit);         //单页记录数

            List<Delivery> listDelivery = deliveryService.listDelivery(pageNumber, pageSize);
            if (listDelivery == null) {
                log.error("listStorage:获取分页出错");
                result.put("error", false);
                return result;
            } else if (listDelivery.isEmpty()) {
                result.put("rows", resultList);
                result.put("results", 7);
            } else {
                List<String> listDevId = new ArrayList<>();
                for (Delivery delivery : listDelivery) {
                    listDevId.add(delivery.getEntity_id());
                }
               /* Set setDevId = new  HashSet();
                setDevId.addAll(listDevId);
                listDevId.clear();
                listDevId.addAll(setDevId);*/
                List<Map<String, Object>> deviceList = deviceService.getStoreDeviceById(listDevId);
                if (resultList == null) {
                    log.error("获取分页出错");
                    result.put("error", false);
                    return result;
                } else {
                    for (Delivery delivery : listDelivery) {
                        Map<String, Object> resultMap = new HashMap<>();
                        // resultMap.put("out_flag", delivery.getOut_flag());
                        resultList.add(resultMap);
                    }
                }
                result.put("rows", resultList);
                result.put("results", 7);
            }
        } catch (Exception e) {
            log.error(e);
            result.put("error", false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/listDeliveryByStock")
    public Map<String, Object> listDeliveryByStock(@RequestParam(value = "stock_id") String stock_id,
                                            @RequestParam(value = "pageIndex") String pageIndex,
                                            @RequestParam(value = "limit") String limit,
                                            @RequestParam(value = "dev_ident") String dev_ident,
                                            @RequestParam(value = "out_confirmed_date") String out_confirmed_date) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            int pageNumber = Integer.parseInt(pageIndex) + 1;//页数，因为pageindex 从0开始要加1
            int pageSize = Integer.parseInt(limit);         //单页记录数
            List<Delivery> listDelivery = deliveryService.listDeliveryByStock(pageNumber, pageSize,stock_id);
            if (listDelivery == null) {
                log.error("listStorage:获取分页出错");
                result.put("error", false);
                return result;
            }else {
                for (Delivery delivery : listDelivery) {
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.putAll(JsonUtils.toMap(delivery));
                    resultList.add(resultMap);
                }
            }
            result.put("rows", resultList);
            result.put("results", 7);

        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", "查询出错");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/listDeliveryByOffice")
    public Map<String, Object> listDeliveryByOffice(@RequestParam(value = "office_id") String office_id) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            List<Delivery> listDelivery = deliveryService.listDeliveryByOffice(1, 9,office_id);
            if (listDelivery == null) {
                log.error("listDeliveryByOffice:获取分页出错");
                result.put("error", false);
                return result;
            }else {
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
}
