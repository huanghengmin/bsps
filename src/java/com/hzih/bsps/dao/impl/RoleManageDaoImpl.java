package com.hzih.bsps.dao.impl;

import com.hzih.bsps.dao.RoleManageDao;
import com.hzih.bsps.domain.RoleManage;
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
 * Date: 12-11-27
 * Time: 下午3:58
 * To change this template use File | Settings | File Templates.
 */
public class RoleManageDaoImpl extends HibernateDaoSupport implements RoleManageDao{

    public ArrayList<RoleManage> findRoleManages() {
        String hql = "select t from com.hzih.bsps.domain.RoleManage t";
        ArrayList<RoleManage> list = (ArrayList<RoleManage>) this.getHibernateTemplate().find(hql);
        return list;
    }
    public ArrayList<RoleManage> findRoleManages(final int start,final int limit) {
        final String hql = "select t from com.hzih.bsps.domain.RoleManage t order by t.id asc";
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
        return (ArrayList<RoleManage>) list;
    }

    public ArrayList<RoleManage> findRoleManages(String rolename) {
        String hql = String.format("select t from com.hzih.bsps.domain.RoleManage t where t.rolename like'%%%s%%'", rolename);
        ArrayList<RoleManage> list = (ArrayList<RoleManage>) this.getHibernateTemplate().find(hql);
        return list;
    }
    public ArrayList<RoleManage> findRoleManages(String rolename,final int start,final int limit) {
        final String hql = String.format("select t from com.hzih.bsps.domain.RoleManage t where t.rolename like'%%%s%%' order by t.id asc", rolename);
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
        return (ArrayList<RoleManage>) list;
    }

    @Override
    public ArrayList<RoleManage> findRoleManagesById(int id) {
        String hql = "select t from com.hzih.bsps.domain.RoleManage t where t.id=?";
        ArrayList<RoleManage> list = (ArrayList<RoleManage>) this.getHibernateTemplate().find(hql,id);
        return list;
    }

    public ArrayList<RoleManage> findRoleManagesByRolename(String rolename) {
        String hql = "select t from com.hzih.bsps.domain.RoleManage t where t.rolename=?";
        ArrayList<RoleManage> list = (ArrayList<RoleManage>) this.getHibernateTemplate().find(hql,rolename);
        return list;
    }

    @Override
    public void addRoleManage(RoleManage roleManage) {
        this.getHibernateTemplate().save(roleManage);
    }

    @Override
    public void delRoleManage(RoleManage roleManage) {
        this.getHibernateTemplate().delete(roleManage);
    }

    @Override
    public void updRoleManage(RoleManage roleManage) {
        this.getHibernateTemplate().update(roleManage);
    }

}
