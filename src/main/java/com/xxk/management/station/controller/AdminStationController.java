package com.xxk.management.station.controller;

import com.xxk.core.file.BaseController;
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
@RequestMapping("sys_station")
public class AdminStationController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private StationService stationService;

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
                result.put("hasError", true);
                result.put("error", "更新出错");
            } else {
                result.put("success", true);
            }
        } catch (Exception e) {
            result.put("hasError", true);
            result.put("error", "更新出错");
            log.error(e);
        }
        return result;
        //return "system/index";
    }

}
