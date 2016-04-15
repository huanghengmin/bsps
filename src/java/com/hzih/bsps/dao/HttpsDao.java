package com.hzih.bsps.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;
import com.hzih.bsps.domain.Https;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-22
 * Time: 下午2:55
 * To change this template use File | Settings | File Templates.
 */
public interface HttpsDao extends BaseDao {
    public boolean add(Https https)throws Exception;

    public boolean modify(Https https)throws Exception;

    public boolean delete(Https https)throws Exception;

    public PageResult findByPages(int start, int limit)throws Exception;

    Https findById(int i);

    List<Https> findAllHttp();

    List<Https> findAllHttps();

    boolean findByManagerAndProt(String managerip);

    boolean checkSiteBlock(String id);

    List<Https> findAllTcp();

    List<Https> findAllTHttp();
}
