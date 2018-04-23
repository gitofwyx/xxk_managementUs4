package com.xxk.management.operation.service.impl;

import com.xxk.management.operation.dao.OperationDao;
import com.xxk.management.operation.entity.Operation;
import com.xxk.management.operation.service.OperationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class OperationServiceImpl implements OperationService {

    private static Logger log = Logger.getLogger(OperationServiceImpl.class);

    @Autowired
    private OperationDao dao;


    @Override
    public List<Operation> listOffices(int pageStart, int pageSize) {
        return dao.listOffices((pageStart-1)*pageSize, pageSize);
    }

    @Override
    public boolean addOffices(Operation office) {
        return dao.addOffices(office)==1?true:false;
    }

    @Override
    public List<Map<String, Object>> getOfficeSelect() {
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
}
