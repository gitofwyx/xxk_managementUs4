package com.xxk.management.station.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.JsonUtils;
import com.xxk.management.device.service.DeviceService;
import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.devices.service.DevicesService;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.station.service.StationService;
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

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("")
public class StationDevicesController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private StationService stationService;

    @Autowired
    private DevicesService devicesService;

    @ResponseBody
    @RequestMapping("/listStation")
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

}
