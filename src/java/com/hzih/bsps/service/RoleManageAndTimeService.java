package com.hzih.bsps.service;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-29
 * Time: 上午11:28
 * To change this template use File | Settings | File Templates.
 */
public interface RoleManageAndTimeService {
    public void addRoleManageAndTime(int rolenameid,int timetype,Date starttime, Date endTime);
}
