package com.hzih.bsps.web.action.serve;

import com.hzih.bsps.web.action.ActionBase;
import com.inetec.common.util.Proc;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-3-6
 * Time: 上午10:02
 * To change this template use File | Settings | File Templates.
 */
public class SquidServerStatusAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(SquidServerStatusAction.class);

    public String openServer() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = "1";
        try {
            Proc proc= new Proc();
            proc.exec("service squid start");
            Thread.sleep(1000*2);
            proc.exec("service squid status");
            String msg_on = proc.getOutput();
//            logger.info(msg_on);
//            if(msg_on.contains("No running copy")) {
                if (msg_on.contains("is running")) {
                msg = "1";
            }else {
                msg = "0";
            }
        } catch (Exception e) {
            msg = "0";
        }
        String json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String closeServer() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = "1";
        try {
            Proc proc= new Proc();
            proc.exec("service squid stop");
            Thread.sleep(1000*15);
            proc.exec("service squid status");
            String msg_on = proc.getOutput();
//            logger.info(msg_on);
//            if(msg_on.contains("No running copy")) {
                if (msg_on.contains("is running")) {
                msg = "1";
            }else {
                msg = "0";
            }
        } catch (Exception e) {
            msg = "0";
        }
        String json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String checkServerStatus() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = "1";
        try{
            Proc proc = new Proc();
            proc.exec("service squid status");
            String msg_on = proc.getOutput();
//            logger.info("error_output:"+msg_on);
//            logger.info("output:"+ proc.getOutput());
//            if(msg_on.contains("No running copy")) {
            if (msg_on.contains("is running")) {
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

    public String reloadServer() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = "1";
        try{
            Proc proc= new Proc();
            proc.exec("service squid restart");
            Thread.sleep(1000*15);
            try{
                proc.exec("service squid status");
                String msg_on = proc.getOutput();
//                logger.info(msg_on);
//                if(msg_on.contains("No running copy")) {
                if (msg_on.contains("is running")) {
                    msg = "1";
                }else {
                    msg = "0";
                }
            } catch (Exception e){
                msg = "0";
            }
        } catch (Exception e){
            msg = "0";
        }
        String json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }
}
