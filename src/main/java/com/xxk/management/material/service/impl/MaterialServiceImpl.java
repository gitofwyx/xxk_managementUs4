package com.xxk.management.material.service.impl;

import com.xxk.management.material.dao.MaterialDao;
import com.xxk.management.material.entity.Material;
import com.xxk.management.material.service.MaterialService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class MaterialServiceImpl implements MaterialService {

    private static Logger log = Logger.getLogger(MaterialServiceImpl.class);

    @Autowired
    private MaterialDao dao;


    @Override
    public List<Material> listMaterial(int pageStart, int pageSize) {
        return dao.listMaterial((pageStart-1)*pageSize, pageSize);
    }

    @Override
    public List<Material> listMaterialById(List<String> listMatId) {
        return dao.listMaterialById(listMatId);
    }

    @Override
    public boolean addMaterial(Material material) {
        return dao.addMaterial(material)==1?true:false;
    }

    @Override
    public List<Map<String, Object>> getMaterialNumber(String materialId) {
        return null;
    }

    @Override
    public  List<Map<String, Object>> getMaterialSelect() {
        return dao.getMaterialSelect();
    }

    @Override
    public List<Map<String, Object>> getMaterialByIdent(List<String> listDevId) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getDeviceIdent() {
        return null;
    }


    @Override
    public boolean plusMaterialNumber(int dev_no,String deviceId) {
        return dao.plusMaterialNumber(dev_no,deviceId)==1?true:false;
    }

    @Override
    public boolean minusMaterialNumber(int dev_no, String deviceId) {
        return dao.minusMaterialNumber(dev_no,deviceId)==1?true:false;
    }

}
