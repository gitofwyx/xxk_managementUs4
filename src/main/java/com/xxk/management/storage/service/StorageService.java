package com.xxk.management.storage.service;

import com.xxk.management.device.entity.Device;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.storage.entity.Storage;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface StorageService {

    public List<Storage> listStorage(int pageStart, int pageSize);

    public  List<Storage> listStorageByStock(int pageStart, int pageSize, String class_id, String entity_id, String stock_id, String officeId);

    public Map<String, Object> addStorage(Stock stock, Storage storage);

    public boolean deleteListRegUser(List<String> listStr);

    public List<String> getStorageIdByIdent(String ident);

}
