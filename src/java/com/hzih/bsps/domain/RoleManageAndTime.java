package com.hzih.bsps.domain;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-28
 * Time: 下午5:29
 * To change this template use File | Settings | File Templates.
 */
public class RoleManageAndTime {
    private int id;
    private int rolemanageid;
    private int timetype;
    private Date starttime;
    private Date endtime;

    public RoleManageAndTime() {
    }

    public RoleManageAndTime(int rolemanageid, int timetype, Date starttime, Date endtime ) {
        this.rolemanageid = rolemanageid;
        this.timetype = timetype;
        this.starttime =  starttime;
        this.endtime =  endtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRolemanageid() {
        return rolemanageid;
    }

    public void setRolemanageid(int rolemanageid) {
        this.rolemanageid = rolemanageid;
    }

    public int getTimetype() {
        return timetype;
    }

    public void setTimetype(int timetype) {
        this.timetype = timetype;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
}
