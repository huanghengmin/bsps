package com.hzih.bsps.domain;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-3-4
 * Time: 下午3:43
 * To change this template use File | Settings | File Templates.
 */
public class WapControl {
    int id;
    String ip;

    public WapControl() {
    }

    public WapControl(String ip) {
        this.ip = ip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
