package com.xxk.management.stock.dao;

import com.xxk.management.stock.entity.Stock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface StockDao {

    public List<Stock> listStock(@Param("pageStart")int pageStart, @Param("pageSize")int pageSize, @Param("class_id")String class_id,
                                 @Param("entity_id")String entity_id,@Param("stock_office_id") String stock_office_id, @Param("search_type")int search_type);

    public int countStock(String search_type);

    public List<Stock> listStockByEntityId(String entity_id,String office_id);

    public int addStock(Stock stock);

    public int updateStock(Stock stock);

    public List<String> getStockIdByIdent(String ident);

    public Stock getStockById(String id);

    public int updateStock(Stock stock,String entityId);

    public int plusStockNo(Stock stock);

    public int reduceStockNo(Stock stock);

    public int reduceSingleStockNo(String stock_id,String userId,String date);

}
