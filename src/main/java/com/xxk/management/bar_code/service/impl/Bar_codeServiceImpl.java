package com.xxk.management.bar_code.service.impl;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.bar_code.dao.Bar_codeDao;
import com.xxk.management.bar_code.entity.Bar_code;
import com.xxk.management.bar_code.service.Bar_codeService;
import com.xxk.management.device.entity.Device;
import com.xxk.management.device.service.DeviceService;
import com.xxk.management.registration.entity.Registration;
import com.xxk.management.stock.entity.Stock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Bar_codeServiceImpl implements Bar_codeService {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private Bar_codeDao dao;

    @Autowired
    private DeviceService deviceService;


    @Override
    public List<Bar_code> listDevice(int pageStart, int pageSize) {
        return dao.listDevice((pageStart - 1) * pageSize, pageSize);
    }

    @Override
    public int countDevice() {
        return dao.countDevice();
    }

    @Override
    public Bar_code getBar_codeByBar_code_ident(String bar_code_ident) throws Exception {
        return dao.getBar_codeByBar_code_ident(bar_code_ident);
    }

    @Override
    public List<Bar_code> lisBar_codeByINSTRBar_code_ident(String bar_code_ident) {
        return dao.lisBar_codeByINSTRBar_code_ident(bar_code_ident);
    }

    //入库操作
    // 2019年8月19日 13:44:05更新
    @Override
    public Map<String, Object> addBar_code(Bar_code bar_code) {
        Map<String, Object> result = new HashMap<>();
        String bar_codeId = UUIdUtil.getUUID();
        String createDate = DateUtil.getFullTime();
        try {
            //
            bar_code.setBar_code_ident(bar_code.getBar_code()+bar_code.getBar_code_type()+bar_code.getBar_code_genre());

            bar_code.setId(bar_codeId);
            bar_code.setBar_code_date(createDate);
            bar_code.setCreateUserId(bar_code.getBar_code_by());
            bar_code.setCreateDate(createDate);
            bar_code.setUpdateUserId(bar_code.getBar_code_by());
            bar_code.setUpdateDate(createDate);
            bar_code.setDeleteFlag("0");
            Boolean bar_codeResult = dao.addBar_code(bar_code) == 1 ? true : false;
            if (!(bar_codeResult)) {
                log.error("stockResult:" + bar_codeResult);
                result.put("hasError", true);
                result.put("error", "添加出错");
                return result;

            }
        } catch (DuplicateKeyException e) {
            result.put("hasError", true);
            result.put("error", "重复值异常，可能编号值重复");
            log.error(e);
        } catch (Exception e) {
            result.put("hasError", true);
            result.put("error", "添加出错");
            log.error(e);
        }
        return result;
    }

}
