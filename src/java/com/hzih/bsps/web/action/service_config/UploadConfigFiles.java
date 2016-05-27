package com.hzih.bsps.web.action.service_config;

import com.hzih.bsps.utils.FileUtil;
import com.hzih.bsps.utils.StringContext;
import com.inetec.common.util.Proc;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-7
 * Time: 上午9:36
 * To change this template use File | Settings | File Templates.
 */
public class UploadConfigFiles extends ActionSupport {
    //管理端更新代理tcp配置
    public String upload_tcp_config()throws Exception{
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String json ="{success:false}";
        ServletInputStream inputStream = request.getInputStream();
        if(null!=inputStream) {
            FileUtil.copy(inputStream, StringContext.nginxConfigPath+"/tcp.conf");
            json="{success:true}";
        }
        writer.write(json);
        writer.close();
        reconfigure_nginx();
        return null;
    }
   /* //管理端更新代理http配置
    public String upload_http_config()throws Exception{
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String json ="{success:false}";
        ServletInputStream inputStream = request.getInputStream();
        if(null!=inputStream) {
            FileUtil.copy(inputStream, StringContext.nginxConfigPath+"/bsPx.conf");
            json ="{success:true}";
        }
        writer.write(json);
        writer.close();
        reconfigure_nginx();
        return null;
    }
    //管理端更新代理透明代理配置
    public String upload_tHttp_config()throws Exception{
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String json ="{success:false}";
        ServletInputStream inputStream = request.getInputStream();
        if(null!=inputStream) {
            FileUtil.copy(inputStream, StringContext.squidConfigPath+"/config.conf");
            json ="{success:true}";
        }
        writer.write(json);
        writer.close();
        reconfigure_squid();
        return null;
    }*/


    public void reconfigure_nginx(){
        Proc proc = new Proc();
//        proc.exec("sh "+StringContext.systemPath+"/bsshell/reconfigure_nginx.sh");
        proc.exec("service nginx status");
//        if(proc.getOutput().contains("found running")){
            if(proc.getOutput().contains("running")) {
            proc.exec("service nginx reload");
        } else {
            proc.exec("service nginx start");
        }
    }

  /*  public void reconfigure_squid(){
        Proc proc = new Proc();
        proc.exec("sh "+StringContext.systemPath+"/bsshell/reconfigure_squid.sh");
    }*/

}
