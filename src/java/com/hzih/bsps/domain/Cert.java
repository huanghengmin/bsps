package com.hzih.bsps.domain;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-23
 * Time: 下午4:14
 * To change this template use File | Settings | File Templates.
 */
public class Cert {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String key_path;
    private String cert_path;

    public String getKey_path() {
        return key_path;
    }

    public void setKey_path(String key_path) {
        this.key_path = key_path;
    }

    public String getCert_path() {
        return cert_path;
    }

    public void setCert_path(String cert_path) {
        this.cert_path = cert_path;
    }
}
