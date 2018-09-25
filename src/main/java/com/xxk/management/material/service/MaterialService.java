package com.xxk.management.material.service;

import com.xxk.management.material.entity.Material;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface MaterialService {

    public List<Material> listMaterial(int pageStart, int pageSize);

    public List<Material> listMaterialById(List<String> listMatId);

    public boolean addMaterial(Material material);

    public List<Map<String, Object>> getMaterialNumber(String materialId);

    public List<Map<String, Object>> getMaterialSelect(String tab);

    public List<Map<String, Object>> getMaterialByIdent(List<String> listMatId);

    public List<Map<String, Object>> getDeviceIdent();  //获取设备编号

    public boolean plusMaterialNumber(int dev_no, String materialId);

    public boolean minusMaterialNumber(int dev_no, String materialId);

}
