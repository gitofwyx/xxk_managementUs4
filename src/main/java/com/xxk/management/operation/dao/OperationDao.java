package com.xxk.management.operation.dao;

import com.xxk.management.operation.entity.Operation;
import com.xxk.management.registration_record.entity.Registration_record;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface OperationDao {

    public List<Operation> listOperation(int pageStart, int pageSize);

    public List<Map<String, Object>> listOperationByRegId(@Param("ope_registration_id")String ope_registration_id,
                                                          @Param("statusList")String[] listStr);

    public int addOperation(Operation operation);

    public List<Map<String, Object>> getOfficeSelect();

    public int getUnderlingCount(String belong_to_id);

    public int geRootCount(String belong_to_id);

}
