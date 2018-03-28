package com.xxk.management.device.dao;

import com.xxk.management.device.entity.Device;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface DeviceDao {

    public List<Device> listDevice(int pageStart, int pageSize);

    public List<Device> listDeviceById(List<String> listDevId);

    public int addDevice(Device device);

    public List<Map<String, Object>> getDeviceNumber(String deviceId);

    public List<Map<String, Object>> getDeviceSelect();

    public List<Map<String, Object>> getStoreDeviceById(List<String> listDevId);

    public List<Map<String, Object>> getDeviceIdent();   //获取编号

    public int plusDeviceNumber(int dev_no,String deviceId);   //更新设备数量 （增加）

    public int minusDeviceNumber(int dev_no,String deviceId);   //更新设备数量 （减少）

}
