package com.xxk.management.office.storage.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.core.util.build_ident.IdentUtil;
import com.xxk.management.office.depository.entity.Depository;
import com.xxk.management.office.devices.entity.Devices;
import com.xxk.management.office.storage.dao.OfficesStorageDao;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.office.storage.service.OfficesStorageService;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.storage.dao.DeliveryDao;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.service.DeliveryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class OfficesStorageServiceImpl implements OfficesStorageService {


    private static Logger log = LogManager.getLogger();

    @Autowired
    private OfficesStorageDao dao;

    @Override
    public List<OfficesStorage> listOfficesStorage(int pageStart, int pageSize) {
        return dao.listOfficesStorage((pageStart - 1) * pageSize, pageSize);
    }

    @Override
    public List<OfficesStorage> listOfficesStorageByStock(int pageStart, int pageSize, String stock_id) {
        return dao.listOfficesStorageByStock((pageStart - 1) * pageSize, pageSize, stock_id);
    }

    @Override
    public List<OfficesStorage> listOfficesStorageByOffice(int pageStart, int pageSize, String stock_id) {
        return dao.listOfficesStorageByOffice((pageStart - 1) * pageSize, pageSize, stock_id);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> addOfficesStorage(Depository depository, OfficesStorage officesStorage, String status) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();

        if ("".equals(officesStorage.getEntity_id()) || officesStorage.getEntity_id() == null) {
            log.error("addOfficesStorage:officesStorage.getEntity_id为空！");
            throw new Exception("addOfficesStorage:officesStorage.getEntity_id为空！");
        }
        if ("".equals(officesStorage.getId()) || officesStorage.getId() == null) {
            log.error("addOfficesStorage:officesStorage.getId为空！");
            throw new Exception("addOfficesStorage:officesStorage.getId为空！");
        }
        //库存编号生成
        String out_confirmed_ident = IdentUtil.getIdentNo((int) depository.getDepository_no(), createDate);
        if ("".equals(out_confirmed_ident) || out_confirmed_ident == null) {
            log.error("addOfficesStorage:out_confirmed_ident为空！");
            throw new Exception("addOfficesStorage:out_confirmed_ident为空！");
        }
        //入库
        officesStorage.setOffices_storage_ident(out_confirmed_ident);
        officesStorage.setOffices_storage_by(depository.getUpdateUserId());
        officesStorage.setOffices_storage_officeId(depository.getDepository_officeId());
        officesStorage.setOffices_storage_date(createDate);
        officesStorage.setEntity_entry_status(status);
        officesStorage.setCreateDate(createDate);
        officesStorage.setCreateUserId(depository.getUpdateUserId());
        officesStorage.setUpdateDate(createDate);
        officesStorage.setUpdateUserId(depository.getUpdateUserId());
        officesStorage.setDeleteFlag("0");

        boolean storageResult = dao.addOfficesStorage(officesStorage) == 1 ? true : false;
        if (!(storageResult)) {
            log.error("addOfficesStorage:dao.addOfficesStorage出错！");
            throw new Exception("addOfficesStorage:dao.addOfficesStorage出错！");
        } else {
            //log.info(">>>>保存成功");
            result.put("success", true);
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Map<String, Object> addOfficesStorage(Devices devices, OfficesStorage officesStorage) throws Exception, RuntimeException {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        
        if ("".equals(officesStorage.getEntity_id()) || officesStorage.getEntity_id() == null) {
            log.error("addOfficesStorage:officesStorage.getEntity_id为空！");
            throw new Exception("addOfficesStorage:officesStorage.getEntity_id为空！");
        }
        //库存编号生成
        String out_confirmed_ident = "NO";
        if ("".equals(out_confirmed_ident) || out_confirmed_ident == null) {
            log.error("addOfficesStorage:out_confirmed_ident为空！");
            throw new Exception("addOfficesStorage:out_confirmed_ident为空！");
        }
        //
        officesStorage.setOffices_storage_ident(out_confirmed_ident);
        officesStorage.setOffices_storage_by(devices.getUpdateUserId());
        officesStorage.setOriginal_storage_officeId(devices.getInventory_office_id());
        officesStorage.setOffices_storage_officeId(devices.getLocation_office_id());
        officesStorage.setOffices_storage_date(devices.getDevice_deployment_date());
        officesStorage.setOffices_storage_total(1.0);
        officesStorage.setOffices_storage_total_unit(officesStorage.getOffices_storage_unit());//单出库或出库总量为1时为了简化开发流程用出库数量单位作为出库总量单位
        officesStorage.setCreateDate(createDate);
        officesStorage.setCreateUserId(devices.getUpdateUserId());
        officesStorage.setUpdateDate(createDate);
        officesStorage.setUpdateUserId(devices.getUpdateUserId());
        officesStorage.setDeleteFlag("0");

        boolean storageResult = dao.addOfficesStorage(officesStorage) == 1 ? true : false;
        ;
        if (!(storageResult)) {
            log.error("addOfficesStorage:dao.addOfficesStorage出错！");
            throw new Exception("addOfficesStorage:dao.addOfficesStorage出错！");
        } else {
            //log.info(">>>>保存成功");
            result.put("success", true);
        }

        return result;
    }

    @Override
    public boolean updateOfficesStorageStatus(String id, String status) {
        return dao.updateOfficesStorageStatus(id, status) == 1 ? true : false;
    }

    @Override
    public boolean updateOfficesStorageGenre_and_Status(String id,String genre,String status,String userId,String Date) {
        return dao.updateOfficesStorageGenre_and_Status(id, genre,status,userId,Date) == 1 ? true : false;
    }


    @Override
    public boolean deleteListRegUser(List<String> listStr) {
        return false;
    }
}
