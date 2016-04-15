package com.hzih.bsps.dao.impl;

import com.hzih.bsps.dao.PowerDao;
import com.hzih.bsps.domain.Power;
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
 * Date: 12-12-2
 * Time: 下午6:54
 * To change this template use File | Settings | File Templates.
 */
public class PowerDaoImpl extends HibernateDaoSupport implements PowerDao {
    @Override
    public ArrayList<Power> findPowers() {
        String hql = "select t from com.hzih.bsps.domain.Power t";
        ArrayList<Power> list = (ArrayList<Power>) this.getHibernateTemplate().find(hql);
        return list;
    }
    @Override
    public ArrayList<Power> findPowers(final int start,final int limit) {
        final String hql = "select t from com.hzih.bsps.domain.Power t";
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
        return (ArrayList<Power>) list;
    }

    @Override
    public ArrayList<Power> findPowersById(int id) {
        String hql = "select t from com.hzih.bsps.domain.Power t where t.id=?";
        ArrayList<Power> list = (ArrayList<Power>) this.getHibernateTemplate().find(hql,id);
        return list;
    }

    @Override
    public ArrayList<Power> findPowersByIds(String ids) {
        String hql = "select t from com.hzih.bsps.domain.Power t where t.id in ("+ids+")";
        ArrayList<Power> list = (ArrayList<Power>) this.getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public ArrayList<Power> findPowersByRoleName(String rolename) {
        String hql = "select t from com.hzih.bsps.domain.Power t where t.rolename=?";
        ArrayList<Power> list = (ArrayList<Power>) this.getHibernateTemplate().find(hql,rolename);
        return list;
    }

    @Override
    public ArrayList<Power> findPowersByResourceName(String resourcename) {
        String hql = "select t from com.hzih.bsps.domain.Power t where t.resourcename=?";
        ArrayList<Power> list = (ArrayList<Power>) this.getHibernateTemplate().find(hql,resourcename);
        return list;
    }

    @Override
    public ArrayList<Power> findPowersByTwoLikeNames(String rolename, String resourcename) {
        String hql = "select t from com.hzih.bsps.domain.Power t where t.rolename like '%"+rolename+"%' and t.resourcename like '%"+resourcename+"%'";
        ArrayList<Power> list = (ArrayList<Power>) this.getHibernateTemplate().find(hql);
        return list;
    }
    @Override
    public ArrayList<Power> findPowersByTwoLikeNames(String rolename, String resourcename,final int start,final int limit) {
        final String hql = "select t from com.hzih.bsps.domain.Power t where t.rolename like '%"+rolename+"%' and t.resourcename like '%"+resourcename+"%'";
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
        return (ArrayList<Power>) list;
    }

    @Override
    public void addPower(Power power) {
        this.getHibernateTemplate().save(power);
    }

    @Override
    public void delPower(Power power) {
        this.getHibernateTemplate().delete(power);
    }

    @Override
    public void delPowers(ArrayList<Power> list) {
        this.getHibernateTemplate().deleteAll(list);
    }

    @Override
    public void updPower(Power power) {
        this.getHibernateTemplate().update(power);
    }
}
