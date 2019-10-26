package com.xxk.management.storage.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.JsonUtils;
import com.xxk.management.device.service.DeviceService;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.stock.service.StockService;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.entity.Storage;
import com.xxk.management.storage.service.DeliveryService;

import com.xxk.management.storage.service.StorageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
public class StorageController extends BaseController {

    private static Logger log = Logger.getLogger(StorageController.class);

    @Autowired
    private StorageService storageService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private StockService stockService;

    @ResponseBody
    @RequestMapping("/listStorage")
    public Map<String, Object> listStorage(@RequestParam(value = "pageIndex") String pageIndex,
                                           @RequestParam(value = "limit") String limit,
                                           @RequestParam(value = "account") String account,
                                           @RequestParam(value = "name") String name,
                                           @RequestParam(value = "in_confirmed_date") String in_confirmed_date) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            int pageNumber = Integer.parseInt(pageIndex) + 1;//页数，因为pageindex 从0开始要加1
            int pageSize = Integer.parseInt(limit);         //单页记录数

            List<Storage> listStorage = storageService.listStorage(pageNumber, pageSize);
            if (listStorage == null) {
                log.error("listStorage:获取分页出错");
                result.put("hasError", true);
                result.put("error", "获取分页出错");
                return result;
            }else {
                for (Storage storage : listStorage) {
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.putAll(JsonUtils.toMap(storage));
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
    @RequestMapping("/addStorage")
    public Map<String, Object> addStorage(Stock stock, Storage storage) {
        Map<String, Object> result = new HashMap<>();
        result = storageService.addStorage(stock,storage);

        return result;
        //return "system/index";
    }

    @ResponseBody
    @RequestMapping("/updateStorage")
    public void updateStorage(Storage storage, Delivery delivery) {
       return ;
    }

}
