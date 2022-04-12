package com.xxk.management.station.service;

import com.xxk.management.office.depository.entity.Depository;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.roles.entity.Roles;
import com.xxk.management.station.entity.Station;

import java.util.List;
import java.util.Map;


public interface StationService {

    public List<Station> listWorkstation(int pageStart, int pageSize);

    public boolean addWorkstation(Station station) throws Exception;

    public  List<Map<String, Object>> getStationSelectByOfficeId(String office_id);

}
