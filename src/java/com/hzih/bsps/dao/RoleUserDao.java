package com.hzih.bsps.dao;

import com.hzih.bsps.domain.RoleUser;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-30
 * Time: 上午11:15
 * To change this template use File | Settings | File Templates.
 */
public interface RoleUserDao {
    public void addRoleUserDao(RoleUser roleUser);
    public void delRoleUserDao(RoleUser roleUser);
    public void delRoleUsers(ArrayList<RoleUser> list);
    public ArrayList<RoleUser> findRoleUsersByRoleIdAndUserId(int roleid, int userid);
    public ArrayList<RoleUser> findRoleUsersByRoleId(int roleid);
    public ArrayList<RoleUser> findRoleUsersByUserId(int userid);
    
}
