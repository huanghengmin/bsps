package com.hzih.bsps.dao;

import com.hzih.bsps.domain.UserManage;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-21
 * Time: 下午2:22
 * To change this template use File | Settings | File Templates.
 */
public interface UserManageDao {
    public ArrayList<UserManage> findUserManages();
    public ArrayList<UserManage> findUserManages(int start, int limit);
    public ArrayList<UserManage> findUserManageById(int id);
    public ArrayList<UserManage> findUserManageByWhere(String coloum, String name);
    public ArrayList<UserManage> findUserManageByWhere(String coloum, String name, int start, int limit);
    public ArrayList<UserManage> findUserManageByCacn(String cacn);
    public int findUserManageCount();
    public void addUserManage(UserManage userManage);
    public void delUserManage(UserManage userManage);
    public void updUserManage(UserManage userManage);

    public ArrayList<UserManage> findUserManagesByRoleManageId(int roleid);
    public ArrayList<UserManage> findUserManagesByRoleManageId(int roleid, final int start,final int limit);
    public ArrayList<UserManage> findUserManagesByOtherRoleId(int roleid);
    public ArrayList<UserManage> findUserManagesByOtherRoleId(int roleid, final int start,final int limit);
}
