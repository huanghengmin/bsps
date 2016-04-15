package com.hzih.bsps.service.impl;

import com.hzih.bsps.dao.WapControlDao;
import com.hzih.bsps.domain.WapControl;
import com.hzih.bsps.service.WapControlService;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-3-4
 * Time: 下午4:20
 * To change this template use File | Settings | File Templates.
 */
public class WapControlServiceImpl implements WapControlService{
    private WapControlDao wapControlDao;

    public WapControlDao getWapControlDao() {
        return wapControlDao;
    }

    public void setWapControlDao(WapControlDao wapControlDao) {
        this.wapControlDao = wapControlDao;
    }

    @Override
    public String findWapControls() {
        ArrayList<WapControl> list = wapControlDao.findAllWapControls();
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{success:true,'waplist':").append(list.size()).append(",'waprow':[");
        for(WapControl s : list){
            jsonb.append("{id:").append(s.getId()).append(",wapip:'").append(s.getIp()).append("'},");
        }
        if(list.size()!=0){
            jsonb.deleteCharAt(jsonb.length()-1);
        }
        jsonb.append("]}");
        String jsons = jsonb.toString();
        return jsons;
    }

    @Override
    public boolean checkWapControlByIp(String ip) {
        ArrayList<WapControl> list = wapControlDao.findWapControlsByIp(ip);
        if(null==list||list.size()==0){
            return false;
        }
        return true;
    }

    @Override
    public void addWapControl(String ip) {
        wapControlDao.addWapControl(new WapControl(ip));
    }

    @Override
    public void delWapControlByIds(String ids) {
        ArrayList<WapControl> list = wapControlDao.findWapControlsByIds(ids);
        wapControlDao.delWapControls(list);
    }

    @Override
    public WapControl findWapControlById(int id) {
        return wapControlDao.findWapControlsById(id).get(0);
    }

    @Override
    public void updWapControl(WapControl wapControl, String ip) {
        wapControl.setIp(ip);
        wapControlDao.updWapControl(wapControl);
    }
}
