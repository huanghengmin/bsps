package com.hzih.bsps.service.impl;

import com.hzih.bsps.dao.RoleUserDao;
import com.hzih.bsps.dao.UserManageDao;
import com.hzih.bsps.domain.RoleUser;
import com.hzih.bsps.domain.UserManage;
import com.hzih.bsps.service.UserManageService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-21
 * Time: 下午2:48
 * To change this template use File | Settings | File Templates.
 */
public class UserManageServiceImpl implements UserManageService{
    private UserManageDao userManageDao;
    private RoleUserDao roleUserDao;

    public RoleUserDao getRoleUserDao() {
        return roleUserDao;
    }

    public void setRoleUserDao(RoleUserDao roleUserDao) {
        this.roleUserDao = roleUserDao;
    }

    public void setUserManageDao(UserManageDao userManageDao) {
        this.userManageDao = userManageDao;
    }

    public UserManageDao getUserManageDao() {
        return userManageDao;
    }

    public ArrayList<UserManage> findUserManages() {
        return userManageDao.findUserManages();
    }

    @Override
    public ArrayList<UserManage> findUserManages(int start, int limit) {
        return userManageDao.findUserManages(start,limit);
    }

    @Override
    public UserManage findUserManageById(int id) {
        ArrayList<UserManage> list = userManageDao.findUserManageById(id);
        return list.get(0);
    }

    @Override
    public ArrayList<UserManage> findUserManageByWhere(String coloum, String name) {
        ArrayList list = userManageDao.findUserManageByWhere(coloum, name);
        return list;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ArrayList<UserManage> findUserManageByWhere(String coloum, String name, int start, int limit) {
        ArrayList list = userManageDao.findUserManageByWhere(coloum, name, start, limit);
        return list;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ArrayList<UserManage> findUserManagesByCacn(String cacn) {
        ArrayList<UserManage> list = userManageDao.findUserManageByCacn(cacn);
        return list;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean checkUserManageByCacn(String cacn) {
        ArrayList<UserManage> list = userManageDao.findUserManageByCacn(cacn);
        if(null==list||list.size()==0){
            return false;
        }
        return true;
    }

    @Override
    public void addUserManage(String cacn, String province, String city, String department, String policestation, String email, String tel, String address, String idcard, String description) {
        UserManage userManage = new UserManage(cacn,province,city,department,policestation,email,tel,address,idcard,description);
        userManageDao.addUserManage(userManage);
    }

    @Override
    public void delUserManageById(int id) {
        UserManage userManage = findUserManageById(id);
        userManageDao.delUserManage(userManage);
    }

    @Override
    public void delUserManageByIds(String ids) {
        String[] ida = ids.split(",");
        for(String s : ida) {
            int id = Integer.parseInt(s);
            ArrayList<RoleUser> list = roleUserDao.findRoleUsersByUserId(id);
            roleUserDao.delRoleUsers(list);
            delUserManageById(id);
        }
    }

    @Override
    public void updUserManage(int id, String cacn, String province, String city, String department, String policestation, String email, String tel, String address, String idcard, String description) {
        UserManage userManage = findUserManageById(id);
        userManage.setCacn(cacn);
        userManage.setProvince(province);
        userManage.setCity(city);
        userManage.setDepartment(department);
        userManage.setPolicestation(policestation);
        userManage.setEmail(email);
        userManage.setTel(tel);
        userManage.setAddress(address);
        userManage.setIdcard(idcard);
        userManage.setDescription(description);
        userManageDao.updUserManage(userManage);
    }

    @Override
    public void updUserManage(UserManage userManage) {
        userManageDao.updUserManage(userManage);
    }



    @Override
    public String findUserManagesByRoleManageId(int roleid,int start,int limit) {
        ArrayList<UserManage> allList = userManageDao.findUserManagesByRoleManageId(roleid);
        ArrayList<UserManage> list = userManageDao.findUserManagesByRoleManageId(roleid,start,limit);
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{success:true,'urlist':").append(allList.size()).append(",'urrow':[");
        for(UserManage s : list){
            jsonb.append("{id:").append(s.getId()).append(",cacn:'").append(s.getCacn());
            jsonb.append("',province:'").append(s.getProvince()).append("',city:'").append(s.getCity());
            jsonb.append("',department:'").append(s.getDepartment()).append("',policestation:'").append(s.getPolicestation());
            jsonb.append("',email:'").append(s.getEmail()).append("',tel:'").append(s.getTel());
            jsonb.append("',address:'").append(s.getAddress()).append("',idcard:'").append(s.getIdcard());
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
    public String findUserManagesByOtherRoleId(int roleid,int start,int limit) {
        List<UserManage> list = userManageDao.findUserManagesByOtherRoleId(roleid);
        List<UserManage> userlist = userManageDao.findUserManagesByOtherRoleId(roleid,start,limit);
        String jsons = null;

        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{success:true,'modellist':").append(list.size()).append(",'rows':[");
        for(UserManage s : userlist){
            jsonb.append("{id:").append(s.getId()).append(",cacn:'").append(s.getCacn());
            jsonb.append("',province:'").append(s.getProvince()).append("',city:'").append(s.getCity());
            jsonb.append("',department:'").append(s.getDepartment()).append("',policestation:'").append(s.getPolicestation());
            jsonb.append("',email:'").append(s.getEmail()).append("',tel:'").append(s.getTel());
            jsonb.append("',address:'").append(s.getAddress()).append("',idcard:'").append(s.getIdcard());
            jsonb.append("',description:'").append(s.getDescription()).append("'},");
        }
        jsonb.deleteCharAt(jsonb.length()-1);
        jsonb.append("]}");
        jsons = jsonb.toString();
        return jsons;
    }


}
