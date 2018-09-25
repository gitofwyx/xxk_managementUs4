package com.xxk.management.storage.service.impl;
import com.xxk.management.storage.service.StorageService;
import com.xxk.management.storage.dao.StorageDao;
import com.xxk.management.storage.entity.Storage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class StorageServiceImpl implements StorageService {

    private static Logger log = Logger.getLogger(StorageServiceImpl.class);

    @Autowired
    private StorageDao dao;


    @Override
    public List<Storage> listStorage(int pageStart, int pageSize) {
        return dao.listStorage((pageStart-1)*pageSize, pageSize);
    }

    @Override
    public boolean addStorage(Storage storage) {
        return dao.addStorage(storage)==1?true:false;
    }

    @Override
    public boolean deleteListRegUser(List<String> listStr) {
        return false;
    }

    @Override
    public List<String> getStorageIdByIdent(String ident) {
        return dao.getStorageIdByIdent(ident);
    }
}
