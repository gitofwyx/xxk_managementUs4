package com.xxk.management.office.devices.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.office.depository.service.DepositoryService;
import com.xxk.management.office.devices.dao.DevicesDao;
import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.devices.service.DevicesService;
import com.xxk.management.office.devices.service.StockDevicesService;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.office.storage.service.OfficesStorageService;
import com.xxk.management.storage.entity.Delivery;
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
public class StockDevicesServiceImpl implements StockDevicesService {

    private static Logger log = Logger.getLogger(StockDevicesServiceImpl.class);

    @Autowired
    private DevicesDao dao;

    @Autowired
    private OfficesStorageService storageService;

    @Autowired
    private DepositoryService depositoryService;


    @Override
    public List<Devices> listDevices(int pageStart, int pageSize, String class_id, String device_id, String location_office_id) {
        return dao.listDevices((pageStart - 1) * pageSize, pageSize, class_id, device_id, location_office_id);
    }

    @Override
    public int countDevices() {
        return 0;
    }

    @Override
    public boolean addStockDevices(Devices devices, Delivery delivery) {

        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String devicesId = UUIdUtil.getUUID();
        boolean devicesResult = false;
        try {

            devices.setId(devicesId);
            devices.setClass_id(delivery.getClass_id());
            devices.setDevice_id(delivery.getEntity_id());
            devices.setDevice_ident("NO");
            devices.setDevice_state("0");
            devices.setLocation_office_id(delivery.getOut_confirmed_officeId());
            devices.setInventory_office_id(delivery.getOut_confirmed_officeId());
            devices.setDevice_origin("1");
            devices.setDevice_deployment_status("0");
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
                if ("true".equals(result.get("hasError"))) {
                    return false;
                }

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

    //根据部署状态获取设备
    @Override
    public List<Devices> getDevicesWithStatus(String deviceId, String officeId, String status) {

        return dao.getDevicesWithStatus(deviceId, officeId, status);
    }

}
