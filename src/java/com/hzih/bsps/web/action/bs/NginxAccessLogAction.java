package com.hzih.bsps.web.action.bs;

import com.hzih.bsps.utils.FileUtil;
import com.hzih.bsps.utils.StringContext;
import com.hzih.bsps.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-24
 * Time: 下午6:01
 * To change this template use File | Settings | File Templates.
 */
public class NginxAccessLogAction extends ActionSupport {

    public String download()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String json = "{success:false}";
        String nginx_log_path = StringContext.nginxPath+"/logs/access.log";
        String Agent = request.getHeader("User-Agent");
        StringTokenizer st = new StringTokenizer(Agent,";");
        st.nextToken();
        //得到用户的浏览器名  MSIE  Firefox
        String userBrowser = st.nextToken();
        File file = new File(nginx_log_path);
        FileUtil.downType(response, file.getName(),userBrowser);
        response = FileUtil.copy(file, response);
        json = "{success:true}";
        actionBase.actionEnd(response,json,result);
        return null;
    }
}
