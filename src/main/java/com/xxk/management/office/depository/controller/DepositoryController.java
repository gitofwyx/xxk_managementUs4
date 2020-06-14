package com.xxk.management.office.depository.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.JsonUtils;
import com.xxk.management.device.service.DeviceService;
import com.xxk.management.office.depository.entity.Depository;
import com.xxk.management.office.depository.service.DepositoryService;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.entity.Storage;
import org.apache.log4j.Logger;
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
public class DepositoryController extends BaseController {

    private static Logger log = Logger.getLogger(DepositoryController.class);

    @Autowired
    private DepositoryService depositoryService;

    @Autowired
    private DeviceService deviceService;

    @ResponseBody
    @RequestMapping("/listDepository")
    public Map<String, Object> listDepository(@RequestParam(value = "pageIndex") String pageIndex,
                                              @RequestParam(value = "limit") String limit,
                                              @RequestParam(value = "search_class_id") String class_id,//型号id
                                              @RequestParam(value = "search_entity_id") String entity_id,//设备、耗材id
                                              @RequestParam(value = "search_office_id") String depository_officeId,//库存科室id
                                              @RequestParam(value = "search_type") int search_type) {//类别
        Map<String, Object> result = new HashMap<>();
        try {
            int pageNumber = Integer.parseInt(pageIndex) + 1;//页数，因为pageindex 从0开始要加1
            int pageSize = Integer.parseInt(limit);         //单页记录数
            List<Depository> listDepository = depositoryService.listDepository(pageNumber, pageSize,class_id,entity_id,depository_officeId,search_type);
            if (listDepository == null) {
                log.error("listStock:获取分页出错");
                result.put("hasError", true);
                result.put("error", "获取出错");
                return result;
            } else if (listDepository.isEmpty()) {
                result.put("rows", listDepository);
                result.put("results", 7);
            } else {
                List<String> listDevId = new ArrayList<>();

               /* Set setDevId = new  HashSet();
                setDevId.addAll(listDevId);
                listDevId.clear();
                listDevId.addAll(setDevId);*/
                result.put("rows", listDepository);
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
    @RequestMapping(value = "/getDepositoryByEntId",method = RequestMethod.POST)
    public Map<String, Object> getDepositoryByEntId(@RequestParam(value = "entity_record_id") String entity_record_id) {
        Map<String, Object> result=new HashMap<>();
        try {
            Depository depository=depositoryService.getDepositoryByEntId(entity_record_id);
            if (depository == null) {
                return null;
            }
            result.put("Object", depository);
            result.put("success", true);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
        return result;
        //return "system/index";
    }

    @ResponseBody
    @RequestMapping(value = "/addDepository",method = RequestMethod.POST)
    public Map<String, Object> addDepository(Depository depository, OfficesStorage storage,
                                             @RequestParam(value = "entity_record_id") String entity_record_id,
                                             @RequestParam(value = "depository_id") String depository_id) {
        Map<String, Object> result=new HashMap<>();
        depository.setEntity_id(entity_record_id);//获取库存的id值
        if (!"".equals(depository_id)&&depository_id!=null) {
            depository.setId(depository_id);
            result = depositoryService.updateDepositoryWithStorage(depository,storage);
        } else {
            result = depositoryService.addDepositoryWithStorage(depository,storage);
        }

        return result;
        //return "system/index";
    }

    @ResponseBody
    @RequestMapping("/updateDepository")
    public Map<String, Object> updateDepository(Depository depository, OfficesStorage storage,
                                           @RequestParam(value = "stock_record_id") String stock_record_id) {
        Map<String, Object> result=new HashMap<>();
        if(stock_record_id!=null&&!"".equals(stock_record_id)){
            depository.setId(stock_record_id);//获取库存的id值
            result = depositoryService.updateDepositoryWithStorage(depository,storage);
        }else {
            result.put("hasError", true);
            result.put("error", "updateDepository:获取stock_record_id出错");
            log.error("updateStock:" + result.get("error"));
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/listDepositoryByEntityId",method = RequestMethod.POST)
    public Map<String, Object> listDepositoryByEntityId(@RequestParam(value = "entity_id") String entity_id,
                                                   @RequestParam(value = "stock_office") String office_id) {
        int id = 0;
        Map<String, Object> result = new HashMap<>();
        try {
            List<Depository> listDepository = depositoryService.listDepositoryByEntityId(entity_id,office_id);
            if (listDepository == null) {
                log.error("获取出错");
                return null;
            }
            result.put("entityData", listDepository);
            /*result.put("dev_count", dev_count);*/
        } catch (Exception e) {
            log.error(e);
            return null;
        }
        return result;
    }

}
