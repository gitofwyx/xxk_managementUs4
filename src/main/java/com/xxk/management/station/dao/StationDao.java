package com.xxk.management.station.dao;

import com.xxk.management.station.entity.Station;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface StationDao {

    public List<Station> listWorkstation(int pageStart, int pageSize);

    public List<Map<String, Object>> listWorkstationWithDevices(@Param("pageStart") int pageStart,
                                                                @Param("pageSize") int pageSize,
                                                                @Param("class_id") String class_id,
                                                                @Param("device_id") String device_id,
                                                                @Param("device_flag") String device_flag,
                                                                @Param("device_deployment_status") String device_deployment_status,
                                                                @Param("location_office_id") String location_office_id,
                                                                @Param("station_id") String station_id);

    public List<Map<String, Object>> getStationSelectByOfficeId(@Param("office_id")String office_id);

    public int addWorkstation(Station station);
}
