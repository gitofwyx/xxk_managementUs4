package com.xxk.management.storage.service;

import com.xxk.management.storage.entity.Delivery;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface DeliveryService {

    public List<Delivery> listDelivery(int pageStart, int pageSize);

    public boolean addDelivery(Delivery delivery);

    public boolean deleteListRegUser(List<String> listStr);

}
