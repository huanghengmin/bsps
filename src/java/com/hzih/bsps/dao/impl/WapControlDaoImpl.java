package com.hzih.bsps.dao.impl;

import com.hzih.bsps.dao.WapControlDao;
import com.hzih.bsps.domain.WapControl;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-3-4
 * Time: 下午3:51
 * To change this template use File | Settings | File Templates.
 */
public class WapControlDaoImpl extends HibernateDaoSupport implements WapControlDao{

    @Override
    public ArrayList<WapControl> findAllWapControls() {
        String hql = "select t from com.hzih.bsps.domain.WapControl t";
        ArrayList<WapControl> list = (ArrayList<WapControl>) this.getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public ArrayList<WapControl> findWapControlsById(int id) {
        String hql = "select t from com.hzih.bsps.domain.WapControl t where t.id=?";
        ArrayList<WapControl> list = (ArrayList<WapControl>) this.getHibernateTemplate().find(hql,id);
        return list;
    }
    @Override
    public ArrayList<WapControl> findWapControlsByIds(String ids) {
        String hql = "select t from com.hzih.bsps.domain.WapControl t where t.id in ("+ids+")";
        ArrayList<WapControl> list = (ArrayList<WapControl>) this.getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public ArrayList<WapControl> findWapControlsByIp(String ip) {
        String hql = "select t from com.hzih.bsps.domain.WapControl t where t.ip=?";
        ArrayList<WapControl> list = (ArrayList<WapControl>) this.getHibernateTemplate().find(hql,ip);
        return list;
    }

    @Override
    public void addWapControl(WapControl wapControl) {
        this.getHibernateTemplate().save(wapControl);
    }

    @Override
    public void delWapControl(WapControl wapControl) {
        this.getHibernateTemplate().delete(wapControl);
    }

    @Override
    public void delWapControls(ArrayList<WapControl> list) {
        this.getHibernateTemplate().deleteAll(list);
    }

    @Override
    public void updWapControl(WapControl wapControl) {
        this.getHibernateTemplate().update(wapControl);
    }
}
