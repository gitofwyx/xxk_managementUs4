package com.xxk.management.device.service.impl;

import com.xxk.management.device.dao.DeviceDao;
import com.xxk.management.device.entity.Device;
import com.xxk.management.device.service.DeviceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    private static Logger log = Logger.getLogger(DeviceServiceImpl.class);

    @Autowired
    private DeviceDao dao;


    @Override
    public List<Device> listDevice(int pageStart, int pageSize) {
        return dao.listDevice((pageStart-1)*pageSize, pageSize);
    }

    @Override
    public List<Device> listDeviceById(List<String> listDevId) {
        return dao.listDeviceById(listDevId);
    }

    @Override
    public boolean addDevice(Device device) {
        return dao.addDevice(device)==1?true:false;
    }

    @Override
    public List<Map<String, Object>> getDeviceNumber(String deviceId) {
        return dao.getDeviceNumber(deviceId);
    }

    @Override
    public  List<Map<String, Object>> getDeviceSelect() {
        return dao.getDeviceSelect();
    }

    @Override
    public List<Map<String, Object>> getStoreDeviceById(List<String> listDevId) {
        return dao.getStoreDeviceById(listDevId);
    }

    @Override
    public List<Map<String, Object>> getDeviceIdent() {
        if(dao.getDeviceIdent().size()!=1){
            log.error("getDeviceIdent:获取设备编号错误");
            return null;
        }
        return dao.getDeviceIdent();
    }

    @Override
    public boolean plusDeviceNumber(int dev_no,String deviceId) {
        return dao.plusDeviceNumber(dev_no,deviceId)==1?true:false;
    }

    @Override
    public boolean minusDeviceNumber(int dev_no, String deviceId) {
        return dao.minusDeviceNumber(dev_no,deviceId)==1?true:false;
    }

}
