package com.xxk.management.stock.dao;

import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.storage.entity.Delivery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface StockDao {

    public List<Stock> listStock(@Param("pageStart")int pageStart, @Param("pageSize")int pageSize, @Param("class_id")String class_id,
                                 @Param("entity_id")String entity_id,@Param("stock_office_id") String stock_office_id, @Param("search_type")String search_type);

    public List<LinkedHashMap<String, Object>> getStockSuggest(String entity_id, String office_id);

    public int countStock(String search_type);

    public List<Stock> listStockByEntityId(String entity_id,String office_id);

    public List<Stock> getStocksByEntityId(String entity_id);

    public int addStock(Stock stock);

    public int addStockForOfficesStorage(OfficesStorage storage);

    public int updateStockForOfficesStorage(OfficesStorage storage);

    public int updateStock(Stock stock);

    public List<String> getStockIdByIdent(String ident);

    public Stock getStockById(String id);

    public int updateStock(Stock stock,String entityId);

    public int plusStockNo(Stock stock);

    public int plusStockNoById(String stock_id,Double stock_no,Double stock_total,String userId,String date);

    public int plusStockNoByIdOfConfigured(String stock_id,Double stock_no,Double stock_total,String userId,String date);

    public int plusStockConfiguredTotal(String stock_id,String userId,String date,String stock_version);

    public int reduceStockNo(Stock stock);

    public int reduceSingleStockNo(String stock_id,double stock_no,String userId,String date);

}
