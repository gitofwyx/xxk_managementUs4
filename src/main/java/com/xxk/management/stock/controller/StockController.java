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
                                              @RequestParam(value = "account") String account,
                                              @RequestParam(value = "name") String name,
                                              @RequestParam(value = "in_confirmed_date") String in_confirmed_date,
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
                if(search_type==1){
                     entityList = deviceService.getStoreDeviceById(listDevId);
                }
                else if(search_type==2||search_type==3){
                    entityList = deviceService.getStoreDeviceById(listDevId);
                }else {
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
    @RequestMapping("/addStock")
    public Map<String, Object> addStock(Stock stock,Storage storage,
                                        @RequestParam(value = "stock_record_id") String stock_record_id) {
        Map<String, Object> result=new HashMap<>();
        if(stock_record_id!=null&&!"".equals(stock_record_id)){
            stock.setId(stock_record_id);
            result = stockService.updateStockWithStorage(stock,storage);
        }else {
            result = stockService.addStockWithStorage(stock,storage);
        }

        return result;
        //return "system/index";
    }

    @ResponseBody
    @RequestMapping("/updateStock")
    public Map<String, Object> updateStock(Storage storage,Delivery delivery) {
        Map<String, Object> result = new HashMap<>();
        //String phone=(String)request.getAttribute("phone");
        //String phone = request.getParameter("phone");
        String deliveryId = UUIdUtil.getUUID();
        String updateDate = DateUtil.getFullTime();
        try{
            if(!"".equals(delivery.getDevice_id())&&delivery.getDevice_id()!=null){
                List<Map<String, Object>> resultList = deviceService.getDeviceNumber(delivery.getDevice_id());////获取设备数量
                if (resultList.size() != 1 || resultList == null) {
                    log.info("出错！无法获取设备ID对应的库存量！");
                    result.put("error", false);
                    return result;
                }
                String dev_ident=(String) resultList.get(0).get("dev_ident");//提取设备编号
                int dev_no=(int)resultList.get(0).get("dev_no");//提取设备数量
                String out_confirmed_ident= IdentUtil.getIdentNo(dev_ident,dev_no,(int)storage.getIn_confirmed_no(),updateDate);
                //出库记录
                delivery.setId(deliveryId);
                delivery.setOut_confirmed_ident(out_confirmed_ident);
                delivery.setCreateDate(updateDate);
                delivery.setCreateUserId("admin");
                delivery.setUpdateDate(updateDate);
                delivery.setUpdateUserId("admin");
                delivery.setDeleteFlag("0");
                boolean deliveryResult = deliveryService.addDelivery(delivery);

            }
            result.put("success",true);
        }catch (DuplicateKeyException e) {
            result.put("hasError", true);
            result.put("error", "重复值异常，可能编号值重复");
            log.error(e);
        }catch (Exception e){
            log.error(e);
            result.put("error",false);
        }finally {
            return result;
        }
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
