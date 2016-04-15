package com.hzih.bsps.domain;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-22
 * Time: 下午5:58
 * To change this template use File | Settings | File Templates.
 */
public class SysLogEntity {
    private String user;
    private String userid;
    private String province;
    private String organization;
    private String institutions;
    private String date;
    private String phone;
    private String phonenetid;
    private String serialnumber;
    private String url;
    private int   count;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getInstitutions() {
        return institutions;
    }

    public void setInstitutions(String institutions) {
        this.institutions = institutions;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhonenetid() {
        return phonenetid;
    }

    public void setPhonenetid(String phonenetid) {
        this.phonenetid = phonenetid;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
