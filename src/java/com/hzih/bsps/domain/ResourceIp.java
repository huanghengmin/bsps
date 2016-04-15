package com.hzih.bsps.domain;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-30
 * Time: 下午6:34
 * To change this template use File | Settings | File Templates.
 */

public class ResourceIp {
    public int id;
    public int resourceid;
    public String ipaddress;
    public String subnetmask;

    public ResourceIp() {
    }

    public ResourceIp(int resourceid, String ipaddress, String subnetmask) {
        this.resourceid = resourceid;
        this.ipaddress = ipaddress;
        this.subnetmask = subnetmask;
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

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getSubnetmask() {
        return subnetmask;
    }

    public void setSubnetmask(String subnetmask) {
        this.subnetmask = subnetmask;
    }
}
