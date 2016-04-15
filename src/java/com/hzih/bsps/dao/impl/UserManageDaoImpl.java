package com.hzih.bsps.dao.impl;

import com.hzih.bsps.dao.UserManageDao;
import com.hzih.bsps.domain.UserManage;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-21
 * Time: 下午2:22
 * To change this template use File | Settings | File Templates.
 */
public class UserManageDaoImpl extends HibernateDaoSupport implements UserManageDao{

    @Override
    public ArrayList<UserManage> findUserManages() {
        String hql = "select t from com.hzih.bsps.domain.UserManage t";
        ArrayList<UserManage> list = (ArrayList<UserManage>) this.getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public ArrayList<UserManage> findUserManages(final int start, final int limit) {
        final String hql = "select t from com.hzih.bsps.domain.UserManage t order by t.id asc";
        List list = this.getHibernateTemplate().find(hql);
        if( list.size()!=0 ){
            list = getHibernateTemplate().executeFind(
                new HibernateCallback() {
                    public Object doInHibernate(Session session) throws HibernateException, SQLException {
                        Query query = session.createQuery(hql);
                        query.setFirstResult(start);
                        query.setMaxResults(limit);
                        List list = query.list();
                        return list;
                    }
                }
            );
        }
        return (ArrayList<UserManage>) list;
    }

    @Override
    public ArrayList<UserManage> findUserManageById(int id) {
        String hql = "select t from com.hzih.bsps.domain.UserManage t where t.id=? order by t.id asc";
        ArrayList<UserManage> list = (ArrayList<UserManage>) this.getHibernateTemplate().find(hql, id);
        return list;
    }

    @Override
    public ArrayList<UserManage> findUserManageByWhere(String coloum, String name) {
        final String hql = "select t from com.hzih.bsps.domain.UserManage t where "+"t."+coloum+" like " + "'%"+name+"%' order by t.id asc";
        ArrayList<UserManage> list = (ArrayList<UserManage>) this.getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public ArrayList<UserManage> findUserManageByWhere(String coloum, String name,final int start,final int limit) {
        final String hql = "select t from com.hzih.bsps.domain.UserManage t where "+"t."+coloum+" like " + "'%"+name+"%' order by t.id asc";
        List list = this.getHibernateTemplate().find(hql);
        if( list.size()!=0 ){
            list = getHibernateTemplate().executeFind(
                new HibernateCallback() {
                    public Object doInHibernate(Session session) throws HibernateException, SQLException {
                        Query query = session.createQuery(hql);
                        query.setFirstResult(start);
                        query.setMaxResults(limit);
                        List list = query.list();
                        return list;
                    }
                }
            );
        }
        return (ArrayList<UserManage>) list;
    }

    @Override
    public ArrayList<UserManage> findUserManageByCacn(String cacn) {
        String hql = "select t from com.hzih.bsps.domain.UserManage t where t.cacn=? order by t.id asc";
        ArrayList<UserManage> list = (ArrayList<UserManage>) this.getHibernateTemplate().find(hql, cacn);
        return list;
    }

    @Override
    public int findUserManageCount() {
        String hql = "select select count(*) from UserManage";

        return 0;
    }

    @Override
    public void delUserManage(UserManage userManage) {
        this.getHibernateTemplate().delete(userManage);
    }

    @Override
    public void updUserManage(UserManage userManage) {
       this.getHibernateTemplate().update(userManage);
    }

    @Override
    public ArrayList<UserManage> findUserManagesByRoleManageId(int roleid) {
        String hql = "select u from com.hzih.bsps.domain.UserManage u,com.hzih.bsps.domain.RoleUser r where u.id=r.userid and r.roleid=?";
        ArrayList<UserManage> list = (ArrayList<UserManage>) this.getHibernateTemplate().find(hql, roleid);
        return list;
    }

    @Override
    public ArrayList<UserManage> findUserManagesByRoleManageId(int roleid, final int start,final int limit) {
        final String hql = "select u from com.hzih.bsps.domain.UserManage u,com.hzih.bsps.domain.RoleUser r where u.id=r.userid and r.roleid="+roleid+" order by u.id asc";
        List list = this.getHibernateTemplate().find(hql);
        if( list.size()!=0 ){
            list = getHibernateTemplate().executeFind(
                    new HibernateCallback() {
                        public Object doInHibernate(Session session) throws HibernateException, SQLException {
                            Query query = session.createQuery(hql);
                            query.setFirstResult(start);
                            query.setMaxResults(limit);
                            List list = query.list();
                            return list;
                        }
                    }
            );
        }
        return (ArrayList<UserManage>) list;
    }

    @Override
    public ArrayList<UserManage> findUserManagesByOtherRoleId(int roleid) {
        String hql = "select u2 from com.hzih.bsps.domain.UserManage u2 where u2.id not in (select u.id from com.hzih.bsps.domain.UserManage u,com.hzih.bsps.domain.RoleUser r where u.id=r.userid and r.roleid=?)";
        ArrayList<UserManage> list = (ArrayList<UserManage>) this.getHibernateTemplate().find(hql, roleid);
        return list;
    }

    @Override
    public ArrayList<UserManage> findUserManagesByOtherRoleId(int roleid, final int start,final int limit) {
        final String hql = "select u2 from com.hzih.bsps.domain.UserManage u2 where u2.id not in (select u.id from com.hzih.bsps.domain.UserManage u,com.hzih.bsps.domain.RoleUser r where u.id=r.userid and r.roleid="+roleid+") order by u2.id asc";
        List list = this.getHibernateTemplate().find(hql);
        if( list.size()!=0 ){
            list = getHibernateTemplate().executeFind(
                    new HibernateCallback() {
                        public Object doInHibernate(Session session) throws HibernateException, SQLException {
                            Query query = session.createQuery(hql);
                            query.setFirstResult(start);
                            query.setMaxResults(limit);
                            List list = query.list();
                            return list;
                        }
                    }
            );
        }
        return (ArrayList<UserManage>) list;
    }

    @Override
    public void addUserManage(UserManage userManage) {
        this.getHibernateTemplate().save(userManage);
    }


}
