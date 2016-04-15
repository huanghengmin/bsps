package com.hzih.bsps.web.action.bs;

import com.hzih.bsps.utils.StringContext;
import com.hzih.bsps.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-24
 * Time: 下午5:34
 * To change this template use File | Settings | File Templates.
 */
public class BsReloadAction extends ActionSupport {

    public String reload()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String json = "{success:false}";
        String result =	actionBase.actionBegin(request);
        boolean flag = CertUtils.execute( StringContext.nginxSbin+";./nginx -s reload");
        if(flag){
            json = "{success:true}";
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }
}
