package com.hzih.bsps.domain;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-22
 * Time: 下午2:22
 * To change this template use File | Settings | File Templates.
 */
public class Site {
    private int id;
//    Set<Https> httpsSet;
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

    public Site() {
    }

    public Site(int i) {
        this.id =i;
    }

    public Site(String site_name) {
        this.site_name = site_name;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    private String site_name;

//    public Set<Https> getHttpsSet() {
//        return httpsSet;
//    }

//    public void setHttpsSet(Set<Https> httpsSet) {
//        this.httpsSet = httpsSet;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
