package com.xxk.management.offices.service.impl;

import com.xxk.management.offices.dao.OfficesDao;
import com.xxk.management.offices.entity.Offices;
import com.xxk.management.offices.service.OfficesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class OfficesServiceImpl implements OfficesService {

    private static Logger log = Logger.getLogger(OfficesServiceImpl.class);

    @Autowired
    private OfficesDao dao;


    @Override
    public List<Offices> listOffices(int pageStart, int pageSize) {
        return dao.listOffices((pageStart-1)*pageSize, pageSize);
    }

    @Override
    public boolean addOffices(Offices office) {
        return dao.addOffices(office)==1?true:false;
    }
}
