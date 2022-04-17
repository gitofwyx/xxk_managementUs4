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
    @RequestMapping("/addWorkstation")
    public Map<String, Object> addWorkstation(Station station) {
        Map<String, Object> result = new HashMap<>();
        boolean Result=false;
        try {

            String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            station.setCreateUserId(CurrentUserId);
            Result = stationService.addWorkstation(station);

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
    @RequestMapping(value = "/getStationSelectByOfficeId",method = RequestMethod.POST)
    public Map<String, Object> getStationSelectByOfficeId(@RequestParam(value = "office_id") String office_id) {
        int id = 0;
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> listStation = new ArrayList<>();
        try {
            listStation = stationService.getStationSelectByOfficeId(office_id);
            if ( listStation == null) {
                log.error("获取出错");
                return null;
            }
            result.put("station_data", listStation);

        } catch (Exception e) {
            log.error(e);
            return null;
        }
        return result;
    }

}
