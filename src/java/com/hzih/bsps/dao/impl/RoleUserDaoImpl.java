package com.hzih.bsps.dao.impl;

import com.hzih.bsps.dao.RoleUserDao;
import com.hzih.bsps.domain.RoleUser;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-30
 * Time: 上午11:16
 * To change this template use File | Settings | File Templates.
 */
public class RoleUserDaoImpl extends HibernateDaoSupport implements  RoleUserDao{
    @Override
    public void addRoleUserDao(RoleUser roleUser) {
        this.getHibernateTemplate().save(roleUser);
    }

    @Override
    public void delRoleUserDao(RoleUser roleUser) {
        this.getHibernateTemplate().delete(roleUser);
    }

    @Override
    public void delRoleUsers(ArrayList<RoleUser> list) {
        this.getHibernateTemplate().deleteAll(list);
    }

    @Override
    public ArrayList<RoleUser> findRoleUsersByRoleIdAndUserId(int roleid, int userid) {
        String hql = "select r from com.hzih.bsps.domain.RoleUser r where r.roleid=? and r.userid=?";
        ArrayList<RoleUser> list = (ArrayList<RoleUser>) this.getHibernateTemplate().find(hql, new Object[]{roleid,userid});
        return list;
    }

    @Override
    public ArrayList<RoleUser> findRoleUsersByRoleId(int roleid) {
        String hql = "select r from com.hzih.bsps.domain.RoleUser r where r.roleid=?";
        ArrayList<RoleUser> list = (ArrayList<RoleUser>) this.getHibernateTemplate().find(hql, roleid);
        return list;
    }

    @Override
    public ArrayList<RoleUser> findRoleUsersByUserId(int userid) {
        String hql = "select r from com.hzih.bsps.domain.RoleUser r where r.userid=?";
        ArrayList<RoleUser> list = (ArrayList<RoleUser>) this.getHibernateTemplate().find(hql, userid);
        return list;
    }


}
