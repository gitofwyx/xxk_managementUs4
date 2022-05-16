package com.xxk.management.office.offices.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.tree.StreamTreeUtil;
import com.xxk.management.office.offices.dao.OfficesDao;
import com.xxk.management.office.offices.entity.Offices;
import com.xxk.management.office.offices.service.OfficesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class OfficesServiceImpl implements OfficesService {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private OfficesDao dao;


    @Override
    public List<Offices> listOffices(int pageStart, int pageSize) {
        return dao.listOffices((pageStart-1)*pageSize, pageSize);
    }

    @Override
    public boolean addOffices(Offices office) {

        Boolean result=dao.addOffices(office)==1?true:false;
        if(result){
            dao.updateOfficesUDstatus("1","admin", DateUtil.getFullTime());
        }

        return result;
    }

    public boolean updateOfficesUDstatus(String office_ident,String userId,String date){
        return dao.updateOfficesUDstatus("1","admin", DateUtil.getFullTime())==1?true:false;
    }

    @Override
    public List<Map<String, Object>> getOfficeSelect() {
        return dao.getOfficeSelect();
    }

    @Override
    public List<Map<String, Object>> verifyIsBelong_toOffices(String BId) {

        try {
            Map<String, Object> result = new HashMap<>();
            List<Map<String, Object>> offices=dao.getOfficeSelect();
            List<Map<String, Object>> familyOffices= StreamTreeUtil.getChildrenData(BId,offices);
            return familyOffices;

        }catch (Exception e){
            log.error(e);
        }

        return dao.getOfficeSelect();
    }

    @Override
    public int getUnderlingCount(String belong_to_id) {
        return dao.getUnderlingCount(belong_to_id);
    }

    @Override
    public int geRootCount(String belong_to_id) {
        return dao.geRootCount(belong_to_id);
    }

    public String getOfficeUpdateDate(String office_ident){
        return dao.getOfficeUpdateDate(office_ident);
    }
}
