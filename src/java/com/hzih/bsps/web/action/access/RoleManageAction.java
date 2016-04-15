package com.hzih.bsps.web.action.access;

import com.hzih.bsps.domain.RoleManage;
import com.hzih.bsps.service.RoleManageAndTimeService;
import com.hzih.bsps.service.RoleManageService;
import com.hzih.bsps.service.RoleUserService;
import com.hzih.bsps.service.UserManageService;
import com.hzih.bsps.web.action.ActionBase;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-27
 * Time: 下午3:53
 * To change this template use File | Settings | File Templates.
 */
public class RoleManageAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(RoleManage.class);
    private int start;
    private int limit;
    private int id;
    private String rolename;
    private String description;
    private int timetype;
    private Date starttime;
    private Date endtime;
    private String likename;
    private RoleManageService roleManageService;
    private String userids;
    private int userid;
    private String ids;
    private RoleManageAndTimeService roleManageAndTimeService;
    private UserManageService userManageService;
    private RoleUserService roleUserService;

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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUserids() {
        return userids;
    }

    public void setUserids(String userids) {
        this.userids = userids;
    }

    public RoleUserService getRoleUserService() {
        return roleUserService;
    }

    public void setRoleUserService(RoleUserService roleUserService) {
        this.roleUserService = roleUserService;
    }

    public UserManageService getUserManageService() {
        return userManageService;
    }

    public void setUserManageService(UserManageService userManageService) {
        this.userManageService = userManageService;
    }

    public RoleManageAndTimeService getRoleManageAndTimeService() {
        return roleManageAndTimeService;
    }

    public void setRoleManageAndTimeService(RoleManageAndTimeService roleManageAndTimeService) {
        this.roleManageAndTimeService = roleManageAndTimeService;
    }

    public int getTimetype() {
        return timetype;
    }
    public void setTimetype(int timetype) {
        this.timetype = timetype;
    }
    public Date getStarttime() {
        return starttime;
    }
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }
    public Date getEndtime() {
        return endtime;
    }
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
    public String getIds() {
        return ids;
    }
    public void setIds(String ids) {
        this.ids = ids;
    }
    public String getLikename() {
        return likename;
    }
    public void setLikename(String likename) {
        this.likename = likename;
    }
    public RoleManageService getRoleManageService() {
        return roleManageService;
    }
    public void setRoleManageService(RoleManageService roleManageService) {
        this.roleManageService = roleManageService;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getRolename() {
        return rolename;
    }
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String findAllRoleManages() {
        ArrayList<RoleManage> alllist = roleManageService.findRoleManages();
        ArrayList<RoleManage> list = roleManageService.findRoleManages(start,limit);
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{success:true,'rolemanagelist':").append(alllist.size()).append(",'rolerows':[");
        for(RoleManage s : list){
            jsonb.append("{id:").append(s.getId()).append(",rolename:'").append(s.getRolename());
            jsonb.append("',description:'").append(s.getDescription()).append("'},");
        }
        if(list.size()!=0) {
            jsonb.deleteCharAt(jsonb.length()-1);
        }
        jsonb.append("]}");
        String jsons = jsonb.toString();
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println(jsons);
            logger.info(jsons);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        out.flush();
        out.close();
        return SUCCESS;
    }

    public String findRoleManagesByRolname() {
        ArrayList<RoleManage> alllist = null;
        ArrayList<RoleManage> list = null;
        if(null==likename||"".equals(likename)){
            alllist = roleManageService.findRoleManages();
            list = roleManageService.findRoleManages(start,limit);
        }else{
            alllist = roleManageService.findRoleManages(likename);
            list = roleManageService.findRoleManages(likename,start,limit);
        }
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{success:true,'rolemanagelist':").append(alllist.size()).append(",'rolerows':[");
        for(RoleManage s : list){
            jsonb.append("{id:").append(s.getId()).append(",rolename:'").append(s.getRolename());
            jsonb.append("',description:'").append(s.getDescription()).append("'},");
        }
        if(list.size()!=0) {
            jsonb.deleteCharAt(jsonb.length()-1);
        }
        jsonb.append("]}");
        String jsons = jsonb.toString();
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println(jsons);
            logger.info(jsons);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        out.flush();
        out.close();
        return SUCCESS;
    }

    public String setNull() {
        likename = null;
        return SUCCESS;
    }

    public String addRoleManage() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        String json = null;
        if(roleManageService.checkRoleManageByRolename(rolename)){
            msg = "ssadd";
            json = "{success:true,msg:'"+msg+"'}";
        }else {
            try{
                roleManageService.addRoleManage(rolename,description);
                msg = "增加成功";
            } catch (Exception e){
                logger.error(e);
                msg = "增加失败";
            }
            json = "{success:true,msg:'"+msg+"'}";
        }
        try {
            actionBase.actionEnd(response, json, result);
        } catch (IOException e) {
            logger.error(e);
        }
        return null;
    }

    public String delRoleManagesByIds() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        try{
            roleManageService.delRoleManageByIds(ids);
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

    public String updRoleManagesByIds() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        String json = null;
        RoleManage roleManage = roleManageService.findRoleManageById(id);
        if( (roleManage.getRolename().equals(rolename)) || (roleManageService.checkRoleManageByRolename(rolename)==false)){
            try{
                roleManageService.updRoleManage(roleManage, rolename,description);
                msg = "修改成功";
            } catch (Exception e){
                logger.error(e);
                msg = "修改失败";
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

    public String addRoleManageAndTime() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        String json = null;
        try{
            roleManageAndTimeService.addRoleManageAndTime(id,timetype,starttime,endtime);
            msg = "增加成功";
        } catch (Exception e){
            e.printStackTrace();
            logger.error(e);
            msg = "增加失败";
        }
        json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }
    
    public String findUserManagesByRoleManageId() {
        String jsons = userManageService.findUserManagesByRoleManageId(id,start,limit);
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println(jsons);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        logger.info(jsons);
        return null;
    }

    public String donull() {
       return null;
    }

    public String findOtherUserManagesByOtherRoleId() throws Exception {
        String jsons="";
        try{

           jsons = userManageService.findUserManagesByOtherRoleId(id,start,limit);
        }catch (Exception e) {
            e.printStackTrace();
        }
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return SUCCESS;
    }



    public String addRoleUsers() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        String json = null;
        try{
            roleUserService.addRoleUsers(id,userids);
            msg = "为角色增加用户成功";
        } catch (Exception e){
            e.printStackTrace();
            logger.error(e);
            msg = "为角色增加用户失败";
        }
        json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String delRoleUser() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        String json = null;
        try{
            roleUserService.delRoleUser(id,userid);
            msg = "为角色删除用户成功";
        } catch (Exception e){
            e.printStackTrace();
            logger.error(e);
            msg = "为角色删除用户失败";
        }
        json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }
}
