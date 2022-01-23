package com.xxk.management.WeChat.WeChatRobot.service;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface WeChatRobotService {

    public void chatBotSendByText(Map<String, Object> textMap);

    public void chatBotSendByMarkdown(Map<String, Object> markdownMap);

}
