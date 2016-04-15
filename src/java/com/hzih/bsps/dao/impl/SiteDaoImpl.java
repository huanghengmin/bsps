package com.hzih.bsps.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.bsps.dao.SiteDao;
import com.hzih.bsps.domain.Site;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-22
 * Time: 下午2:58
 * To change this template use File | Settings | File Templates.
 */
public class SiteDaoImpl extends MyDaoSupport implements SiteDao{

    @Override
    public void setEntityClass() {
        this.entityClass = Site.class;
    }

    @Override
    public boolean add(Site site) throws Exception {
        boolean flag =false;
        super.getHibernateTemplate().save(site);
        flag = true;
        return flag;
    }

    @Override
    public boolean modify(Site site) throws Exception {
        boolean flag =false;
        super.getHibernateTemplate().update(site);
        flag = true;
        return flag;
    }

    @Override
    public boolean delete(Site site) throws Exception {
        String hql="delete from Site where id = "+site.getId();
        boolean flag = false;
        Session session = super.getSession();
        try{
            session.beginTransaction();
            Query query = session.createQuery(hql);
            query.executeUpdate();
            session.getTransaction().commit();
            flag = true;

        } catch (Exception e){

        } finally {
            session.close();
        }
        return flag;
    }

    @Override
    public PageResult findByPages(int start, int limit) throws Exception {
        int pageIndex = start/limit+1;
        String hql = "from Site";
        String countHql = "select count(*) " + hql;
        PageResult ps = this.findByPage(hql, countHql, pageIndex, limit);
        return ps;

    }

    @Override
    public boolean check_site_name(String site_name) {
        String hql="from Site s where s.site_name = '"+site_name+"'";
        List<Site> sites  = super.getHibernateTemplate().find(hql);
        if(sites!=null&&sites.size()>0)
            return true;
        else
            return false;
    }

    @Override
    public Site findById(int siteId) {
        String hql="from Site s where s.id = "+siteId;
        List<Site> httpses  = super.getHibernateTemplate().find(hql);
        if(httpses!=null&&httpses.size()>0)
            return httpses.get(0);
        else
            return null;
    }
}
