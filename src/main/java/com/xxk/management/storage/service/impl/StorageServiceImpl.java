package com.xxk.management.storage.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.core.util.build_ident.IdentUtil;
import com.xxk.management.device.entity.Device;
import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.stock.service.StockService;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.service.DeliveryService;
import com.xxk.management.storage.service.StorageService;
import com.xxk.management.storage.dao.StorageDao;
import com.xxk.management.storage.entity.Storage;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class StorageServiceImpl implements StorageService {

    private static Logger log = Logger.getLogger(StorageServiceImpl.class);

    @Autowired
    private StorageDao dao;

    @Autowired
    private StockService stockService;

    @Autowired
    private DeliveryService deliveryService;


    @Override
    public List<Storage> listStorage(int pageStart, int pageSize) {
        return dao.listStorage((pageStart - 1) * pageSize, pageSize);
    }

    @Override
    public int countStorage() {
        return dao.countStorage();
    }

    @Override
    public List<Storage> listStorageByStock(int pageStart, int pageSize, String class_id, String entity_id, String stock_id, String officeId) {
        return dao.listStorageByStock((pageStart - 1) * pageSize, pageSize, class_id, entity_id, stock_id, officeId);
    }

    //入库记录
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> addStorage(Stock stock, Storage storage) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String storageId = UUIdUtil.getUUID();

        if ("".equals(storage.getEntity_id()) || storage.getEntity_id() == null) {
            log.error("addStorage:storage.getEntity_id为空！");
            throw new Exception("addStorage:storage.getEntity_id为空！");
        }
        String in_confirmed_ident = IdentUtil.getIdentNo((int) storage.getIn_confirmed_no(), createDate);
        if ("".equals(in_confirmed_ident) || in_confirmed_ident == null) {
            log.error("addStorage:in_confirmed_ident为空！");
            throw new Exception("addStorage:in_confirmed_ident空！");
        }
        //入库
        storage.setId(storageId);
        storage.setStock_id(stock.getId());
        storage.setIn_confirmed_ident(in_confirmed_ident);
        storage.setIn_confirmed_type(stock.getStock_type());
        storage.setIn_confirmed_by(stock.getUpdateUserId());
        storage.setOut_flag("0");
        storage.setCreateDate(createDate);
        storage.setCreateUserId(stock.getUpdateUserId());
        storage.setUpdateDate(createDate);
        storage.setUpdateUserId(stock.getUpdateUserId());
        storage.setDeleteFlag("0");

        boolean storageResult = dao.addStorage(storage) == 1 ? true : false;
        ;
        if (!(storageResult)) {
            log.error("addStorage:dao.addStorage出错！");
            throw new Exception("addStorage:dao.addStorage出错！");
        } else {
            //log.info(">>>>保存成功");
            result.put("success", true);
        }

        result.put("success", true);
        return result;
    }

    //入库记录
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> addStorage(Delivery delivery, Storage storage) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String storageId = UUIdUtil.getUUID();

        if ("".equals(storage.getEntity_id()) || storage.getEntity_id() == null) {
            log.error("addStorage:storage.getEntity_id为空！");
            throw new Exception("addStorage:storage.getEntity_id为空！");
        }
        String in_confirmed_ident = IdentUtil.getIdentNo((int) storage.getIn_confirmed_no(), createDate);
        if ("".equals(in_confirmed_ident) || in_confirmed_ident == null) {
            log.error("addStorage:in_confirmed_ident为空！");
            throw new Exception("addStorage:in_confirmed_ident空！");
        }
        //入库
        storage.setId(storageId);
        storage.setStock_id(delivery.getStock_id());
        storage.setIn_confirmed_ident(in_confirmed_ident);
        storage.setIn_confirmed_type(delivery.getOut_confirmed_type());
        storage.setIn_confirmed_by(delivery.getUpdateUserId());
        storage.setIn_confirmed_origin("3");
        storage.setOut_flag("0");
        storage.setCreateDate(createDate);
        storage.setCreateUserId(delivery.getUpdateUserId());
        storage.setUpdateDate(createDate);
        storage.setUpdateUserId(delivery.getUpdateUserId());
        storage.setDeleteFlag("0");

        boolean storageResult = dao.addStorage(storage) == 1 ? true : false;
        ;
        if (!(storageResult)) {
            log.error("addStorage:dao.addStorage出错！");
            throw new Exception("addStorage:dao.addStorage出错！");
        } else {
            //log.info(">>>>保存成功");
            result.put("success", true);
        }

        result.put("success", true);
        return result;
    }

    //入库记录
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> addStorageForOfficesStorage(OfficesStorage storage) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String storageId = UUIdUtil.getUUID();

        if ("".equals(storage.getEntity_id()) || storage.getEntity_id() == null) {
            log.error("addStorageForOfficesStorage:storage.getEntity_id为空！");
            throw new Exception("addStorageForOfficesStorage:storage.getEntity_id为空！");
        }
        String in_confirmed_ident = IdentUtil.getIdentNo((int) storage.getOffices_storage_no(), storage.getCreateDate());
        if ("".equals(in_confirmed_ident) || in_confirmed_ident == null) {
            log.error("addStorageForOfficesStorage:in_confirmed_ident为空！");
            throw new Exception("addStorageForOfficesStorage:in_confirmed_ident为空！");
        }
        //入库
        storage.setId(storageId);
        storage.setOffices_storage_ident(in_confirmed_ident);
        storage.setEntity_entry_status("0");
        storage.setDeleteFlag("0");

        boolean storageResult = dao.addStorageForOfficesStorage(storage) == 1 ? true : false;
        ;
        if (!(storageResult)) {
            log.error("addStorageForOfficesStorage:dao.addStorageForOfficesStorage出错！");
            throw new Exception("addStorageForOfficesStorage:dao.addStorageForOfficesStorage出错！");
        } else {
            //log.info(">>>>保存成功");
            result.put("success", true);
        }

        result.put("success", true);
        return result;
    }

    @Override
    public boolean deleteListRegUser(List<String> listStr) {
        return false;
    }

    @Override
    public List<String> getStorageIdByIdent(String ident) {
        return dao.getStorageIdByIdent(ident);
    }
}
