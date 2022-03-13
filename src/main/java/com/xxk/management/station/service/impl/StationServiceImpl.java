package com.xxk.management.station.service.impl;

import com.xxk.management.roles.entity.Roles;
import com.xxk.management.station.dao.StationDao;
import com.xxk.management.station.entity.Station;
import com.xxk.management.station.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    StationDao dao;

    public List<Station> listWorkstation(int pageStart, int pageSize){

        return dao.listWorkstation((pageStart-1)*pageSize,pageSize);

    }

}
