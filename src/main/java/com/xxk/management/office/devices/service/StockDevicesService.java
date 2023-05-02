package com.xxk.management.office.devices.service;

import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.storage.entity.Delivery;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface StockDevicesService {

    public List<Devices> listDevices(int pageStart, int pageSize, String class_id, String device_id, String location_office_id);

    public int countDevices();

    public List<Devices> getDevicesByIdent(String ident);

    public boolean addStockDevices(Devices devices,OfficesStorage storage,String stock_version) throws Exception;

    public boolean deliveryStockDevices(Devices devices, Delivery delivery,double stock_no) throws Exception;

    public List<Devices> getDevicesWithStatus(String deviceId, String officeId, String status);

    boolean updateDevicesStatus(String devicesId, String location_office_id, String present_stock_id, String status, String userId, String Date);

    public boolean updateDevicesSetStatus(String devicesId, String status,String flag, String userId, String Date);

}
