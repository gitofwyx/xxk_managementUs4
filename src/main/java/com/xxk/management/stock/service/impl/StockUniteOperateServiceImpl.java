package com.xxk.management.stock.service.impl;

import com.xxk.management.stock.dao.StockDao;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.stock.service.StockService;
import com.xxk.management.stock.service.StockUniteOperateService;
import com.xxk.management.storage.entity.Storage;
import com.xxk.management.storage.service.DeliveryService;
import com.xxk.management.storage.service.StorageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class StockUniteOperateServiceImpl implements StockUniteOperateService {

    private static Logger log = Logger.getLogger(StockService.class);

    @Autowired
    private StockDao dao;

    @Autowired
    private StorageService storageService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private StockService stockService;


    //转库操作
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> transferStockOfUpdateStock(Stock stock, Storage storage, String delivery_id) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();

        result = stockService.updateStockWithStorage(stock, storage);
        if (result.get("hasError") instanceof Boolean && (Boolean) result.get("hasError")) {
            log.error("transferStockOfUpdateStock:stockService.updateStockWithStorage出错！");
            throw new Exception("transferStockOfUpdateStock:stockService.updateStockWithStorage出错！");
        }
        boolean Result = deliveryService.updateDeliveryStatus(delivery_id, "3");
        if (!Result) {
            log.error("transferStockOfUpdateStock:deliveryService.updateDeliveryStatus出错！");
            throw new Exception("transferStockOfUpdateStock:deliveryService.updateDeliveryStatus出错！");
        }

        result.put("success", true);
        return result;
    }

    //转库操作
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> transferStockOfAddStock(Stock stock, Storage storage, String delivery_id) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();

        result = stockService.addStockWithStorage(stock, storage);
        if (result.get("hasError") instanceof Boolean && (Boolean) result.get("hasError")) {
            log.error("transferStockOfAddStock:stockService.addStockWithStorage出错！");
            throw new Exception("transferStockOfAddStock:stockService.addStockWithStorage出错！");
        }
        boolean Result = deliveryService.updateDeliveryStatus(delivery_id, "3");
        if (!Result) {
            log.error("transferStockOfAddStock:deliveryService.updateDeliveryStatus出错！");
            throw new Exception("transferStockOfAddStock:deliveryService.updateDeliveryStatus出错！");
        }

        result.put("success", true);
        return result;
    }
}
