package com.xxk.management.office.devices.service.impl;

import com.xxk.management.office.devices.dao.DevicesDao;
import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.devices.service.DevicesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class DevicesServiceImpl implements DevicesService {

    private static Logger log = Logger.getLogger(DevicesServiceImpl.class);

    @Autowired
    private DevicesDao dao;


    @Override
    public List<Devices> listDevices(int pageStart, int pageSize) {
        return dao.listDevices((pageStart-1)*pageSize, pageSize);
    }

    @Override
    public List<Devices> listDevicesById(List<String> listDevId) {
        return dao.listDevicesById(listDevId);
    }

    @Override
    public boolean addDevices(Devices devices) {
        return dao.addDevices(devices)==1?true:false;
    }

    @Override
    public List<Map<String, Object>> getDevicesNumber(String devicesId) {
        return dao.getDevicesNumber(devicesId);
    }

    @Override
    public  List<Map<String, Object>> getDevicesSelect() {
        return dao.getDevicesSelect();
    }

    @Override
    public List<Map<String, Object>> getStoreDevicesById(List<String> listDevId) {
        return dao.getStoreDevicesById(listDevId);
    }

    @Override
    public List<Map<String, Object>> getDevicesIdent() {
        if(dao.getDevicesIdent().size()!=1){
            log.error("getDeviceIdent:获取设备编号错误");
            return null;
        }
        return dao.getDevicesIdent();
    }

    @Override
    public boolean plusDevicesNumber(int dev_no,String devicesId) {
        return dao.plusDevicesNumber(dev_no,devicesId)==1?true:false;
    }

    @Override
    public boolean minusDevicesNumber(int dev_no, String devicesId) {
        return dao.minusDevicesNumber(dev_no,devicesId)==1?true:false;
    }

}
