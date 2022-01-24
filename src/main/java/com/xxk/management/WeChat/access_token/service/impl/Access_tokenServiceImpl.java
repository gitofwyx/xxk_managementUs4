package com.xxk.management.WeChat.access_token.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xxk.core.util.DateUtil;
import com.xxk.management.WeChat.WeChatRobot.service.WeChatRobotService;
import com.xxk.management.WeChat.access_token.constants.Access_tokenConstants;
import com.xxk.management.WeChat.access_token.entity.AccessToken;
import com.xxk.management.WeChat.access_token.service.Access_tokenService;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class Access_tokenServiceImpl implements Access_tokenService {

    private static Logger log = LogManager.getLogger();

    public static AccessToken accessToken = null;

    public static String td=null;


    @Override
    public AccessToken makeAccess_token() {


        try {
            long diff=0;
            String nd = DateUtil.getFullTime();
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(!"".equals(td)&&td!=null){
                diff =DateUtil.calLastedTime(sd.parse(td),sd.parse(nd));
            }
            if(accessToken!=null){
                if(accessToken.getExpires_in()>diff)return accessToken;
            }
            HttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(Access_tokenConstants.getAccess_token_url());
            httpget.addHeader("Content-Type", "application/json; charset=utf-8");
            HttpResponse response = null;
            response = httpclient.execute(httpget);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                JSONObject jsonObject = JSONObject.parseObject(result);
                log.info(result);
                if(jsonObject.getInteger("errcode")!=null)return null;
                String access_token = jsonObject.getString("access_token");
                int expires_in = jsonObject.getInteger("expires_in");
                accessToken = new AccessToken(access_token,expires_in);
                td=nd;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return accessToken;
    }
}
