package com.xxk.management.bar_code.service;

import com.xxk.management.bar_code.entity.Bar_code;

import java.util.List;
import java.util.Map;

public interface Bar_codeService {

    public List<Bar_code> listDevice(int pageStart, int pageSize);

    public int countDevice();

    public Bar_code getBar_codeByBar_code_ident(String bar_code_ident) throws Exception;

    public Map<String, Object> addBar_code(Bar_code bar_code) ;

}
