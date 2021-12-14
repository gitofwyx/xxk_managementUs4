package com.xxk.management.system.service;

import com.xxk.management.stock.entity.Stock;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.entity.Storage;
import com.xxk.management.user.entity.RegUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface SystemService {

    public List<RegUser> readRegUserXLS(MultipartFile file) throws IOException;

}
