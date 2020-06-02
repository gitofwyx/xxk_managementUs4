package com.xxk.management.office.depository.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.device.dao.DeviceDao;
import com.xxk.management.device.entity.Device;
import com.xxk.management.office.depository.dao.DepositoryDao;
import com.xxk.management.office.depository.entity.Depository;
import com.xxk.management.office.depository.service.DepositoryService;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.office.storage.service.OfficesStorageService;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.entity.Storage;
import com.xxk.management.storage.service.DeliveryService;
import com.xxk.management.storage.service.StorageService;
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
public class DepositoryServiceImpl implements DepositoryService {

    private static Logger log = Logger.getLogger(DepositoryService.class);

    @Autowired
    private DepositoryDao dao;

    @Autowired
    private DeviceDao devdao;

    @Autowired
    private OfficesStorageService storageService;

    @Autowired
    private DeliveryService deliveryService;

    @Override
    public List<Depository> listDepository(int pageStart, int pageSize, String class_id, String entity_id, String stock_office_id, int search_type) {
        return dao.listDepository((pageStart - 1) * pageSize,pageSize,class_id,entity_id,stock_office_id,search_type);
    }

    @Override
    public List<Depository> listDepositoryByEntityId(String entity_id, String office_id) {
        return dao.listDepositoryByEntityId(entity_id, office_id);
    }

    //新增库存
    // 2019年8月12日 13:44:05更新
    @Override
    public Map<String, Object> addDepositoryWithStorage(Depository depository, OfficesStorage storage) {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String stockId = UUIdUtil.getUUID();
        try {
            if ("".equals(depository.getEntity_id()) || depository.getEntity_id() == null) {
                log.info("出错！无法获取设备ID");
                result.put("hasError", true);
                result.put("error", "添加出错");
                return result;
            }
            Device device = devdao.getDeviceById(depository.getEntity_id());
            if (device != null) {
                depository.setId(stockId);
                depository.setDepository_ident(device.getDev_ident());
                depository.setEntity_id(device.getId());
                depository.setDepository_no(storage.getOffices_storage_no());
                depository.setDepository_total(storage.getOffices_storage_total());
                depository.setDepository_flag("1");
                depository.setCreateDate(createDate);
                depository.setCreateUserId("admin");
                depository.setUpdateDate(createDate);
                depository.setUpdateUserId("admin");
                depository.setDeleteFlag("0");
            }
            boolean stockResult = dao.addDepository(depository) == 1 ? true : false;
            if (!(stockResult)) {
                log.error("stockResult:" + stockResult);
                result.put("hasError", true);
                result.put("error", "添加出错");
            } else {
                storage.setEntity_id(device.getId());
                storage.setOffices_storage_total((int) depository.getDepository_total());
                result = storageService.addOfficesStorage(depository, storage,"1");
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

    //入库操作
    // 2019年8月19日 13:44:05更新
    @Override
    public Map<String, Object> updateDepositoryWithStorage(Depository depository, OfficesStorage storage) {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        try {
            if ("".equals(depository.getEntity_id()) || depository.getEntity_id() == null) {
                log.info("出错！无法获取设备ID");
                result.put("hasError", true);
                result.put("error", "添加出错！无法获取设备ID");
                return result;
            }
            depository.setDepository_total(storage.getOffices_storage_total());
            depository.setUpdateDate(createDate);
            depository.setUpdateUserId("admin");

            boolean stockResult = dao.plusDepositoryNo(depository) == 1 ? true : false;
            if (!(stockResult)) {
                log.error("stockResult:" + stockResult);
                result.put("hasError", true);
                result.put("error", "添加出错");
            } else {
                //入库记录
                storage.setEntity_id(depository.getEntity_id());
                result = storageService.addOfficesStorage(depository, storage,"1");
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

    //出库操作
    // 2019年8月19日 13:43:42更新
    @Override
    public Map<String, Object> updateDepositoryWithDelivery(Depository depository, Delivery delivery) {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        try {
            if ("".equals(depository.getEntity_id()) || depository.getEntity_id() == null) {
                log.info("出错！无法获取设备ID");
                result.put("hasError", true);
                result.put("error", "添加出错！无法获取设备ID");
                return result;
            }
            depository.setDepository_total(delivery.getOut_confirmed_total());
            depository.setUpdateDate(createDate);
            depository.setUpdateUserId("admin");

            boolean stockResult = dao.reduceStockNo(depository) == 1 ? true : false;
            if (!(stockResult)) {
                log.error("stockResult:" + stockResult);
                result.put("hasError", true);
                result.put("error", "添加出错");
            } else {
                //出库更新
                delivery.setEntity_id(depository.getEntity_id());
                //result = deliveryService.addDelivery(depository, delivery,"1");
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

    //更新操作
    @Override
    public Map<String, Object> updateDepository(Depository depository) {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String stockId = UUIdUtil.getUUID();
        try {
            if ("".equals(depository.getEntity_id()) || depository.getEntity_id() == null) {
                log.info("出错！无法获取设备ID");
                result.put("hasError", true);
                result.put("error", "添加出错");
                return result;
            }
            boolean depositoryResult = dao.updateDepository(depository, depository.getEntity_id()) == 1 ? true : false;
            if (!(depositoryResult)) {
                log.error("stockResult:" + depositoryResult);
                result.put("hasError", true);
                result.put("error", "添加出错");
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

    @Override
    public List<String> getDepositoryIdByIdent(String ident) {
        return dao.getDepositoryIdByIdent(ident);
    }

    @Override
    public Depository getDepositoryById(String id) {
        return dao.getDepositoryById(id);
    }
}
