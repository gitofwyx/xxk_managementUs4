package com.xxk.management.operation.service;

import com.xxk.management.operation.entity.OpeRecord;
import com.xxk.management.operation.entity.Operation;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface OpeRecordService {

    public List<Operation> listOpeRecord(int pageStart, int pageSize);

    public boolean addOpeRecord(OpeRecord opeRecord);

    public boolean plusOpeRecord(String ope_office_id);

}
