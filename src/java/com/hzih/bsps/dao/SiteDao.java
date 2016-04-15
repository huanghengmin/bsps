package com.hzih.bsps.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;
import com.hzih.bsps.domain.Site;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-22
 * Time: 下午2:55
 * To change this template use File | Settings | File Templates.
 */
public interface SiteDao extends BaseDao{
    public boolean add(Site site)throws Exception;

    public boolean modify(Site site)throws Exception;

    public boolean delete(Site site)throws Exception;

    public PageResult findByPages(int start, int limit)throws Exception;

    boolean check_site_name(String site_name);

    Site findById(int siteId);

}
