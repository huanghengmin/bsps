package com.hzih.bsps.web.action.cs;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsps.cs.ProcessDao;
import com.hzih.bsps.cs.ProcessEntity;
import com.hzih.bsps.cs.ProcessService;
import com.hzih.bsps.service.LogService;
import com.hzih.bsps.web.SessionUtils;
import com.hzih.bsps.web.action.ActionBase;
import com.hzih.bsps.web.action.system.PlatformInitConfigUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-25
 * Time: 下午12:56
 * To change this template use File | Settings | File Templates.
 */
public class ProcessAction extends ActionSupport {
    private int start;
    private int limit;
    private ProcessDao processDao;
    private ProcessEntity processEntity;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    private static final Logger logger = Logger.getLogger(ProcessAction.class);

    private LogService logService;

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public ProcessEntity getProcessEntity() {
        return processEntity;
    }

    public void setProcessEntity(ProcessEntity processEntity) {
        this.processEntity = processEntity;
    }

    public ProcessDao getProcessDao() {
        return processDao;
    }

    public void setProcessDao(ProcessDao processDao) {
        this.processDao = processDao;
    }

    public String findConfig()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        PageResult pageResult =   processDao.findByPages(start,limit);
        if(pageResult!=null){
            List<ProcessEntity> list = pageResult.getResults();
            int count =  pageResult.getAllResultsAmount();
            if(list!=null){
                String  json= "{success:true,pclist:" + count + ",pcrow:[";
                Iterator<ProcessEntity> raUserIterator = list.iterator();
                while (raUserIterator.hasNext()){
                    ProcessEntity log = raUserIterator.next();
                    if(raUserIterator.hasNext()){
                            json += "{" +
                                    "id:'"+log.getId()+
                                    "',distIp:'"  +log.getDistIp()+
                                    "',distPort:'"+log.getDistPort()+
                                    "',type:'" + log.getType() +
                                    "',sourceIp:'" + log.getSourceIp() +
                                    "',isrun:'" + log.getRun() +
                                    "',flagrun:'" + log.getFlagRun() +
                                    "',describe:'" + log.getDescribe() +
                                    "',sourcePort:'" + log.getSourcePort() +"'" +
                                    "},";
                    } else {
                        json += "{" +
                                "id:'"+log.getId()+
                                "',distIp:'"  +log.getDistIp()+
                                "',distPort:'"+log.getDistPort()+
                                "',type:'" + log.getType() +
                                "',sourceIp:'" + log.getSourceIp() +
                                "',isrun:'" + log.getRun() +
                                "',flagrun:'" + log.getFlagRun() +
                                "',describe:'" + log.getDescribe() +
                                "',sourcePort:'" + log.getSourcePort() +"'" +
                                "}";
                    }
                }
                json += "]}";
                actionBase.actionEnd(response, json, result);
            }
        }
        return null;
    }

    public String addConfig() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String json = "{success:false}";
        String msg ="添加失败";
        boolean save_flag = false;
        boolean flag = false;
        flag = processDao.checkSourceIpPort(processEntity.getSourceIp(),processEntity.getSourcePort());
        if(!flag){
            try {
                processEntity.setFlagRun(1);
                save_flag = processDao.add(processEntity);
                try{
                    String pre_post = null;
                    if(processEntity.getSourceIp().equals(PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post))){
                        pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_pre);
                    }else
                        pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post);
                    if(processEntity.getType().equals("tcp")){
                        ShellUtils.add_nat_tcp(processEntity, pre_post);
                    }else if(processEntity.getType().equals("udp")){
                        ShellUtils.add_nat_udp(processEntity, pre_post);
                    }
                    /*ProcessService service = new ProcessService();
                    service.addPortEntity(processEntity);*/
                }catch (Exception e){
                    logger.error("add iptables 出错!");
                }

                if(save_flag){
                    msg ="添加信息成功";
                    logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "ProcessAction", "添加信息成功!");
                }else {
                    msg ="添加信息失败";
                    logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "ProcessAction", "添加信息失败!");
                }
            } catch (Exception e) {
                msg ="添加信息失败";
                logger.error("添加信息失败:"+e);
            }
        } else {
            msg ="管理端口冲突!";
        }
        if(save_flag){
            json = "{success:true,msg:'"+msg+"'}";
        }else {
            json = "{success:false,msg:'"+msg+"'}";
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String delConfig()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String json = "{success:false}";
        String id = request.getParameter("id");
        ProcessEntity entity = processDao.findById(Integer.parseInt(id));
        boolean flag = processDao.delete(entity);
        try{
            if(entity.getFlagRun()==1){
                /*ProcessService service = new ProcessService();
                service.clearPortEntity(entity);*/
                String pre_post = null;
                if(entity.getSourceIp().equals(PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post))){
                    pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_pre);
                }else
                    pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post);
                if(entity.getType().equals("tcp")){
                    ShellUtils.del_nat_tcp(entity,pre_post);
                }else if(entity.getType().equals("udp")){
                    ShellUtils.del_nat_udp(entity, pre_post);
                }
            }
        }catch (Exception e){
         logger.error("清除iptables 出错!");
        }
        if(flag){
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "ProcessAction","删除配置成功!");
        } else {
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "ProcessAction","删除配置失败!");
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String updateConfig()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String id = request.getParameter("id");
        String oldSourceIp = request.getParameter("oldSourceIp");
        String oldSourcePort = request.getParameter("oldSourcePort");
        String oldDistIp = request.getParameter("oldDistIp");
        String oldDistPort = request.getParameter("oldDistPort");
        String oldType = request.getParameter("oldType");
        String json = "{success:false}";
        String msg ="更新失败";
        boolean save_flag = false;
        boolean flag = false;
        flag = processDao.checkSourceIpPort(processEntity.getSourceIp(),processEntity.getSourcePort());
        if((flag&&(oldSourceIp.equals(processEntity.getSourceIp())&&oldSourcePort.equals(processEntity.getSourcePort())))||!flag){
            if("tcp".equals(oldType)){
                 if(processEntity.getType().equals("tcp")){
                     if(oldSourceIp.equals(processEntity.getSourceIp())&&oldSourcePort.equals(processEntity.getSourcePort())&&oldDistIp.equals(processEntity.getDistIp())&&oldDistPort.equals(processEntity.getDistPort())){
                     //相同，无须修改
                         msg = "配置未更改,无需保存!";
                     }else {
                         ProcessEntity oldEntity = processDao.findById(Integer.parseInt(id));
                         try{
                             if(oldEntity.getFlagRun()==1){
                             /*ProcessService service = new ProcessService();
                             service.clearPortEntity(oldEntity);*/
                                 String pre_post = null;
                                 if(oldEntity.getSourceIp().equals(PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post))){
                                     pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_pre);
                                 }else
                                     pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post);
                                 if(oldEntity.getType().equals("tcp")){
                                     ShellUtils.del_nat_tcp(oldEntity, pre_post);
                                 }else if(oldEntity.getType().equals("udp")){
                                     ShellUtils.del_nat_udp(oldEntity, pre_post);
                                 }
                             }
                         }  catch (Exception e){
                             logger.error("清除iptables 出错! 更新操作!");
                         }
                         oldEntity.setSourceIp(processEntity.getSourceIp());
                         oldEntity.setSourcePort(processEntity.getSourcePort());
                         oldEntity.setDistIp(processEntity.getDistIp());
                         oldEntity.setDistPort(processEntity.getDistPort());
                         oldEntity.setType(processEntity.getType());
                         oldEntity.setFlagRun(1);
                         save_flag =processDao.modify(oldEntity);
                         try{
                            /* ProcessService service = new ProcessService();
                             service.addPortEntity(processEntity);*/
                             String pre_post = null;
                             if(processEntity.getSourceIp().equals(PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post))){
                                 pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_pre);
                             }else
                                 pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post);
                             if(processEntity.getType().equals("tcp")){
                                 ShellUtils.add_nat_tcp(processEntity,pre_post);
                             }else if(processEntity.getType().equals("udp")){
                                 ShellUtils.add_nat_udp(processEntity,pre_post);
                             }
                         }catch (Exception e){
                             logger.error("update  iptables 出错!");
                         }
                         if(save_flag){
                             msg ="修改配置成功";
                             logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "ProcessAction", "修改配置成功!");
                         } else {
                             msg ="修改配置失败";
                             logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "ProcessAction", "修改配置失败!");
                         }
                     }
                }else {
                     ProcessEntity oldEntity = processDao.findById(Integer.parseInt(id));
                     try{
                         if(oldEntity.getFlagRun()==1){
                            /* ProcessService service = new ProcessService();
                             service.clearPortEntity(oldEntity);*/
                             String pre_post = null;
                             if(oldEntity.getSourceIp().equals(PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post))){
                                 pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_pre);
                             }else
                                 pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post);
                             if(oldEntity.getType().equals("tcp")){
                                 ShellUtils.del_nat_tcp(oldEntity,pre_post);
                             }else if(oldEntity.getType().equals("udp")){
                                 ShellUtils.del_nat_udp(oldEntity,pre_post);
                             }
                         }
                     }  catch (Exception e){
                         logger.error("清除iptables 出错! 更新操作!");
                     }
                     oldEntity.setSourceIp(processEntity.getSourceIp());
                     oldEntity.setSourcePort(processEntity.getSourcePort());
                     oldEntity.setDistIp(processEntity.getDistIp());
                     oldEntity.setDistPort(processEntity.getDistPort());
                     oldEntity.setType(processEntity.getType());
                     oldEntity.setFlagRun(1);
                     save_flag =processDao.modify(oldEntity);
                     try{
                        /* ProcessService service = new ProcessService();
                         service.addPortEntity(processEntity);*/
                         String pre_post = null;
                         if(processEntity.getSourceIp().equals(PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post))){
                             pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_pre);
                         }else
                             pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post);
                         if(processEntity.getType().equals("tcp")){
                             ShellUtils.add_nat_tcp(processEntity, pre_post);
                         }else if(processEntity.getType().equals("udp")){
                             ShellUtils.add_nat_udp(processEntity, pre_post);
                         }
                     }catch (Exception e){
                         logger.error("update  iptables 出错!");
                     }
                     if(save_flag){
                         msg ="修改配置成功";
                         logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "ProcessAction", "修改配置成功!");
                     } else {
                         msg ="修改配置失败";
                         logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "ProcessAction", "修改配置失败!");
                     }
                 }
            }else if("udp".equals(oldType)){
                if(processEntity.getType().equals("tcp")){
                    ProcessEntity oldEntity = processDao.findById(Integer.parseInt(id));

                    try{
                        if(oldEntity.getFlagRun()==1){
                            /*ProcessService service = new ProcessService();
                            service.clearPortEntity(oldEntity);*/
                            String pre_post = null;
                            if(oldEntity.getSourceIp().equals(PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post))){
                                pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_pre);
                            }else
                                pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post);
                            if(oldEntity.getType().equals("tcp")){
                                ShellUtils.del_nat_tcp(oldEntity,pre_post);
                            }else if(oldEntity.getType().equals("udp")){
                                ShellUtils.del_nat_udp(oldEntity,pre_post);
                            }
                        }
                    }  catch (Exception e){
                        logger.error("清除iptables 出错! 更新操作!");
                    }
                    oldEntity.setSourceIp(processEntity.getSourceIp());
                    oldEntity.setSourcePort(processEntity.getSourcePort());
                    oldEntity.setDistIp(processEntity.getDistIp());
                    oldEntity.setDistPort(processEntity.getDistPort());
                    oldEntity.setType(processEntity.getType());
                    oldEntity.setFlagRun(1);
                    save_flag =processDao.modify(oldEntity);
                    try{
                        /*ProcessService service = new ProcessService();
                        service.addPortEntity(processEntity);*/
                        String pre_post = null;
                        if(processEntity.getSourceIp().equals(PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post))){
                            pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_pre);
                        }else
                            pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post);
                        if(processEntity.getType().equals("tcp")){
                            ShellUtils.del_nat_tcp(processEntity,pre_post);
                        }else if(processEntity.getType().equals("udp")){
                            ShellUtils.del_nat_udp(processEntity,pre_post);
                        }
                    }catch (Exception e){
                        logger.error("update  iptables 出错!");
                    }
                    if(save_flag){
                        msg ="修改配置成功";
                        logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "ProcessAction", "修改配置成功!");
                    } else {
                        msg ="修改配置失败";
                        logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "ProcessAction", "修改配置失败!");
                    }
                }else {
                    if(oldSourceIp.equals(processEntity.getSourceIp())&&oldSourcePort.equals(processEntity.getSourcePort())&&oldDistIp.equals(processEntity.getDistIp())&&oldDistPort.equals(processEntity.getDistPort())){
                        //相同，无须修改
                        msg = "配置未更改,无需保存!";
                    }else {
                        ProcessEntity oldEntity = processDao.findById(Integer.parseInt(id));

                        try{
                            if(oldEntity.getFlagRun()==1){
                               /* ProcessService service = new ProcessService();
                                service.clearPortEntity(oldEntity);*/
                                String pre_post = null;
                                if(oldEntity.getSourceIp().equals(PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post))){
                                    pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_pre);
                                }else
                                    pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post);
                                if(oldEntity.getType().equals("tcp")){
                                    ShellUtils.del_nat_tcp(oldEntity,pre_post);
                                }else if(oldEntity.getType().equals("udp")){
                                    ShellUtils.del_nat_udp(oldEntity,pre_post);
                                }
                            }
                        }  catch (Exception e){
                            logger.error("清除iptables 出错! 更新操作!");
                        }
                        oldEntity.setSourceIp(processEntity.getSourceIp());
                        oldEntity.setSourcePort(processEntity.getSourcePort());
                        oldEntity.setDistIp(processEntity.getDistIp());
                        oldEntity.setDistPort(processEntity.getDistPort());
                        oldEntity.setType(processEntity.getType());
                        oldEntity.setFlagRun(1);
                        save_flag =processDao.modify(oldEntity);
                        try{
                            /*ProcessService service = new ProcessService();
                            service.addPortEntity(processEntity);*/
                            String pre_post = null;
                            if(processEntity.getSourceIp().equals(PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post))){
                                pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_pre);
                            }else
                                pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post);
                            if(processEntity.getType().equals("tcp")){
                                ShellUtils.del_nat_tcp(processEntity,pre_post);
                            }else if(processEntity.getType().equals("udp")){
                                ShellUtils.del_nat_udp(processEntity,pre_post);
                            }
                        }catch (Exception e){
                            logger.error("update  iptables 出错!");
                        }
                        if(save_flag){
                            msg ="修改配置成功";
                            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "ProcessAction", "修改配置成功!");
                        } else {
                            msg ="修改配置失败";
                            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "ProcessAction", "修改配置失败!");
                        }
                    }
                }
            }
        } else {
            msg = "存在相同的监听Ip和端口,不允许修改";
        }
        if(save_flag){
            json = "{success:true,msg:'"+msg+"'}";
        }else {
            json = "{success:false,msg:'"+msg+"'}";
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    /**
     * 批量删除
     * @return
     * @throws Exception
     */
    public String delBatch()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String[] data = ServletRequestUtils.getStringParameters(request,"idArray");
        String msg = "";
        try {
            boolean flag = false;
            for(int i = 0 ;i< data.length ; i++){
                ProcessEntity entity = processDao.findById(Integer.parseInt(data[i]));
                flag =processDao.delete(entity);
                try{
                    if(entity.getFlagRun()==1){
                      /*  ProcessService service = new ProcessService();
                        service.clearPortEntity(entity);*/
                        String pre_post = null;
                        if(entity.getSourceIp().equals(PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post))){
                            pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_pre);
                        }else
                            pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post);
                        if(entity.getType().equals("tcp")){
                            ShellUtils.del_nat_tcp(entity, pre_post);
                        }else if(entity.getType().equals("udp")){
                            ShellUtils.del_nat_udp(entity, pre_post);
                        }
                        entity.setFlagRun(0);
                        flag = processDao.modify(entity);
                    }
                }catch (Exception e){
                    logger.error("清除iptables 出错!");
                }
            }
            if(flag){
                msg = "删除成功";
            }
        } catch (Exception e) {
            logger.error("配置管理", e);
            msg = "删除失败";
        }
        String json  = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }

    /**
     * 启动批量端口转发
     * @return
     * @throws Exception
     */
    public String enable()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String[] data = ServletRequestUtils.getStringParameters(request,"idArray");
        String msg = "";
        try {
            boolean flag = false;
            for(int i = 0 ;i< data.length ; i++){
                ProcessEntity entity = processDao.findById(Integer.parseInt(data[i]));
                try{
                    if(entity.getFlagRun()==0){
                        /*ProcessService service = new ProcessService();
                        service.addPortEntity(entity);*/
                        String pre_post = null;
                        if(entity.getSourceIp().equals(PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post))){
                            pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_pre);
                        }else
                            pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post);
                        if(entity.getType().equals("tcp")){
                            ShellUtils.add_nat_tcp(entity, pre_post);
                        }else if(entity.getType().equals("udp")){
                            ShellUtils.add_nat_udp(entity, pre_post);
                        }
                        entity.setFlagRun(1);
                        flag = processDao.modify(entity);
                    }
                }catch (Exception e){
                    logger.error("启用 iptables 出错!");
                }
            }

             if(flag){
                msg = "启用成功";
             }else {
                 msg = "已启用,无需重新启用";
             }
        } catch (Exception e) {
            logger.error("配置管理", e);
            msg = "删除失败";
        }
        String json  = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }

    /**
     * 启用所有端口转发
     * @return
     * @throws Exception
     */
    public String enableAll()throws Exception{
        return null;
    }

    /**
     * 清除批量端口转发
     * @return
     * @throws Exception
     */
    public String clear()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String[] data = ServletRequestUtils.getStringParameters(request, "idArray");
        String msg = "";
        try {
            boolean flag = false;
            for(int i = 0 ;i< data.length ; i++){
                ProcessEntity entity = processDao.findById(Integer.parseInt(data[i]));
                try{
                    if(entity.getFlagRun()==1){
                        /*ProcessService service = new ProcessService();
                        service.clearPortEntity(entity);*/
                        String pre_post = null;
                        if(entity.getSourceIp().equals(PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post))){
                            pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_pre);
                        }else
                            pre_post = PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post);
                        if(entity.getType().equals("tcp")){
                            ShellUtils.del_nat_tcp(entity, pre_post);
                        }else if(entity.getType().equals("udp")){
                            ShellUtils.del_nat_udp(entity, pre_post);
                        }
                        entity.setFlagRun(0);
                        flag = processDao.modify(entity);
                    }
                }catch (Exception e){
                    logger.error("清除iptables 出错!");
                }
            }
            if(flag){
                msg = "清除成功";
            } else {
                msg = "未启动,无需清除";
            }
        } catch (Exception e) {
            logger.error("配置管理", e);
            msg = "清除失败";
        }
        String json  = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String clearAll()throws Exception{
        return null;
    }
    
}
