package com.hzih.bsps.dao;

import cn.collin.commons.dao.BaseDao;
import com.hzih.bsps.domain.Role;

public interface RoleDao extends BaseDao {

    public Role findByName(String name) throws Exception;
}
