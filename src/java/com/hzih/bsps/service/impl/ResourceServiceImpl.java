package com.hzih.bsps.service.impl;

import com.hzih.bsps.dao.PowerDao;
import com.hzih.bsps.dao.ResourceDao;
import com.hzih.bsps.dao.ResourceIpDao;
import com.hzih.bsps.dao.ResourceWebDao;
import com.hzih.bsps.domain.Power;
import com.hzih.bsps.domain.Resource;
import com.hzih.bsps.domain.ResourceIp;
import com.hzih.bsps.domain.ResourceWeb;
import com.hzih.bsps.service.ResourceService;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-30
 * Time: 下午7:29
 * To change this template use File | Settings | File Templates.
 */
public class ResourceServiceImpl implements ResourceService{
    private ResourceDao resourceDao;
    private ResourceIpDao resourceIpDao;
    private ResourceWebDao resourceWebDao;
    private PowerDao powerDao;

    public PowerDao getPowerDao() {
        return powerDao;
    }

    public void setPowerDao(PowerDao powerDao) {
        this.powerDao = powerDao;
    }

    public ResourceIpDao getResourceIpDao() {
        return resourceIpDao;
    }

    public void setResourceIpDao(ResourceIpDao resourceIpDao) {
        this.resourceIpDao = resourceIpDao;
    }

    public ResourceWebDao getResourceWebDao() {
        return resourceWebDao;
    }

    public void setResourceWebDao(ResourceWebDao resourceWebDao) {
        this.resourceWebDao = resourceWebDao;
    }

    public ResourceDao getResourceDao() {
        return resourceDao;
    }

    public void setResourceDao(ResourceDao resourceDao) {
        this.resourceDao = resourceDao;
    }


    @Override
    public String findResources(int start, int limit) {
        ArrayList<Resource> alllist = resourceDao.findResources();
        ArrayList<Resource> list = resourceDao.findResources(start, limit);
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{success:true,'relist':").append(alllist.size()).append(",'rerow':[");
        for(Resource s : list){
            jsonb.append("{id:").append(s.getId()).append(",name:'").append(s.getName());
            jsonb.append("',type:'");
            int t = s.getType();
            String type="";
            if(0==t){
                type="IP地址段";
            }else if(1==t){
                type="WEB应用";
            }
            jsonb.append(type);
            jsonb.append("',description:'").append(s.getDescription()).append("'},");
        }
        if(list.size()!=0){
            jsonb.deleteCharAt(jsonb.length()-1);
        }
        jsonb.append("]}");
        String jsons = jsonb.toString();
        return jsons;
    }

    @Override
    public String findResourcesByTypeAndLikeName(int typex, String name, int start, int limit) {
        ArrayList<Resource> list = null;
        ArrayList<Resource> alllist = null;
        if(typex==9){
            alllist = resourceDao.findResourcesByLikeName(name);
            list = resourceDao.findResourcesByLikeName(name,start,limit);
        }else {
            alllist=resourceDao.findResourcesByTypeAndLikeName(typex,name);
            list=resourceDao.findResourcesByTypeAndLikeName(typex,name,start,limit);
        }
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{success:true,'relist':").append(alllist.size()).append(",'rerow':[");
        for(Resource s : list){
            jsonb.append("{id:").append(s.getId()).append(",name:'").append(s.getName());
            jsonb.append("',type:'");
            int t = s.getType();
            String type="";
            if(0==t){
                type="IP地址段";
            }else if(1==t){
                type="WEB应用";
            }
            jsonb.append(type);
            jsonb.append("',description:'").append(s.getDescription()).append("'},");
        }
        if(list.size()!=0){
            jsonb.deleteCharAt(jsonb.length()-1);
        }
        jsonb.append("]}");
        String jsons = jsonb.toString();
        return jsons;
    }

    @Override
    public String findResourcesByOtherRoleName(String rolename, int start, int limit) {
        ArrayList<Resource> alllist = resourceDao.findResourcesByOtherRoleName(rolename);
        ArrayList<Resource> list = resourceDao.findResourcesByOtherRoleName(rolename,start,limit);
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{success:true,'relist':").append(alllist.size()).append(",'rerow':[");
        for(Resource s : list){
            jsonb.append("{id:").append(s.getId()).append(",name:'").append(s.getName());
            jsonb.append("',type:'");
            int t = s.getType();
            String type="";
            if(0==t){
                type="IP地址段";
            }else if(1==t){
                type="WEB应用";
            }
            jsonb.append(type);
            jsonb.append("',description:'").append(s.getDescription()).append("'},");
        }
        if(list.size()!=0){
            jsonb.deleteCharAt(jsonb.length()-1);
        }
        jsonb.append("]}");
        String jsons = jsonb.toString();
        return jsons;
    }

    @Override
    public String findResourcesByOtherRoleName(String rolename,int typex, String name, int start, int limit) {
        ArrayList<Resource> alllist = null;
        ArrayList<Resource> list = null;
        if(typex==9){
            alllist=resourceDao.findResourcesByOtherRoleName(rolename,name);
            list=resourceDao.findResourcesByOtherRoleName(rolename,name,start,limit);
        }else {
            alllist=resourceDao.findResourcesByOtherRoleName(rolename,typex,name);
            list=resourceDao.findResourcesByOtherRoleName(rolename,typex,name,start,limit);
        }
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{success:true,'relist':").append(alllist.size()).append(",'rerow':[");
        for(Resource s : list){
            jsonb.append("{id:").append(s.getId()).append(",name:'").append(s.getName());
            jsonb.append("',type:'");
            int t = s.getType();
            String type="";
            if(0==t){
                type="IP地址段";
            }else if(1==t){
                type="WEB应用";
            }
            jsonb.append(type);
            jsonb.append("',description:'").append(s.getDescription()).append("'},");
        }
        if(list.size()!=0){
            jsonb.deleteCharAt(jsonb.length()-1);
        }
        jsonb.append("]}");
        String jsons = jsonb.toString();
        return jsons;
    }

    @Override
    public String findResourceIp(int resourceid){
        ResourceIp s = resourceIpDao.findResourceIpByResourceid(resourceid).get(0);
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{ipaddress:'").append(s.getIpaddress()).append("',subnetmask:'").append(s.getSubnetmask()).append("'}");
        String jsons = jsonb.toString();
        return jsons;
    }
    @Override
    public String findResourceWeb(int resourceid){
        ResourceWeb s = resourceWebDao.findResourceWebByResourceid(resourceid).get(0);
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{agreement:'").append(s.getAgreement()).append("',ipaddress:'").append(s.getIpaddress());
        jsonb.append("',portweb:'").append(s.getPort()).append("',urlweb:'").append(s.getUrl());
        jsonb.append("'}");
        String jsons = jsonb.toString();
        return jsons;
    }

    @Override
    public boolean checkResourceByName(String name) {
        ArrayList<Resource> list = resourceDao.findResourcesByName(name);
        if(null==list||list.size()==0){
            return false;
        }
        return true;
    }

    @Override
    public void addIpResource(String name, String ipaddress, String subnetmask, String description) {
        resourceDao.addResource(name,0,description);
        Resource resource = resourceDao.findResourcesByName(name).get(0);
        ResourceIp resourceIp = new ResourceIp(resource.getId(),ipaddress,subnetmask);
        resourceIpDao.addResourceIp(resourceIp);
    }

    @Override
    public void addWebResource(String name,String agreement, String ipaddress, String port, String url, String description) {
        resourceDao.addResource(name,1,description);
        Resource resource = resourceDao.findResourcesByName(name).get(0);
        ResourceWeb resourceWeb = new ResourceWeb(resource.getId(),agreement,ipaddress,port,url);
        resourceWebDao.addResourceWeb(resourceWeb);
    }

    @Override
    public void delResourcesByIds(String ids) {
        ArrayList<Resource> list = resourceDao.findResourcesByIds(ids);
        resourceDao.delResources(list);
        resourceIpDao.delResourceIps(resourceIpDao.findResourceIpByResourceids(ids));
        resourceWebDao.delResourceWebs(resourceWebDao.findResourceWebByResourceids(ids));
        for(Resource r:list){
            String rName = r.getName();
            ArrayList<Power> plist = powerDao.findPowersByResourceName(rName);
            powerDao.delPowers(plist);
        }
    }

}
