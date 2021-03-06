package com.hzih.bsps.web.action.net;

import com.hzih.bsps.service.LogService;
import com.hzih.bsps.utils.Configuration;
import com.hzih.bsps.utils.StringContext;
import com.hzih.bsps.web.SessionUtils;
import com.hzih.bsps.web.action.ActionBase;
import com.inetec.common.exception.Ex;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 12-5-18
 * Time: 下午3:54
 * 安全配置
 */
public class SecurityConfigAction extends ActionSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SecurityConfigAction.class);
    private static final String path = StringContext.systemPath +"/tomcat/conf/server.xml";

    private LogService logService;

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    /**
     * 获取管理服务、集控采集数据接口设定IP地址所需json1
     *
     */
	public String readIps() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
		String json = "{'success':true,'total':0,'rows':[]}";
        try {
            json = read(path);
//            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "配置管理","用户获取管理服务、集控采集数据接口设定IP地址成功 ");
        } catch (Exception e) {
            logger.error("配置管理", e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(), "配置管理","用户获取管理服务、集控采集数据接口设定IP地址失败 ");
        }
        actionBase.actionEnd(response, json, result);
        return null;
	}

    /**
     * 获取管理客户机地址所需json
     *
     */
	public String select() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
		String json = "{'success':true,'total':0,'ip1':'','ip2':'','rows':[]}";
        try {
            Integer start = Integer.decode(request.getParameter("start"));
            Integer limit = Integer.decode(request.getParameter("limit"));
            json = read(path,start,limit);
//            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "配置管理","用户获取管理客户机地址成功 ");
        } catch (Exception e) {
            logger.error("配置管理", e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(), "配置管理","用户获取管理客户机地址失败 ");
        }
        actionBase.actionEnd(response, json, result);
        return null;
	}



    /**
     * 删除 管理客户机地址（注：可以多个为客户机地址）
     *
     */
	public String delete() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
		String json = null;
        String msg = null;
        try {
            String[] ip = ServletRequestUtils.getStringParameters(request, "ipArray");
            msg = delete(path, ip);
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "配置管理","用户删除管理客户机地址成功 ");
        } catch (Exception e) {
            logger.error("配置管理", e);
            logger.error( "配置管理,用户删除管理客户机地址失败 ");
            msg = "删除管理客户机地址失败";
        }
        json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
	}
    /**
     * 新增 管理客户机地址（注：可以多个为客户机地址）
     *
     */
	public String insert() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
		String json = null;
        String msg = null;
        try {
            String[] ip = ServletRequestUtils.getStringParameters(request, "ipArray");
            msg = insert(path, ip);
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "配置管理","用户新增管理客户机地址成功 ");
        } catch (Exception e) {
            logger.error("配置管理", e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(), "配置管理","用户新增管理客户机地址失败 ");
            msg = "新增管理客户机地址失败";
        }
        json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
	}

    /**
     * 更新端口是8443的服务ip--管理服务接口设定IP地址
     *
     */
	public String update8443() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
		String json = null;
        String msg = null;
        try {
            String ip = ServletRequestUtils.getStringParameter(request, "ip");
            msg = update(path, ip,8443);
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "配置管理","用户更新管理服务接口设定IP地址成功 ");
        } catch (Exception e) {
            logger.error("配置管理", e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(),"配置管理","用户更新管理服务接口设定IP地址失败 ");
            msg = "更新管理服务接口设定IP地址失败";
        }
        json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
	}

    /**
     * 更新端口是8000的服务ip--集控采集数据接口设定IP地址
     *
     */
	public String update8000() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
		String json = null;
        String msg = null;
        try {
            String ip = ServletRequestUtils.getStringParameter(request, "ip");
            msg = update(path,ip, 8000);
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "配置管理","用户更新集控采集数据接口设定IP地址成功 ");
        } catch (Exception e) {
            logger.error("配置管理", e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(),"配置管理","用户更新集控采集数据接口设定IP地址失败 ");
            msg = "更新集控采集数据接口设定IP地址失败";
        }
        json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
	}

    private String update(String path, String ip, int port) {
        String result = null;
        try {
            Configuration config = new Configuration(path);
            result = config.editConnectorIp(ip,""+port);
        } catch (Ex ex) {
            result = ex.getMessage();
        }
        return result;
    }

    private String insert(String path, String[] ips) {
        String result = null;
        try {
            Configuration config = new Configuration(path);
            List<String> list = config.getAllowIPs();
            String ip = "|";
            for (int i = 0 ; i < ips.length ; i ++){
                boolean isExist = check(ips[i],list);
                if(!isExist){
                    ip += ips[i];
                }
            }
            result = config.editAllowIp( ip);
        } catch (Ex ex) {
            result = ex.getMessage();
        }
        return result;
    }

    private String delete(String path, String[] ips) {
        String result = null;
        try {
            Configuration config = new Configuration(path);
            List<String> temp = new ArrayList<String>();
            List<String> list = config.getAllowIPs();
            for (int i = 0 ; i < ips.length ; i ++){
                boolean isExist = check(ips[i],list);
                if(isExist){
                    temp.add(ips[i]);
                }else {
                    logger.warn(ips[i] + "已经删除或不存在");
                }
            }
            list.removeAll(temp);
            String ip = list.get(0);
            for (int i = 1 ; i < list.size() ; i ++){
                ip += "|"+list.get(i);
            }
            result = config.deleteAllowIp( ip);
        } catch (Ex ex) {
            result = ex.getMessage();
        }
        return result;
    }

    private boolean check(String ip, List<String> list) {
        for (String str : list){
            if(str.equals(ip)){
                return true;
            }
        }
        return false;
    }

    private String read(String path, Integer start, Integer limit) {
        String json = "{success:true,total:0,rows:[]}";
        try {
            Configuration config = new Configuration(path);
            List<String> list = config.getAllowIPs();
            String[] array = (String[]) list.toArray(new String[list.size()]);
            Arrays.sort(array);
            json = "{success:true,total:"+(list.size())+",rows:[";
            int count = 0;
            for (int i= 0;i<array.length;i++){
                if(i == start && count < limit){
                    json +="{ip:'"+array[i]+"'},";
                    count ++;
                    start ++;
                }
            }
            json += "]}";
        } catch (Ex ex) {
            logger.error(ex.getMessage());
        }
        return json;
    }

    private String read(String path) {
        String json = "{success:true,total:0,rows:[]}";
        try {
            Configuration config = new Configuration(path);
            String ip1 = config.getConnectorIp(""+8443);
            String ip2 = config.getConnectorIp(""+8000);
            json = "{success:true,total:1,rows:[{ip1:'"+ip1+"',ip2:'"+ip2+"'}]}";
        } catch (Ex ex) {
            logger.error(ex.getMessage());
        }
        return json;
    }
}
