package com.hzih.bsps.service.impl;

import com.hzih.bsps.dao.PowerDao;
import com.hzih.bsps.dao.RoleManageDao;
import com.hzih.bsps.dao.RoleUserDao;
import com.hzih.bsps.domain.Power;
import com.hzih.bsps.domain.RoleManage;
import com.hzih.bsps.domain.RoleUser;
import com.hzih.bsps.service.RoleManageService;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-27
 * Time: 下午3:47
 * To change this template use File | Settings | File Templates.
 */
public class RoleManageServiceImpl implements RoleManageService{
    private RoleManageDao roleManageDao;
    private RoleUserDao roleUserDao;
    private PowerDao powerDao;

    public PowerDao getPowerDao() {
        return powerDao;
    }

    public void setPowerDao(PowerDao powerDao) {
        this.powerDao = powerDao;
    }

    public RoleUserDao getRoleUserDao() {
        return roleUserDao;
    }

    public void setRoleUserDao(RoleUserDao roleUserDao) {
        this.roleUserDao = roleUserDao;
    }

    public RoleManageDao getRoleManageDao() {
        return roleManageDao;
    }
    public void setRoleManageDao(RoleManageDao roleManageDao) {
        this.roleManageDao = roleManageDao;
    }

    @Override
    public ArrayList<RoleManage> findRoleManages() {
        return roleManageDao.findRoleManages();
    }
    @Override
    public ArrayList<RoleManage> findRoleManages(int start,int limit) {
        return roleManageDao.findRoleManages(start,limit);
    }

    @Override
    public ArrayList<RoleManage> findRoleManages(String rolename) {
        return roleManageDao.findRoleManages(rolename);  //To change body of implemented methods use File | Settings | File Templates.
    }
    @Override
    public ArrayList<RoleManage> findRoleManages(String rolename,int start,int limit) {
        return roleManageDao.findRoleManages(rolename,start,limit);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RoleManage findRoleManageByRolename(String rolename) {
        return roleManageDao.findRoleManagesByRolename(rolename).get(0);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RoleManage findRoleManageById(int id) {
        return roleManageDao.findRoleManagesById(id).get(0);
    }

    @Override
    public boolean checkRoleManageByRolename(String rolename) {
        ArrayList<RoleManage> list = roleManageDao.findRoleManagesByRolename(rolename);
        if(null==list||list.size()==0){
            return false;
        }
        return true;
    }

    @Override
    public void delRoleManageByRolenames(String rolenames) {
        String[] ida = rolenames.split(",");
        for(String s : ida) {
            RoleManage roleManage = findRoleManageByRolename(s);
            roleManageDao.delRoleManage(roleManage);
        }
    }

    @Override
    public void delRoleManageByIds(String ids) {
        String[] ida = ids.split(",");
        for(String s : ida) {
            int id = Integer.parseInt(s);
            RoleManage roleManage = roleManageDao.findRoleManagesById(id).get(0);
            String rolename=roleManage.getRolename();
            ArrayList<Power> powerlist = powerDao.findPowersByRoleName(rolename);
            ArrayList<RoleUser> list = roleUserDao.findRoleUsersByRoleId(id);
            powerDao.delPowers(powerlist);
            roleUserDao.delRoleUsers(list);
            roleManageDao.delRoleManage(roleManage);
        }
    }

    @Override
    public void addRoleManage(String rolename, String description) {
        roleManageDao.addRoleManage( new RoleManage(rolename,description) );
    }

    @Override
    public void updRoleManage(RoleManage roleManage, String rolename, String description) {
        roleManage.setRolename(rolename);
        roleManage.setDescription(description);
        roleManageDao.updRoleManage(roleManage);
    }


}
