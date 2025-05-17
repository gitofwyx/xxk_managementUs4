package com.xxk.management.station.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.devices.service.DevicesService;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.station.dao.StationDao;
import com.xxk.management.station.entity.Station;
import com.xxk.management.station.service.StationDevicesService;
import com.xxk.management.station.service.StationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StationDevicesServiceImpl implements StationDevicesService {

    private static Logger log = LogManager.getLogger();

    @Autowired
    StationDao dao;

    @Autowired
    DevicesService devicesService;

    //换出操作；标注时间：2021年6月17日 23:53:48
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public boolean updateStationDevicesForExchange(Devices devices, OfficesStorage storage,OfficesStorage bf_storage) throws Exception, RuntimeException {

        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        boolean devicesResult = false;

        devices.setDeleteFlag("0");
        devicesResult = devicesService.updateDevicesForDeployment(devices,storage) ;
        if (!(devicesResult)) {
            log.error("updateDevicesForDeployment: dao.updateDevicesForDeployment出错！");
            throw new Exception("updateDevicesForDeployment: dao.updateDevicesForDeployment出错！");
        } else {
            devices.setClass_id(bf_storage.getClass_id());
            devices.setDevice_id(bf_storage.getEntity_id());
            devicesResult = devicesService.updateDevicesForWithdraw(devices,bf_storage) ;
            if (!(devicesResult)) {
                log.error("updateStationDevicesForExchange: devicesService.updateDevicesForWithdraw！");
                throw new Exception("updateStationDevicesForExchange: devicesService.updateDevicesForWithdraw出错！");
            }
        }

        return devicesResult;
    }

    //换出操作；标注时间：2021年6月17日 23:53:48
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public boolean updateStationDevicesForWithdraw(Devices devices, OfficesStorage storage) throws Exception, RuntimeException {

        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        boolean devicesResult = false;

        devices.setDeleteFlag("0");
        devices.setClass_id(storage.getClass_id());
        devices.setDevice_id(storage.getEntity_id());
        devicesResult = devicesService.updateDevicesForWithdraw(devices,storage) ;
        if (!(devicesResult)) {
            log.error("updateStationDevicesForExchange: devicesService.updateDevicesForWithdraw！");
            throw new Exception("updateStationDevicesForExchange: devicesService.updateDevicesForWithdraw出错！");
        }

        return devicesResult;
    }


}
