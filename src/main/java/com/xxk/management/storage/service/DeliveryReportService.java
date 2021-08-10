package com.xxk.management.storage.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface DeliveryReportService {

    public List<Map<String, Object>> getDeliveryReportSingleParam(String startDate, String eDate);


}
