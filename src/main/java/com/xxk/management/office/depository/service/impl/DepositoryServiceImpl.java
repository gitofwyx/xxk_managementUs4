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

    //新增库存
    // 2019年8月12日 13:44:05更新
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> addDepositoryWithStorage(Depository depository, OfficesStorage storage) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String depositoryId = UUIdUtil.getUUID();
        String officesStorageId = UUIdUtil.getUUID();

            /*if ("".equals(depository.getEntity_id()) || depository.getEntity_id() == null) {
                log.info("出错！无法获取设备ID");
                result.put("hasError", true);
                result.put("error", "添加出错");
                return result;
            }*/
        boolean Result = deliveryService.updateDeliveryStatus(depository.getDelivery_id(), "3",depository.getUpdateUserId(),createDate);
        if (!Result) {

            log.error("updateDepositoryWithStorage:deliveryService.updateDeliveryStatus出错！");
            throw new Exception("updateDepositoryWithStorage:deliveryService.updateDeliveryStatus出错！");
        }
        if ("0".equals(storage.getEntity_entry_status())) {
            boolean devicesResult = devicesService.updateDevicesStatus(storage.getOffices_entity_id(),
                    depository.getDepository_officeId(),
                    depositoryId,
                    officesStorageId,
                    "",
                    "2",
                    depository.getUpdateUserId(), createDate,"1");
            if (!(devicesResult)) {
                log.error("addDepositoryWithStorage:devicesService.updateDevicesStatus出错！");
                throw new Exception("addDepositoryWithStorage:devicesService.updateDevicesStatus出错！");
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
            log.error("addDepositoryWithStorage:dao.addDepository出错！");
            throw new Exception("addDepositoryWithStorage:dao.addDepository出错！");
        } else {
            storage.setId(officesStorageId);
            storage.setClass_id(depository.getClass_id());
            storage.setEntity_id(depository.getEntity_id());
            storage.setOffices_storage_total(depository.getDepository_total());
            result = storageService.addOfficesStorage(depository, storage, "3");//“1”代表入科标记
        }
        result.put("success", true);
        return result;
    }

    //入库操作
    // 2019年8月19日 13:44:05更新
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> updateDepositoryWithStorage(Depository depository, OfficesStorage storage) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String officesStorageId = UUIdUtil.getUUID();

           /* if ("".equals(depository.getEntity_id()) || depository.getEntity_id() == null) {
                log.info("出错！无法获取设备ID");
                result.put("hasError", true);
                result.put("error", "添加出错！无法获取设备ID");
                return result;
            }*/
        boolean Result=false;
        if ("1".equals(storage.getOffices_storage_genre())) {
            //不是原科登记更新出库记录的状态
             Result = deliveryService.updateDeliveryStatus(depository.getDelivery_id(), "3",depository.getUpdateUserId(),createDate);
        }
        else {Result = storageService.updateOfficesStorageStatus(depository.getDelivery_id(), "5");}

        if(!Result){
            log.error("updateDepositoryWithStorage:deliveryService.updateDeliveryStatus出错！");
            throw new Exception("updateDepositoryWithStorage:deliveryService.updateDeliveryStatus出错！");
        }
        if ("0".equals(storage.getEntity_entry_status())) {
            boolean devicesResult = devicesService.updateDevicesStatus(storage.getOffices_entity_id(),
                    depository.getDepository_officeId(),
                    depository.getId(),
                    officesStorageId,
                    "",
                    "2",
                    depository.getUpdateUserId(), createDate,"1");
            if (!(devicesResult)) {
                log.error("updateDepositoryWithStorage:devicesService.updateDevicesStatus出错！");
                throw new Exception("updateDepositoryWithStorage:devicesService.updateDevicesStatus出错！");
            }
        }
        depository.setDepository_total(storage.getOffices_storage_total());
        depository.setDepository_idle_total(storage.getOffices_storage_total());
        depository.setUpdateDate(createDate);

        boolean stockResult = dao.plusDepositoryNo(depository) == 1 ? true : false;
        if (!(stockResult)) {
            log.error("updateDepositoryWithStorage:dao.plusDepositoryNo出错！");
            throw new Exception("updateDepositoryWithStorage:dao.plusDepositoryNo出错！");
        } else {
            //入库记录
            storage.setId(officesStorageId);
            storage.setClass_id(depository.getClass_id());
            storage.setEntity_id(depository.getEntity_id());
            result = storageService.addOfficesStorage(depository, storage, "3");//“3”代表入科标记
        }
        result.put("success", true);
        return result;
    }

    //回收操作
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> recoveryDepository(Depository depository, Stock stock, Storage storage) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String stockId = UUIdUtil.getUUID();

        if ("".equals(depository.getEntity_id()) || depository.getEntity_id() == null) {
            log.error("recoveryDepository:depository.getEntity_id为空！");
            throw new Exception("recoveryDepository:depository.getEntity_id为空！");
        }
        depository.setDepository_total(storage.getIn_confirmed_total());
        depository.setDepository_idle_total(storage.getIn_confirmed_total());
        depository.setUpdateDate(createDate);
        boolean depositoryResult = dao.recoveryDepository(depository) == 1 ? true : false;
        if (!(depositoryResult)) {
            log.error("recoveryDepository:dao.recoveryDepository出错！");
            throw new Exception("recoveryDepository:dao.recoveryDepository出错！");
        }
        if ("".equals(stock.getId()) || stock.getId() == null) {
            result = stockService.addStockWithStorage(stock, storage);
        } else {
            result = stockService.updateStockWithStorage(stock, storage);
        }
        if (result.get("hasError") instanceof Boolean && (Boolean) result.get("hasError")) {
            log.error("recoveryDepository:stockService.add/updateStockWithStorage出错！");
            throw new Exception("recoveryDepository:stockService.add/updateStockWithStorage出错！");
        }
        result.put("success", true);
        return result;
    }

    //转移库存(入库)
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> transferDepositoryForStorage(Devices devices, OfficesStorage storage) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String stockId = UUIdUtil.getUUID();
        if ("".equals(devices.getDevice_id()) || devices.getDevice_id() == null) {
            log.error("transferDepositoryForStorage:devices.getDevice_id为空！");
            throw new Exception("transferDepositoryForStorage:devices.getDevice_id为空！");
        }

        boolean depositoryResult = dao.transferDepositoryForDelivery(storage) == 1 ? true : false;
        if (!(depositoryResult)) {
            log.error("transferDepositoryForStorage:dao.transferDepositoryForDelivery出错！");
            throw new Exception("transferDepositoryForStorage:dao.transferDepositoryForDelivery出错！");
        }
        //入库记录
        storage.setEntity_id(devices.getDevice_id());
        result = storageService.addOfficesStorage(devices, storage);//“1”代表入科标记
        result.put("success", true);
        return result;
    }

    //转移库存(出库)
    //Delivery为出库的意思，跟方法形参没有关系
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> transferDepositoryForDelivery(Devices devices, OfficesStorage storage) throws Exception, RuntimeException{
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String stockId = UUIdUtil.getUUID();

        if ("".equals(devices.getDevice_id()) || devices.getDevice_id() == null) {
            log.error("transferDepositoryForDelivery:devices.getDevice_id为空！");
            throw new Exception("transferDepositoryForDelivery:devices.getDevice_id为空！");
        }

        boolean depositoryResult = dao.transferDepositoryForDelivery(storage) == 1 ? true : false;
        if (!(depositoryResult)) {
            log.error("transferDepositoryForDelivery:dao.transferDepositoryForDelivery为空！");
            throw new Exception("transferDepositoryForDelivery:dao.transferDepositoryForDelivery为空！");
        }
        //入库记录
        storage.setEntity_id(devices.getDevice_id());
        result = storageService.addOfficesStorage(devices, storage);//“1”代表入科标记
        result.put("success", true);
        return result;
    }

    //部署操作（库存减一）；标注时间：2021年6月17日 23:53:48
    @Override
    public boolean deploymentDeviceWithSingle(String depository_id, String updateUserId, String updateDate) {
        return dao.deploymentDeviceWithSingle(depository_id, updateUserId, updateDate) == 1 ? true : false;
    }

    //撤出操作（库存加一）；标注时间：2021年6月17日 23:53:48
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
