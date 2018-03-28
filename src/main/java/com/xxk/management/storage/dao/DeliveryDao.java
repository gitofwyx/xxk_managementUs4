package com.xxk.management.storage.dao;

import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.entity.Storage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface DeliveryDao {

    public List<Delivery> listDelivery(int pageStart, int pageSize);

    public int addDelivery(Delivery delivery);

    public int deleteListRegUser(List<String> listStr);


}
