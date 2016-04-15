package com.hzih.bsps.service.impl;

import com.hzih.bsps.dao.RoleManageAndTimeDao;
import com.hzih.bsps.domain.RoleManageAndTime;
import com.hzih.bsps.service.RoleManageAndTimeService;

import java.util.Date;


/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-29
 * Time: 上午11:28
 * To change this template use File | Settings | File Templates.
 */
public class RoleManageAndTimeServiceImpl implements RoleManageAndTimeService{
    RoleManageAndTimeDao roleManageAndTimeDao;

    public RoleManageAndTimeDao getRoleManageAndTimeDao() {
        return roleManageAndTimeDao;
    }
    public void setRoleManageAndTimeDao(RoleManageAndTimeDao roleManageAndTimeDao) {
        this.roleManageAndTimeDao = roleManageAndTimeDao;
    }

    @Override
    public void addRoleManageAndTime(int rolenameid, int timetype, Date starttime, Date endTime) {
        roleManageAndTimeDao.addRoleManageAndTime( new RoleManageAndTime(rolenameid,timetype,starttime,endTime) );
    }

}
