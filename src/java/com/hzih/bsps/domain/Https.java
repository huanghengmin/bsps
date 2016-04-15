package com.hzih.bsps.domain;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-22
 * Time: 下午2:21
 * To change this template use File | Settings | File Templates.
 */
public class Https {
    private int id;
    private String manager_ip;
    private String manager_port;
    private String proxy_ip;
    private String proxy_port;
    private String protocol;
    private String site;
    private String site_id_server;

    public String getSite_id_server() {
        return site_id_server;
    }

    public void setSite_id_server(String site_id_server) {
        this.site_id_server = site_id_server;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

  
    //    private Site site;

//    public Site getSite() {
//        return site;
//    }

//    public void setSite(Site site) {
//        this.site = site;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
