package com.xxk.management.storage.service;

import com.xxk.management.storage.entity.Storage;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface StorageService {

    public List<Storage> listStorage(int pageStart, int pageSize);

    public boolean addStorage(Storage storage);

    public boolean deleteListRegUser(List<String> listStr);

}
