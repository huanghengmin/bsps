package com.hzih.bsps.service;

import com.hzih.bsps.domain.UserManage;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-21
 * Time: 下午2:45
 * To change this template use File | Settings | File Templates.
 */
public interface UserManageService {
    public ArrayList<UserManage> findUserManages();
    public ArrayList<UserManage> findUserManages(int start, int limit);
    public UserManage findUserManageById(int id);
    public ArrayList<UserManage> findUserManageByWhere(String coloum, String name);
    public ArrayList<UserManage> findUserManageByWhere(String coloum, String name, int start, int limit);
    public ArrayList<UserManage> findUserManagesByCacn(String cacn);
    public boolean checkUserManageByCacn(String cacn);
    public void addUserManage(String cacn, String province, String city, String department, String policestation, String email, String tel, String address, String idcard, String description);
    public void delUserManageById(int id);
    public void updUserManage(int id, String cacn, String province, String city, String department, String policestation, String email, String tel, String address, String idcard, String description);
    public void updUserManage(UserManage userManage);
    public void delUserManageByIds(String ids);

    public String findUserManagesByRoleManageId(int roleid,int start,int limit);
    public String findUserManagesByOtherRoleId(int roleid,int start,int limit);
}
