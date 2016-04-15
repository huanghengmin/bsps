package com.hzih.bsps.utils;

import com.inetec.common.security.License;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

public class LicenseUtils {
    private static final Logger logger = Logger.getLogger(LicenseUtils.class);
    /**
     *  权限控制
     * @param isExistLicense    是否存在 usb-key
     * @return
     */
	public List<String> getNeedsLicenses(boolean isExistLicense) {
        String qxManager = "TOP_QXGL:SECOND_YHGL:SECOND_JSGL:SECOND_AQCL:";                                             //权限管理
        String wlManager = "TOP_WLGL:SECOND_JKGL:SECOND_LTCS:SECOND_LYGL:SECOND_PZGL:";                               //网络管理
        String xtManager = "TOP_XTGL:SECOND_PTSM:SECOND_PTGL:SECOND_ZSGL:SECOND_RZXZ:SECOND_BBSJ:";                 //系统管理
        String sjManager = "TOP_SJGL:SECOND_YHRZ:";                                                                         //审计管理
        String csManager = "TOP_CSGL:SECOND_CSPZ:SECOND_CSFW:";                                                             //cs
//        String csManager = "TOP_CSGL:SECOND_CS:";                                                             //cs
        String bsManager = "TOP_BSGL:SECOND_BSST:SECOND_BSPS:";                                                           //bs
        String xpManager = "TOP_XTPZ:SECOND_SYSLOG:";                                                                       //系统配置
        String jkManager = "TOP_JKGL:SECOND_ZJJK:";                                                                         //监控管理

        String permission = qxManager + wlManager + xtManager + sjManager + csManager + bsManager + xpManager+jkManager;
        if(isExistLicense){
	    	try{
                String license = License.getModules();//许可证允许的权限
                permission += license;
            } catch (Exception e) {
                logger.error("读取USB-KEY出错!");
            }
		}
		String[] permissions = permission.split(":");
		List<String> lps = new ArrayList<String>();
		for (int i = 0; i < permissions.length; i++) {
			lps.add(permissions[i]);
		}
		return lps;
	}
}
