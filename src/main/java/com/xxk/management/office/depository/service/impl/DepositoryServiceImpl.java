package com.xxk.management.office.depository.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.office.depository.dao.DepositoryDao;
import com.xxk.management.office.depository.entity.Depository;
import com.xxk.management.office.depository.service.DepositoryService;
import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.devices.service.DevicesService;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.office.storage.service.OfficesStorageService;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.stock.service.StockService;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.entity.Storage;
import com.xxk.management.storage.service.DeliveryService;
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
public class DepositoryServiceImpl implements DepositoryService {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private DepositoryDao dao;

    @Autowired
    private OfficesStorageService storageService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private StockService stockService;

    @Autowired
    private DevicesService devicesService;

    @Override
    public List<Depository> listDepository(int pageStart, int pageSize, String class_id, String entity_id, String depository_officeId, int search_type) {
        return dao.listDepository((pageStart - 1) * pageSize, pageSize, class_id, entity_id, depository_officeId, search_type);
    }

    @Override
    public List<Depository> selectDepository(String entity_id, String depository_officeId) {
        List<Depository> res = dao.selectDepositoryWithOfficeEnt(entity_id, depository_officeId);
        if (res.size() == 0) {
            return null;
        }
        return res;
    }

    @Override
    public int countDepository(String search_type) {
        return dao.countDepository(search_type);
    }

    @Override
    public Depository getDepositoryByEntId(String entity_id) {
        List<Depository> res = dao.getDepositoryByEntId(entity_id);
        if (res.size() == 0) {
            return null;
        }
        return res.get(0);
    }

    //????????????
    // 2019???8???12??? 13:44:05??????
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> addDepositoryWithStorage(Depository depository, OfficesStorage storage) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String depositoryId = UUIdUtil.getUUID();

            /*if ("".equals(depository.getEntity_id()) || depository.getEntity_id() == null) {
                log.info("???????????????????????????ID");
                result.put("hasError", true);
                result.put("error", "????????????");
                return result;
            }*/
        boolean Result = deliveryService.updateDeliveryStatus(depository.getDelivery_id(), "3");
        if (!Result) {
            Result = storageService.updateOfficesStorageStatus(depository.getDelivery_id(), "5");
            if(!Result){
                log.error("updateDepositoryWithStorage:deliveryService.updateDeliveryStatus?????????");
                throw new Exception("updateDepositoryWithStorage:deliveryService.updateDeliveryStatus?????????");
            }
        }
        if ("0".equals(storage.getEntity_entry_status())) {
            boolean devicesResult = devicesService.updateDevicesStatus(storage.getOffices_entity_id(),
                    depository.getDepository_officeId(),
                    depositoryId,
                    "2",
                    depository.getUpdateUserId(), createDate);
            if (!(devicesResult)) {
                log.error("addDepositoryWithStorage:devicesService.updateDevicesStatus?????????");
                throw new Exception("addDepositoryWithStorage:devicesService.updateDevicesStatus?????????");
            }
        }
        depository.setId(depositoryId);
        depository.setDepository_ident("NO");
        depository.setDepository_no(storage.getOffices_storage_total());
        depository.setDepository_idle_no(storage.getOffices_storage_total());
        depository.setDepository_total(storage.getOffices_storage_total());
        depository.setDepository_idle_total(storage.getOffices_storage_total());
        depository.setDepository_flag("1");
        depository.setCreateDate(createDate);
        depository.setCreateUserId(depository.getUpdateUserId());
        depository.setUpdateDate(createDate);
        //depository.setUpdateUserId(depository.getUpdateUserId());
        depository.setDeleteFlag("0");
        boolean depositoryResult = dao.addDepository(depository) == 1 ? true : false;
        if (!(depositoryResult)) {
            log.error("addDepositoryWithStorage:dao.addDepository?????????");
            throw new Exception("addDepositoryWithStorage:dao.addDepository?????????");
        } else {
            storage.setClass_id(depository.getClass_id());
            storage.setEntity_id(depository.getEntity_id());
            storage.setOffices_storage_total(depository.getDepository_total());
            result = storageService.addOfficesStorage(depository, storage, "3");//???1?????????????????????
        }
        result.put("success", true);
        return result;
    }

    //????????????
    // 2019???8???19??? 13:44:05??????
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> updateDepositoryWithStorage(Depository depository, OfficesStorage storage) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();

           /* if ("".equals(depository.getEntity_id()) || depository.getEntity_id() == null) {
                log.info("???????????????????????????ID");
                result.put("hasError", true);
                result.put("error", "?????????????????????????????????ID");
                return result;
            }*/
        if (!"".equals(depository.getDelivery_id()) && depository.getDelivery_id() != null) {
            //?????????????????????????????????????????????
            boolean Result = deliveryService.updateDeliveryStatus(depository.getDelivery_id(), "3");
            if (!Result) {
                Result = storageService.updateOfficesStorageStatus(depository.getDelivery_id(), "5");
                if(!Result){
                    log.error("updateDepositoryWithStorage:deliveryService.updateDeliveryStatus?????????");
                    throw new Exception("updateDepositoryWithStorage:deliveryService.updateDeliveryStatus?????????");
                }
            }
        }
        if ("0".equals(storage.getEntity_entry_status())) {
            boolean devicesResult = devicesService.updateDevicesStatus(storage.getOffices_entity_id(),
                    depository.getDepository_officeId(),
                    depository.getId(),
                    "2",
                    depository.getUpdateUserId(), createDate);
            if (!(devicesResult)) {
                log.error("updateDepositoryWithStorage:devicesService.updateDevicesStatus?????????");
                throw new Exception("updateDepositoryWithStorage:devicesService.updateDevicesStatus?????????");
            }
        }
        depository.setDepository_total(storage.getOffices_storage_total());
        depository.setDepository_idle_total(storage.getOffices_storage_total());
        depository.setUpdateDate(createDate);

        boolean stockResult = dao.plusDepositoryNo(depository) == 1 ? true : false;
        if (!(stockResult)) {
            log.error("updateDepositoryWithStorage:dao.plusDepositoryNo?????????");
            throw new Exception("updateDepositoryWithStorage:dao.plusDepositoryNo?????????");
        } else {
            //????????????
            storage.setClass_id(depository.getClass_id());
            storage.setEntity_id(depository.getEntity_id());
            result = storageService.addOfficesStorage(depository, storage, "3");//???3?????????????????????
        }
        result.put("success", true);
        return result;
    }

    //????????????
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> recoveryDepository(Depository depository, Stock stock, Storage storage) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String stockId = UUIdUtil.getUUID();

        if ("".equals(depository.getEntity_id()) || depository.getEntity_id() == null) {
            log.error("recoveryDepository:depository.getEntity_id?????????");
            throw new Exception("recoveryDepository:depository.getEntity_id?????????");
        }
        depository.setDepository_total(storage.getIn_confirmed_total());
        depository.setDepository_idle_total(storage.getIn_confirmed_total());
        depository.setUpdateDate(createDate);
        boolean depositoryResult = dao.recoveryDepository(depository) == 1 ? true : false;
        if (!(depositoryResult)) {
            log.error("recoveryDepository:dao.recoveryDepository?????????");
            throw new Exception("recoveryDepository:dao.recoveryDepository?????????");
        }
        if ("".equals(stock.getId()) || stock.getId() == null) {
            result = stockService.addStockWithStorage(stock, storage);
        } else {
            result = stockService.updateStockWithStorage(stock, storage);
        }
        if (result.get("hasError") instanceof Boolean && (Boolean) result.get("hasError")) {
            log.error("recoveryDepository:stockService.add/updateStockWithStorage?????????");
            throw new Exception("recoveryDepository:stockService.add/updateStockWithStorage?????????");
        }
        result.put("success", true);
        return result;
    }

    //????????????(??????)
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> transferDepositoryForStorage(Devices devices, OfficesStorage storage) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String stockId = UUIdUtil.getUUID();
        if ("".equals(devices.getDevice_id()) || devices.getDevice_id() == null) {
            log.error("transferDepositoryForStorage:devices.getDevice_id?????????");
            throw new Exception("transferDepositoryForStorage:devices.getDevice_id?????????");
        }

        boolean depositoryResult = dao.transferDepositoryForDelivery(storage) == 1 ? true : false;
        if (!(depositoryResult)) {
            log.error("transferDepositoryForStorage:dao.transferDepositoryForDelivery?????????");
            throw new Exception("transferDepositoryForStorage:dao.transferDepositoryForDelivery?????????");
        }
        //????????????
        storage.setEntity_id(devices.getDevice_id());
        result = storageService.addOfficesStorage(devices, storage);//???1?????????????????????
        result.put("success", true);
        return result;
    }

    //????????????(??????)
    //Delivery????????????????????????????????????????????????
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> transferDepositoryForDelivery(Devices devices, OfficesStorage storage) throws Exception, RuntimeException{
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String stockId = UUIdUtil.getUUID();

        if ("".equals(devices.getDevice_id()) || devices.getDevice_id() == null) {
            log.error("transferDepositoryForDelivery:devices.getDevice_id?????????");
            throw new Exception("transferDepositoryForDelivery:devices.getDevice_id?????????");
        }

        boolean depositoryResult = dao.transferDepositoryForDelivery(storage) == 1 ? true : false;
        if (!(depositoryResult)) {
            log.error("transferDepositoryForDelivery:dao.transferDepositoryForDelivery?????????");
            throw new Exception("transferDepositoryForDelivery:dao.transferDepositoryForDelivery?????????");
        }
        //????????????
        storage.setEntity_id(devices.getDevice_id());
        result = storageService.addOfficesStorage(devices, storage);//???1?????????????????????
        result.put("success", true);
        return result;
    }

    //????????????????????????????????????????????????2021???6???17??? 23:53:48
    @Override
    public boolean deploymentDeviceWithSingle(String depository_id, String updateUserId, String updateDate) {
        return dao.deploymentDeviceWithSingle(depository_id, updateUserId, updateDate) == 1 ? true : false;
    }

    //????????????????????????????????????????????????2021???6???17??? 23:53:48
    @Override
    public boolean withdrawDeviceWithSingle(String depository_id, String updateUserId, String updateDate) {
        return dao.withdrawDeviceWithSingle(depository_id, updateUserId, updateDate) == 1 ? true : false;
    }


    @Override
    public List<String> getDepositoryIdByIdent(String ident) {
        return dao.getDepositoryIdByIdent(ident);
    }

    @Override
    public Depository getDepositoryById(String id) {
        return dao.getDepositoryById(id);
    }
}
