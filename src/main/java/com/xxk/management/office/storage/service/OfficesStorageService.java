package com.xxk.management.office.storage.service;

import com.xxk.management.office.depository.entity.Depository;
import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.storage.entity.Delivery;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface OfficesStorageService {

    public List<OfficesStorage> listOfficesStorage(int pageStart, int pageSize);

    public  List<OfficesStorage> listOfficesStorageByStock(int pageStart, int pageSize,String stock_id);

    public  List<OfficesStorage> listOfficesStorageByOffice(int pageStart, int pageSize,String stock_id);

    public Map<String, Object> addOfficesStorage(Depository depository, OfficesStorage officesStorage, String status) throws Exception;

    public Map<String, Object> addOfficesStorage(Devices devices, OfficesStorage officesStorage) throws Exception;

    boolean updateOfficesStorageStatus(String id, String status);

    public boolean updateOfficesStorageGenre_and_Status(String id,String genre,String status,String userId,String Date);

    public boolean deleteListRegUser(List<String> listStr);

}
