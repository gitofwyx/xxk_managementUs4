package com.xxk.management.stock.service;

import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.entity.Storage;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface UniteOperateStockService {

    public Map<String, Object> transferStockOfUpdateStock(Stock stock,Storage storage,String delivery_id);

    public Map<String, Object> transferStockOfAddStock(Stock stock,Storage storage,String delivery_id);

}
