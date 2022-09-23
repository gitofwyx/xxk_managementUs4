package com.xxk.management.stock.service;

import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.entity.Storage;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface StockService {

    public List<Stock> listStock(int pageStart, int pageSize,String class_id,String entity_id,String stock_office_id,String search_type);

    public List<LinkedHashMap<String, Object>> getStockSuggest(String entity_id, String office_id);

    public int countStock(String search_type);

    public List<Stock> listStockByEntityId(String entity_id,String office_id);

    public List<Stock> getStocksByEntityId(String entity_id);

    public Map<String, Object> addStockWithStorage(Stock stock,Storage storage) throws Exception;

    public Map<String, Object> updateStockWithStorage(Stock stock,Storage storage) throws Exception;

    public Map<String, Object> updateStockWithDelivery(Stock stock, Delivery delivery) throws Exception;

    public Map<String, Object> updateSingleStockWithDelivery(Delivery delivery,double stock_no) throws Exception;

    public Map<String, Object> updateStock(Stock stock) throws Exception;

    public Map<String, Object> addStockForRecovery(OfficesStorage officesStorage) throws Exception;

    public Map<String, Object> updateStockForRecovery(OfficesStorage officesStorage) throws Exception;

    public Map<String, Object> updateStockForBackward(Storage storage,Delivery delivery,String stock_no) throws Exception;

    public boolean plusStockConfiguredTotal(String stockId,String userId,String date,String stock_version);

    public boolean deleteListRegUser(List<String> listStr);

    public List<String> getStorageIdByIdent(String ident);

    public Stock getStockById(String id);

}
