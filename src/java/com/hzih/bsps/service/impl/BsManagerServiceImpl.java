package com.hzih.bsps.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsps.dao.BsManagerDao;
import com.hzih.bsps.domain.BsManager;
import com.hzih.bsps.service.BsManagerService;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-22
 * Time: 下午3:16
 * To change this template use File | Settings | File Templates.
 */
public class BsManagerServiceImpl implements BsManagerService{
    private BsManagerDao  bsManagerDao;

    public BsManagerDao getBsManagerDao() {
        return bsManagerDao;
    }

    public void setBsManagerDao(BsManagerDao bsManagerDao) {
        this.bsManagerDao = bsManagerDao;
    }

    @Override
    public boolean add(BsManager bsManager) throws Exception {
        return bsManagerDao.add(bsManager);
    }

    @Override
    public boolean modify(BsManager bsManager) throws Exception {
        return bsManagerDao.modify(bsManager);
    }

    @Override
    public boolean delete(BsManager bsManager) throws Exception {
        return bsManagerDao.delete(bsManager);
    }

    @Override
    public BsManager findById(int id) throws Exception {
        return bsManagerDao.findById(id);
    }

    @Override
    public PageResult findByPages(int start, int limit) throws Exception {
        return bsManagerDao.findByPages(start,limit);
    }
}
