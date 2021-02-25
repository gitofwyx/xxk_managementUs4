package com.xxk.management.office.devices.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.office.depository.service.DepositoryService;
import com.xxk.management.office.devices.dao.DevicesDao;
import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.devices.service.DevicesService;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.office.storage.service.OfficesStorageService;
import com.xxk.management.storage.service.StorageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class DevicesServiceImpl implements DevicesService {

    private static Logger log = Logger.getLogger(DevicesServiceImpl.class);

    @Autowired
    private DevicesDao dao;

    @Autowired
    private OfficesStorageService storageService;

    @Autowired
    private DepositoryService depositoryService;


    @Override
    public List<Devices> listDevices(int pageStart, int pageSize, String class_id, String device_id, String location_office_id) {
        return dao.listDevices((pageStart - 1) * pageSize,pageSize,class_id,device_id,location_office_id);
    }

    @Override
    public int countDevices() {
        return dao.countDevices();
    }

    @Override
    public List<Devices> listDevicesById(List<String> listDevId) {
        return dao.listDevicesById(listDevId);
    }

    @Override
    public boolean addDevices(Devices devices, OfficesStorage storage) {

        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String devicesId = UUIdUtil.getUUID();
        boolean devicesResult=false;
        try {

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
                log.error("depositoryResult:" + devicesResult);
                result.put("hasError", true);
                result.put("error", "添加出错");
            } else {
                storage.setEntity_id(devices.getDevice_id());
                storage.setOffices_entity_id(devicesId);
                storage.setOriginal_storage_officeId(devices.getInventory_office_id());
                storage.setOffices_storage_genre("2");
                storage.setEntity_entry_status("2");
                result = storageService.addOfficesStorage(devices,storage);
                if("true".equals(result.get("hasError"))){
                    return false;
                }
                devicesResult=depositoryService.deploymentDeviceWithSingle(storage.getStock_or_depository_id(),storage.getUpdateUserId(),createDate);
            }
        } catch (DuplicateKeyException e) {
            result.put("hasError", true);
            result.put("error", "重复值异常，可能编号值重复");
            log.error(e);
        } catch (Exception e) {
            result.put("hasError", true);
            result.put("error", "添加出错");
            log.error(e);
        }
        return devicesResult;
    }

    @Override
    public boolean updateDevicesForDeployment(Devices devices, OfficesStorage storage) {

        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String devicesId = UUIdUtil.getUUID();
        boolean devicesResult=false;
        try {

            devices.setId(devicesId);
            devices.setClass_id(storage.getClass_id());
            devices.setDevice_id(storage.getEntity_id());
            devices.setDevices_ident("NO");
            devices.setDevice_deployment_status("2");
            devices.setRelated_flag("0");
            devices.setUpdateUserId(devices.getCreateUserId());
            devices.setUpdateDate(createDate);

            devices.setDeleteFlag("0");
            devicesResult = dao.addDevices(devices) == 1 ? true : false;
            if (!(devicesResult)) {
                log.error("depositoryResult:" + devicesResult);
                result.put("hasError", true);
                result.put("error", "添加出错");
            } else {
                storage.setEntity_id(devices.getDevice_id());
                storage.setOffices_entity_id(devicesId);
                storage.setOriginal_storage_officeId(devices.getInventory_office_id());
                storage.setOffices_storage_genre("2");
                storage.setEntity_entry_status("2");
                result = storageService.addOfficesStorage(devices,storage);
                if("true".equals(result.get("hasError"))){
                    return false;
                }
                devicesResult=depositoryService.deploymentDeviceWithSingle(storage.getStock_or_depository_id(),storage.getUpdateUserId(),createDate);
            }
        } catch (DuplicateKeyException e) {
            result.put("hasError", true);
            result.put("error", "重复值异常，可能编号值重复");
            log.error(e);
        } catch (Exception e) {
            result.put("hasError", true);
            result.put("error", "添加出错");
            log.error(e);
        }
        return devicesResult;
    }

    //
    @Override
    public boolean updateDevicesStatus(String devicesId,String status,String present_stock_id,String userId,String Date) {

        String createDate = DateUtil.getFullTime();
        boolean devicesResult = false;
        try {
            devicesResult = dao.updateDevicesStatus(devicesId,status,present_stock_id,userId,Date) == 1 ? true : false;
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
    public List<Map<String, Object>> getDevicesNumber(String devicesId) {
        return dao.getDevicesNumber(devicesId);
    }

    @Override
    public  List<Map<String, Object>> getDevicesSelect() {
        return dao.getDevicesSelect();
    }

    @Override
    public List<Map<String, Object>> getStoreDevicesById(List<String> listDevId) {
        return dao.getStoreDevicesById(listDevId);
    }

    @Override
    public List<Map<String, Object>> getDevicesIdent() {
        if(dao.getDevicesIdent().size()!=1){
            log.error("getDeviceIdent:获取设备编号错误");
            return null;
        }
        return dao.getDevicesIdent();
    }

    //根据部署状态获取设备
    @Override
    public List<Devices> getDevicesWithStatus(String deviceId,String officeId,String status) {

        return dao.getDevicesWithStatus(deviceId,officeId,status);
    }

    //根据部署状态获取设备
    @Override
    public List<Map<String, Object>> getDevicesWithDepositoryId(String depositoryId,String status) {

        return dao.getDevicesWithDepositoryId(depositoryId,status);
    }

    @Override
    public boolean plusDevicesNumber(int dev_no,String devicesId) {
        return dao.plusDevicesNumber(dev_no,devicesId)==1?true:false;
    }

    @Override
    public boolean minusDevicesNumber(int dev_no, String devicesId) {
        return dao.minusDevicesNumber(dev_no,devicesId)==1?true:false;
    }

}
