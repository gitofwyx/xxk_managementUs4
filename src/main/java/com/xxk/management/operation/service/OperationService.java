package com.xxk.management.operation.service;

import com.xxk.management.operation.entity.Operation;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface OperationService {

    public List<Operation> listOperation(int pageStart, int pageSize);

    public List<Map<String, Object>> listOperationAccordingDate(String registration_id, String[] status);

    public boolean addOperation(Operation operation,String currentUser)throws Exception, RuntimeException;

    public List<Map<String, Object>> getOfficeSelect();

    public int getUnderlingCount(String belong_to_id);

    public int geRootCount(String belong_to_id);

}
