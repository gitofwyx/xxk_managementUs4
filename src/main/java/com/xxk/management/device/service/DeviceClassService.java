package com.xxk.management.device.service;

import com.xxk.management.device.entity.Device;
import com.xxk.management.device.entity.DeviceClass;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface DeviceClassService {

    public List<Device> listDeviceClass(int pageStart, int pageSize);

    public List<Map<String, Object>> listAllDeviceName();

    public boolean addDeviceClass(DeviceClass deviceClass);

   // public DeviceClass getDeviceClassById(String id);

    public List<Map<String, Object>> getDeviceClassById(String id);

    public boolean updateDevMax(DeviceClass deviceClass);

    public boolean updateDev_typeMax(DeviceClass deviceClass);

}
