package com.xxk.management.storage.dao;

import com.xxk.management.storage.entity.Storage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface StorageDao {

    public List<Storage> listStorage(int pageStart, int pageSize);

    public int addStorage(Storage storage);

    public int deleteListRegUser(List<String> listStr);

    public List<String> getStorageIdByIdent(String ident);

}
