package com.xxk.management.offices.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.core.util.device_ident.IdentUtil;
import com.xxk.management.offices.entity.Offices;
import com.xxk.management.offices.service.OfficesService;
import org.apache.log4j.Logger;
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
public class DeviceController extends BaseController {

    private static Logger log = Logger.getLogger(DeviceController.class);

    @Autowired
    private OfficesService deviceService;



    @ResponseBody
    @RequestMapping("/listDevice")
    public Map<String, Object> listDevice(@RequestParam(value = "pageIndex") String pageIndex,
                                          @RequestParam(value = "limit") String limit,
                                          @RequestParam(value = "dev_name") String dev_name,
                                          @RequestParam(value = "dev_type") String dev_type,
                                          @RequestParam(value = "startDate") String startDate) {
        Map<String, Object> result = new HashMap<>();
        try {
            int pageNumber = Integer.parseInt(pageIndex) + 1;//因为pageindex 从0开始
            int pageSize = Integer.parseInt(limit);

            List<Offices> listDevice = deviceService.listDevice(pageNumber, pageSize);
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
    @RequestMapping("/addDevice")
    public Map<String, Boolean> addDevice(Offices device, DeviceClass deviceClass,
                                          @RequestParam(value = "dev_class_count") String dev_class_count,
                                          @RequestParam(value = "lastUpDate") String lastUpDate) {
        Map<String, Boolean> result = new HashMap<>();
        String Date = DateUtil.getFullTime();
        String deviceId = UUIdUtil.getUUID();
        String deviceClassId = UUIdUtil.getUUID();
        String dev_ident = "";
        try {
            if (deviceClass.getClass_ident() > 0 && deviceClass.getType_max() > 0) {
                dev_ident = IdentUtil.getIdent(deviceClass.getClass_ident(), deviceClass.getType_max(), Date);

            } else if (!"".equals(dev_class_count) && dev_class_count != null) {
                dev_ident = IdentUtil.getIdent(Integer.parseInt(dev_class_count), deviceClass.getType_max(), Date);
            } else if (("".equals(dev_class_count) || dev_class_count == null)) {
                dev_ident = IdentUtil.getIdent(0, deviceClass.getType_max(), Date);
            }
            if (!"".equals(device.getDev_class_id()) && device.getDev_class_id() != null) {

                deviceClass.setId(device.getDev_class_id());
                deviceClass.setUpdateUserId("admin");
                deviceClass.setUpdateDate(Date);
                Boolean resultBl = deviceClassService.updateDev_typeMax(deviceClass);
                if (!(resultBl)) {
                    result.put("error", false);
                    return result;
                }
            } else {
                device.setDev_class_id(deviceClassId);
                deviceClass.setId(deviceClassId);
                deviceClass.setDev_class(device.getDev_name());
                deviceClass.setDev_max(1);
                deviceClass.setType_max(1);
                deviceClass.setCreateUserId("admin");
                deviceClass.setCreateDate(Date);
                deviceClass.setUpdateUserId("admin");
                deviceClass.setUpdateDate(Date);
                deviceClass.setDeleteFlag("0");
                Boolean resultBl = deviceClassService.addDeviceClass(deviceClass);
                if (!(resultBl)) {
                    result.put("error", false);
                    return result;
                }
            }
            device.setId(deviceId);
            device.setDev_ident(dev_ident);
            device.setRelated_flag("0");
            device.setCreateDate(Date);
            device.setCreateUserId("admin");
            device.setUpdateDate(Date);
            device.setUpdateUserId("admin");
            device.setDeleteFlag("0");

            boolean Result = deviceService.addDevice(device);
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
    @RequestMapping("/getDeviceName")
    public Map<String, Object> getDeviceName() {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> listResult = new ArrayList<>();
        //List<Map<String, Object>> dev_count = new ArrayList<>();
        try {
            listResult = deviceClassService.listAllDeviceName();
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

    @ResponseBody
    @RequestMapping("/getStoreSelect")
    public Map<String, Object> getStoreDevice() {
        int id = 0;
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> listClass = new ArrayList<>();
        List<Map<String, Object>> listDevice = new ArrayList<>();
        try {
            listClass = deviceClassService.listAllDeviceName();
            listDevice = deviceService.getDeviceSelect();
            if (listClass == null || listDevice == null) {
                log.error("获取出错");
                return null;
            }
            result.put("dev_name", listClass);
            result.put("dev_type", listDevice);
            /*result.put("dev_count", dev_count);*/
        } catch (Exception e) {
            log.error(e);
            return null;
        }
        return result;
    }

}
