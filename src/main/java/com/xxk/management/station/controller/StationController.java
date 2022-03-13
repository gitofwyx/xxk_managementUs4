package com.xxk.management.station.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.offices.entity.Offices;
import com.xxk.management.office.offices.service.OfficesService;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.station.entity.Station;
import com.xxk.management.station.service.StationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@RequestMapping("station")
public class StationController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private StationService stationService;


    @ResponseBody
    @RequestMapping("/listStation")
    public Map<String, Object> listStation(@RequestParam(value = "pageIndex") String pageIndex,
                                           @RequestParam(value = "limit") String limit) {
        Map<String, Object> result = new HashMap<>();
        try {
            int pageNumber = Integer.parseInt(pageIndex) + 1;//因为pageindex 从0开始
            int pageSize = Integer.parseInt(limit);

            List<Station> listStation = stationService.listWorkstation(pageNumber, pageSize);
            if (listStation == null) {
                log.error("获取分页出错");
                result.put("success", false);
                return result;
            } else {
                result.put("rows", listStation);
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
    public Map<String, Object> addDevices(Devices devices, OfficesStorage officesStorage,
                                          @RequestParam(value = "depository_id") String depository_id,
                                          @RequestParam(value = "devices_id") String devices_id) {
        Map<String, Object> result = new HashMap<>();
        String Date = DateUtil.getFullTime();
        boolean Result=false;
        try {
            if ("".equals(depository_id) || depository_id == null) {
                result.put("hasError", true);
                result.put("error", "设备更新出错！");
                return result;
            }
            String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            devices.setCreateUserId(CurrentUserId);
            officesStorage.setStock_or_depository_id(depository_id);
            officesStorage.setOffices_storage_type("1");
            if(!"".equals(devices_id) || devices_id != null){
                devices.setId(devices_id);
                Result = devicesService.updateDevicesForDeployment(devices, officesStorage);
            }/*else {
                Result = devicesService.addDevices(devices, officesStorage);
            }*/
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
