package com.xxk.management.office.depository.service;

import com.xxk.management.office.depository.entity.Depository;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.storage.entity.Delivery;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface DepositoryService {

    public List<Depository> listDepository(int pageStart, int pageSize, String class_id, String entity_id, String depository_officeId, int search_type);

    public List<Depository> listDepositoryByEntityId(String entity_id, String office_id);

    public Depository getDepositoryByEntId(String entity_id);

    public Map<String, Object> addDepositoryWithStorage(Depository depository, OfficesStorage storage);

    public Map<String, Object> updateDepositoryWithStorage(Depository depository, OfficesStorage storage);

    public Map<String, Object> updateDepositoryWithDelivery(Depository depository, Delivery delivery);

    public Map<String, Object> updateDepository(Depository depository);

    public boolean deleteListRegUser(List<String> listStr);

    public List<String> getDepositoryIdByIdent(String ident);

    public Depository getDepositoryById(String id);

}
