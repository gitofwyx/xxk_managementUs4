package com.xxk.management.storage.service.impl;

import com.xxk.management.storage.dao.DeliveryReportDao;
import com.xxk.management.storage.service.DeliveryReportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeliveryReportServiceImpl implements DeliveryReportService {


    private static Logger log = LogManager.getLogger();

    @Autowired
    private DeliveryReportDao dao;

    @Override
    public List<Map<String, Object>> getDeliveryReportSingleParam(String startDate, String endDate) {
        return dao.getDeliveryReportSingleParam(startDate,endDate);
    }
}
