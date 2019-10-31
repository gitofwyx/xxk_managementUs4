package com.xxk.management.stock.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.JsonUtils;
import com.xxk.core.util.UUIdUtil;
import com.xxk.core.util.build_ident.IdentUtil;
import com.xxk.management.device.service.DeviceService;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.entity.Storage;
import com.xxk.management.storage.service.DeliveryService;
import com.xxk.management.stock.service.StockService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
public class StockController extends BaseController {

    private static Logger log = Logger.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private DeviceService deviceService;

    @ResponseBody
    @RequestMapping("/listStock")
    public Map<String, Object> listStock(@RequestParam(value = "pageIndex") String pageIndex,
                                              @RequestParam(value = "limit") String limit,
                                              @RequestParam(value = "search_class_id") String search_class_id,
                                              @RequestParam(value = "search_device_id") String search_device_id,
                                              @RequestParam(value = "search_office_id") String search_office_id,
                                              @RequestParam(value = "search_type") int search_type) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            int pageNumber = Integer.parseInt(pageIndex) + 1;//页数，因为pageindex 从0开始要加1
            int pageSize = Integer.parseInt(limit);         //单页记录数
            List<Stock> listStock = stockService.listStock(pageNumber, pageSize,search_type);
            if (listStock == null) {
                log.error("listStock:获取分页出错");
                result.put("hasError", true);
                result.put("error", "获取出错");
                return result;
            } else if (listStock.isEmpty()) {
                result.put("rows", resultList);
                result.put("results", 7);
            } else {
                List<String> listDevId = new ArrayList<>();
                for (Stock stock : listStock) {
                    listDevId.add(stock.getEntity_id());
                }
               /* Set setDevId = new  HashSet();
                setDevId.addAll(listDevId);
                listDevId.clear();
                listDevId.addAll(setDevId);*/
                List<Map<String, Object>> entityList=new ArrayList<>();
                if(search_type==1||search_type==2||search_type==3){
                     entityList = deviceService.getStoreDeviceById(listDevId);
                } else {
                    entityList=null;
                }
                if (resultList == null||entityList==null) {
                    log.error("获取分页出错");
                    result.put("hasError", true);
                    result.put("error", "获取出错");
                    return result;
                } else {
                    for (Stock stock : listStock) {
                        Map<String, Object> resultMap = new HashMap<>();
                        for (Map<String, Object> entityMap : entityList) {
                            if (stock.getEntity_id().equals(entityMap.get("id"))) {
                                resultMap.put("entity_name", entityMap.get("entity_name"));
                                resultMap.put("entity_type", entityMap.get("entity_type"));
                            }
                        }
                        resultMap.putAll(JsonUtils.toMap(stock));
                        resultList.add(resultMap);
                    }
                }
                result.put("rows", resultList);
                result.put("results", 7);
            }
        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", "获取出错");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/addStock",method = RequestMethod.POST)
    public Map<String, Object> addStock(Stock stock,Storage storage,
                                        @RequestParam(value = "stock_record_id") String stock_record_id) {
        Map<String, Object> result=new HashMap<>();
        if(stock_record_id!=null&&!"".equals(stock_record_id)){
            stock.setId(stock_record_id);//获取库存的id值
            result = stockService.updateStockWithStorage(stock,storage);
        }else {
            result = stockService.addStockWithStorage(stock,storage);
        }

        return result;
        //return "system/index";
    }

    @ResponseBody
    @RequestMapping("/updateStock")
    public Map<String, Object> updateStock(Stock stock,Delivery delivery,
                                           @RequestParam(value = "stock_record_id") String stock_record_id) {
        Map<String, Object> result=new HashMap<>();
        if(stock_record_id!=null&&!"".equals(stock_record_id)){
            stock.setId(stock_record_id);//获取库存的id值
            result = stockService.updateStockWithDelivery(stock,delivery);
        }else {
            result.put("hasError", true);
            result.put("error", "updateStock:获取stock_record_id出错");
            log.error("updateStock:" + result.get("error"));
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/listStockByEntityId",method = RequestMethod.POST)
    public Map<String, Object> listStockByEntityId(@RequestParam(value = "entity_id") String entity_id,
                                                   @RequestParam(value = "stock_office") String office_id) {
        int id = 0;
        Map<String, Object> result = new HashMap<>();
        try {
            List<Stock> listStock = stockService.listStockByEntityId(entity_id,office_id);
            if (listStock == null) {
                log.error("获取出错");
                return null;
            }
            result.put("entityData", listStock);
            /*result.put("dev_count", dev_count);*/
        } catch (Exception e) {
            log.error(e);
            return null;
        }
        return result;
    }

}
