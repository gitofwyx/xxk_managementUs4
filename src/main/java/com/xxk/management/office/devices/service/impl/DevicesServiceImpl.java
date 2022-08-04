package com.xxk.management.office.devices.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.office.depository.service.DepositoryService;
import com.xxk.management.office.devices.dao.DevicesDao;
import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.devices.service.DevicesService;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.office.storage.service.OfficesStorageService;
import com.xxk.management.stock.service.StockService;
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
public class DevicesServiceImpl implements DevicesService {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private DevicesDao dao;

    @Autowired
    private OfficesStorageService storageService;

    @Autowired
    private StockService stockService;

    @Autowired
    private DepositoryService depositoryService;


    @Override
    public List<Devices> listDevices(int pageStart, int pageSize, String class_id, String device_id, String location_office_id) {
        return dao.listDevices((pageStart - 1) * pageSize, pageSize, class_id, device_id, location_office_id);
    }

    @Override
    public int countDevices() {
        return dao.countDevices();
    }

    @Override
    public List<Devices> listDevicesById(List<String> listDevId) {
        return dao.listDevicesById(listDevId);
    }

    /*@Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public boolean addDevices(Devices devices, OfficesStorage storage) throws Exception, RuntimeException {

        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String devicesId = UUIdUtil.getUUID();
        boolean devicesResult = false;

        devices.setId(devicesId);
        devices.setClass_id(storage.getClass_id());
        devices.setDevice_id(storage.getEntity_id());
        devices.setDevices_ident("NO");
        devices.setDevice_state("0");
        devices.setLocation_office_id(storage.getOffices_storage_officeId());
        devices.setInventory_office_id(storage.getOffices_storage_officeId());
        devices.setDevice_origin("1");
        devices.setDevice_deployment_status("2");
        devices.setRelated_flag("0");
        devices.setCreateDate(createDate);
        devices.setUpdateUserId(devices.getCreateUserId());
        devices.setUpdateDate(createDate);

        devices.setDeleteFlag("0");
        devicesResult = dao.addDevices(devices) == 1 ? true : false;
        if (!(devicesResult)) {
            log.error("addDevices:dao.addDevices出错！");
            throw new Exception("addDevices:dao.addDevices出错！");
        } else {
            storage.setEntity_id(devices.getDevice_id());
            storage.setOffices_entity_id(devicesId);
            storage.setOriginal_storage_officeId(devices.getInventory_office_id());
            storage.setOffices_storage_genre("2");
            storage.setEntity_entry_status("2");
            result = storageService.addOfficesStorage(devices, storage);
            if (result.get("hasError") instanceof Boolean && (Boolean) result.get("hasError")) {
                log.error("addDevices:dao.addDevices出错！");
                throw new Exception("addDevices:dao.addDevices出错！");
            }
            devicesResult = depositoryService.deploymentDeviceWithSingle(storage.getStock_or_depository_id(), storage.getUpdateUserId(), createDate);
        }

        return devicesResult;
    }*/

    //部署操作；标注时间：2021年6月17日 23:53:48
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public boolean updateDevicesForDeployment(Devices devices, OfficesStorage storage) throws Exception, RuntimeException {

        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        boolean devicesResult = false;

        devices.setDevice_deployment_status("3");
        devices.setUpdateUserId(devices.getCreateUserId());
        devices.setUpdateDate(createDate);

        devices.setDeleteFlag("0");
        devicesResult = dao.updateDevicesForDeployment(devices) == 1 ? true : false;
        if (!(devicesResult)) {
            log.error("updateDevicesForDeployment: dao.updateDevicesForDeployment出错！");
            throw new Exception("updateDevicesForDeployment: dao.updateDevicesForDeployment出错！");
        } else {

            storage.setOriginal_storage_officeId(devices.getInventory_office_id());
            storage.setOffices_storage_genre("2");
            storage.setEntity_entry_status("3");
            result = storageService.addOfficesStorage(devices, storage);
            if (result.get("hasError") instanceof Boolean && (Boolean) result.get("hasError")) {
                log.error("updateDevicesForDeployment: dao.updateDevicesForDeployment出错！");
                throw new Exception("updateDevicesForDeployment: dao.updateDevicesForDeployment出错！");
            }
            devicesResult = depositoryService.deploymentDeviceWithSingle(storage.getStock_or_depository_id(), storage.getUpdateUserId(), createDate);
        }

        return devicesResult;
    }

    //撤出操作；标注时间：2021年6月17日 23:53:48
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public boolean updateDevicesForWithdraw(Devices devices, OfficesStorage storage) throws Exception, RuntimeException {

        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        boolean devicesResult = false;

        devices.setId(storage.getOffices_entity_id());
        devices.setDevice_deployment_status("2");
        devices.setStation_id("");
        devices.setDevice_location("");
        devices.setUpdateUserId(devices.getCreateUserId());
        devices.setUpdateDate(createDate);

        devices.setDeleteFlag("0");
        devicesResult = dao.updateDevicesForWithdraw(devices) == 1 ? true : false;
        if (!(devicesResult)) {
            log.error("updateDevicesForDeployment: dao.updateDevicesForDeployment出错！");
            throw new Exception("updateDevicesForDeployment: dao.updateDevicesForDeployment出错！");
        } else {
            
            storage.setOriginal_storage_officeId(devices.getInventory_office_id());
            storage.setOffices_storage_genre("3");
            storage.setEntity_entry_status("3");
            result = storageService.addOfficesStorage(devices, storage);
            if (result.get("hasError") instanceof Boolean && (Boolean) result.get("hasError")) {
                log.error("updateDevicesForDeployment: dao.updateDevicesForDeployment出错！");
                throw new Exception("updateDevicesForDeployment: dao.updateDevicesForDeployment出错！");
            }
            devicesResult = depositoryService.withdrawDeviceWithSingle(storage.getStock_or_depository_id(), storage.getUpdateUserId(), createDate);
        }

        return devicesResult;
    }

    //
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

    //转科
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public boolean transferDevices(Devices devices, OfficesStorage officesStorage) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        boolean devicesResult = false;

        devices.setPresent_stock_id(officesStorage.getStock_or_depository_id());//获取库存的id值
        devices.setId(officesStorage.getOffices_entity_id());
        devices.setDevice_id(officesStorage.getEntity_id());
        devices.setLocation_office_id(officesStorage.getOffices_storage_officeId());
        devices.setDevice_deployment_date(officesStorage.getOffices_storage_date());
        devices.setDevice_deployment_status("1");
        devices.setUpdateDate(createDate);
        devices.setDeleteFlag("0");
        devicesResult = dao.transferDevices(devices) == 1 ? true : false;
        if (!(devicesResult)) {
            log.error("transferDevices:dao.transferDevices出错！");
            throw new Exception("transferDevices:dao.transferDevices出错！");
        } else {
            officesStorage.setOffices_storage_ident("NO");
            officesStorage.setOffices_storage_type("1");//设备\耗材类别（1.设备2.配件3.耗材）
            officesStorage.setOffices_storage_genre("5");//流动类别（0：配置1.入科2.部署3.撤出4.回收5.调用6.借用）
            officesStorage.setOffices_storage_total(1);
            officesStorage.setOffices_storage_total_unit(officesStorage.getOffices_storage_unit());//设备调用的数量单位理论等于总量单位
            officesStorage.setOffices_storage_by(devices.getUpdateUserId());
            officesStorage.setEntity_entry_status("0");//入科状态（0：配置待入科1：待入科；2：部分待入科；3：已入科；4：部分已入科）
            result = depositoryService.transferDepositoryForDelivery(devices, officesStorage);
            if (result.get("hasError") instanceof Boolean && (Boolean) result.get("hasError")) {
                log.error("transferDevices:depositoryService.transferDepositoryForDelivery出错！");
                throw new Exception("transferDevices:depositoryService.transferDepositoryForDelivery出错！");
            }
        }
        return devicesResult;
    }

    //回收
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public boolean recoveryDevices(Devices devices, OfficesStorage officesStorage, String stock_no, String stock_unit, String stock_proportion) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        boolean devicesResult = false;

        devices.setId(officesStorage.getOffices_entity_id());
        devices.setDevice_id(officesStorage.getEntity_id());
        devices.setLocation_office_id(officesStorage.getOffices_storage_officeId());
        devices.setDevice_deployment_status("0");
        devices.setUpdateDate(createDate);
        devicesResult = dao.transferDevices(devices) == 1 ? true : false;
        if (!(devicesResult)) {
            log.error("recoveryDevices:dao.transferDevices出错！");
            throw new Exception("recoveryDevices:dao.transferDevices出错！");
        } else {
            officesStorage.setOffices_storage_ident("NO");
            officesStorage.setOffices_storage_type("1");//设备\耗材类别（1.设备2.配件3.耗材）
            officesStorage.setOffices_storage_genre("4");//流动类别（0：配置1.入科2.部署3.撤出4.回收5.调用6.借用）

            officesStorage.setOffices_storage_total(1);
            officesStorage.setOffices_storage_by(devices.getUpdateUserId());
            officesStorage.setEntity_entry_status("0");//入科状态（0：配置待入科1：待入科；2：部分待入科；3：已入科；4：部分已入科）
            result = depositoryService.transferDepositoryForDelivery(devices, officesStorage);
            if (result.get("hasError") instanceof Boolean && (Boolean) result.get("hasError")) {
                log.error("recoveryDevices:depositoryService.transferDepositoryForDelivery出错！");
                throw new Exception("recoveryDevices:depositoryService.transferDepositoryForDelivery出错！");
            }
            officesStorage.setStock_or_depository_id(devices.getPresent_stock_id());
            officesStorage.setOffices_storage_no(Float.parseFloat(stock_no));
            officesStorage.setOffices_storage_total(1);
            officesStorage.setOffices_storage_unit(stock_unit);
            officesStorage.setOffices_storage_proportion(Integer.parseInt(stock_proportion));
            officesStorage.setCreateUserId(devices.getUpdateUserId());
            officesStorage.setCreateDate(createDate);
            officesStorage.setUpdateUserId(devices.getUpdateUserId());
            officesStorage.setUpdateDate(createDate);
            if ("".equals(devices.getPresent_stock_id()) || devices.getPresent_stock_id() == null) {
                result = stockService.addStockForRecovery(officesStorage);
            } else {
                result = stockService.updateStockForRecovery(officesStorage);
            }
            if (!(devicesResult)) {
                log.error("recoveryDevices:stockService.add/updateStockForRecovery出错！");
                throw new Exception("recoveryDevices:stockService.add/updateStockForRecovery出错！");
            }
        }

        return devicesResult;
    }

    @Override
    public List<Map<String, Object>> getDevicesNumber(String devicesId) {
        return dao.getDevicesNumber(devicesId);
    }

    @Override
    public List<Map<String, Object>> getDevicesSelect() {
        return dao.getDevicesSelect();
    }

    @Override
    public List<Map<String, Object>> getStoreDevicesById(List<String> listDevId) {
        return dao.getStoreDevicesById(listDevId);
    }

    @Override
    public List<Map<String, Object>> getDevicesIdent() {
        if (dao.getDevicesIdent().size() != 1) {
            log.error("getDeviceIdent:获取设备编号错误");
            return null;
        }
        return dao.getDevicesIdent();
    }

    //根据部署状态获取设备
    @Override
    public List<Devices> getDevicesWithStatus(String deviceId, String officeId, String status) {

        return dao.getDevicesWithStatus(deviceId, officeId, status);
    }

    //根据部署状态获取设备
    @Override
    public List<Map<String, Object>> getDevicesWithDepositoryId(String depositoryId, String status) {

        return dao.getDevicesWithDepositoryId(depositoryId, status);
    }

}
