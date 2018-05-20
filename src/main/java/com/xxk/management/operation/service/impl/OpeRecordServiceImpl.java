package com.xxk.management.operation.service.impl;

import com.xxk.management.operation.dao.OpeRecordDao;
import com.xxk.management.operation.dao.OperationDao;
import com.xxk.management.operation.entity.OpeRecord;
import com.xxk.management.operation.entity.Operation;
import com.xxk.management.operation.service.OpeRecordService;
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
public class OpeRecordServiceImpl implements OpeRecordService {

    private static Logger log = Logger.getLogger(OpeRecordServiceImpl.class);

    @Autowired
    private OpeRecordDao dao;


    @Override
    public List<Operation> listOpeRecord(int pageStart, int pageSize) {
        return dao.listOpeRecord((pageStart-1)*pageSize, pageSize);
    }

    @Override
    public boolean addOpeRecord(OpeRecord opeRecord) {
        return dao.addOpeRecord(opeRecord)==1?true:false;
    }

    @Override
    public boolean plusOpeRecord(String ope_office_id) {
        return dao.plusOpeRecord(ope_office_id)==1?true:false;
    }

}
