package com.hzih.ssl.core.util;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-9-26
 * Time: 下午4:46
 * To change this template use File | Settings | File Templates.
 */
public class User {
    private String username;
    private String IDcard;
//    private String ip;
    private String type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIDcard() {
        return IDcard;
    }

    public void setIDcard(String IDcard) {
        this.IDcard = IDcard;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
