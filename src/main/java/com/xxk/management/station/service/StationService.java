package com.xxk.management.station.service;

import com.xxk.management.roles.entity.Roles;
import com.xxk.management.station.entity.Station;

import java.util.List;


public interface StationService {

    public List<Station> listWorkstation(int pageStart, int pageSize);

}
