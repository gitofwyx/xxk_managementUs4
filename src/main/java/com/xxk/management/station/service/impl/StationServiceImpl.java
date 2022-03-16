package com.xxk.management.station.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.roles.entity.Roles;
import com.xxk.management.station.dao.StationDao;
import com.xxk.management.station.entity.Station;
import com.xxk.management.station.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    StationDao dao;

    public List<Station> listWorkstation(int pageStart, int pageSize){

        return dao.listWorkstation((pageStart-1)*pageSize,pageSize);

    }

    @Override
    public boolean addWorkstation(Station station) throws Exception {

        String createDate = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        station.setId(id);
        station.setWorkstation_state("1");
        station.setRelated_flag("0");
        station.setWorkstation_auditing_mark("0");
        station.setCreateDate(createDate);
        station.setUpdateUserId(station.getCreateUserId());
        station.setUpdateDate(createDate);

        return dao.addWorkstation(station) == 1 ? true : false;
    }

}
