package com.xxk.management.storage.service.impl;
import com.xxk.management.storage.dao.DeliveryDao;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.service.DeliveryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class DeliveryServiceImpl implements DeliveryService {

    private static Logger log = Logger.getLogger(DeliveryServiceImpl.class);

    @Autowired
    private DeliveryDao dao;

    @Override
    public List<Delivery> listDelivery(int pageStart, int pageSize) {
        return dao.listDelivery((pageStart-1)*pageSize, pageSize);
    }

    @Override
    public boolean addDelivery(Delivery delivery) {
        return dao.addDelivery(delivery)==1?true:false;
    }


    @Override
    public boolean deleteListRegUser(List<String> listStr) {
        return false;
    }
}
