package com.redis.service;

public interface MsgService {

    public String setMsg(String key, String msg);

    public String getMsg(String key);
}
