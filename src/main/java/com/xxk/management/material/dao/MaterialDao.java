package com.xxk.management.material.dao;

import com.xxk.management.material.entity.Material;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface MaterialDao {

    public List<Material> listMaterial(int pageStart, int pageSize);

    public List<Material> listMaterialById(List<String> listMatId);

    public int addMaterial(Material material);

    public List<Map<String, Object>> getMaterialSelect();

    public int plusMaterialNumber(int dev_no, String deviceId);   //更新设备数量 （增加）

    public int minusMaterialNumber(int dev_no, String deviceId);   //更新设备数量 （减少）

}
