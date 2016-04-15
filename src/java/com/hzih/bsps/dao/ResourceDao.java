package com.hzih.bsps.dao;

import com.hzih.bsps.domain.Resource;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-30
 * Time: 下午7:16
 * To change this template use File | Settings | File Templates.
 */
public interface ResourceDao {
    public ArrayList<Resource> findResources();
    public ArrayList<Resource> findResources(final int start, final int limit);
    public ArrayList<Resource> findResourcesById(int id);
    public ArrayList<Resource> findResourcesByIds(String ids);
    public ArrayList<Resource> findResourcesByName(String name);
    public ArrayList<Resource> findResourcesByLikeName(String name);
    public ArrayList<Resource> findResourcesByLikeName(String name,final int start, final int limit);
    public ArrayList<Resource> findResourcesByTypeAndLikeName(int type,String name);
    public ArrayList<Resource> findResourcesByTypeAndLikeName(int type, String name,final int start, final int limit);
    public void addResource(String name, int type, String description);
    public void delResource(Resource resource);
    public void updResource(Resource resource);
    public void delResources(ArrayList<Resource> list);
    public ArrayList<Resource> findResourcesByOtherRoleName(String rolename);
    public ArrayList<Resource> findResourcesByOtherRoleName(String rolename, final int start, final int limit);
    public ArrayList<Resource> findResourcesByOtherRoleName(String rolename, String name);
    public ArrayList<Resource> findResourcesByOtherRoleName(String rolename, String name, final int start, final int limit);
    public ArrayList<Resource> findResourcesByOtherRoleName(String rolename,int type, String name);
    public ArrayList<Resource> findResourcesByOtherRoleName(String rolename,int type, String name, final int start, final int limit);
    public ArrayList<Resource> findResourcesByPowerResourceName();
}
