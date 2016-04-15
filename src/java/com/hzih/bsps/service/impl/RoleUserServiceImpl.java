package com.hzih.bsps.service.impl;

import com.hzih.bsps.dao.RoleUserDao;
import com.hzih.bsps.domain.RoleUser;
import com.hzih.bsps.service.RoleUserService;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-30
 * Time: 下午12:11
 * To change this template use File | Settings | File Templates.
 */
public class RoleUserServiceImpl implements RoleUserService{
    private RoleUserDao roleUserDao;

    public RoleUserDao getRoleUserDao() {
        return roleUserDao;
    }

    public void setRoleUserDao(RoleUserDao roleUserDao) {
        this.roleUserDao = roleUserDao;
    }

    @Override
    public void addRoleUser(int roleid, int userid) {
        roleUserDao.addRoleUserDao(new RoleUser(roleid,userid));
    }

    @Override
    public void addRoleUsers(int roleid, String ids) {
        String[] ida = ids.split(",");
        for(String s : ida) {
            int id = Integer.parseInt(s);
            addRoleUser(roleid, id);
        }
    }

    @Override
    public void delRoleUser(int roleid, int userid) {
        RoleUser roleUser = roleUserDao.findRoleUsersByRoleIdAndUserId(roleid, userid).get(0);
        roleUserDao.delRoleUserDao(roleUser);
    }

}
