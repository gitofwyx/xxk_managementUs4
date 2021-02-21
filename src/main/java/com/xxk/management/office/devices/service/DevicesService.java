package com.xxk.management.office.devices.service;

import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.storage.entity.OfficesStorage;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface DevicesService {

    public List<Devices> listDevices(int pageStart, int pageSize, String class_id, String device_id, String location_office_id);

    public int countDevices();

    public List<Devices> listDevicesById(List<String> listDevId);

    public boolean addDevices(Devices devices, OfficesStorage storage);

    public boolean updateDevicesStatus(String devicesId,String status,String present_stock_id,String userId,String Date);

    public List<Map<String, Object>> getDevicesNumber(String deviceId);

    public List<Map<String, Object>> getDevicesSelect();

    public List<Map<String, Object>> getStoreDevicesById(List<String> listDevId);

    public List<Map<String, Object>> getDevicesIdent();  //获取设备编号

    public List<Devices> getDevicesWithStatus(String deviceId,String officeId,String status);

    public List<Devices> getDevicesWithDepositoryId(String DepositoryId,String status) ;

    public boolean plusDevicesNumber(int dev_no, String devicesId);

    public boolean minusDevicesNumber(int dev_no, String devicesId);

}
