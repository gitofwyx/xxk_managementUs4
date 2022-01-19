package com.xxk.management.registration_record.task;

import com.xxk.core.util.DateUtil;
import com.xxk.management.WeChatRobot.service.WeChatRobotService;
import com.xxk.management.registration.service.RegistrationMService;
import com.xxk.management.registration_record.dao.Registration_recordDao;
import com.xxk.management.registration_record.entity.Registration_record;
import com.xxk.management.registration_record.service.Registration_recordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/3/15.
 */
@Lazy(false)
@Configuration
@EnableScheduling
public class Registration_recordServiceTask {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private WeChatRobotService weChatRobotService;

    @Autowired
    private RegistrationMService registrationMService;

    @Scheduled(cron = "${schedule.task.test}")
    public void getRegistration_recordMakeDate() {

        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            String[] status={"0"};
            String[] e_status={"0"};
            List<Map<String, Object>> listReg = registrationMService.listRegistrationUnionMap(null,
                    null,
                    null,
                    status,
                    e_status,
                    1,10
            );
            Map<String, Object> textMap = new HashMap<>();
            String text="最近十条未处理信息：\n";
            List<String> strReg=listReg.stream().map(reg-> reg.get("reg_office") .toString()+ reg.get("reg_record_content").toString()).collect(Collectors.toList());
            int i=0;
            String textReg="";
            for (String reg:strReg) {
                i=i+1;
                textReg=textReg+i+"、"+reg+"\n";
            }
            textMap.put("content",text+textReg);
            weChatRobotService.chatBotSendByText(textMap);


        } catch (Exception e) {
            log.error(e);

        }
    }



}
