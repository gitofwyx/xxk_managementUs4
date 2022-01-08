package com.xxk.management.WeChatRobot.service;

import com.xxk.management.office.storage.entity.OfficesStorage;
import com.xxk.management.stock.entity.Stock;
import com.xxk.management.storage.entity.Delivery;
import com.xxk.management.storage.entity.Storage;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface WeChatRobotService {

    public void chatBotSendByText(Map<String, Object> textMap);

    public void chatBotSendByMarkdown(Map<String, Object> markdownMap);

}
