package com.xxk.management.bar_code.service;

import com.xxk.management.bar_code.entity.Bar_code;

import java.util.List;
import java.util.Map;

public interface Bar_codeService {

    public List<Bar_code> listDevice(int pageStart, int pageSize);

    public int countDevice();

    public Map<String, Object> updateBar_codeForDevice(Bar_code bar_code) ;

}
