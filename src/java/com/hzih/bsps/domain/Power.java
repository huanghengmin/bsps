package com.hzih.bsps.domain;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-12-2
 * Time: 下午6:44
 * To change this template use File | Settings | File Templates.
 */
public class Power {
    private int id;
    private String rolename;
    private int rights;
    private String resourcename;

    public Power() {
    }

    public Power(String rolename, int rights, String resourcename) {
        this.rolename = rolename;
        this.rights = rights;
        this.resourcename = resourcename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public int getRights() {
        return rights;
    }

    public void setRights(int rights) {
        this.rights = rights;
    }

    public String getResourcename() {
        return resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename;
    }
}
