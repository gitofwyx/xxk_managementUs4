package com.xxk.management.office.devices.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.core.util.build_ident.IdentUtil;
import com.xxk.management.device.entity.DeviceClass;
import com.xxk.management.device.service.DeviceClassService;
import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.devices.service.DevicesService;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.storage.entity.Delivery;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
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
public class DevicesController extends BaseController {

    private static Logger log = Logger.getLogger(DevicesController.class);

    @Autowired
    private DevicesService devicesService;


    @ResponseBody
    @RequestMapping("/listDevices")
    public Map<String, Object> listDevices(@RequestParam(value = "pageIndex") String pageIndex,
                                           @RequestParam(value = "limit") String limit,
                                           @RequestParam(value = "search_class_id") String class_id,//型号id
                                           @RequestParam(value = "search_entity_id") String entity_id,//设备、耗材id
                                           @RequestParam(value = "location_office_id") String location_office_id) {//科室id
        Map<String, Object> result = new HashMap<>();
        try {
            int pageNumber = Integer.parseInt(pageIndex) + 1;//因为pageindex 从0开始
            int pageSize = Integer.parseInt(limit);

            List<Devices> listDevice = devicesService.listDevices(pageNumber, pageSize, class_id, entity_id,location_office_id);
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
    @RequestMapping("/addDevices")
    public Map<String, Boolean> addDevices(Devices devices, OfficesStorage officesStorage,
                                           @RequestParam(value = "depository_id") String depository_id) {
        Map<String, Boolean> result = new HashMap<>();
        String Date = DateUtil.getFullTime();
        try {
            String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            devices.setCreateUserId(CurrentUserId);
            officesStorage.setDepository_id(depository_id);
            officesStorage.setOffices_storage_type("1");
            boolean Result = devicesService.addDevices(devices,officesStorage);
            if (!(Result)) {
                result.put("success", false);
            } else {
                result.put("success", true);
            }
        } catch (Exception e) {
            result.put("success", false);
            log.error(e);
        }
        return result;
        //return "system/index";
    }

    @ResponseBody
    @RequestMapping("/getDevicesName")
    public Map<String, Object> getDevicesName() {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> listResult = new ArrayList<>();
        //List<Map<String, Object>> dev_count = new ArrayList<>();
        try {
            //listResult = devicesService.();
            //dev_count = deviceClassService.getCountClassById("1fa2614d-4a55-1234-a79a-5546319b9123");
            if (listResult == null) {
                log.error("获取出错");
                return null;
            }
            result.put("dev_class", listResult);
            /*result.put("dev_count", dev_count);*/
        } catch (Exception e) {
            log.error(e);
            return null;
        }
        return result;
    }

}
