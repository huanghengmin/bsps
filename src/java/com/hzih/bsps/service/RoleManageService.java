package com.hzih.bsps.service;

import com.hzih.bsps.domain.RoleManage;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-27
 * Time: 下午3:47
 * To change this template use File | Settings | File Templates.
 */
public interface RoleManageService {
    public ArrayList<RoleManage> findRoleManages();
    public ArrayList<RoleManage> findRoleManages(int start,int limit);
    public ArrayList<RoleManage> findRoleManages(String rolename);
    public ArrayList<RoleManage> findRoleManages(String rolename,int start,int limit);
    public RoleManage findRoleManageByRolename(String rolename);
    public RoleManage findRoleManageById(int id);
    public boolean checkRoleManageByRolename(String rolename);
    public void delRoleManageByRolenames(String rolenames);
    public void delRoleManageByIds(String ids);
    public void addRoleManage(String rolename,String description);
    public void updRoleManage(RoleManage roleManage, String rolename,String description);

}
