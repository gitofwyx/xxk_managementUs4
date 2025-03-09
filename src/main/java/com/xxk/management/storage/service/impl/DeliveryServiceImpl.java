package com.xxk.management.storage.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.core.util.build_ident.IdentUtil;
import com.xxk.management.office.depository.entity.Depository;
import com.xxk.management.office.devices.service.StockDevicesService;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.stock.service.StockService;
import com.xxk.management.storage.dao.DeliveryDao;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.entity.Storage;
import com.xxk.management.storage.service.DeliveryReportService;
import com.xxk.management.storage.service.DeliveryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class DeliveryServiceImpl implements DeliveryService {


    private static Logger log = LogManager.getLogger();

    @Autowired
    private DeliveryDao dao;

    @Autowired
    private StockService stockService;

    @Autowired
    private StockDevicesService stockDevicesService;

    @Autowired
    private DeliveryReportService deliveryReportService;

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
    public List<Delivery> listDeliveryByHandler(String user_id) {
        return dao.listDeliveryByHandler(user_id);
    }

    @Override
    public List<Delivery> listDeliveryByOffice(int pageStart, int pageSize, String stock_id) {
        return dao.listDeliveryByOffice((pageStart - 1) * pageSize, pageSize, stock_id);
    }

    @Override
    public List<Delivery> listDeliveryForTransfer(int pageStart, int pageSize, String stock_id) {
        return dao.listDeliveryForTransfer((pageStart - 1) * pageSize, pageSize, stock_id);
    }

    @Override
    public List<Delivery> listDeliveryUNIONStorageByOffice(int pageStart, int pageSize, String stock_id) {
        return dao.listDeliveryUNIONStorageByOffice((pageStart - 1) * pageSize, pageSize, stock_id);
    }

    @Override
    public Delivery getDeliveryById(String id) {
        return dao.getDeliveryById(id);
    }

    public List<Delivery> getDeliveryUNIONStorageByEntityId(String entity_id){
        String[] deliveryStatus = {"0","1","2"};//入科状态(-:库间转移0：配置待入科1：待入科；2：部分待入科；3：已入科；4：部分已入科；5：反出库)
        String[] deliveryGenre = {"0"};//（0：配置1.入科2.部署3.撤出4.回收5.调用6.借用）
        String[] offStorageStatus = {"0","1","2","3","4"};//入科状态（0：配置待入科1：待入科；2：部分待入科；3：已入科；4：部分已入科；5：已出科）
        String[] offStorageGenre = {"0","1","2","3","5","6"};//（0：配置1.入科2.部署3.撤出4.回收5.调用6.借用）
        return dao.getDeliveryUNIONStorageByEntityId(entity_id,deliveryStatus,deliveryGenre,offStorageStatus,offStorageGenre);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> addDelivery(Stock stock, Delivery delivery, String genre, String status) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String deliveryId = UUIdUtil.getUUID();

        if ("".equals(delivery.getEntity_id()) || delivery.getEntity_id() == null) {
            log.error("addDelivery:delivery.getEntity_id为空！");
            throw new Exception("addDelivery:delivery.getEntity_id为空！");
        }
        //库存编号生成
        String out_confirmed_ident = IdentUtil.getIdentNo((int) stock.getStock_no(), createDate);
        if ("".equals(out_confirmed_ident) || out_confirmed_ident == null) {
            log.error("addDelivery:out_confirmed_ident为空！");
            throw new Exception("addDelivery:out_confirmed_ident为空！");
        }
        //
        delivery.setId(deliveryId);
        delivery.setStock_id(stock.getId());
        delivery.setOut_confirmed_ident(out_confirmed_ident);
        delivery.setOut_confirmed_type(stock.getStock_type());
        delivery.setOut_confirmed_genre(genre);
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
            log.error("addDelivery:dao.addDelivery出错！");
            throw new Exception("addDelivery:dao.addDelivery出错！");
        } else {
            //log.info(">>>>保存成功");
            result.put("success", true);
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> addDelivery(Delivery delivery, String genre, String status) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();

        if ("".equals(delivery.getEntity_id()) || delivery.getEntity_id() == null) {
            log.error("addDelivery:delivery.getEntity_id为空！");
            throw new Exception("addDelivery:delivery.getEntity_id为空！");
        }

        //c库
        delivery.setOut_confirmed_genre(genre);
        delivery.setEntity_entry_status(status);
        delivery.setDeleteFlag("0");

        boolean storageResult = dao.addDelivery(delivery) == 1 ? true : false;
        ;
        if (!(storageResult)) {
            log.error("addDelivery:dao.addDelivery出错！");
            throw new Exception("addDelivery:dao.addDelivery出错！");
        } else {
            //log.info(">>>>保存成功");
            result.put("success", true);
        }

        result.put("success", true);
        return result;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> backwardDelivery(Storage storage, Delivery delivery, String stock_no, String out_total) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String deliveryId = UUIdUtil.getUUID();
       
        if ("".equals(delivery.getEntity_id()) || delivery.getEntity_id() == null) {
            log.error("backwardDelivery:delivery.getEntity_id为空！");
            throw new Exception("backwardDelivery:delivery.getEntity_id为空！");
        }
        //原纪录作废
        boolean Result = dao.updateDeliveryStatus(delivery.getId(), "5",delivery.getUpdateUserId(),createDate) == 1 ? true : false;
        if (!Result) {
            log.error("backwardDelivery:dao.updateDeliveryStatus出错！");
            throw new Exception("出错！查看是否已经入科！");
        }
        //更新库存记录
        result = stockService.updateStockForBackward(storage, delivery, stock_no);
        if (result.get("hasError") instanceof Boolean && (Boolean) result.get("hasError")) {
            log.error("backwardDelivery:stockService.updateStockForBackward出错！");
            throw new Exception("backwardDelivery:stockService.updateStockForBackward出错！");
        } else if (delivery.getOut_confirmed_no() != 0 && delivery.getOut_confirmed_total() != 0 && storage.getIn_confirmed_total() < Double.valueOf(out_total).intValue()) {
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
                log.error("backwardDelivery:dao.addDelivery出错！");
                throw new Exception("backwardDelivery:dao.addDelivery出错！");
            }
        }
        if (!"".equals(delivery.getStock_entity_id()) && delivery.getStock_entity_id() != null) {
            Result = stockDevicesService.updateDevicesSetStatus(delivery.getStock_entity_id(), "0","", delivery.getUpdateUserId(), delivery.getUpdateDate());
            if (!Result) {
                log.error("backwardDelivery:stockDevicesService.updateDevicesSetStatus出错！");
                throw new Exception("backwardDelivery:stockDevicesService.updateDevicesSetStatus出错！");
            }
        }


        result.put("success", true);
        return result;
    }


    @Override
    public boolean updateDeliveryStatus(String id, String status,String user_id,String updateDate) {
        return dao.updateDeliveryStatus(id, status,user_id,updateDate) == 1 ? true : false;
    }

    @Override
    public Map<String, Object> deliveryReport(String startDate, String endDate) {

        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<Map<String, Object>> listDelivery = deliveryReportService.getDeliveryReportSingleParam(startDate, endDate);
            resultMap.put("data", listDelivery);
        } catch (Exception e) {
            resultMap.put("hasError", true);
            resultMap.put("error", "查询出错");
            log.error(e);
        }
        return resultMap;
    }
}
