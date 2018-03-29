package com.xxk.management.offices.service;

import com.xxk.management.offices.entity.Offices;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface OfficesService {

    public List<Offices> listOffices(int pageStart, int pageSize);

    public boolean addOffices(Offices office);

}
