package com.hzih.bsps.web.action.access;

import com.hzih.bsps.service.ResourceService;
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
 * Date: 12-11-30
 * Time: 下午7:41
 * To change this template use File | Settings | File Templates.
 */
public class ResourceAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(ResourceAction.class);
    private int start;
    private int limit;
    private String ids;
    private int id;
    private String name;
    private int type;
    private String types;
    private String description;
    private String ipaddress;
    private String subnetmask;
    private String agreement;
    private String port;
    private String url;
    private String rolename;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    private ResourceService resourceService;

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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getIpaddress() {
        return ipaddress;
    }
    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }
    public String getSubnetmask() {
        return subnetmask;
    }
    public void setSubnetmask(String subnetmask) {
        this.subnetmask = subnetmask;
    }
    public String getAgreement() {
        return agreement;
    }
    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public ResourceService getResourceService() {
        return resourceService;
    }
    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    public String findAllResources() throws Exception{
        String jsons = resourceService.findResources(start,limit);
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return null;
    }

    public String findResourcesByTypeAndLikeName() throws Exception {
        if(null==types||"".equals(types)){
            type=9;
        }else {
            type=Integer.parseInt(types);
        }
        String jsons = resourceService.findResourcesByTypeAndLikeName(type,name,start,limit);
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return null;
    }

    public String findResourcesByTypeAndLikeNameOtherRoleName() throws Exception {
        if(null==types||"".equals(types)){
            type=9;
        }else {
            type=Integer.parseInt(types);
        }
        String jsons = resourceService.findResourcesByOtherRoleName(rolename,type,name,start,limit);
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return null;
    }

    public String findResourcesByOtherRoleName() throws Exception{
        String jsons = resourceService.findResourcesByOtherRoleName(rolename,start,limit);
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return null;
    }
    
    public String addIpResource() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        String json = null;
        if(resourceService.checkResourceByName(name)){
            msg = "mccf";
            json = "{success:true,msg:'"+msg+"'}";
        }else {
            try{
                resourceService.addIpResource(name,ipaddress,subnetmask,description);
                msg = "增加IP地址段应用成功";
            } catch (Exception e){
                logger.error(e);
                msg = "增加IP地址段应用失败";
            }
            json = "{success:true,msg:'"+msg+"'}";
        }
        try {
            actionBase.actionEnd(response, json, result);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public String addWebResource() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        String json = null;
        if(resourceService.checkResourceByName(name)){
            msg = "mccf";
            json = "{success:true,msg:'"+msg+"'}";
        }else {
            try{
                resourceService.addWebResource(name,agreement,ipaddress,port,url,description);
                msg = "增加WEB应用成功";
            } catch (Exception e){
                e.printStackTrace();
                logger.error(e);
                msg = "增加WEB应用失败";
            }
            json = "{success:true,msg:'"+msg+"'}";
        }
        try {
            actionBase.actionEnd(response, json, result);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
    
    public String delResources() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        try{
            resourceService.delResourcesByIds(ids);
            msg = "删除成功";
        } catch (Exception e){
            msg = "删除失败";
            e.printStackTrace();
        }
        String json = "{success:true,msg:'"+msg+"'}";
        try {
            actionBase.actionEnd(response, json, result);
        } catch (IOException e) {
            logger.error(e);
        }
        return null;
    }
    
    public String donull() {
        return null;
    }
    
    public String findResourceIp() throws Exception{
        String jsons = resourceService.findResourceIp(id);
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return null;
    }

    public String findResourceWeb() throws Exception{
        String jsons = resourceService.findResourceWeb(id);
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return null;
    }
}
