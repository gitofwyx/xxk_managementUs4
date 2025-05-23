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

    /*public boolean addDevices(Devices devices, OfficesStorage storage) throws Exception;*/

    public boolean updateDevicesForDeployment(Devices devices, OfficesStorage storage) throws Exception;

    public boolean updateDevicesForWithdraw(Devices devices, OfficesStorage storage) throws Exception;

    public boolean updateDevicesStatus(String devicesId,String location_office_id,String present_stock_id,String in_storage_id,String delivery_id,String status,String userId,String Date,String cond);

    public boolean transferDevices(Devices devices, OfficesStorage officesStorage) throws Exception;

    public boolean recoveryDevices(Devices devices, OfficesStorage officesStorage, String stock_no, String stock_unit, String stock_proportion) throws Exception;

    public List<Map<String, Object>> getDevicesNumber(String deviceId);

    public List<Map<String, Object>> getDevicesSelect();

    public List<Map<String, Object>> getStoreDevicesById(List<String> listDevId);

    public List<Map<String, Object>> getDevicesIdent();  //获取设备编号

    public List<Devices> getDevicesWithStatus(String deviceId,String officeId,String status);

    public List<Map<String, Object>> getDevicesWithDepositoryId(String DepositoryId,String status) ;

}
