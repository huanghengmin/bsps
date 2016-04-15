package com.hzih.bsps.dao;

import com.hzih.bsps.domain.ResourceIp;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-30
 * Time: 下午9:04
 * To change this template use File | Settings | File Templates.
 */
public interface ResourceIpDao {
    public ArrayList<ResourceIp> findResourceIpByResourceid(int resourceid);
    public ArrayList<ResourceIp> findResourceIpByResourceids(String ids);
    public void addResourceIp(ResourceIp resourceIp);
    public void delResourceIp(ResourceIp resourceIp);
    public void delResourceIps(ArrayList<ResourceIp> list);
}
