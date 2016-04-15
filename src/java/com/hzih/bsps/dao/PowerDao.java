package com.hzih.bsps.dao;

import com.hzih.bsps.domain.Power;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-12-2
 * Time: 下午6:53
 * To change this template use File | Settings | File Templates.
 */
public interface PowerDao {
    public ArrayList<Power> findPowers();
    public ArrayList<Power> findPowers(final int start,final int limit);
    public ArrayList<Power> findPowersById(int id);
    public ArrayList<Power> findPowersByIds(String ids);
    public ArrayList<Power> findPowersByRoleName(String rolename);
    public ArrayList<Power> findPowersByResourceName(String resourcename);
    public ArrayList<Power> findPowersByTwoLikeNames(String rolename, String resourcename);
    public ArrayList<Power> findPowersByTwoLikeNames(String rolename, String resourcename,final int start,final int limit);
    public void addPower(Power power);
    public void delPower(Power power);
    public void delPowers(ArrayList<Power> list);
    public void updPower(Power power);
}
