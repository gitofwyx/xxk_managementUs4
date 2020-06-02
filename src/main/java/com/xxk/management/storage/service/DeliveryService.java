package com.xxk.management.storage.service;

import com.xxk.management.stock.entity.Stock;
import com.xxk.management.storage.entity.Delivery;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface DeliveryService {

    public List<Delivery> listDelivery(int pageStart, int pageSize);

    public  List<Delivery> listDeliveryByStock(int pageStart, int pageSize,String stock_id);

    public  List<Delivery> listDeliveryByOffice(int pageStart, int pageSize,String stock_id);

    public Map<String, Object> addDelivery(Stock stock, Delivery delivery,String status);

    public boolean deleteListRegUser(List<String> listStr);

}
