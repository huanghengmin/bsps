package com.hzih.bsps.cs;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-25
 * Time: 下午1:56
 * To change this template use File | Settings | File Templates.
 */
public class ProcessDaoImpl extends MyDaoSupport implements ProcessDao {
    @Override
    public void setEntityClass() {
        this.entityClass = ProcessEntity.class;
    }


    @Override
    public boolean add(ProcessEntity https) throws Exception {
        boolean flag =false;
        super.getHibernateTemplate().save(https);
        flag = true;
        return flag;
    }

    @Override
    public boolean modify(ProcessEntity https) throws Exception {
        boolean flag =false;
        super.getHibernateTemplate().update(https);
        flag = true;
        return flag;
    }

    @Override
    public boolean delete(ProcessEntity https) throws Exception {
        boolean flag =false;
        super.getHibernateTemplate().delete(https);
        flag = true;
        return flag;
    }

    @Override
    public PageResult findByPages(int start, int limit) throws Exception {
        int pageIndex = start/limit+1;
        String hql = "from ProcessEntity ";
        String countHql = "select count(*) " + hql;
        PageResult ps = this.findByPage(hql, countHql, pageIndex, limit);
        return ps;
    }

    @Override
    public ProcessEntity findById(int i) {
        String hql="from ProcessEntity s where s.id = "+i;
        List<ProcessEntity> httpses  = super.getHibernateTemplate().find(hql);
        if(httpses!=null&&httpses.size()>0)
            return httpses.get(0);
        else
            return null;
    }

    @Override
    public boolean checkSourceIpPort(String sourceIp, String sourcePort) {
        String hql="from ProcessEntity s where s.sourceIp = '"+sourceIp +"' and  s.sourcePort='"+sourcePort+"'";
        List<ProcessEntity> httpses  = super.getHibernateTemplate().find(hql);
        if(httpses!=null&&httpses.size()>0)
            return true;
        else
            return false;
    }
}
