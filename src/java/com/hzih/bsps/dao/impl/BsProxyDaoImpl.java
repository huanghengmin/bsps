package com.hzih.bsps.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.bsps.dao.BsProxyDao;
import com.hzih.bsps.domain.BsProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-22
 * Time: 下午2:22
 * To change this template use File | Settings | File Templates.
 */
public class BsProxyDaoImpl extends MyDaoSupport implements BsProxyDao {
    @Override
    public boolean add(BsProxy bsProxy) throws Exception {
        boolean flag =false;
        try {
            super.getHibernateTemplate().save(bsProxy);
            flag = true;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean modify(BsProxy bsProxy) throws Exception {
        boolean flag =false;
        try {
            super.getHibernateTemplate().saveOrUpdate(bsProxy);
            flag = true;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean delete(BsProxy bsProxy) throws Exception {
        boolean flag =false;
        try {
            super.getHibernateTemplate().delete(bsProxy);
            flag = true;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return flag;
    }

    @Override
    public BsProxy findById(int id) throws Exception {
        String hql= String.format("from BsProxy bsproxy  where bsproxy.id ='%d'", id);
        List<BsProxy> bsProxies = null;
        try {
            bsProxies = super.getHibernateTemplate().find(hql);
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        if(bsProxies.size()>0&&bsProxies!=null){
            return bsProxies.get(0);
        }else {
            return null;
        }
    }

    @Override
    public PageResult findByPages(int start, int limit) throws Exception {
        int pageIndex = start/limit+1;
        String hql = " from BsProxy s where 1=1";
        List paramsList = new ArrayList();
        String countHql = "select count(*) " + hql;
        PageResult ps = this.findByPage(hql, countHql, paramsList.toArray(),
                pageIndex, limit);
        return ps;
    }

    @Override
    public void setEntityClass() {
        this.entityClass = BsProxy.class;
    }
}
