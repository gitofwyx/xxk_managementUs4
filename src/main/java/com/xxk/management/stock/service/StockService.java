package com.xxk.management.stock.service;

import com.xxk.management.stock.entity.Stock;
import com.xxk.management.storage.entity.Storage;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface StockService {

    public List<Stock> listStock(int pageStart, int pageSize);

    public List<Stock> listStockByEntityId(String entityId);

    public Map<String, Object> addStock(Stock stock);

    public Map<String, Object> updateStock(Stock stock);

    public boolean deleteListRegUser(List<String> listStr);

    public List<String> getStorageIdByIdent(String ident);

}
