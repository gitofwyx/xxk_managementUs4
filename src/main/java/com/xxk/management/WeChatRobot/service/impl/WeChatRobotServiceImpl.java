package com.xxk.management.WeChatRobot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xxk.core.util.DateUtil;
import com.xxk.core.util.UUIdUtil;
import com.xxk.management.WeChatRobot.service.WeChatRobotService;
import com.xxk.management.device.service.DeviceService;
import com.xxk.management.material.service.MaterialService;
import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.stock.dao.StockDao;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.stock.service.StockService;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.entity.Storage;
import com.xxk.management.storage.service.DeliveryService;
import com.xxk.management.storage.service.StorageService;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class WeChatRobotServiceImpl implements WeChatRobotService {

    private static Logger log = LogManager.getLogger();

    @Async
    public void chatBotSendByText(Map<String, Object> textMap) {
        //String WEBHOOK_TOKEN = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=48ff7828-9fe4-4115-86a8-a2db2d9bc91a";
        String WEBHOOK_TOKEN = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=de038aad-b19f-4912-81e5-af107608757d";
        Map<String, Object> msgMap = new HashMap<>();
        List<String> mentioned_mobile_list=new ArrayList<String>();

        try {
            /*Thread.sleep(1 *   // minutes to sleep
                    60 *   // seconds to a minute
                    1000);
            log.info("定时1min");*/
            if ("".equals(textMap.get("content"))||textMap.get("content")==null){
                return;
            }
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");
            //构建一个json格式字符串textMsg，其内容是接收方需要的参数和消息内容
            textMap.put("mentioned_mobile_list",mentioned_mobile_list);
            mentioned_mobile_list.add("@all");
            msgMap.put("msgtype","text");
            msgMap.put("text",textMap);

            JSONObject msgJson = new JSONObject(msgMap);
            StringEntity se = new StringEntity(msgJson.toJSONString(), "utf-8");
            httppost.setEntity(se);
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                log.info(result);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    @Async
    public void chatBotSendByMarkdown(Map<String, Object> markdownMap) {
        //String WEBHOOK_TOKEN = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=48ff7828-9fe4-4115-86a8-a2db2d9bc91a";
        String WEBHOOK_TOKEN = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=de038aad-b19f-4912-81e5-af107608757d";
        Map<String, Object> msgMap = new HashMap<>();

        try {
            /*Thread.sleep(1 *   // minutes to sleep
                    60 *   // seconds to a minute
                    1000);
            log.info("定时1min");*/
            if ("".equals(markdownMap.get("content"))||markdownMap.get("content")==null){
                return;
            }
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");
            //构建一个json格式字符串textMsg，其内容是接收方需要的参数和消息内容
            msgMap.put("msgtype","markdown");
            msgMap.put("markdown",markdownMap);

            JSONObject msgJson = new JSONObject(msgMap);
            StringEntity se = new StringEntity(msgJson.toJSONString(), "utf-8");
            httppost.setEntity(se);
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                log.info(result);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}
