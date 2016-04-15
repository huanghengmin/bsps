package com.hzih.bsps.web.action.access;

import com.hzih.bsps.service.PowerService;
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
 * Date: 12-12-2
 * Time: 下午7:05
 * To change this template use File | Settings | File Templates.
 */
public class PowerAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(PowerAction.class);
    private int start;
    private int limit;
    private String ids;
    private int id;
    private String rolename;
    private int rights;
    private String resourcename;
    private String renames;

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

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getRenames() {
        return renames;
    }

    public void setRenames(String renames) {
        this.renames = renames;
    }

    private PowerService powerService;

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public int getRights() {
        return rights;
    }

    public void setRights(int rights) {
        this.rights = rights;
    }

    public String getResourcename() {
        return resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename;
    }

    public PowerService getPowerService() {
        return powerService;
    }

    public void setPowerService(PowerService powerService) {
        this.powerService = powerService;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String findPowers() throws Exception {
        String jsons =null;
        try{
            jsons = powerService.findPowers(start,limit);
        }catch (Exception e){
           e.printStackTrace();
        }   
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return null;
    }

    public String findPowersByNames() throws Exception {
        String jsons =null;
        try{
            jsons = powerService.findPowerByNames(rolename,resourcename,start,limit);
        }catch (Exception e){
            e.printStackTrace();
        }
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return null;
    }

    public String addPowers() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        String json = null;
        try{
            powerService.addPowers(rolename,rights,renames);
            msg = "增加成功";
        } catch (Exception e){
            logger.error(e);
            msg = "增加失败";
        }
        json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String delPowers() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        try{
            powerService.delPowers(ids);
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

    public String updPowersRightsById() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        try{
            powerService.updPowersRightsById(id,rights);
            msg = "修改权限成功";
        } catch (Exception e){
            msg = "删除权限失败";
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

    public String startBsAgent() {

        return null;
    }
}
