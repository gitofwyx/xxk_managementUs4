package com.xxk.management.operation.dao;

import com.xxk.management.operation.entity.OpeRecord;
import com.xxk.management.operation.entity.Operation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface OpeRecordDao {

    public List<Operation> listOpeRecord(int pageStart, int pageSize);

    public int addOpeRecord(OpeRecord opeRecord);

    public int plusOpeRecord(String ope_office_id);

}
