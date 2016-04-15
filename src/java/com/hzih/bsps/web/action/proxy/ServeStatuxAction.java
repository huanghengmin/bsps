package com.hzih.bsps.web.action.proxy;

//import com.hzih.myjfree.LinuxUtil;
import com.hzih.bsps.utils.StringContext;
import com.hzih.bsps.web.action.ActionBase;
import com.hzih.bsps.web.action.bs.CertUtils;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-3-14
 * Time: 下午6:31
 * To change this template use File | Settings | File Templates.
 */
public class ServeStatuxAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(ServeStatuxAction.class);
    
    public String openServe() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = "0";
        try{
//            LinuxUtil linuxUtil = new LinuxUtil();
//            String lines = linuxUtil.getLinuxByCommand("192.168.1.232", "root", "admin","cd StringContext.nginxPath;./nginx;cd;ps -ef");
            CertUtils.execute(StringContext.nginxSbin+";./nginx;");
            String lines = CertUtils.executeString("ps -ef");
            if(lines.indexOf("nginx:")!=-1) {
                msg = "1";
            }else {
                msg = "0";
            }
        } catch (Exception e){
            msg = "0";
        }
        String json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String closeServe() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = "0";
        try{
//            LinuxUtil linuxUtil = new LinuxUtil();
//            String lines = linuxUtil.getLinuxByCommand("192.168.1.232", "root", "admin","cd StringContext.nginxPath;./nginx -s stop;cd;ps -ef");
            CertUtils.execute("cd "+StringContext.nginxSbin+";./nginx -s stop;");
            String lines = CertUtils.executeString("ps -ef");
            if(lines.indexOf("nginx:")==-1) {
                msg = "1";
            }else {
                msg = "0";
            }
        } catch (Exception e){
            msg = "0";
        }
        String json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }
    
    public String checkServeStaus() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = "0";
        try{
//            LinuxUtil linuxUtil = new LinuxUtil();
//            String lines = linuxUtil.getProcessInfo("192.168.1.232", "root", "admin", "ps -eo pid,lstart,etime,args", "nginx:");
            String lines = CertUtils.executeContainsProcess("ps -eo pid,lstart,etime,args", "nginx:");
            if("0".endsWith("nginx:")) {
                msg = "0";
            }else {
                String[] aa = lines.split("\\s{1,}");
//                for(int i=0;i<aa.length;i++) {
//                    System.out.println(i+" : "+aa[i]);
//                }
                msg = "自"+aa[6]+"-"+aa[3]+"-"+aa[4]+" "+aa[5]+"至今,共"+aa[7];
            }
        } catch (Exception e){
            msg = "0";
        }
        String json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }
}
