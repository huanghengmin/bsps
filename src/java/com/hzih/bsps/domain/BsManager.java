package com.hzih.bsps.domain;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-22
 * Time: 下午2:07
 * To change this template use File | Settings | File Templates.
 */
public class BsManager {
    private int id;
    private String protocol;
    private String manager_ip;
    private String manager_port;
    private String proxy_ip;
    private String proxy_port;
    private String key_path;
    private String cert_path;

    public BsManager() {
    }

    public BsManager(String cert_path, String protocol, String manager_ip, String manager_port, String proxy_ip, String proxy_port, String key_path) {
        this.cert_path = cert_path;
        this.protocol = protocol;
        this.manager_ip = manager_ip;
        this.manager_port = manager_port;
        this.proxy_ip = proxy_ip;
        this.proxy_port = proxy_port;
        this.key_path = key_path;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getManager_ip() {
        return manager_ip;
    }

    public void setManager_ip(String manager_ip) {
        this.manager_ip = manager_ip;
    }

    public String getManager_port() {
        return manager_port;
    }

    public void setManager_port(String manager_port) {
        this.manager_port = manager_port;
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
}
