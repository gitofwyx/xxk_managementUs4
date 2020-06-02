package com.xxk.management.office.depository.dao;

import com.xxk.management.office.depository.entity.Depository;
import com.xxk.management.stock.entity.Stock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface DepositoryDao {

    public List<Depository> listDepository(@Param("pageStart") int pageStart, @Param("pageSize") int pageSize, @Param("class_id") String class_id,
                                 @Param("entity_id") String entity_id, @Param("stock_office_id") String stock_office_id, @Param("search_type") int search_type);

    public List<Depository> listDepositoryByEntityId(String entity_id, String office_id);

    public int addDepository(Depository depository);

    public int updateDepository(Depository depository);

    public int deleteListRegUser(List<String> listStr);

    public List<String> getDepositoryIdByIdent(String ident);

    public Depository getDepositoryById(String id);

    public int updateDepository(Depository depository, String entityId);

    public int plusDepositoryNo(Depository depository);

    public int reduceStockNo(Depository depository);

}
