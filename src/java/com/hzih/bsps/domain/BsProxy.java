package com.hzih.bsps.domain;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-22
 * Time: 下午2:08
 * To change this template use File | Settings | File Templates.
 */
public class BsProxy {
    private int id;
    private String proxy_ip;
    private String proxy_port;
    private String protocol;
    private String app_flag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProxy_ip() {
        return proxy_ip;
    }

    public void setProxy_ip(String proxy_ip) {
        this.proxy_ip = proxy_ip;
    }

    public String getProxy_port() {
        return proxy_port;
    }

    public void setProxy_port(String proxy_port) {
        this.proxy_port = proxy_port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getApp_flag() {
        return app_flag;
    }

    public void setApp_flag(String app_flag) {
        this.app_flag = app_flag;
    }
}
