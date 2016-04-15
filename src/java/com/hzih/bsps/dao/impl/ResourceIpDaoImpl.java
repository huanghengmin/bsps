package com.hzih.bsps.dao.impl;

import com.hzih.bsps.dao.ResourceIpDao;
import com.hzih.bsps.domain.ResourceIp;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-30
 * Time: 下午9:05
 * To change this template use File | Settings | File Templates.
 */
public class ResourceIpDaoImpl extends HibernateDaoSupport implements ResourceIpDao{

    @Override
    public ArrayList<ResourceIp> findResourceIpByResourceid(int resourceid) {
        String hql = "select t from com.hzih.bsps.domain.ResourceIp t where t.resourceid=?";
        ArrayList<ResourceIp> list = (ArrayList<ResourceIp>) this.getHibernateTemplate().find(hql,resourceid);
        return list;
    }

    @Override
    public ArrayList<ResourceIp> findResourceIpByResourceids(String ids) {
        String hql = "select t from com.hzih.bsps.domain.ResourceIp t where t.resourceid in ("+ids+")";
        ArrayList<ResourceIp> list = (ArrayList<ResourceIp>) this.getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public void addResourceIp(ResourceIp resourceIp) {
        this.getHibernateTemplate().save(resourceIp);
    }

    @Override
    public void delResourceIp(ResourceIp resourceIp) {
        this.getHibernateTemplate().delete(resourceIp);
    }

    @Override
    public void delResourceIps(ArrayList<ResourceIp> list) {
        this.getHibernateTemplate().deleteAll(list);
    }
}
