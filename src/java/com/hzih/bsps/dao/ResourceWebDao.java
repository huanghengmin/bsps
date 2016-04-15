package com.hzih.bsps.dao;

import com.hzih.bsps.domain.ResourceWeb;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-30
 * Time: 下午9:11
 * To change this template use File | Settings | File Templates.
 */
public interface ResourceWebDao {
    public ArrayList<ResourceWeb> findResourceWebByResourceid(int resourceid);
    public ArrayList<ResourceWeb> findResourceWebByResourceids(String ids);
    public void addResourceWeb(ResourceWeb resourceWeb);
    public void delResourceWeb(ResourceWeb resourceWeb);
    public void delResourceWebs(ArrayList<ResourceWeb> list);
}
