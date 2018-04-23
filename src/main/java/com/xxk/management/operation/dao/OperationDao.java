package com.xxk.management.operation.dao;

import com.xxk.management.operation.entity.Operation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface OperationDao {

    public List<Operation> listOffices(int pageStart, int pageSize);

    public int addOffices(Operation office);

    public List<Map<String, Object>> getOfficeSelect();

    public int getUnderlingCount(String belong_to_id);

    public int geRootCount(String belong_to_id);

}
