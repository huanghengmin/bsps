package com.hzih.bsps.dao;

import com.hzih.bsps.domain.WapControl;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-3-4
 * Time: 下午3:51
 * To change this template use File | Settings | File Templates.
 */
public interface WapControlDao {

    ArrayList<WapControl> findAllWapControls();

    ArrayList<WapControl> findWapControlsById(int id);

    ArrayList<WapControl> findWapControlsByIds(String ids);

    ArrayList<WapControl> findWapControlsByIp(String ip);

    void addWapControl(WapControl wapControl);

    void delWapControl(WapControl wapControl);

    void delWapControls(ArrayList<WapControl> list);

    void updWapControl(WapControl wapControl);
}
