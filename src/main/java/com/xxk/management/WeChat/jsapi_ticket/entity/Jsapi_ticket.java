package com.xxk.management.WeChat.jsapi_ticket.entity;

public class Jsapi_ticket {

    //ticket
    private String ticket;
    //有效时间
    private int expires_in;

    public Jsapi_ticket(String ticket, int expires_in) {
        this.ticket = ticket;
        this.expires_in = expires_in;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
