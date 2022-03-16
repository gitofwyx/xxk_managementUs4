package com.xxk.management.station.dao;

import com.xxk.management.station.entity.Station;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface StationDao {

    public List<Station> listWorkstation(int pageStart, int pageSize);

    public int addWorkstation(Station station);


}
