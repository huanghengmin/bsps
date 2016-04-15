package com.hzih.bsps.service;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-12-2
 * Time: 下午7:02
 * To change this template use File | Settings | File Templates.
 */
public interface PowerService {
    public String findPowers(int start,int limit);
    public String findPowerByNames(String rolename, String resourcename, int start, int limit);
    public void addPowers(String rolename, int rights, String renames);
    public void delPowers(String ids);
    public void updPowersRightsById(int id,int rights);
    public void startBsAgent();
}
