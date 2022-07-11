package com.xxk.management.office.depository.service;

import com.xxk.management.office.depository.entity.Depository;
import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.entity.Storage;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface DepositoryService {

    public List<Depository> listDepository(int pageStart, int pageSize, String class_id, String entity_id, String depository_officeId, int search_type);

    public List<Depository> selectDepository(String entity_id,String depository_officeId);

    public int countDepository(String search_type);

    public Depository getDepositoryByEntId(String entity_id);

    public Map<String, Object> addDepositoryWithStorage(Depository depository, OfficesStorage storage) throws Exception;

    public Map<String, Object> updateDepositoryWithStorage(Depository depository, OfficesStorage storage) throws Exception;

    public Map<String, Object> recoveryDepository(Depository depository, Stock stock, Storage storage) throws Exception;

    public Map<String, Object> transferDepositoryForStorage(Devices devices, OfficesStorage storage) throws Exception;

    public Map<String, Object> transferDepositoryForDelivery(Devices devices, OfficesStorage storage) throws Exception;

    public boolean deploymentDeviceWithSingle(String depository_id,String updateUserId,String updateDate);

    public boolean withdrawDeviceWithSingle(String depository_id, String updateUserId, String updateDate);

    public List<String> getDepositoryIdByIdent(String ident);

    public Depository getDepositoryById(String id);

}
