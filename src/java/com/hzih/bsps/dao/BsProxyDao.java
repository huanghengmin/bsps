package com.hzih.bsps.dao;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsps.domain.BsProxy;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-22
 * Time: 下午2:14
 * To change this template use File | Settings | File Templates.
 */
public interface BsProxyDao {

    public boolean add(BsProxy bsProxy)throws Exception;

    public boolean modify(BsProxy bsProxy)throws Exception;

    public boolean delete(BsProxy bsProxy)throws Exception;

    public BsProxy findById(int id)throws Exception;

    public PageResult findByPages(int start, int limit)throws Exception;

}
