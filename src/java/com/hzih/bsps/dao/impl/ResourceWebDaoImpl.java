package com.hzih.bsps.dao.impl;

import com.hzih.bsps.dao.ResourceWebDao;
import com.hzih.bsps.domain.ResourceWeb;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-30
 * Time: 下午9:13
 * To change this template use File | Settings | File Templates.
 */
public class ResourceWebDaoImpl extends HibernateDaoSupport implements ResourceWebDao {
    @Override
    public ArrayList<ResourceWeb> findResourceWebByResourceid(int resourceid) {
        String hql = "select t from com.hzih.bsps.domain.ResourceWeb t where t.resourceid=?";
        ArrayList<ResourceWeb> list = (ArrayList<ResourceWeb>) this.getHibernateTemplate().find(hql,resourceid);
        return list;
    }

    @Override
    public ArrayList<ResourceWeb> findResourceWebByResourceids(String ids) {
        String hql = "select t from com.hzih.bsps.domain.ResourceWeb t where t.resourceid in ("+ids+")";
        ArrayList<ResourceWeb> list = (ArrayList<ResourceWeb>) this.getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public void addResourceWeb(ResourceWeb resourceWeb) {
        this.getHibernateTemplate().save(resourceWeb);
    }

    @Override
    public void delResourceWeb(ResourceWeb resourceWeb) {
        this.getHibernateTemplate().delete(resourceWeb);
    }

    @Override
    public void delResourceWebs(ArrayList<ResourceWeb> list) {
        this.getHibernateTemplate().deleteAll(list);
    }
}
