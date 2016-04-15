package com.hzih.bsps.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.bsps.dao.BsManagerDao;
import com.hzih.bsps.domain.BsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-22
 * Time: 下午2:22
 * To change this template use File | Settings | File Templates.
 */
public class BsManagerDaoImpl  extends MyDaoSupport implements BsManagerDao{

    @Override
    public void setEntityClass() {
        this.entityClass = BsManager.class;
    }

    @Override
    public boolean add(BsManager bsManager) throws Exception {
        boolean flag =false;
        try {
            super.getHibernateTemplate().save(bsManager);
            flag = true;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean modify(BsManager bsManager) throws Exception {
        boolean flag =false;
        try {
            super.getHibernateTemplate().saveOrUpdate(bsManager);
            flag = true;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean delete(BsManager bsManager) throws Exception {
        boolean flag =false;
        try {
            super.getHibernateTemplate().delete(bsManager);
            flag = true;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return flag;
    }

    @Override
    public BsManager findById(int id) throws Exception {
        String hql= String.format("from BsManager bsmanager where bsmanager.id ='%d'", id);
        List<BsManager> bsManagers = null;
        try {
            bsManagers = super.getHibernateTemplate().find(hql);
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        if(bsManagers.size()>0&&bsManagers!=null){
            return bsManagers.get(0);
        }else {
            return null;
        }
    }

    @Override
    public PageResult findByPages(int start, int limit) throws Exception {
        int pageIndex = start/limit+1;
        String hql = " from BsManager s where 1=1";
        List paramsList = new ArrayList();
        String countHql = "select count(*) " + hql;
        PageResult ps = this.findByPage(hql, countHql, paramsList.toArray(),
                pageIndex, limit);
        return ps;
    }
}
