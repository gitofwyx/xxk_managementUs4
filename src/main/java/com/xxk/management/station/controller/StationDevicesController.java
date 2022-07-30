package com.xxk.management.station.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.JsonUtils;
import com.xxk.management.device.service.DeviceService;
import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.devices.service.DevicesService;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.station.service.StationDevicesService;
import com.xxk.management.station.service.StationService;
import com.xxk.management.user.entity.RegUser;
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
import java.util.function.Supplier;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("/stations")
public class StationDevicesController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private StationService stationService;

    @Autowired
    private StationDevicesService stationDevicesService;

    private Supplier<OfficesStorage> storageSupplier = OfficesStorage::new;

    @Autowired
    private DevicesService devicesService;

    @ResponseBody
    @RequestMapping("/listStationDevices")
    public Map<String, Object> listDevices(@RequestParam(value = "pageIndex") String pageIndex,
                                           @RequestParam(value = "limit") String limit,
                                           @RequestParam(value = "search_class_id") String class_id,//型号id
                                           @RequestParam(value = "search_entity_id") String entity_id,//设备、耗材id
                                           @RequestParam(value = "location_office_id") String location_office_id) {//科室id
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            int pageNumber = Integer.parseInt(pageIndex) + 1;//因为pageindex 从0开始
            int pageSize = Integer.parseInt(limit);

            List<Map<String, Object>> listStation = stationService.listWorkstationWithDevices(pageNumber, pageSize, class_id, entity_id, location_office_id);
            if (listStation == null) {
                log.error("获取分页出错");
                result.put("success", false);
                return result;
            }
            result.put("rows", listStation);
            result.put("results", devicesService.countDevices());

        } catch (Exception e) {
            log.error(e);
            result.put("success", false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/exchangeStationDevices")
    public Map<String, Object> exchangeStationDevices(Devices devices, OfficesStorage officesStorage,
                                                      @RequestParam(value = "bf_class_id") String bf_class_id,
                                                      @RequestParam(value = "bf_entity_id") String bf_entity_id,
                                                      @RequestParam(value = "depository_id") String depository_id,
                                                      @RequestParam(value = "bf_depository_id") String bf_depository_id,
                                                      @RequestParam(value = "devices_id") String devices_id,
                                                      @RequestParam(value = "bf_devices_id") String bf_devices_id,
                                                      @RequestParam(value = "bf_dev_name") String bf_dev_name,
                                                      @RequestParam(value = "bf_dev_type") String bf_dev_type) {
        Map<String, Object> result = new HashMap<>();
        String Date = DateUtil.getFullTime();
        boolean Result = false;
        try {
            if ("".equals(depository_id) || depository_id == null || "".equals(devices_id) || devices_id == null) {
                result.put("hasError", true);
                result.put("error", "设备更新出错！");
                return result;
            }
            String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            devices.setCreateUserId(CurrentUserId);
            officesStorage.setStock_or_depository_id(depository_id);
            officesStorage.setOffices_storage_type("1");
            if ("NO".equals(bf_devices_id) || "".equals(bf_devices_id)|| bf_devices_id == null) {
                devices.setId(devices_id);
                Result = devicesService.updateDevicesForDeployment(devices, officesStorage);

            }else {
                OfficesStorage bf_storage = storageSupplier.get();
                bf_storage.setClass_id(bf_class_id);
                bf_storage.setEntity_id(bf_entity_id);
                bf_storage.setStock_or_depository_id(bf_depository_id);
                bf_storage.setOffices_entity_id(bf_devices_id);
                bf_storage.setEntity_name(bf_dev_name);
                bf_storage.setEntity_type(bf_dev_type);
                bf_storage.setOffices_storage_type("1");

                devices.setId(devices_id);
                Result = stationDevicesService.updateStationDevicesForExchange(devices, officesStorage, bf_storage);
            }

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

}
