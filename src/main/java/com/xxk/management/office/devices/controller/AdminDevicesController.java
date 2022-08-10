package com.xxk.management.office.devices.controller;

import com.xxk.core.file.BaseController;
import com.xxk.core.util.JsonUtils;
import com.xxk.management.device.service.DeviceService;
import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.devices.service.StockDevicesService;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.stock.service.StockService;
import com.xxk.management.storage.entity.Delivery;
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
@RequestMapping("sys_device")
public class AdminDevicesController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private StockService stockService;

    @Autowired
    private StockDevicesService stockDevicesService;

    @Autowired
    private DeviceService deviceService;

    @ResponseBody
    @RequestMapping(value = "/addStockDevices", method = RequestMethod.POST)
    public Map<String, Object> addStockDevices(Devices devices, OfficesStorage storage,
                                               @RequestParam(value = "stock_version") String stock_version) {
        Map<String, Object> result = new HashMap<>();
        try {

            String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            devices.setCreateUserId(CurrentUserId);
            if (devices.getPresent_stock_id() != null && !"".equals(devices.getPresent_stock_id())) {
                storage.setStock_or_depository_id(devices.getPresent_stock_id());//获取库存的id值
                storage.setOffices_storage_type("1");
                boolean Result = stockDevicesService.addStockDevices(devices, storage,stock_version);
                if (!(Result)) {
                    result.put("success", false);
                } else {
                    result.put("success", true);
                }
            }

        } catch (Exception e) {
            log.error(e);
            result.put("hasError", true);
            result.put("error", "更新出错");
        }
        return result;
        //return "system/index";
    }

}
