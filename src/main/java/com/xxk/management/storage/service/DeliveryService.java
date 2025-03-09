package com.xxk.management.storage.service;

import com.xxk.management.stock.entity.Stock;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.entity.Storage;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface DeliveryService {

    public List<Delivery> listDelivery(int pageStart, int pageSize);

    public int countDelivery();

    public  List<Delivery> listDeliveryByStock(int pageStart, int pageSize, String class_id, String entity_id, String stock_id, String officeId);

    public List<Delivery> listDeliveryByHandler(String user_id);

    public  List<Delivery> listDeliveryByOffice(int pageStart, int pageSize,String stock_id);

    public  List<Delivery> listDeliveryForTransfer(int pageStart, int pageSize,String stock_id);

    public List<Delivery> listDeliveryUNIONStorageByOffice(int pageStart, int pageSize, String stock_id);

    public Delivery getDeliveryById(String id);

    public List<Delivery> getDeliveryUNIONStorageByEntityId(String entity_id);

    public Map<String, Object> addDelivery(Stock stock, Delivery delivery,String genre,String status) throws Exception;

    public Map<String, Object> addDelivery(Delivery delivery, String genre,String status) throws Exception;

    public Map<String, Object> backwardDelivery(Storage storage, Delivery delivery, String stock_no,String out_total) throws Exception;

    public boolean updateDeliveryStatus(String id,String status,String user_id,String updateDate);

    public Map<String, Object> deliveryReport(String startDate,String endDate);

}
