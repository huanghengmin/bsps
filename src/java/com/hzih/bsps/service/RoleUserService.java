package com.hzih.bsps.service;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-30
 * Time: 下午12:06
 * To change this template use File | Settings | File Templates.
 */
public interface RoleUserService {
    public void addRoleUser(int roleid, int userid);
    public void addRoleUsers(int roleid, String userids);
    public void delRoleUser(int roleid, int userid);
}
