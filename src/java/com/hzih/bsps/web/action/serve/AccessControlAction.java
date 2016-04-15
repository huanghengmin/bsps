package com.hzih.bsps.web.action.serve;

import com.hzih.bsps.domain.WapControl;
import com.hzih.bsps.service.WapControlService;
import com.hzih.bsps.web.action.ActionBase;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-3-4
 * Time: 下午4:30
 * To change this template use File | Settings | File Templates.
 */
public class AccessControlAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(AccessControlAction.class);

    private String ids;
    private int id;
    private String wapip;

    private WapControlService wapControlService;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWapip() {
        return wapip;
    }

    public void setWapip(String wapip) {
        this.wapip = wapip;
    }

    public WapControlService getWapControlService() {
        return wapControlService;
    }

    public void setWapControlService(WapControlService wapControlService) {
        this.wapControlService = wapControlService;
    }

    public String findServeStatus () throws Exception {
        String jsons = "{success:true,'servelist':3,'serverow':[" +
                "{id:1,servename:'移动应用代理服务',ipport:'192.225.235.2:1212',serveagreement:'tcp'}," +
                "{id:2,servename:'SLL1.0兼容服务',ipport:'192.1.1.2:8802',serveagreement:'tcp'}," +
                "{id:3,servename:'DNS服务',ipport:'172.225.235.2:8080',serveagreement:'udtp'}" +
                "]}";
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return null;
    }


    public String findAllWapControls() throws Exception{
        String jsons = wapControlService.findWapControls();
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return null;
    }
    
    public String addWapControl() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        String json = null;
        if(wapControlService.checkWapControlByIp(wapip)){
            msg = "ssadd";
            json = "{success:true,msg:'"+msg+"'}";
        }else {
            try{
                wapControlService.addWapControl(wapip);
                msg = "增加成功";
            } catch (Exception e){
                logger.error(e);
                msg = "增加失败";
            }
            json = "{success:true,msg:'"+msg+"'}";
        }
            actionBase.actionEnd(response, json, result);
        return null;
    }
    
    public String delWapControls() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        try{
            wapControlService.delWapControlByIds(ids);
            msg = "删除成功";
        } catch (Exception e){
            msg = "删除失败";
        }
        String json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String updWapControl() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        String json = null;
        WapControl wapComtrol = wapControlService.findWapControlById(id);
        if( (wapComtrol.getIp().equals(wapip)) || (wapControlService.checkWapControlByIp(wapip)==false)){
            try{
                wapControlService.updWapControl(wapComtrol,wapip);
                msg = "修改IP成功";
            } catch (Exception e){
                logger.error(e);
                msg = "修改IP失败";
            }
            json = "{success:true,msg:'"+msg+"'}";
        }else {
            msg = "mccf";
            json = "{success:true,msg:'"+msg+"'}";
        }
        try {
            actionBase.actionEnd(response, json, result);
        } catch (IOException e) {
            logger.error(e);
        }
        return null;
    }
}
