package com.hzih.bsps.service;

import com.hzih.bsps.domain.WapControl;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-3-4
 * Time: 下午4:20
 * To change this template use File | Settings | File Templates.
 */
public interface WapControlService {

    String findWapControls();

    boolean checkWapControlByIp(String ip);

    void addWapControl(String ip);

    void delWapControlByIds(String ids);

    WapControl findWapControlById(int id);

    void updWapControl(WapControl wapControl, String ip);
}
