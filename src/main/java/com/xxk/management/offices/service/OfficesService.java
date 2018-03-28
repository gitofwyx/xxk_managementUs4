package com.xxk.management.offices.service;

import com.xxk.management.offices.entity.Offices;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface OfficesService {

    public List<Offices> listDevice(int pageStart, int pageSize);

    public List<Offices> listDeviceById(List<String> listDevId);

    public boolean addDevice(Offices device);

    public List<Map<String, Object>> getDeviceNumber(String deviceId);

    public List<Map<String, Object>> getDeviceSelect();

    public List<Map<String, Object>> getStoreDeviceById(List<String> listDevId);

    public List<Map<String, Object>> getDeviceIdent();  //获取设备编号

    public boolean plusDeviceNumber(int dev_no, String deviceId);

    public boolean minusDeviceNumber(int dev_no, String deviceId);

}
