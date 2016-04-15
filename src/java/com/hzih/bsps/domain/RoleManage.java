package com.hzih.bsps.domain;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-27
 * Time: 下午3:23
 * To change this template use File | Settings | File Templates.
 */
public class RoleManage {
    private int id;
    private String rolename;
    private String description;

    public RoleManage() {
    }

    public RoleManage(String rolename, String description) {
        this.rolename = rolename;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
