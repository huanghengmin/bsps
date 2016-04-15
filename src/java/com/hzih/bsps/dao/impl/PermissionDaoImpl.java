package com.hzih.bsps.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import com.hzih.bsps.dao.PermissionDao;
import com.hzih.bsps.domain.Permission;

public class PermissionDaoImpl extends MyDaoSupport implements PermissionDao {

	@Override
	public void setEntityClass() {
		this.entityClass = Permission.class;
	}

}
