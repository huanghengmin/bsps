/*
package com.hzih.bsps.web.action.bs;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsps.dao.HttpDao;
import com.hzih.bsps.domain.Http;
import com.hzih.bsps.service.LogService;
import com.hzih.bsps.web.SessionUtils;
import com.hzih.bsps.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

*/
/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-22
 * Time: 下午3:07
 * To change this template use File | Settings | File Templates.
 *//*

public class HttpAction extends ActionSupport {
    private Http http;
    private HttpDao httpDao;
    private int start;
    private int limit;

    public Http getHttp() {
        return http;
    }

    public void setHttp(Http http) {
        this.http = http;
    }

    public HttpDao getHttpDao() {
        return httpDao;
    }

    public void setHttpDao(HttpDao httpDao) {
        this.httpDao = httpDao;
    }

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

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    private LogService logService;
    private Logger logger = Logger.getLogger(HttpAction.class);
    public String add() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        try{
            boolean flag = httpDao.add(http);
            if(flag){
                json= "{success:true,msg:'新增http代理信息成功'}";
                logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","用户新增http代理成功");
            }  else {
                json = "{success:false,msg:'新增http代理失败'}";
                logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户新增http代理失败");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String modify()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        try{
            boolean flag = httpDao.modify(http);
            if(flag){
                json= "{success:true,msg:'修改http代理成功'}";
                logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","用户修改http代理成功");
            }  else {
                json = "{success:false,msg:'修改http代理失败'}";
                logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户修改http代理失败");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String delete()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        try{
            httpDao.delete(http);
            json= "{success:true,msg:'删除http代理成功!'}";
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","用户删除http代理成功");

        }catch (Exception e){
            json = "{success:false,msg:'删除http代理失败'}";
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户删除http代理失败");
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    */
/**
     * 查找
     * @return
     * @throws Exception
     *//*

    public String findByPages() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        PageResult pageResult =  httpDao.findByPages(start,limit);
        if(pageResult!=null){
            List<Http> list = pageResult.getResults();
            int count =  pageResult.getAllResultsAmount();
            if(list!=null){
                String  json= "{success:true,total:" + count + ",rows:[";
                Iterator<Http> raUserIterator = list.iterator();
                while (raUserIterator.hasNext()){
                    Http log = raUserIterator.next();
                    if(raUserIterator.hasNext()){
                        json += "{" +
                                "id:'"+log.getId()+"',"+
                                "manager_ip:'"+log.getManager_ip()+"',"+
                                "manager_port:'"+log.getManager_port()+"',"+
                                "proxy_ip:'"+log.getProxy_ip()+"',"+
                                "proxy_port:'"+log.getProxy_port()+"'"+
                                "},";
                    }else {
                        json += "{" +
                                "id:'"+log.getId()+"',"+
                                "manager_ip:'"+log.getManager_ip()+"',"+
                                "manager_port:'"+log.getManager_port()+"',"+
                                "proxy_ip:'"+log.getProxy_ip()+"',"+
                                "proxy_port:'"+log.getProxy_port()+"'"+
                                "}";
                    }
                }
                json += "]}";
                actionBase.actionEnd(response, json, result);
            }
        }
        return null;
    }
}
*/
