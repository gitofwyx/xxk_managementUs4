package com.xxk.management.office.storage.service.impl;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.core.util.build_ident.IdentUtil;
import com.xxk.management.office.depository.entity.Depository;
import com.xxk.management.office.storage.dao.OfficesStorageDao;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.office.storage.service.OfficesStorageService;
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
public class OfficesStorageServiceImpl implements OfficesStorageService {


    private static Logger log = Logger.getLogger(OfficesStorageServiceImpl.class);

    @Autowired
    private OfficesStorageDao dao;

    @Override
    public List<OfficesStorage> listOfficesStorage(int pageStart, int pageSize) {
        return dao.listOfficesStorage((pageStart - 1) * pageSize, pageSize);
    }

    @Override
    public List<OfficesStorage> listOfficesStorageByStock(int pageStart, int pageSize,String stock_id) {
        return dao.listOfficesStorageByStock((pageStart - 1) * pageSize, pageSize,stock_id);
    }

    @Override
    public List<OfficesStorage> listOfficesStorageByOffice(int pageStart, int pageSize,String stock_id) {
        return dao.listOfficesStorageByOffice((pageStart - 1) * pageSize, pageSize,stock_id);
    }

    @Override
    public Map<String, Object> addOfficesStorage(Depository depository, OfficesStorage officesStorage, String status) {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String officesStorageId = UUIdUtil.getUUID();
        try {
            if ("".equals(officesStorage.getEntity_id()) || officesStorage.getEntity_id() == null) {
                log.error("addOfficesStorage出错！无法获取设备ID");
                result.put("hasError", true);
                result.put("error", "入科记录出错！无法获取设备ID");
                return result;
            }
            //库存编号生成
            String out_confirmed_ident = IdentUtil.getIdentNo((int)depository.getDepository_no(),createDate);
            if ("".equals(out_confirmed_ident) || out_confirmed_ident == null) {
                result.put("hasError", true);
                result.put("error", "添加出错,无法生成入库编号！");
                return result;
            }
            //入库
            officesStorage.setId(officesStorageId);
            officesStorage.setDepository_id(depository.getId());
            officesStorage.setOffices_storage_ident(out_confirmed_ident);
            officesStorage.setOffices_storage_by(depository.getDepository_by());
            officesStorage.setOffices_storage_officeId(depository.getDepository_officeId());
            officesStorage.setOffices_storage_unit(depository.getDepository_unit());
            officesStorage.setOffices_storage_proportion(depository.getDepository_proportion());
            officesStorage.setEntity_entry_status(status);
            officesStorage.setCreateDate(createDate);
            officesStorage.setCreateUserId(depository.getDepository_by());
            officesStorage.setUpdateDate(createDate);
            officesStorage.setUpdateUserId(depository.getDepository_by());
            officesStorage.setDeleteFlag("0");

            boolean storageResult = dao.addOfficesStorage(officesStorage) == 1 ? true : false;
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
