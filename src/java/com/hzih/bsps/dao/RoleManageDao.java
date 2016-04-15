package com.hzih.bsps.dao;

import com.hzih.bsps.domain.RoleManage;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-27
 * Time: 下午3:44
 * To change this template use File | Settings | File Templates.
 */
public interface RoleManageDao {
    public ArrayList<RoleManage> findRoleManages();
    public ArrayList<RoleManage> findRoleManages(final int start,final int limit);
    public ArrayList<RoleManage> findRoleManages(String rolename);
    public ArrayList<RoleManage> findRoleManages(String rolename,final int start,final int limit);
    public ArrayList<RoleManage> findRoleManagesById(int id);
    public ArrayList<RoleManage> findRoleManagesByRolename(String rolename);
    public void addRoleManage(RoleManage roleManage);
    public void delRoleManage(RoleManage roleManage);
    public void updRoleManage(RoleManage roleManage);
}
