package com.xxk.management.stock.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.device.dao.DeviceDao;
import com.xxk.management.device.entity.Device;
import com.xxk.management.stock.dao.StockDao;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.storage.entity.Storage;
import com.xxk.management.stock.service.StockService;
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
public class StockServiceImpl implements StockService {

    private static Logger log = Logger.getLogger(StockService.class);

    @Autowired
    private StockDao dao;

    @Autowired
    private DeviceDao devdao;

    @Override
    public List<Stock> listStock(int pageStart, int pageSize) {
        return dao.listStock((pageStart - 1) * pageSize, pageSize);
    }

    @Override
    public List<Stock> listStockByEntityId(String entityId) {
        return dao.listStockByEntityId(entityId);
    }

    //新增库存记录
    @Override
    public Map<String, Object> addStock(Stock stock) {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String stockId = UUIdUtil.getUUID();
        try {
            if ("".equals(stock.getEntity_id()) || stock.getEntity_id() == null) {
                log.info("出错！无法获取设备ID");
                result.put("error", false);
                return result;
            }
            Device device = devdao.getDeviceById(stock.getEntity_id());
            if (device != null) {
                stock.setId(stockId);
                stock.setStock_ident(device.getDev_ident());
                stock.setEntity_id(device.getId());
                stock.setCreateDate(createDate);
                stock.setCreateUserId("admin");
                stock.setUpdateDate(createDate);
                stock.setUpdateUserId("admin");
                stock.setDeleteFlag("0");
            }
            //入库记录
            boolean stockResult = dao.addStock(stock) == 1 ? true : false;
            if (!(stockResult)) {
                log.error("stockResult:" + stockResult);
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

    //更新操作
    @Override
    public Map<String, Object> updateStock(Stock stock) {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String stockId = UUIdUtil.getUUID();
        try {
            if ("".equals(stock.getEntity_id()) || stock.getEntity_id() == null) {
                log.info("出错！无法获取设备ID");
                result.put("hasError", true);
                result.put("error", "添加出错");
                return result;
            }
            boolean stockResult = dao.updateStock(stock, stock.getEntity_id()) == 1 ? true : false;
            if (!(stockResult)) {
                log.error("stockResult:" + stockResult);
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
    public List<String> getStorageIdByIdent(String ident) {
        return dao.getStockIdByIdent(ident);
    }
}
