package com.hzih.bsps.domain;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-30
 * Time: 下午7:01
 * To change this template use File | Settings | File Templates.
 */
public class ResourceWeb {
    public int id;
    public int resourceid;
    public String agreement;
    public String ipaddress;
    public String port;
    public String url;

    public ResourceWeb() {
    }

    public ResourceWeb(int resourceid, String agreement, String ipaddress, String port, String url) {
        this.resourceid = resourceid;
        this.agreement = agreement;
        this.ipaddress = ipaddress;
        this.port = port;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
