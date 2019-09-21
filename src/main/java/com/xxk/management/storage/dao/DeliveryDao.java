package com.xxk.management.storage.dao;

import com.xxk.management.storage.entity.Delivery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface DeliveryDao {

    public List<Delivery> listDelivery(int pageStart, int pageSize);

    public List<Delivery> listDeliveryByStock(int pageStart, int pageSize,String stock_id);

    public int addDelivery(Delivery delivery);

    public int deleteListRegUser(List<String> listStr);


}
