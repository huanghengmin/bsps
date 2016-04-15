package com.hzih.bsps.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.bsps.dao.HttpsDao;
import com.hzih.bsps.domain.Https;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-22
 * Time: 下午2:58
 * To change this template use File | Settings | File Templates.
 */
public class HttpsDaoImpl extends MyDaoSupport implements HttpsDao {

    @Override
    public boolean add(Https site) throws Exception {
        boolean flag =false;
        super.getHibernateTemplate().save(site);
        flag = true;
        return flag;
    }

    @Override
    public boolean modify(Https site) throws Exception {
        boolean flag =false;
        super.getHibernateTemplate().update(site);
        flag = true;
        return flag;
    }

    @Override
    public boolean delete(Https site) throws Exception {
        boolean flag =false;
        super.getHibernateTemplate().delete(site);
        flag = true;
        return flag;
    }

    @Override
    public PageResult findByPages(int start, int limit) throws Exception {
        int pageIndex = start/limit+1;
        String hql = "from Https ";
        String countHql = "select count(*) " + hql;
        PageResult ps = this.findByPage(hql, countHql, pageIndex, limit);
        return ps;
    }

    @Override
    public Https findById(int i) {
        String hql="from Https s where s.id = "+i;
        List<Https> httpses  = super.getHibernateTemplate().find(hql);
        if(httpses!=null&&httpses.size()>0)
            return httpses.get(0);
        else
            return null;
    }

    @Override
    public List<Https> findAllHttp() {
        String hql="from Https s where s.protocol = 'http'";
        List<Https> httpses  = super.getHibernateTemplate().find(hql);
        if(httpses!=null&&httpses.size()>0)
            return httpses;
        else
            return null;
    }

    @Override
    public List<Https> findAllHttps() {
        String hql="from Https s where s.protocol = 'https'";
        List<Https> httpses  = super.getHibernateTemplate().find(hql);
        if(httpses!=null&&httpses.size()>0)
            return httpses;
        else
            return null;
    }

    @Override
    public boolean findByManagerAndProt(String managerport) {
        String hql="from Https s where s.manager_port = '"+managerport+"'";
        List<Https> httpses  = super.getHibernateTemplate().find(hql);
        if(httpses!=null&&httpses.size()>0)
            return true;
        else
            return false;
    }


    @Override
    public List<Https> findAllTcp() {
        String hql="from Https s where s.protocol = 'tcp'";
        List<Https> httpses  = super.getHibernateTemplate().find(hql);
        if(httpses!=null&&httpses.size()>0)
            return httpses;
        else
            return null;
    }

    @Override
    public List<Https> findAllTHttp() {
        String hql="from Https s where s.protocol = 'thttp'";
        List<Https> httpses  = super.getHibernateTemplate().find(hql);
        if(httpses!=null&&httpses.size()>0)
            return httpses;
        else
            return null;
    }

    @Override
    public boolean checkSiteBlock(String id) {
        String hql="from Https s where s.site = '"+id+"'";
        List<Https> httpses  = super.getHibernateTemplate().find(hql);
        if(httpses!=null&&httpses.size()>0)
            return true;
        else
            return false;
    }

    @Override
    public void setEntityClass() {
        this.entityClass = Https.class;
    }
}
