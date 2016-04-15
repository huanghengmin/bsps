package com.hzih.bsps.dao.impl;

import com.hzih.bsps.dao.ResourceDao;
import com.hzih.bsps.domain.Resource;
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
 * Date: 12-11-30
 * Time: 下午7:16
 * To change this template use File | Settings | File Templates.
 */
public class ResourceDaoImpl extends HibernateDaoSupport implements ResourceDao {
    @Override
    public ArrayList<Resource> findResources() {
        String hql = "select t from com.hzih.bsps.domain.Resource t";
        ArrayList<Resource> list = (ArrayList<Resource>) this.getHibernateTemplate().find(hql);
        return list;
    }
    @Override
    public ArrayList<Resource> findResources(final int start, final int limit) {
        final String hql = "select t from com.hzih.bsps.domain.Resource t order by t.id asc";
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
        return (ArrayList<Resource>) list;
    }


    @Override
    public ArrayList<Resource> findResourcesById(int id) {
        String hql = "select t from com.hzih.bsps.domain.Resource t where t.id=?";
        ArrayList<Resource> list = (ArrayList<Resource>) this.getHibernateTemplate().find(hql,id);
        return list;
    }

    @Override
    public ArrayList<Resource> findResourcesByIds(String ids) {
        String hql = "select t from com.hzih.bsps.domain.Resource t where t.id in ("+ids+")";
        ArrayList<Resource> list = (ArrayList<Resource>) this.getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public ArrayList<Resource> findResourcesByName(String name) {
        String hql = "select t from com.hzih.bsps.domain.Resource t where t.name=?";
        ArrayList<Resource> list = (ArrayList<Resource>) this.getHibernateTemplate().find(hql,name);
        return list;
    }

    @Override
    public ArrayList<Resource> findResourcesByLikeName(String name) {
        String hql = "select t from com.hzih.bsps.domain.Resource t where t.name like '%"+name+"%'";
        ArrayList<Resource> list = (ArrayList<Resource>) this.getHibernateTemplate().find(hql);
        return list;
    }
    @Override
    public ArrayList<Resource> findResourcesByLikeName(String name,final int start, final int limit) {
        final String hql = "select t from com.hzih.bsps.domain.Resource t where t.name like '%"+name+"%' order by t.id asc";
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
        return (ArrayList<Resource>) list;
    }

    @Override
    public ArrayList<Resource> findResourcesByTypeAndLikeName(int type, String name) {
        String hql = "select t from com.hzih.bsps.domain.Resource t where t.type="+type+" and t.name like '%"+name+"%'";
        ArrayList<Resource> list = (ArrayList<Resource>) this.getHibernateTemplate().find(hql);
        return list;
    }
    @Override
    public ArrayList<Resource> findResourcesByTypeAndLikeName(int type, String name,final int start, final int limit) {
        final String hql = "select t from com.hzih.bsps.domain.Resource t where t.type="+type+" and t.name like '%"+name+"%' order by t.id asc";
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
        return (ArrayList<Resource>) list;
    }

    @Override
    public void addResource(String name, int type, String description) {
        this.getHibernateTemplate().save(new Resource(name, type, description));
    }

    @Override
    public void delResource(Resource resource) {
        this.getHibernateTemplate().delete(resource);
    }

    @Override
    public void updResource(Resource resource) {
        this.getHibernateTemplate().update(resource);
    }

    @Override
    public void delResources(ArrayList<Resource> list) {
        this.getHibernateTemplate().deleteAll(list);
    }

    @Override
    public ArrayList<Resource> findResourcesByOtherRoleName(String rolename) {
        String hql = "select t from com.hzih.bsps.domain.Resource t where t.name not in (select p.resourcename from com.hzih.bsps.domain.Power p where p.rolename=?)";
        ArrayList<Resource> list = (ArrayList<Resource>) this.getHibernateTemplate().find(hql,rolename);
        return list;
    }
    @Override
    public ArrayList<Resource> findResourcesByOtherRoleName(String rolename, final int start, final int limit) {
        final String hql = "select t from com.hzih.bsps.domain.Resource t where t.name not in (select p.resourcename from com.hzih.bsps.domain.Power p where p.rolename='"+rolename+"')";
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
        return (ArrayList<Resource>) list;
    }

    @Override
    public ArrayList<Resource> findResourcesByOtherRoleName(String rolename, String name) {
        String hql = "select t from com.hzih.bsps.domain.Resource t where t.name like '%"+name+"%'and"+" t.name not in (select p.resourcename from com.hzih.bsps.domain.Power p where p.rolename=?)";
        ArrayList<Resource> list = (ArrayList<Resource>) this.getHibernateTemplate().find(hql,rolename);
        return list;
    }
    @Override
    public ArrayList<Resource> findResourcesByOtherRoleName(String rolename, String name, final int start, final int limit) {
        final String hql = "select t from com.hzih.bsps.domain.Resource t where t.name like '%"+name+"%'and"+" t.name not in (select p.resourcename from com.hzih.bsps.domain.Power p where p.rolename='"+rolename+"')";
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
        return (ArrayList<Resource>) list;
    }

    @Override
    public ArrayList<Resource> findResourcesByOtherRoleName(String rolename,int type, String name) {
        String hql = "select t from com.hzih.bsps.domain.Resource t where t.type="+type+" and t.name like '%"+name+"%'and"+" t.name not in (select p.resourcename from com.hzih.bsps.domain.Power p where p.rolename=?)";
        ArrayList<Resource> list = (ArrayList<Resource>) this.getHibernateTemplate().find(hql,rolename);
        return list;
    }
    @Override
    public ArrayList<Resource> findResourcesByOtherRoleName(String rolename,int type, String name, final int start, final int limit) {
        final String hql = "select t from com.hzih.bsps.domain.Resource t where t.type="+type+" and t.name like '%"+name+"%'and"+" t.name not in (select p.resourcename from com.hzih.bsps.domain.Power p where p.rolename='"+rolename+"')";
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
        return (ArrayList<Resource>) list;
    }

    @Override
    public ArrayList<Resource> findResourcesByPowerResourceName() {
        String hql = "select r from com.hzih.bsps.domain.Resource r where r.name in(select p.resourcename from com.hzih.bsps.domain.Power p group by p.resourcename)";
        ArrayList<Resource> list = (ArrayList<Resource>) this.getHibernateTemplate().find(hql);
        return list;
    }


}
