package com.xxk.management.device.service.impl;

import com.xxk.management.device.dao.DeviceClassDao;
import com.xxk.management.device.entity.Device;
import com.xxk.management.device.entity.DeviceClass;
import com.xxk.management.device.service.DeviceClassService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class DeviceClassServiceImpl implements DeviceClassService {

    private static Logger log = Logger.getLogger(DeviceClassServiceImpl.class);

    @Autowired
    private DeviceClassDao dao;


    @Override
    public List<Device> listDeviceClass(int pageStart, int pageSize) {
        return dao.listDeviceClass((pageStart-1)*pageSize, pageSize);
    }

    @Override
    public List<Map<String, Object>> listAllDeviceName() {
        return dao.listAllDeviceName();
    }

    @Override
    public boolean addDeviceClass(DeviceClass deviceClass) {
        return dao.addDeviceClass(deviceClass)==1?true:false;
    }

    /*@Override
    public DeviceClass getDeviceClassById(String id) {
        return dao.getDeviceClassById(id);
    }*/

    @Override
    public List<Map<String, Object>> getDeviceClassById(String id) {
        return dao.getClassClassById(id);
    }

    @Override
    public boolean updateDevMax(DeviceClass deviceClass) {
        return dao.updateDevMax(deviceClass)==1?true:false;
    }

    @Override
    public boolean updateDev_typeMax(DeviceClass deviceClass) {
        return dao.updateDev_typeMax(deviceClass)==1?true:false;
    }
}
