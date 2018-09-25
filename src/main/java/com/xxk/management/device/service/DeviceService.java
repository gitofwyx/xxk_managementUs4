package com.xxk.management.device.service;

import com.xxk.management.device.entity.Device;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface DeviceService {

    public List<Device> listDevice(int pageStart, int pageSize);

    public List<Device> listDeviceById(List<String> listDevId);

    public boolean addDevice(Device device);

    public Device getDeviceById(String deviceId);

    public List<Map<String, Object>> getDeviceNumber(String deviceId);

    public List<Map<String, Object>> getDeviceSelect();

    public List<Map<String, Object>> getStoreDeviceById(List<String> listDevId);

    public List<Map<String, Object>> getDeviceIdent();  //获取设备编号

}
