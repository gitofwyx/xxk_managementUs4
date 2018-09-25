package com.xxk.management.storage.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.core.util.build_ident.IdentUtil;
import com.xxk.management.device.service.DeviceService;
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

import java.util.*;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("")
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
                result.put("error", false);
                return result;
            } else if (listStorage.isEmpty()) {
                result.put("rows", resultList);
                result.put("results", 7);
            } else {
                List<String> listDevId = new ArrayList<>();
                for (Storage storage : listStorage) {
                    listDevId.add(storage.getEntity_id());
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
                    for (Storage storage : listStorage) {
                        Map<String, Object> resultMap = new HashMap<>();
                        for (Map<String, Object> deviceMap : deviceList) {
                            if (storage.getEntity_id().equals(deviceMap.get("id"))) {
                                resultMap.put("dev_name", deviceMap.get("dev_name"));
                                resultMap.put("dev_type", deviceMap.get("dev_type"));
                                resultMap.put("dev_no", deviceMap.get("dev_no"));
                            }
                        }
                        resultMap.put("device_id", storage.getEntity_id());
                        resultMap.put("in_confirmed_ident", storage.getIn_confirmed_ident());
                        resultMap.put("in_confirmed_by", storage.getIn_confirmed_by());
                        resultMap.put("in_confirmed_date", storage.getIn_confirmed_date());
                        resultMap.put("in_confirmed_no", storage.getIn_confirmed_no());
                        resultMap.put("last_rest_no", storage.getFact_dev_no());
                        resultMap.put("out_flag", storage.getOut_flag());
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
                    listDevId.add(delivery.getDevice_id());
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
                        for (Map<String, Object> deviceMap : deviceList) {
                            if (delivery.getDevice_id().equals(deviceMap.get("id"))) {
                                resultMap.put("dev_name", deviceMap.get("dev_name"));
                                resultMap.put("dev_type", deviceMap.get("dev_type"));
                                resultMap.put("dev_no", deviceMap.get("dev_no"));
                            }
                        }
                        resultMap.put("device_id", delivery.getDevice_id());
                        resultMap.put("out_confirmed_ident", delivery.getOut_confirmed_ident());
                        resultMap.put("use_offices", delivery.getUse_offices());
                        resultMap.put("used_no", delivery.getUsed_no());
                        resultMap.put("used_name", delivery.getUsed_name());
                        resultMap.put("used_date", delivery.getUsed_date());
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
    @RequestMapping("/addStorage")
    public Map<String, Object> addStorage(Storage storage, @RequestParam(value = "dev_type") String entityId) {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String storageId = UUIdUtil.getUUID();
        try {
            if ("".equals(entityId) || entityId == null) {
                log.info("出错！无法获取设备ID");
                result.put("error", false);
                return result;
            }
            List<Map<String, Object>> resultList = deviceService.getDeviceNumber(entityId);////获取设备数量
            if (resultList.size() != 1 || resultList == null) {
                log.info("出错！无法获取设备ID对应的库存量！");
                result.put("error", false);
                return result;
            }
            String dev_ident=(String) resultList.get(0).get("dev_ident");//提取设备编号
            int dev_no=(int)resultList.get(0).get("dev_no");//提取设备数量
            String in_confirmed_ident= IdentUtil.getIdentNo(dev_ident,dev_no,storage.getIn_confirmed_no(),createDate);
            if("".equals(in_confirmed_ident)||in_confirmed_ident==null){
                result.put("hasError", true);
                result.put("error", "添加出错,无法生成入库编号！");
                return result;
            }
            //入库记录
            storage.setId(storageId);
            storage.setEntity_id(entityId);
            storage.setIn_confirmed_ident(in_confirmed_ident);
            storage.setFact_dev_no(storage.getIn_confirmed_no());
            storage.setOut_flag("0");
            storage.setCreateDate(createDate);
            storage.setCreateUserId("admin");
            storage.setUpdateDate(createDate);
            storage.setUpdateUserId("admin");
            storage.setDeleteFlag("0");

            boolean storageResult = storageService.addStorage(storage);
            if (!(storageResult)) {
                log.error("addstorage:" + storageResult);
                result.put("hasError", true);
                result.put("error", "添加出错");
            } else {
                //log.info(">>>>保存成功");
                result.put("success", true);
            }
        } catch (DuplicateKeyException e) {
            result.put("hasError", true);
            result.put("error", "重复值异常，可能编号值重复");
            log.error(e);
        } catch (Exception e) {
            result.put("hasError", true);
            result.put("error", "添加出错");
            log.error(e);
        }
        return result;
        //return "system/index";
    }

    @ResponseBody
    @RequestMapping("/updateStorage")
    public Map<String, Object> updateStorage(Storage storage,Delivery delivery) {
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
                String out_confirmed_ident= IdentUtil.getIdentNo(dev_ident,dev_no,storage.getIn_confirmed_no(),updateDate);
                //出库记录
                delivery.setId(deliveryId);
                delivery.setOut_confirmed_ident(out_confirmed_ident);
                delivery.setCreateDate(updateDate);
                delivery.setCreateUserId("admin");
                delivery.setUpdateDate(updateDate);
                delivery.setUpdateUserId("admin");
                delivery.setDeleteFlag("0");
                boolean deliveryResult = deliveryService.addDelivery(delivery);
                if (!(deliveryResult)) {
                    result.put("hasError", true);
                    result.put("error", "添加出错");

                } else {
                    log.info(">>>>保存成功");
                    result.put("success", true);
                }
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

}
