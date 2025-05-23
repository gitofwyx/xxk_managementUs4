package com.xxk.management.storage.dao;

import com.xxk.management.office.depository.entity.Depository;
import com.xxk.management.storage.entity.Delivery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface DeliveryDao {

    public List<Delivery> listDelivery(int pageStart, int pageSize);

    public int countDelivery();

    public List<Delivery> listDeliveryByStock(@Param("pageStart") int pageStart,
                                              @Param("pageSize") int pageSize,
                                              @Param("class_id") String class_id,
                                              @Param("entity_id") String entity_id,
                                              @Param("stock_id") String stock_id,
                                              @Param("officeId") String officeId);

    public List<Delivery> listDeliveryByHandler(String user_id);

    public List<Delivery> listDeliveryByOffice(int pageStart, int pageSize,String office_id);

    public List<Delivery> listDeliveryForTransfer(int pageStart, int pageSize,String office_id);

    public List<Delivery> listDeliveryUNIONStorageByOffice(int pageStart, int pageSize,String office_id);

    public Delivery getDeliveryById(String id);

    public List<Delivery> getDeliveryUNIONStorageByEntityId(@Param("entity_id") String entity_id,
                                                            @Param("deliveryStatus") String[] deliveryStatus,
                                                            @Param("deliveryGenre") String[] deliveryGenre,
                                                            @Param("offStorageStatus") String[] offStorageStatus,
                                                            @Param("offStorageGenre") String[] offStorageGenre);

    public List<Delivery> getDeliveryUNIONStorageById(@Param("id") String id,
                                                            @Param("deliveryStatus") String[] deliveryStatus,
                                                            @Param("deliveryGenre") String[] deliveryGenre,
                                                            @Param("offStorageStatus") String[] offStorageStatus,
                                                            @Param("offStorageGenre") String[] offStorageGenre);

    public int addDelivery(Delivery delivery);

    public int updateDeliveryStatus(String id,String status,String user_id,String date);


}
