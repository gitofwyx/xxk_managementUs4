package com.xxk.management.stock.controller;

import com.xxk.core.file.BaseController;
import com.xxk.management.device.service.DeviceService;
import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.devices.service.StockDevicesService;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.stock.service.StockService;
import com.xxk.management.storage.entity.Storage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("sys_stock")
public class AdminStockController extends BaseController {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private StockService stockService;

    @Autowired
    private StockDevicesService stockDevicesService;

    @Autowired
    private DeviceService deviceService;

    @ResponseBody
    @RequestMapping(value = "/addStock", method = RequestMethod.POST)
    public Map<String, Object> addStock(Stock stock, Storage storage,
                                        @RequestParam(value = "stock_record_id") String stock_record_id) {
        Map<String, Object> result = new HashMap<>();
        try {

            String CurrentUserId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
            stock.setUpdateUserId(CurrentUserId);
            if (stock_record_id != null && !"".equals(stock_record_id)) {
                stock.setId(stock_record_id);//获取库存的id值
                result = stockService.updateStockWithStorage(stock, storage);
            } else {
                result = stockService.addStockWithStorage(stock, storage);
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
