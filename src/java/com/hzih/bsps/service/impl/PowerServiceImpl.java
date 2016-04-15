package com.hzih.bsps.service.impl;

import com.hzih.bsps.dao.PowerDao;
import com.hzih.bsps.dao.ResourceDao;
import com.hzih.bsps.dao.ResourceIpDao;
import com.hzih.bsps.dao.ResourceWebDao;
import com.hzih.bsps.domain.Power;
import com.hzih.bsps.domain.Resource;
import com.hzih.bsps.domain.ResourceIp;
import com.hzih.bsps.domain.ResourceWeb;
import com.hzih.bsps.service.PowerService;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-12-2
 * Time: 下午7:03
 * To change this template use File | Settings | File Templates.
 */
public class PowerServiceImpl implements PowerService{

    private PowerDao powerDao;
    private ResourceDao resourceDao;
    private ResourceIpDao resourceIpDao;
    private ResourceWebDao resourceWebDao;

    public ResourceDao getResourceDao() {
        return resourceDao;
    }

    public void setResourceDao(ResourceDao resourceDao) {
        this.resourceDao = resourceDao;
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

    public PowerDao getPowerDao() {
        return powerDao;
    }

    public void setPowerDao(PowerDao powerDao) {
        this.powerDao = powerDao;
    }

    public String findPowers(int start,int limit) {
        ArrayList<Power> alllist = powerDao.findPowers();
        ArrayList<Power> list = powerDao.findPowers(start,limit);
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{success:true,'powerlist':").append(alllist.size()).append(",'powerrow':[");
        for(Power s : list){
            jsonb.append("{id:").append(s.getId()).append(",rolename:'").append(s.getRolename());
            int r = s.getRights();
            String rights="";
            if(0==r){
                rights="禁止";
            }else if(1==r){
                rights="允许";
            }
            jsonb.append("',rights:'").append(rights).append("',resourcename:'").append(s.getResourcename());
            jsonb.append("'},");
        }
        if(list.size()!=0){
            jsonb.deleteCharAt(jsonb.length()-1);
        }
        jsonb.append("]}");
        String jsons = jsonb.toString();
        return jsons;
    }

    @Override
    public String findPowerByNames(String rolename, String resourcename, int start, int limit) {
        ArrayList<Power> alllist = powerDao.findPowersByTwoLikeNames(rolename,resourcename);
        ArrayList<Power> list = powerDao.findPowersByTwoLikeNames(rolename,resourcename,start,limit);
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{success:true,'powerlist':").append(alllist.size()).append(",'powerrow':[");
        for(Power s : list){
            jsonb.append("{id:").append(s.getId()).append(",rolename:'").append(s.getRolename());
            int r = s.getRights();
            String rights="";
            if(0==r){
                rights="禁止";
            }else if(1==r){
                rights="允许";
            }
            jsonb.append("',rights:'").append(rights).append("',resourcename:'").append(s.getResourcename());
            jsonb.append("'},");
        }
        if(list.size()!=0){
            jsonb.deleteCharAt(jsonb.length()-1);
        }
        jsonb.append("]}");
        String jsons = jsonb.toString();
        return jsons;
    }

    @Override
    public void addPowers(String rolename, int rights, String renames) {
        String[] rns = renames.split(",");
        for(String s : rns) {
            powerDao.addPower(new Power(rolename,rights,s));
        }
    }

    @Override
    public void delPowers(String ids) {
        ArrayList<Power> list = powerDao.findPowersByIds(ids);
        powerDao.delPowers(list);
    }

    @Override
    public void updPowersRightsById(int id, int rights) {
        Power power = powerDao.findPowersById(id).get(0);
        power.setRights(rights);
        powerDao.updPower(power);
    }

    @Override
    public void startBsAgent() {
        ArrayList<Resource> resourcelist = resourceDao.findResourcesByPowerResourceName();
        for(Resource r : resourcelist) {
            int type = r.getType();
            int resourceid = r.getId();
            if(type==0){
                ResourceIp resourceIp = resourceIpDao.findResourceIpByResourceid(resourceid).get(0);
            }else if(type==1){
                ResourceWeb resourceWeb = resourceWebDao.findResourceWebByResourceid(resourceid).get(0);
            }
        }
    }

}
