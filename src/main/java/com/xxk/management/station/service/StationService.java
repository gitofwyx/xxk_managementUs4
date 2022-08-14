package com.xxk.management.station.service;

import com.xxk.management.office.depository.entity.Depository;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.roles.entity.Roles;
import com.xxk.management.station.entity.Station;

import java.util.List;
import java.util.Map;


public interface StationService {

    public List<Station> listWorkstation(int pageStart, int pageSize);

    public  List<Map<String, Object>> listWorkstationWithDevices(int pageStart,
                                                                 int pageSize,
                                                                 String class_id,
                                                                 String device_id,
                                                                 String device_flag,
                                                                 String device_deployment_status,
                                                                 String office_id,
                                                                 String station_id);

    public  List<Map<String, Object>> getStationSelectByOfficeId(String office_id);

    public boolean addWorkstation(Station station) throws Exception;

}
