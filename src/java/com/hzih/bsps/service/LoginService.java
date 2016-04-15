package com.hzih.bsps.service;

import com.hzih.bsps.domain.Account;

public interface LoginService {

	Account getAccountByNameAndPwd(String name, String pwd) ;

    Account getAccountByName(String name) ;
}
