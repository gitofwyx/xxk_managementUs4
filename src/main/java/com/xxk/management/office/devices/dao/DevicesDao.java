package com.xxk.management.office.devices.dao;

import com.xxk.management.office.devices.entity.Devices;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface DevicesDao {

    public List<Devices> listDevices(int pageStart, int pageSize);

    public List<Devices> listDevicesById(List<String> listDevId);

    public int addDevices(Devices device);

    public List<Map<String, Object>> getDevicesNumber(String deviceId);

    public List<Map<String, Object>> getDevicesSelect();

    public List<Map<String, Object>> getStoreDevicesById(List<String> listDevId);

    public List<Map<String, Object>> getDevicesIdent();   //获取编号

    public int plusDevicesNumber(int dev_no, String deviceId);   //更新设备数量 （增加）

    public int minusDevicesNumber(int dev_no, String deviceId);   //更新设备数量 （减少）

}
