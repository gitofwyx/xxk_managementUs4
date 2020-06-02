package com.xxk.management.storage.service.impl;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.core.util.build_ident.IdentUtil;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.storage.dao.DeliveryDao;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.service.DeliveryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return dao.listDelivery((pageStart - 1) * pageSize, pageSize);
    }

    @Override
    public List<Delivery> listDeliveryByStock(int pageStart, int pageSize,String stock_id) {
        return dao.listDeliveryByStock((pageStart - 1) * pageSize, pageSize,stock_id);
    }

    @Override
    public List<Delivery> listDeliveryByOffice(int pageStart, int pageSize,String stock_id) {
        return dao.listDeliveryByOffice((pageStart - 1) * pageSize, pageSize,stock_id);
    }

    @Override
    public Map<String, Object> addDelivery(Stock stock, Delivery delivery,String status) {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String deliveryId = UUIdUtil.getUUID();
        try {
            if ("".equals(delivery.getEntity_id()) || delivery.getEntity_id() == null) {
                log.info("出错！无法获取设备ID");
                result.put("hasError", true);
                result.put("error", "添加出错！无法获取设备ID");
                return result;
            }
            //库存编号生成
            String out_confirmed_ident = IdentUtil.getIdentNo((int)stock.getStock_no(), createDate);
            if ("".equals(out_confirmed_ident) || out_confirmed_ident == null) {
                result.put("hasError", true);
                result.put("error", "添加出错,无法生成入库编号！");
                return result;
            }
            //入库
            delivery.setId(deliveryId);
            delivery.setStock_id(stock.getId());
            delivery.setOut_confirmed_ident(out_confirmed_ident);
            delivery.setOut_confirmed_unit(stock.getStock_unit());
            delivery.setOut_confirmed_proportion(stock.getStock_proportion());
            delivery.setEntity_entry_status(status);
            delivery.setCreateDate(createDate);
            delivery.setCreateUserId("admin");
            delivery.setUpdateDate(createDate);
            delivery.setUpdateUserId("admin");
            delivery.setDeleteFlag("0");

            boolean storageResult = dao.addDelivery(delivery) == 1 ? true : false;
            ;
            if (!(storageResult)) {
                log.error("addstorage:" + storageResult);
                result.put("hasError", true);
                result.put("error", "添加出错");
            } else {
                //log.info(">>>>保存成功");
                result.put("success", true);
            }
        } catch (DuplicateKeyException e) {
            result.put("hasError", true);
            result.put("error", "重复值异常，可能编号值重复");
            log.error(e);
        } catch (Exception e) {
            result.put("hasError", true);
            result.put("error", "添加出错");
            log.error(e);
        }
        return result;
    }


    @Override
    public boolean deleteListRegUser(List<String> listStr) {
        return false;
    }
}
