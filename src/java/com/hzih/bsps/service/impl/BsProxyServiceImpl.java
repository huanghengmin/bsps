package com.hzih.bsps.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsps.dao.BsProxyDao;
import com.hzih.bsps.domain.BsProxy;
import com.hzih.bsps.service.BsProxyService;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-22
 * Time: 下午3:16
 * To change this template use File | Settings | File Templates.
 */
public class BsProxyServiceImpl implements BsProxyService{
    private BsProxyDao proxyDao;

    public BsProxyDao getProxyDao() {
        return proxyDao;
    }

    public void setProxyDao(BsProxyDao proxyDao) {
        this.proxyDao = proxyDao;
    }

    @Override
    public boolean add(BsProxy bsProxy) throws Exception {
        return proxyDao.add(bsProxy);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean modify(BsProxy bsProxy) throws Exception {
        return proxyDao.modify(bsProxy);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean delete(BsProxy bsProxy) throws Exception {
        return proxyDao.delete(bsProxy);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public BsProxy findById(int id) throws Exception {
        return proxyDao.findById(id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PageResult findByPages(int start, int limit) throws Exception {
        return proxyDao.findByPages(start,limit);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
