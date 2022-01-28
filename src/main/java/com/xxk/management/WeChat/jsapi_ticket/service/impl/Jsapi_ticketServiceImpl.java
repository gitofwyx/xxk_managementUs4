package com.xxk.management.WeChat.jsapi_ticket.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xxk.core.util.DateUtil;
import com.xxk.management.WeChat.access_token.entity.AccessToken;
import com.xxk.management.WeChat.access_token.service.Access_tokenService;
import com.xxk.management.WeChat.jsapi_ticket.entity.Jsapi_ticket;
import com.xxk.management.WeChat.jsapi_ticket.service.Jsapi_ticketService;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class Jsapi_ticketServiceImpl implements Jsapi_ticketService {

    private static Logger log = LogManager.getLogger();

    public static Jsapi_ticket jsapi_ticket = null;

    public static String td=null;

    private static String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    @Autowired
    private Access_tokenService access_tokenService;

    @Override
    public Jsapi_ticket makeJsapi_ticket() {


        try {
            long diff=0;
            String nd = DateUtil.getFullTime();
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(!"".equals(td)&&td!=null){
                diff =DateUtil.calLastedTime(sd.parse(td),sd.parse(nd));
            }
            if(jsapi_ticket!=null){
                if(jsapi_ticket.getExpires_in()>diff)return jsapi_ticket;
            }
            AccessToken accessToken=access_tokenService.makeAccess_token();
            if(accessToken==null){
                return null;
            }
            String jsApi_url=JSAPI_TICKET_URL.replace("ACCESS_TOKEN",accessToken.getAccess_token());
            HttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(jsApi_url);
            httpget.addHeader("Content-Type", "application/json; charset=utf-8");
            HttpResponse response = null;
            response = httpclient.execute(httpget);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                JSONObject jsonObject = JSONObject.parseObject(result);
                log.info(result);
                if(jsonObject.getInteger("errcode")!=0)return null;
                String ticket = jsonObject.getString("ticket");
                int expires_in = jsonObject.getInteger("expires_in");
                jsapi_ticket = new Jsapi_ticket(ticket,expires_in);
                td=nd;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsapi_ticket;
    }
}
