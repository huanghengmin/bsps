package com.hzih.bsps.service;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-30
 * Time: 下午7:29
 * To change this template use File | Settings | File Templates.
 */
public interface ResourceService {
    public String findResources(int start, int limit);
    public String findResourcesByTypeAndLikeName(int type, String name, int start, int limit);
    public String findResourcesByOtherRoleName(String rolename, int start, int limit);
    public boolean checkResourceByName(String naem);
    public void addIpResource(String name,String ipaddress,String subnetmask,String description);
    public void addWebResource(String name,String agreement, String ipaddress, String port, String url, String description);
    public void delResourcesByIds(String ids);

    public String findResourcesByOtherRoleName(String rolename,int typex, String name, int start, int limit);

    public String findResourceIp(int resourceid);

    public String findResourceWeb(int resourceid);
}
