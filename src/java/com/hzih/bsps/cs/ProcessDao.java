package com.hzih.bsps.cs;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-25
 * Time: 下午1:56
 * To change this template use File | Settings | File Templates.
 */
public interface ProcessDao extends BaseDao {

    public boolean add(ProcessEntity https)throws Exception;

    public boolean modify(ProcessEntity https)throws Exception;

    public boolean delete(ProcessEntity https)throws Exception;

    public PageResult findByPages(int start, int limit)throws Exception;

    ProcessEntity findById(int i);

    public boolean checkSourceIpPort(String sourceIp, String sourcePort);
}
