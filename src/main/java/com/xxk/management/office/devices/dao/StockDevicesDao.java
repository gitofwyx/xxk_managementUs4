package com.xxk.management.office.devices.dao;

import com.xxk.management.office.devices.entity.Devices;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface StockDevicesDao {

    public List<Devices> listStockDevices(@Param("pageStart") int pageStart, @Param("pageSize") int pageSize, @Param("class_id") String class_id,
                                     @Param("device_id") String device_id, @Param("location_office_id") String location_office_id);

    public int countDevices();

    public List<Devices> listDevicesById(List<String> listDevId);

    public int addStockDevices(Devices device);

    public List<Devices> getDevicesWithStatus(String deviceId, String officeId, String status);

    public int updateDeviceStatus(Devices device);   //更新设备数量 （减少）

    public int updateDevicesStatus(String devicesId,String location_office_id,String present_stock_id,String status,String userId,String Date);

    public int updateDevicesSetStatus(String devicesId, String status, String userId, String Date);

}
