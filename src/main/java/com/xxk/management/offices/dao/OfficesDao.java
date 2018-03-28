package com.xxk.management.offices.dao;

import com.xxk.management.offices.entity.Offices;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface OfficesDao {

    public List<Offices> listDeviceClass(int pageStart, int pageSize);

    public List<Map<String, Object>> listAllDeviceName();



}
