package com.xxk.management.storage.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.core.util.build_ident.IdentUtil;
import com.xxk.management.office.devices.service.StockDevicesService;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.stock.service.StockService;
import com.xxk.management.storage.dao.DeliveryDao;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.entity.Storage;
import com.xxk.management.storage.service.DeliveryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Autowired
    private StockService stockService;

    @Autowired
    private StockDevicesService stockDevicesService;

    @Override
    public List<Delivery> listDelivery(int pageStart, int pageSize) {
        return dao.listDelivery((pageStart - 1) * pageSize, pageSize);
    }

    @Override
    public int countDelivery() {
        return dao.countDelivery();
    }

    @Override
    public List<Delivery> listDeliveryByStock(int pageStart, int pageSize, String class_id, String entity_id, String stock_id, String officeId) {
        return dao.listDeliveryByStock((pageStart - 1) * pageSize, pageSize, class_id, entity_id, stock_id, officeId);
    }

    @Override
    public List<Delivery> listDeliveryByOffice(int pageStart, int pageSize, String stock_id) {
        return dao.listDeliveryByOffice((pageStart - 1) * pageSize, pageSize, stock_id);
    }

    @Override
    public List<Delivery> listDeliveryUNIONStorageByOffice(int pageStart, int pageSize, String stock_id) {
        return dao.listDeliveryUNIONStorageByOffice((pageStart - 1) * pageSize, pageSize, stock_id);
    }

    @Override
    public Map<String, Object> addDelivery(Stock stock, Delivery delivery, String status) {
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
            String out_confirmed_ident = IdentUtil.getIdentNo((int) stock.getStock_no(), createDate);
            if ("".equals(out_confirmed_ident) || out_confirmed_ident == null) {
                result.put("hasError", true);
                result.put("error", "添加出错,无法生成入库编号！");
                return result;
            }
            //
            delivery.setId(deliveryId);
            delivery.setStock_id(stock.getId());
            delivery.setOut_confirmed_ident(out_confirmed_ident);
            delivery.setOut_confirmed_type(stock.getStock_type());
            delivery.setOut_confirmed_genre("1");
            delivery.setOut_confirmed_by(stock.getUpdateUserId());
            delivery.setOut_confirmed_unit(stock.getStock_unit());
            delivery.setOut_confirmed_proportion(stock.getStock_proportion());
            delivery.setEntity_entry_status(status);
            delivery.setCreateDate(createDate);
            delivery.setCreateUserId(stock.getUpdateUserId());
            delivery.setUpdateDate(createDate);
            delivery.setUpdateUserId(stock.getUpdateUserId());
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
    public Map<String, Object> addDelivery(Delivery delivery, String status) {
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

            //c库
            delivery.setId(deliveryId);
            delivery.setOut_confirmed_genre("1");
            delivery.setEntity_entry_status(status);
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
    public Map<String, Object> backwardDelivery(Storage storage, Delivery delivery, String stock_no,String out_total) {
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
            //原纪录作废
            boolean Result = dao.updateDeliveryStatus(delivery.getId(), "5") == 1 ? true : false;
            if (!Result) {
                log.error("backwardDelivery:updateDeliveryStatus->error！");
                result.put("hasError", true);
                result.put("error", "添加出错");
                return result;
            }
            //更新库存记录
            result = stockService.updateStockForBackward(storage, delivery, stock_no);
            if ("true".equals(result.get("hasError"))) {
                log.error("backwardDelivery:updateStockForBackward->error！");
                return result;
            } else if (delivery.getOut_confirmed_no() != 0 && delivery.getOut_confirmed_total() != 0&&storage.getIn_confirmed_total()<Integer.parseInt(out_total)) {
                //新增出库记录
                delivery.setId(deliveryId);
                delivery.setOut_confirmed_genre("1");
                delivery.setEntity_entry_status("2");
                delivery.setDeleteFlag("0");
                delivery.setCreateDate(createDate);
                delivery.setCreateUserId(delivery.getUpdateUserId());
                delivery.setUpdateDate(createDate);
                delivery.setDeleteFlag("0");

                boolean storageResult = dao.addDelivery(delivery) == 1 ? true : false;
                if (!(storageResult)) {
                    log.error("addstorage:" + storageResult);
                    result.put("hasError", true);
                    result.put("error", "添加出错");
                    return result;
                }
            }
            if (!"".equals(delivery.getStock_entity_id()) && delivery.getStock_entity_id() != null) {
                Result = stockDevicesService.updateDevicesSetStatus(delivery.getStock_entity_id(), "0", delivery.getUpdateUserId(), delivery.getUpdateDate());
                if (!Result) {
                    log.error("backwardDelivery:updateDeliveryStatus->error！");
                    result.put("hasError", true);
                    result.put("error", "添加出错");
                    return result;
                }
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
        result.put("success", true);
        return result;
    }


    @Override
    public boolean updateDeliveryStatus(String id, String status) {
        return dao.updateDeliveryStatus(id, status) == 1 ? true : false;
    }
}
