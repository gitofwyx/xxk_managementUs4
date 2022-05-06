package com.xxk.management.office.devices.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.office.depository.service.DepositoryService;
import com.xxk.management.office.devices.dao.DevicesDao;
import com.xxk.management.office.devices.dao.StockDevicesDao;
import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.devices.service.DevicesService;
import com.xxk.management.office.devices.service.StockDevicesService;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.office.storage.service.OfficesStorageService;
import com.xxk.management.stock.service.StockService;
import com.xxk.management.storage.entity.Delivery;
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
public class StockDevicesServiceImpl implements StockDevicesService {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private StockDevicesDao dao;

    @Autowired
    private OfficesStorageService storageService;

    @Autowired
    private StockService stockService;


    @Override
    public List<Devices> listDevices(int pageStart, int pageSize, String class_id, String device_id, String location_office_id) {
        return dao.listStockDevices((pageStart - 1) * pageSize, pageSize, class_id, device_id, location_office_id);
    }

    @Override
    public int countDevices() {
        return 0;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public boolean addStockDevices(Devices devices, OfficesStorage storage, String stock_version) throws Exception, RuntimeException {

        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String devicesId = UUIdUtil.getUUID();
        boolean devicesResult = false;

        boolean Result = stockService.plusStockConfiguredTotal(devices.getPresent_stock_id(), devices.getCreateUserId(), createDate, stock_version);
        if (!Result) {
            log.error("addStockDevices:stockService.plusStockConfiguredTotal出错！");
            throw new Exception("addStockDevices:stockService.plusStockConfiguredTotal出错！");
        }
        devices.setId(devicesId);
        devices.setClass_id(storage.getClass_id());
        devices.setDevice_id(storage.getEntity_id());
        devices.setDevices_ident("NO");
        devices.setDevice_state("0");
        devices.setLocation_office_id(storage.getOffices_storage_officeId());
        devices.setInventory_office_id(storage.getOffices_storage_officeId());
        devices.setDevice_origin("1");
        devices.setDevice_deployment_status("8");
        devices.setRelated_flag("0");
        devices.setCreateDate(createDate);
        devices.setUpdateUserId(devices.getCreateUserId());
        devices.setUpdateDate(createDate);

        devices.setDeleteFlag("0");
        devicesResult = dao.addStockDevices(devices) == 1 ? true : false;
        if (!(devicesResult)) {
            log.error("addStockDevices:dao.addStockDevices出错！");
            throw new Exception("addStockDevices:dao.addStockDevices出错！");
        } else {
            storage.setEntity_id(devices.getDevice_id());
            storage.setOffices_entity_id(devicesId);
            storage.setOffices_storage_genre("0");
            storage.setEntity_entry_status("0");
            result = storageService.addOfficesStorage(devices, storage);
            if (result.get("hasError") instanceof Boolean && (Boolean) result.get("hasError")) {
                log.error("addStockDevices:storageService.addOfficesStorage出错！");
                throw new Exception("addStockDevices:storageService.addOfficesStorage出错！");
            }
        }

        return devicesResult;
    }

    //出库
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public boolean deliveryStockDevices(Devices devices, Delivery delivery, double stock_no) throws Exception, RuntimeException {

        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        boolean devicesResult = false;

        devices.setId(delivery.getStock_entity_id());
        devices.setDevice_deployment_status("1");
        devices.setRelated_flag("0");
        devices.setUpdateDate(createDate);

        devices.setDeleteFlag("0");
        devicesResult = dao.updateDeviceStatus(devices) == 1 ? true : false;
        if (!(devicesResult)) {
            log.error("deliveryStockDevices:dao.updateDeviceStatus出错！");
            throw new Exception("deliveryStockDevices:dao.updateDeviceStatus出错！");
        } else {
            delivery.setOut_confirmed_ident("NO");
            delivery.setOut_confirmed_type("1");
            delivery.setOut_confirmed_total(1);
            delivery.setOut_confirmed_by(devices.getUpdateUserId());
            delivery.setCreateUserId(devices.getUpdateUserId());
            delivery.setCreateDate(createDate);
            delivery.setUpdateUserId(devices.getUpdateUserId());
            delivery.setUpdateDate(createDate);
            result = stockService.updateSingleStockWithDelivery(delivery, stock_no);
            if (result.get("hasError") instanceof Boolean && (Boolean) result.get("hasError")) {
                log.error("deliveryStockDevices:stockService.updateSingleStockWithDelivery出错！");
                throw new Exception("deliveryStockDevices:stockService.updateSingleStockWithDelivery出错！");
            }
        }

        return devicesResult;
    }

    //根据部署状态获取设备
    @Override
    public List<Devices> getDevicesWithStatus(String deviceId, String officeId, String status) {

        return dao.getDevicesWithStatus(deviceId, officeId, status);
    }

    @Override
    public boolean updateDevicesStatus(String devicesId, String location_office_id, String present_stock_id, String status, String userId, String Date) {

        String createDate = DateUtil.getFullTime();
        boolean devicesResult = false;
        try {
            devicesResult = dao.updateDevicesStatus(devicesId, location_office_id, present_stock_id, status, userId, Date) == 1 ? true : false;
            if (!(devicesResult)) {
                log.error("depositoryResult:" + devicesResult);
            }
        } catch (DuplicateKeyException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
        }
        return devicesResult;
    }

    @Override
    public boolean updateDevicesSetStatus(String devicesId, String status, String userId, String Date) {

        boolean devicesResult = false;
        try {
            devicesResult = dao.updateDevicesSetStatus(devicesId, status, userId, Date) == 1 ? true : false;
            if (!(devicesResult)) {
                log.error("depositoryResult:" + devicesResult);
            }
        } catch (DuplicateKeyException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
        }
        return devicesResult;
    }

}
