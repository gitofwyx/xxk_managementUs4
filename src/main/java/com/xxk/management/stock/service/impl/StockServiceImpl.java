package com.xxk.management.stock.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.device.dao.DeviceDao;
import com.xxk.management.device.entity.Device;
import com.xxk.management.device.service.DeviceService;
import com.xxk.management.material.service.MaterialService;
import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.stock.dao.StockDao;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.entity.Storage;
import com.xxk.management.stock.service.StockService;
import com.xxk.management.storage.service.DeliveryService;
import com.xxk.management.storage.service.StorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class StockServiceImpl implements StockService {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private StockDao dao;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private DeliveryService deliveryService;

    @Override
    public List<Stock> listStock(int pageStart, int pageSize, String class_id, String entity_id, String stock_office_id, String search_type) {
        return dao.listStock((pageStart - 1) * pageSize, pageSize, class_id, entity_id, stock_office_id, search_type);
    }

    @Override
    public int countStock(String search_type) {
        return dao.countStock(search_type);
    }

    @Override
    public List<Stock> listStockByEntityId(String entity_id, String office_id) {
        return dao.listStockByEntityId(entity_id, office_id);
    }

    @Override
    public List<Stock> getStocksByEntityId(String entity_id) {
        return dao.getStocksByEntityId(entity_id);
    }

    //????????????
    // 2019???8???12??? 13:44:05??????
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> addStockWithStorage(Stock stock, Storage storage) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String stockId = UUIdUtil.getUUID();

        if ("".equals(stock.getEntity_id()) || stock.getEntity_id() == null) {
            log.error("addStockWithStorage:stock.getEntity_id?????????");
            throw new Exception("addStockWithStorage:stock.getEntity_id?????????");
        }
        if ("1".equals(stock.getStock_type())) {
            stock = deviceService.makeStockByDevice(stock);
        } else if ("2".equals(stock.getStock_type()) || ("3".equals(stock.getStock_type()))) {
            stock = materialService.makeStockByMaterial(stock);
        } else {
            stock = null;
        }
        if (stock != null) {
            stock.setId(stockId);
            stock.setStock_no(storage.getIn_confirmed_no());
            stock.setStock_total(storage.getIn_confirmed_total());
            stock.setStock_flag("1");
            stock.setCreateDate(createDate);
            stock.setCreateUserId(stock.getUpdateUserId());
            stock.setUpdateDate(createDate);
            stock.setUpdateUserId(stock.getUpdateUserId());
            stock.setDeleteFlag("0");
            if (stock.getStock_office_id() == null || "".equals(stock.getStock_office_id())) {
                stock.setStock_office_id(createDate);
            }
        } else {
            log.error("addStockWithStorage:(stock ?????????");
            throw new Exception("addStockWithStorage:stock ?????????");
        }
        boolean stockResult = dao.addStock(stock) == 1 ? true : false;
        if (!(stockResult)) {
            log.error("addStockWithStorage:dao.addStock?????????");
            throw new Exception("addStockWithStorage:dao.addStock?????????");
        } else {
            storage.setEntity_id(stock.getEntity_id());
            storage.setClass_id(stock.getClass_id());
            result = storageService.addStorage(stock, storage);
        }
        result.put("success", true);
        return result;
    }

    //????????????
    // 2019???8???19??? 13:44:05??????
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> updateStockWithStorage(Stock stock, Storage storage) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        if ("".equals(stock.getEntity_id()) || stock.getEntity_id() == null) {
            log.error("updateStockWithStorage:stock.getEntity_id?????????");
            throw new Exception("updateStockWithStorage:stock.getEntity_id?????????");
        }
        stock.setStock_total(storage.getIn_confirmed_total());
        stock.setUpdateDate(createDate);
        stock.setUpdateUserId(stock.getUpdateUserId());

        boolean stockResult = dao.plusStockNo(stock) == 1 ? true : false;
        if (!(stockResult)) {
            log.error("updateStockWithStorage:dao.plusStockNo?????????");
            throw new Exception("updateStockWithStorage:dao.plusStockNo?????????");
        } else {
            //????????????
            storage.setClass_id(stock.getClass_id());
            storage.setEntity_id(stock.getEntity_id());
            result = storageService.addStorage(stock, storage);
        }
        result.put("success", true);
        return result;
    }

    //????????????
    // 2019???8???19??? 13:43:42??????
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> updateStockWithDelivery(Stock stock, Delivery delivery) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();

        if ("".equals(stock.getEntity_id()) || stock.getEntity_id() == null) {
            log.error("updateStockWithDelivery:stock.getEntity_id?????????");
            throw new Exception("updateStockWithDelivery:stock.getEntity_id?????????");
        }
        stock.setStock_total(delivery.getOut_confirmed_total());
        stock.setUpdateDate(createDate);
        stock.setUpdateUserId(stock.getUpdateUserId());

        boolean stockResult = dao.reduceStockNo(stock) == 1 ? true : false;
        if (!(stockResult)) {
            log.error("updateStockWithDelivery:dao.reduceStockNo?????????");
            throw new Exception("updateStockWithDelivery:dao.reduceStockNo?????????");
        } else {
            //????????????
            delivery.setClass_id(stock.getClass_id());
            delivery.setEntity_id(stock.getEntity_id());
            if ("6".equals(delivery.getOut_confirmed_genre())) {
                result = deliveryService.addDelivery(stock, delivery, "6", "-");//??????
            } else if ("".equals(delivery.getOut_confirmed_genre()) || !"6".equals(delivery.getOut_confirmed_genre())) {
                result = deliveryService.addDelivery(stock, delivery, "1", "1");
            }

        }
        result.put("success", true);
        return result;
    }

    //??????????????????
    // 2019???8???19??? 13:43:42??????
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> updateSingleStockWithDelivery(Delivery delivery, double stock_no) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();

        if ("".equals(delivery.getStock_id()) || delivery.getStock_id() == null) {
            log.error("updateSingleStockWithDelivery:delivery.getStock_id?????????");
            throw new Exception("updateSingleStockWithDelivery:delivery.getStock_id?????????");
        }
        boolean stockResult = dao.reduceSingleStockNo(delivery.getStock_id(), stock_no, delivery.getCreateUserId(), createDate) == 1 ? true : false;
        if (!(stockResult)) {
            log.error("updateSingleStockWithDelivery:dao.reduceSingleStockNo?????????");
            throw new Exception("updateSingleStockWithDelivery:dao.reduceSingleStockNo?????????");
        } else {
            //????????????
            result = deliveryService.addDelivery(delivery, "0", "0");
        }
        result.put("success", true);
        return result;
    }

    //????????????
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> updateStock(Stock stock) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String stockId = UUIdUtil.getUUID();

        if ("".equals(stock.getEntity_id()) || stock.getEntity_id() == null) {
            log.error("updateStock:stock.getEntity_id?????????");
            throw new Exception("updateStock:stock.getEntity_id?????????");
        }
        boolean stockResult = dao.updateStock(stock, stock.getEntity_id()) == 1 ? true : false;
        if (!(stockResult)) {
            log.error("updateStock:dao.updateStock?????????");
            throw new Exception("updateStock:dao.updateStock?????????");
        }
        result.put("success", true);
        return result;
    }

    //????????????????????????
    // 2019???8???12??? 13:44:05??????
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> addStockForRecovery(OfficesStorage officesStorage) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String stockId = UUIdUtil.getUUID();

        if ("".equals(officesStorage.getEntity_id()) || officesStorage.getEntity_id() == null) {
            log.error("addStockForRecovery:officesStorage.getEntity_id?????????");
            throw new Exception("addStockForRecovery:officesStorage.getEntity_id?????????");
        }
        officesStorage.setId(stockId);
        officesStorage.setOffices_storage_ident("NO");
        officesStorage.setOffices_storage_total(1);
        officesStorage.setEntity_entry_status("1");
        officesStorage.setCreateDate(createDate);
        officesStorage.setUpdateDate(createDate);
        officesStorage.setDeleteFlag("0");

        boolean stockResult = dao.addStockForOfficesStorage(officesStorage) == 1 ? true : false;
        if (!(stockResult)) {
            log.error("addStockForRecovery:dao.addStockForOfficesStorage?????????");
            throw new Exception("addStockForRecovery:dao.addStockForOfficesStorage?????????");
        } else {

            result = storageService.addStorageForOfficesStorage(officesStorage);
        }
        result.put("success", true);
        return result;
    }

    //????????????
    // 2019???8???19??? 13:44:05??????
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> updateStockForRecovery(OfficesStorage officesStorage) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();

        if ("".equals(officesStorage.getEntity_id()) || officesStorage.getEntity_id() == null) {
            log.error("updateStockForRecovery:officesStorage.getEntity_id?????????");
            throw new Exception("updateStockForRecovery:officesStorage.getEntity_id?????????");
        }

        boolean stockResult = dao.updateStockForOfficesStorage(officesStorage) == 1 ? true : false;
        if (!(stockResult)) {
            log.error("updateStockForRecovery:dao.updateStockForOfficesStorage?????????");
            throw new Exception("updateStockForRecovery:dao.updateStockForOfficesStorage?????????");
        } else {
            //????????????
            result = storageService.addStorageForOfficesStorage(officesStorage);
        }
        result.put("success", true);
        return result;
    }

    //???????????????
    // 2019???8???19??? 13:44:05??????
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> updateStockForBackward(Storage storage, Delivery delivery, String stock_no) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        boolean stockResult = false;
        if ("".equals(delivery.getStock_id()) || delivery.getStock_id() == null) {
            log.error("updateStockForBackward:delivery.getStock_id?????????");
            throw new Exception("updateStockForBackward:delivery.getStock_id?????????");
        }

        if (!"".equals(delivery.getStock_entity_id()) && delivery.getStock_entity_id() != null) {
            stockResult = dao.plusStockNoByIdOfConfigured(delivery.getStock_id(),
                    Double.valueOf(stock_no),
                    storage.getIn_confirmed_no(),
                    delivery.getUpdateUserId(),
                    delivery.getUpdateDate()) == 1;
        } else {
            stockResult = dao.plusStockNoById(delivery.getStock_id(),
                    Double.valueOf(stock_no),
                    storage.getIn_confirmed_no(),
                    delivery.getUpdateUserId(),
                    delivery.getUpdateDate()) == 1;
        }
        if (!(stockResult)) {
            log.error("updateStockForBackward: dao.plusStockNoById?????????");
            throw new Exception("updateStockForBackward: dao.plusStockNoById?????????");
        } else {
            //????????????
            storage.setClass_id(delivery.getClass_id());
            storage.setEntity_id(delivery.getEntity_id());
            result = storageService.addStorage(delivery, storage);
        }
        result.put("success", true);
        return result;
    }

    @Override
    public boolean plusStockConfiguredTotal(String stockId, String userId, String date, String stock_version) {
        return dao.plusStockConfiguredTotal(stockId, userId, date, stock_version) == 1 ? true : false;
    }

    @Override
    public boolean deleteListRegUser(List<String> listStr) {
        return false;
    }

    @Override
    public List<String> getStorageIdByIdent(String ident) {
        return dao.getStockIdByIdent(ident);
    }

    @Override
    public Stock getStockById(String id) {
        return dao.getStockById(id);
    }
}
