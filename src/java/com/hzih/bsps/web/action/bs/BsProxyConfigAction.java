package com.hzih.bsps.web.action.bs;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsps.dao.HttpsDao;
import com.hzih.bsps.dao.SiteDao;
import com.hzih.bsps.domain.Https;
import com.hzih.bsps.domain.Site;
import com.hzih.bsps.service.LogService;
import com.hzih.bsps.utils.StringContext;
import com.hzih.bsps.web.SessionUtils;
import com.hzih.bsps.web.action.ActionBase;
import com.hzih.bsps.web.action.socat.HttpsProcess;
import com.hzih.bsps.web.servlet.SiteContextLoaderServlet;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-22
 * Time: 下午3:43
 * To change this template use File | Settings | File Templates.
 */
public class BsProxyConfigAction extends ActionSupport {
    private HttpsDao httpsDao;
    private SiteDao siteDao;

    public SiteDao getSiteDao() {
        return siteDao;
    }

    public void setSiteDao(SiteDao siteDao) {
        this.siteDao = siteDao;
    }

    private int start;
    private int limit;

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

    public HttpsDao getHttpsDao() {
        return httpsDao;
    }

    public void setHttpsDao(HttpsDao httpsDao) {
        this.httpsDao = httpsDao;
    }

    private static final Logger logger = Logger.getLogger(BsProxyConfigAction.class);
    //    private String config_path = StringContext.systemPath+File.separator+"bsconfig"+File.separator+"bsPx.xml";
    private LogService logService;

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }
//    private String nginx_path = StringContext.nginxPath + "/bsPx.conf";
//    private String tcp_path = StringContext.nginxPath + "/tcp.conf";
    private String nginx_path = StringContext.nginxConfigPath+ "/bsPx.conf";
    private String tcp_path = StringContext.nginxConfigPath + "/tcp.conf";
//    private String thttp_path = StringContext.nginxConfigPath+"/thttp.conf";
    private String thttp_path = StringContext.squidConfigPath+"/config.conf";
//    private String ss_path = StringContext.systemPath+"/"+"ssl";
//    private File   uploadFile;
//    private String uploadFileFileName;
//    private String uploadFileContentType;
    private String protocol;
    //    private String ecpassword;
    private String managerip;
    private String managerport;
    private String proxyip;
    private String proxyport;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

//    public String getEcpassword() {
//        return ecpassword;
//    }

//    public void setEcpassword(String ecpassword) {
//        this.ecpassword = ecpassword;
//    }

    public String getManagerip() {
        return managerip;
    }

    public void setManagerip(String managerip) {
        this.managerip = managerip;
    }

    public String getManagerport() {
        return managerport;
    }

    public void setManagerport(String managerport) {
        this.managerport = managerport;
    }

    public String getProxyip() {
        return proxyip;
    }

    public void setProxyip(String proxyip) {
        this.proxyip = proxyip;
    }

    public String getProxyport() {
        return proxyport;
    }

    public void setProxyport(String proxyport) {
        this.proxyport = proxyport;
    }

//    public File getUploadFile() {
//        return uploadFile;
//    }

//    public void setUploadFile(File uploadFile) {
//        this.uploadFile = uploadFile;
//    }

//    public String getUploadFileFileName() {
//        return uploadFileFileName;
//    }

//    public void setUploadFileFileName(String uploadFileFileName) {
//        this.uploadFileFileName = uploadFileFileName;
//    }

//    public String getUploadFileContentType() {
//        return uploadFileContentType;
//    }

//    public void setUploadFileContentType(String uploadFileContentType) {
//        this.uploadFileContentType = uploadFileContentType;
//    }

    public String findConfig()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
//        StringBuilder sb=new StringBuilder();
        PageResult pageResult =   httpsDao.findByPages(start,limit);
        if(pageResult!=null){
            List<Https> list = pageResult.getResults();
            int count =  pageResult.getAllResultsAmount();
            if(list!=null){
                String  json= "{success:true,pclist:" + count + ",pcrow:[";
                Iterator<Https> raUserIterator = list.iterator();
                while (raUserIterator.hasNext()){
                    Https log = raUserIterator.next();

                    if(raUserIterator.hasNext()){
                        if(log.getProtocol().equals("https")){
                            String site =  log.getSite();
                            String server_site = log.getSite_id_server();
                            Site si_te =siteDao.findById(Integer.parseInt(site));
                            Site se_te = siteDao.findById(Integer.parseInt(server_site));
                            if(si_te!=null&&se_te!=null){
                                json += "{" +
                                        "id:'"+log.getId()+
                                        "',proxyip:'" + log.getProxy_ip() +
                                        "',site_name:'" + si_te.getSite_name() +
                                        "',site_id:'" + si_te.getId() +
                                        "',server_site_name:'" + se_te.getSite_name() +
                                        "',server_site_id:'" + se_te.getId() +
                                        "',proxyport:'" + log.getProxy_port() +
                                        "',protocol:'" + log.getProtocol() +
                                        "',managerip:'" + log.getManager_ip() +
                                        "',managerport:'" + log.getManager_port() +"'" +
                                        "},";
                            }
                        }else if(log.getProtocol().equals("thttp")){
                            json += "{" +
                                    "id:'"+log.getId()+
                                    "',proxyip:'"  +
                                    "',proxyport:'"+
                                    "',site_name:'"+
                                    "',site_id:'" +
                                    "',server_site_name:'" +
                                    "',server_site_id:'" +
                                    "',protocol:'" + log.getProtocol() +
                                    "',managerip:'" + log.getManager_ip() +
                                    "',managerport:'" + log.getManager_port() +"'" +
                                    "},";
                        } else {
                            json += "{" +
                                    "id:'"+log.getId()+
                                    "',proxyip:'" + log.getProxy_ip() +
                                    "',proxyport:'" + log.getProxy_port() +
                                    "',site_name:'"+
                                    "',site_id:'" +
                                    "',server_site_name:'" +
                                    "',server_site_id:'" +
                                    "',protocol:'" + log.getProtocol() +
                                    "',managerip:'" + log.getManager_ip() +
                                    "',managerport:'" + log.getManager_port() +"'" +
                                    "},";
                        }
                    }else {
                        if(log.getProtocol().equals("https")){
                            String site =  log.getSite();
                            String server_site = log.getSite_id_server();
                            Site si_te =siteDao.findById(Integer.parseInt(site));
                            Site se_te = siteDao.findById(Integer.parseInt(server_site));
                            if(si_te!=null){
                                json += "{" +
                                        "id:'"+log.getId()+
                                        "',proxyip:'" + log.getProxy_ip() +
                                        "',site_name:'" + si_te.getSite_name() +
                                        "',site_id:'" + si_te.getId() +
                                        "',server_site_name:'" + se_te.getSite_name() +
                                        "',server_site_id:'" + se_te.getId() +
                                        "',proxyport:'" + log.getProxy_port() +
                                        "',protocol:'" + log.getProtocol() +
                                        "',managerip:'" + log.getManager_ip() +
                                        "',managerport:'" + log.getManager_port() +"'" +
                                        "}";
                            }
                        }else if(log.getProtocol().equals("thttp")){
                            json += "{" +
                                    "id:'"+log.getId()+
                                    "',proxyip:'"  +
                                    "',proxyport:'"+
                                    "',site_name:'"+
                                    "',site_id:'" +
                                    "',server_site_name:'" +
                                    "',server_site_id:'" +
                                    "',protocol:'" + log.getProtocol() +
                                    "',managerip:'" + log.getManager_ip() +
                                    "',managerport:'" + log.getManager_port() +"'" +
                                    "},";
                        }else {
                            json += "{" +
                                    "id:'"+log.getId()+
                                    "',proxyip:'" + log.getProxy_ip() +
                                    "',proxyport:'" + log.getProxy_port() +
                                    "',site_name:'"+
                                    "',site_id:'" +
                                    "',server_site_name:'" +
                                    "',server_site_id:'" +
                                    "',protocol:'" + log.getProtocol() +
                                    "',managerip:'" + log.getManager_ip() +
                                    "',managerport:'" + log.getManager_port() +"'" +
                                    "}";
                        }
                    }
                }
                json += "]}";
                actionBase.actionEnd(response, json, result);
            }
        }


//        Document doc = Dom4jUtil.getDocument(config_path);
//        List<Element> managers = doc.selectNodes("/root/manager");
//        sb.append("{success:true,'pclist':").append(managers.size()).append(",'pcrow':[");
//        for (int i=0;i<managers.size();i++){
//            Element element = managers.get(i);
//            String managerIp = element.attributeValue("ip");
//            String managerPort = element.attributeValue("port");
//            String managerProtocol = element.attributeValue("protocol");
//            Element pro_element = element.element("proxy");
//            String proxyIp = pro_element.attributeValue("ip");
//            String proxyPort = pro_element.attributeValue("port");
//            sb.append("{");
//            sb.append("proxyip:'").append(proxyIp).append("'").append(",");
//            sb.append("proxyport:'").append(proxyPort).append("'").append(",");
//            sb.append("protocol:'").append(managerProtocol).append("'").append(",");
//            sb.append("managerip:'").append(managerIp).append("'").append(",");
//            sb.append("managerport:'").append(managerPort).append("'");
//            sb.append("}");
//            if(i!=(managers.size()-1)){
//                sb.append(",");
//            }
//        }
//        sb.append("]}");
//        actionBase.actionEnd(response, sb.toString(), result);
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
//        Document doc = Dom4jUtil.getDocument(config_path);
        boolean flag = false;
//        if(doc!=null){
//            List<Element> managers = doc.selectNodes("/root/manager");
        flag = httpsDao.findByManagerAndProt( /*managerip,*/ managerport);
//            for (Element s:managers){
//                if(/*s.attributeValue("ip").equals(managerip)&&*/s.attributeValue("port").equals(managerport)){
//                   flag = true;
//                }
//            }
        if(!flag){
            if(protocol.equals("https")){
                String siteId = request.getParameter("site");
                String serverSite = request.getParameter("serverSite");
                Site site = siteDao.findById(Integer.parseInt(siteId));
                Site server_site = siteDao.findById(Integer.parseInt(serverSite));
                if(site!=null){
                    Https https = new Https();
                    https.setManager_ip(managerip);
                    https.setManager_port(managerport);
                    https.setProtocol(protocol);
                    https.setProxy_ip(proxyip);
                    https.setProxy_port(proxyport);
                    https.setSite(siteId);
                    https.setSite_id_server(serverSite);
                    try {
                        httpsDao.add(https);
                        HttpsProcess httpsProcess = new HttpsProcess();
                        httpsProcess.init(managerip,
                                Integer.parseInt(managerport),
                                proxyip,Integer.parseInt(proxyport),
                                site.getCert_path(),
                                site.getKey_path(),
                                server_site.getCert_path(),
                                server_site.getKey_path()
                                );
                        if(SiteContextLoaderServlet.httpsProcesses.get(httpsProcess)==null){
                            SiteContextLoaderServlet.httpsProcesses.put(httpsProcess,httpsProcess);
                        }
                        msg ="https代理,保存成功!";
                        logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction","添加BS管理端配置成功!");
                        save_flag = true;
                    } catch (Exception e) {
                        msg ="添加https代理失败!";
                    }
                }
//                    }

//                    try {
//                       boolean exist_file = CertUtils.saveUploadFile(ss_path,uploadFile,uploadFileFileName);
//                        if(exist_file){
//                            boolean ex =CertUtils.splitUploadFileAndSave(ss_path,ecpassword,uploadFileFileName);
//                            if(ex){
//                                Element rootElement = (Element)doc.selectSingleNode("/root");
//                                Element manager = rootElement.addElement("manager");
//                                manager.addAttribute("ip",managerip);
//                                manager.addAttribute("port",managerport);
//                                manager.addAttribute("protocol",protocol);
//                                Element proxy = manager.addElement("proxy");
//                                proxy.addAttribute("ip",proxyip);
//                                proxy.addAttribute("port",proxyport);
//                                manager.addElement("cert").setText(ss_path+"/"+/*uploadFileFileName.split("\\.")[0]+*/"ROOT.crt");
//                                manager.addElement("private_key").setText(ss_path+"/"+uploadFileFileName.split("\\.")[0]+".pem");
//                                Dom4jUtil.writeDocumentToFile(doc,config_path);
//                                msg ="https代理,保存成功!";
//                                logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction","添加BS管理端配置成功!");
//                                save_flag = true;
//                            }else {
//                                msg ="上传证书文件保存失败!";
//                            }
//                        } else {
//                            msg="有相同文件存在,请改名后上传!";
//                        }
//                    } catch (IOException e) {
//                        msg ="上传证书文件保存失败!";
//                        logger.info("ss_path:"+ss_path);
//                        logger.info("uploadFile:"+uploadFile);
//                        logger.info("uploadFileFileName:"+uploadFileFileName);
//                        logger.error("上传证书文件保存失败:",e);
//                    }
            }else if(protocol.equals("tcp")){
                Https https = new Https();
                https.setManager_ip(managerip);
                https.setManager_port(managerport);
                https.setProtocol(protocol);
                https.setProxy_ip(proxyip);
                https.setProxy_port(proxyport);
                try {
                    httpsDao.add(https);
                    List<Https> tcpList =httpsDao.findAllTcp();
                    try{
                        convertNginxTcpConf(tcpList,tcp_path);
                    }catch (Exception e){
                        logger.info("转换nginx tcp 配置文件不成功!");
                    }
                    save_flag = true;
                    msg ="tcp管理配置成功";
                    logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                } catch (Exception e) {
                    msg ="tcp管理配置失败";
                    logger.error("tcp管理配置失败:"+e);
                }
            } else if(protocol.equals("http")){
                Https https = new Https();
                https.setManager_ip(managerip);
                https.setManager_port(managerport);
                https.setProtocol(protocol);
                https.setProxy_ip(proxyip);
                https.setProxy_port(proxyport);
                try {
                    httpsDao.add(https);
                    List<Https> httpList =httpsDao.findAllHttp();
                    try{
                        convertNginxConf(httpList,nginx_path);
                    }catch (Exception e){
                        logger.info("转换nginx配置文件不成功!");
                    }
                    save_flag = true;
                    msg ="http管理配置成功";
                    logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                } catch (Exception e) {
                    msg ="http管理配置失败";
//                        logger.info("doc:"+doc);
                    logger.error("http管理配置失败:"+e);
                }
//                    Element rootElement = (Element)doc.selectSingleNode("/root");
//                    Element manager = rootElement.addElement("manager");
//                    manager.addAttribute("ip",managerip);
//                    manager.addAttribute("port",managerport);
//                    manager.addAttribute("protocol",protocol);
//                    Element proxy = manager.addElement("proxy");
//                    proxy.addAttribute("ip",proxyip);
//                    proxy.addAttribute("port",proxyport);
//                    try {
//                        Dom4jUtil.writeDocumentToFile(doc,config_path);
//                        save_flag = true;
//                        msg ="http管理配置成功";
//                        logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
//                    } catch (IOException e) {
//                        msg ="http管理配置失败";
//                        logger.info("doc:"+doc);
//                        logger.error("http管理配置失败:"+e);
//                    }
            } else if(protocol.equals("thttp")){
                Https https = new Https();
                https.setManager_ip(managerip);
                https.setManager_port(managerport);
                https.setProtocol(protocol);
//                https.setProxy_ip(proxyip);
//                https.setProxy_port(proxyport);
                try {
                    httpsDao.add(https);
                    List<Https> httpList =httpsDao.findAllTHttp();
                    try{
                        convertNginxTHttpConf(httpList,thttp_path);
                    }catch (Exception e){
                        logger.info("转换nginx 透明代理 配置文件不成功!");
                    }
                    save_flag = true;
                    msg ="透明代理 配置成功";
                    logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                } catch (Exception e) {
                    msg ="透明代理 配置失败";
//                        logger.info("doc:"+doc);
                    logger.error("透明代理 配置失败:"+e);
                }
            }
        } else {
            msg ="管理端口冲突!";
        }
        /*}else {
            if(protocol.equals("https")){
                try {
                    boolean exist_file = CertUtils.saveUploadFile(ss_path,uploadFile,uploadFileFileName);
                    if(exist_file){
                        boolean ex =CertUtils.splitUploadFileAndSave(ss_path,ecpassword,uploadFileFileName);
                        if(ex){
                            Document document = DocumentHelper.createDocument();
                            Element rootElement = document.addElement("root");
                            Element manager = rootElement.addElement("manager");
                            manager.addAttribute("ip",managerip);
                            manager.addAttribute("port",managerport);
                            manager.addAttribute("protocol",protocol);
                            Element proxy = manager.addElement("proxy");
                            proxy.addAttribute("ip",proxyip);
                            proxy.addAttribute("port",proxyport);
                            manager.addElement("cert").setText(ss_path+"/"+*//*uploadFileFileName.split("\\.")[0]+*//*"ROOT.crt");
                            manager.addElement("private_key").setText(ss_path+"/"+uploadFileFileName.split("\\.")[0]+".pem");
                            Dom4jUtil.writeDocumentToFile(document,config_path);
                            msg ="https管理配置成功!";
                            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction","添加BS管理端配置成功!");
                            save_flag = true;
                        }else {
                            msg ="上传证书文件保存失败!";
                        }
                    }else {
                        msg="有相同文件存在,请改名后上传!";
                    }
                } catch (IOException e) {
                    msg ="上传证书文件保存失败!";
                    logger.info("ss_path:"+ss_path);
                    logger.info("uploadFile:"+uploadFile);
                    logger.info("uploadFileFileName:"+uploadFileFileName);
                    logger.error("上传证书文件保存失败:", e);
                }
            } else {
                Document document = DocumentHelper.createDocument();
                Element rootElement = document.addElement("root");
                Element manager = rootElement.addElement("manager");
                manager.addAttribute("ip",managerip);
                manager.addAttribute("port",managerport);
                manager.addAttribute("protocol",protocol);
                Element proxy = manager.addElement("proxy");
                proxy.addAttribute("ip",proxyip);
                proxy.addAttribute("port",proxyport);
                try {
                    Dom4jUtil.writeDocumentToFile(document,config_path);
                    save_flag = true;
                    msg ="https管理配置成功";
                    logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                } catch (IOException e) {
                    msg ="http管理配置失败";
                    logger.info("doc:"+document);
                    logger.error("http管理配置失败:" + e);
                }
            }
        }*/
        if(save_flag){
            json = "{success:true,msg:'"+msg+"'}";
//            try {
//                BsMsConvertConfig.convert(config_path,nginx_path);
//            } catch (IOException e) {
//                logger.error("BsMsConvertConfig:",e);
//            }
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
        Https https = httpsDao.findById(Integer.parseInt(id));
//        List<Https> httpsLists =httpsDao.findAllHttps();
//        Document doc = Dom4jUtil.getDocument(config_path);
//        Element del_url = (Element)doc.selectSingleNode("/root/manager[@ip='" + managerip + "'][@port='"+managerport+"'][@protocol='"+protocol+"']");
//        try{
//            Element pro_element = del_url.element("proxy");
//            String cert = del_url.element("cert").getText();
//            String private_key = del_url.element("private_key").getText();
//            String proxyIp = pro_element.attributeValue("ip");
//            String proxyPort = pro_element.attributeValue("port");
//            Element root = del_url.getParent();
//            root.remove(del_url);
//            Dom4jUtil.writeDocumentToFile(doc,config_path);
//            json = "{success:true}";
//            BsMsConvertConfig.convert(config_path,nginx_path);
        if(https.getProtocol().equals("https")){
            String siteId = https.getSite();
            String server_site_id = https.getSite_id_server();
            if(siteId!=null){
                Site site = siteDao.findById(Integer.parseInt(siteId));
                Site server_site = siteDao.findById(Integer.parseInt(server_site_id));
                HttpsProcess httpsProcess = new HttpsProcess();
                httpsProcess.init(https.getManager_ip(),
                        Integer.parseInt(https.getManager_port()),
                        https.getProxy_ip(),
                        Integer.parseInt(https.getProxy_port()),
                        site.getCert_path(), site.getKey_path(),
                        server_site.getCert_path(),server_site.getKey_path());
                if(SiteContextLoaderServlet.httpsProcesses.get(httpsProcess)!=null){
                    if(SiteContextLoaderServlet.httpsProcesses.get(httpsProcess).isRun())
                    SiteContextLoaderServlet.httpsProcesses.get(httpsProcess).stop();
                    SiteContextLoaderServlet.httpsProcesses.remove(httpsProcess);
                }
            }
            httpsDao.delete(https);
        } else if(https.getProtocol().equals("tcp")){
            httpsDao.delete(https);
            List<Https> httpList =httpsDao.findAllTcp();
            try{
                convertNginxTcpConf(httpList,tcp_path);
            }catch (Exception e){
                logger.info("转换nginx tcp 配置文件不成功!");
            }
        } else if(https.getProtocol().equals("http")){
            httpsDao.delete(https);
            List<Https> httpList =httpsDao.findAllHttp();
            try{
                convertNginxConf(httpList,nginx_path);
            }catch (Exception e){
                logger.info("转换nginx配置文件不成功!");
            }
        } else if(https.getProtocol().equals("thttp")){
            httpsDao.delete(https);
            List<Https> httpList =httpsDao.findAllTHttp();
            try{
                convertNginxTHttpConf(httpList,thttp_path);
            }catch (Exception e){
                logger.info("转换nginx配置文件不成功!");
            }
        }
//        }catch (Exception e){
//            logger.info(e.getMessage());
//        }
        logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction","删除BS管理端配置成功!");
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String updateConfig()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String id = request.getParameter("id");
        String oldManagerip = request.getParameter("oldManagerip");
        String oldManagerPort = request.getParameter("oldManagerPort");
        String oldProxyip = request.getParameter("oldProxyip");
        String oldProxyport = request.getParameter("oldProxyport");
        String oldProtocol = request.getParameter("oldProtocol");
        String oldSiteId = request.getParameter("oldSiteId");
        String oldServerSiteId = request.getParameter("oldServerSiteId");
        logger.info(id+oldManagerip+oldManagerPort+oldProxyip+oldProxyport+oldProtocol+oldSiteId);
        String json = "{success:false}";
        String msg ="更新失败";
        boolean save_flag = false;
        boolean flag = false;
        if(oldManagerPort.equals(managerport)){
            if("https".equals(oldProtocol)){
                //停止以前的http
                Site oldSite = siteDao.findById(Integer.parseInt(oldSiteId));
                Site oldServerSite = siteDao.findById(Integer.parseInt(oldServerSiteId));
                HttpsProcess httpsProcess = new HttpsProcess();
                httpsProcess.init(oldManagerip,Integer.parseInt(oldManagerPort),oldProxyip,
                        Integer.parseInt(oldManagerPort),
                        oldSite.getCert_path(),oldSite.getKey_path(),
                        oldServerSite.getCert_path(),oldServerSite.getKey_path());
                if(SiteContextLoaderServlet.httpsProcesses.get(httpsProcess)!=null){
                    SiteContextLoaderServlet.httpsProcesses.get(httpsProcess).stop();
                    SiteContextLoaderServlet.httpsProcesses.remove(httpsProcess);
                }
                //停止完成
                if(protocol.equals("http")){
                    Https https = new Https();
                    https.setId(Integer.parseInt(id));
                    https.setManager_ip(managerip);
                    https.setManager_port(managerport);
                    https.setProtocol(protocol);
                    https.setProxy_ip(proxyip);
                    https.setProxy_port(proxyport);
                    try {
                        httpsDao.modify(https);
                        List<Https> httpList =httpsDao.findAllHttp();
                        try{
                            convertNginxConf(httpList,nginx_path);
                        }catch (Exception e){
                            logger.info("转换nginx配置文件不成功!");
                        }
                        save_flag = true;
                        msg ="http管理配置成功";
                        logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                    } catch (Exception e) {
                        msg ="http管理配置失败";
                        logger.error("http管理配置失败:"+e);
                    }
                }else if(protocol.equals("tcp")){
                    Https https = new Https();
                    https.setId(Integer.parseInt(id));
                    https.setManager_ip(managerip);
                    https.setManager_port(managerport);
                    https.setProtocol(protocol);
                    https.setProxy_ip(proxyip);
                    https.setProxy_port(proxyport);
                    try {
                        httpsDao.modify(https);
                        List<Https> httpList =httpsDao.findAllTcp();
                        try{
                            convertNginxTcpConf(httpList,tcp_path);
                        }catch (Exception e){
                            logger.info("转换nginx tcp 配置文件不成功!");
                        }
                        save_flag = true;
                        msg ="tcp 管理配置成功";
                        logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                    } catch (Exception e) {
                        msg ="tcp 管理配置失败";
                        logger.error("tcp 管理配置失败:"+e);
                    }
                }else if(protocol.equals("thttp")){
                    Https https = new Https();
                    https.setId(Integer.parseInt(id));
                    https.setManager_ip(managerip);
                    https.setManager_port(managerport);
                    https.setProtocol(protocol);
//                    https.setProxy_ip(proxyip);
//                    https.setProxy_port(proxyport);
                    try {
                        httpsDao.modify(https);
                        List<Https> httpList =httpsDao.findAllTHttp();
                        try{
                            convertNginxTHttpConf(httpList,thttp_path);
                        }catch (Exception e){
                            logger.info("转换nginx 透明代理 配置文件不成功!");
                        }
                        save_flag = true;
                        msg ="透明代理 配置成功";
                        logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                    } catch (Exception e) {
                        msg ="透明代理 配置失败";
                        logger.error("透明代理 配置失败:"+e);
                    }
                }else {
                    String siteId = request.getParameter("site");
                    String serverSite = request.getParameter("serverSite");
                    Site site = siteDao.findById(Integer.parseInt(siteId));
                    Site serSite = siteDao.findById(Integer.parseInt(serverSite));
                    if(site!=null){
                        Https https = new Https();
                        https.setId(Integer.parseInt(id));
                        https.setManager_ip(managerip);
                        https.setManager_port(managerport);
                        https.setProtocol(protocol);
                        https.setProxy_ip(proxyip);
                        https.setProxy_port(proxyport);
                        https.setSite(siteId);
                        https.setSite_id_server(serverSite);
                        try {
                            httpsDao.modify(https);
                            HttpsProcess https_modify_process = new HttpsProcess();
                            https_modify_process.init(managerip,Integer.parseInt(managerport),proxyip,Integer.parseInt(proxyport),
                                    site.getCert_path(),site.getKey_path(),
                                    serSite.getCert_path(),serSite.getKey_path());
                            if(SiteContextLoaderServlet.httpsProcesses.get(https_modify_process)==null){
//                                https_modify_process.start();
                                SiteContextLoaderServlet.httpsProcesses.put(https_modify_process,https_modify_process);
                            }
                            msg ="https代理,更新成功!";
                            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction","更新BS管理端配置成功!");
                            save_flag = true;
                        } catch (Exception e) {
                            msg ="更新代理失败!";
                        }
                    }
                }
            } else if("http".equals(oldProtocol)) {
                if(protocol.equals("http")){
                    Https https = new Https();
                    https.setId(Integer.parseInt(id));
                    https.setManager_ip(managerip);
                    https.setManager_port(managerport);
                    https.setProtocol(protocol);
                    https.setProxy_ip(proxyip);
                    https.setProxy_port(proxyport);
                    try {
                        httpsDao.modify(https);
                        List<Https> httpList =httpsDao.findAllHttp();
                        try{
                            convertNginxConf(httpList,nginx_path);
                        }catch (Exception e){
                            logger.info("转换nginx配置文件不成功!");
                        }
                        save_flag = true;
                        msg ="http管理配置成功";
                        logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "更新BS管理端配置成功!");
                    } catch (Exception e) {
                        msg ="http管理配置失败";
                        logger.error("http管理配置失败:"+e);
                    }
                }else if(protocol.equals("tcp")){
                    Https https = new Https();
                    https.setId(Integer.parseInt(id));
                    https.setManager_ip(managerip);
                    https.setManager_port(managerport);
                    https.setProtocol(protocol);
                    https.setProxy_ip(proxyip);
                    https.setProxy_port(proxyport);
                    try {
                        httpsDao.modify(https);
                        List<Https> httpList =httpsDao.findAllTcp();
                        List<Https> http_List =httpsDao.findAllHttp();
                        try{
                            convertNginxTcpConf(httpList,tcp_path);
                            convertNginxConf(http_List,nginx_path);
                        }catch (Exception e){
                            logger.info("转换nginx tcp 配置文件不成功!");
                        }
                        save_flag = true;
                        msg ="tcp 管理配置成功";
                        logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                    } catch (Exception e) {
                        msg ="tcp 管理配置失败";
                        logger.error("tcp 管理配置失败:"+e);
                    }
                }else if(protocol.equals("thttp")){
                    Https https = new Https();
                    https.setId(Integer.parseInt(id));
                    https.setManager_ip(managerip);
                    https.setManager_port(managerport);
                    https.setProtocol(protocol);
//                    https.setProxy_ip(proxyip);
//                    https.setProxy_port(proxyport);
                    try {
                        httpsDao.modify(https);
                        List<Https> httpList =httpsDao.findAllHttp();
                        List<Https> http_List =httpsDao.findAllTHttp();
                        try{
                            convertNginxTHttpConf(http_List,thttp_path);
                            convertNginxConf(httpList,nginx_path);
                        }catch (Exception e){
                            logger.info("转换nginx 透明代理 配置文件不成功!");
                        }
                        save_flag = true;
                        msg ="透明代理 配置成功";
                        logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                    } catch (Exception e) {
                        msg ="透明代理 配置失败";
                        logger.error("透明代理 配置失败:"+e);
                    }
                }else {
                    String siteId = request.getParameter("site");
                    String serverSite = request.getParameter("serverSite");
                    Site site = siteDao.findById(Integer.parseInt(siteId));
                    Site serSite = siteDao.findById(Integer.parseInt(serverSite));
                    if(site!=null){
                        Https https = new Https();
                        https.setId(Integer.parseInt(id));
                        https.setManager_ip(managerip);
                        https.setManager_port(managerport);
                        https.setProtocol(protocol);
                        https.setProxy_ip(proxyip);
                        https.setProxy_port(proxyport);
                        https.setSite(siteId);
                        https.setSite_id_server(serverSite);
                        try {
                            httpsDao.modify(https);
                            HttpsProcess https_modify_process = new HttpsProcess();
                            https_modify_process.init(managerip,Integer.parseInt(managerport),proxyip,Integer.parseInt(proxyport),site.getCert_path(),site.getKey_path(),
                                    serSite.getCert_path(),serSite.getKey_path());
                            if(SiteContextLoaderServlet.httpsProcesses.get(https_modify_process)==null){
//                                https_modify_process.start();
                                SiteContextLoaderServlet.httpsProcesses.put(https_modify_process,https_modify_process);
                            }
                            List<Https> httpList =httpsDao.findAllHttp();
                            try{
                                convertNginxConf(httpList,nginx_path);
                            }catch (Exception e){
                                logger.info("转换nginx配置文件不成功!");
                            }
                            msg ="https代理,更新成功!";
                            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction","更新BS管理端配置成功!");
                            save_flag = true;
                        } catch (Exception e) {
                            msg ="更新代理失败!";
                        }
                    }
                }
            } else if("tcp".equals(oldProtocol)){
                if(protocol.equals("http")){
                    Https https = new Https();
                    https.setId(Integer.parseInt(id));
                    https.setManager_ip(managerip);
                    https.setManager_port(managerport);
                    https.setProtocol(protocol);
                    https.setProxy_ip(proxyip);
                    https.setProxy_port(proxyport);
                    try {
                        httpsDao.modify(https);
                        List<Https> httpList =httpsDao.findAllHttp();
                        List<Https> tcpList = httpsDao.findAllTcp();
                        try{
                            convertNginxConf(httpList,nginx_path);
                            convertNginxTcpConf(tcpList,tcp_path);
                        }catch (Exception e){
                            logger.info("转换nginx配置文件不成功!");
                        }
                        save_flag = true;
                        msg ="http管理配置成功";
                        logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "更新BS管理端配置成功!");
                    } catch (Exception e) {
                        msg ="http管理配置失败";
                        logger.error("http管理配置失败:"+e);
                    }
                }else if(protocol.equals("tcp")){
                    Https https = new Https();
                    https.setId(Integer.parseInt(id));
                    https.setManager_ip(managerip);
                    https.setManager_port(managerport);
                    https.setProtocol(protocol);
                    https.setProxy_ip(proxyip);
                    https.setProxy_port(proxyport);
                    try {
                        httpsDao.modify(https);
                        List<Https> httpList =httpsDao.findAllTcp();
                        try{
                            convertNginxTcpConf(httpList,tcp_path);
                        }catch (Exception e){
                            logger.info("转换nginx tcp 配置文件不成功!");
                        }
                        save_flag = true;
                        msg ="tcp 管理配置成功";
                        logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                    } catch (Exception e) {
                        msg ="tcp 管理配置失败";
                        logger.error("tcp 管理配置失败:"+e);
                    }
                }else if(protocol.equals("thttp")){
                    Https https = new Https();
                    https.setId(Integer.parseInt(id));
                    https.setManager_ip(managerip);
                    https.setManager_port(managerport);
                    https.setProtocol(protocol);
//                    https.setProxy_ip(proxyip);
//                    https.setProxy_port(proxyport);
                    try {
                        httpsDao.modify(https);
                        List<Https> httpList =httpsDao.findAllTcp();
                        List<Https> http_List =httpsDao.findAllTHttp();
                        try{
                            convertNginxTHttpConf(http_List,thttp_path);
                            convertNginxTcpConf(httpList,tcp_path);
                        }catch (Exception e){
                            logger.info("转换nginx 透明代理 配置文件不成功!");
                        }
                        save_flag = true;
                        msg ="透明代理 配置成功";
                        logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                    } catch (Exception e) {
                        msg ="透明代理 配置失败";
                        logger.error("透明代理 配置失败:"+e);
                    }
                }else {
                    String siteId = request.getParameter("site");
                    String serverSite = request.getParameter("serverSite");
                    Site site = siteDao.findById(Integer.parseInt(siteId));
                    Site serSite = siteDao.findById(Integer.parseInt(serverSite));
                    if(site!=null){
                        Https https = new Https();
                        https.setId(Integer.parseInt(id));
                        https.setManager_ip(managerip);
                        https.setManager_port(managerport);
                        https.setProtocol(protocol);
                        https.setProxy_ip(proxyip);
                        https.setProxy_port(proxyport);
                        https.setSite(siteId);
                        https.setSite_id_server(serverSite);
                        try {
                            httpsDao.modify(https);
                            HttpsProcess https_modify_process = new HttpsProcess();
                            https_modify_process.init(managerip,Integer.parseInt(managerport),proxyip,Integer.parseInt(proxyport),site.getCert_path(),site.getKey_path(),
                                    serSite.getCert_path(),serSite.getKey_path());
                            if(SiteContextLoaderServlet.httpsProcesses.get(https_modify_process)==null){
//                                https_modify_process.start();
                                SiteContextLoaderServlet.httpsProcesses.put(https_modify_process,https_modify_process);
                            }
                            List<Https> httpList =httpsDao.findAllTcp();
                            try{
                                convertNginxTcpConf(httpList,tcp_path);
                            }catch (Exception e){
                                logger.info("转换nginx配置文件不成功!");
                            }
                            msg ="https代理,更新成功!";
                            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction","更新BS管理端配置成功!");
                            save_flag = true;
                        } catch (Exception e) {
                            msg ="更新代理失败!";
                        }
                    }
                }
            } else if("thttp".equals(oldProtocol)){
                if(protocol.equals("http")){
                    Https https = new Https();
                    https.setId(Integer.parseInt(id));
                    https.setManager_ip(managerip);
                    https.setManager_port(managerport);
                    https.setProtocol(protocol);
                    https.setProxy_ip(proxyip);
                    https.setProxy_port(proxyport);
                    try {
                        httpsDao.modify(https);
                        List<Https> httpList =httpsDao.findAllHttp();
                        List<Https> http_List =httpsDao.findAllTHttp();
                        try{
                            convertNginxConf(httpList,nginx_path);
                            convertNginxTHttpConf(http_List,thttp_path);
                        }catch (Exception e){
                            logger.info("转换nginx配置文件不成功!");
                        }
                        save_flag = true;
                        msg ="http管理配置成功";
                        logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "更新BS管理端配置成功!");
                    } catch (Exception e) {
                        msg ="http管理配置失败";
                        logger.error("http管理配置失败:"+e);
                    }
                }else if(protocol.equals("tcp")){
                    Https https = new Https();
                    https.setId(Integer.parseInt(id));
                    https.setManager_ip(managerip);
                    https.setManager_port(managerport);
                    https.setProtocol(protocol);
                    https.setProxy_ip(proxyip);
                    https.setProxy_port(proxyport);
                    try {
                        httpsDao.modify(https);
                        List<Https> httpList =httpsDao.findAllTcp();
                        List<Https> http_List =httpsDao.findAllTHttp();
                        try{
                            convertNginxTcpConf(httpList,tcp_path);
                            convertNginxTHttpConf(http_List,thttp_path);
                        }catch (Exception e){
                            logger.info("转换nginx tcp 配置文件不成功!");
                        }
                        save_flag = true;
                        msg ="tcp 管理配置成功";
                        logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                    } catch (Exception e) {
                        msg ="tcp 管理配置失败";
                        logger.error("tcp 管理配置失败:"+e);
                    }
                }else if(protocol.equals("thttp")){
                    Https https = new Https();
                    https.setId(Integer.parseInt(id));
                    https.setManager_ip(managerip);
                    https.setManager_port(managerport);
                    https.setProtocol(protocol);
//                    https.setProxy_ip(proxyip);
//                    https.setProxy_port(proxyport);
                    try {
                        httpsDao.modify(https);
                        List<Https> http_List =httpsDao.findAllTHttp();
                        try{
                            convertNginxTHttpConf(http_List,thttp_path);
                        }catch (Exception e){
                            logger.info("转换nginx 透明代理 配置文件不成功!");
                        }
                        save_flag = true;
                        msg ="透明代理 配置成功";
                        logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                    } catch (Exception e) {
                        msg ="透明代理 配置失败";
                        logger.error("透明代理 配置失败:"+e);
                    }
                }else {
                    String siteId = request.getParameter("site");
                    String serverSite = request.getParameter("serverSite");
                    Site site = siteDao.findById(Integer.parseInt(siteId));
                    Site serSite = siteDao.findById(Integer.parseInt(serverSite));
                    if(site!=null){
                        Https https = new Https();
                        https.setId(Integer.parseInt(id));
                        https.setManager_ip(managerip);
                        https.setManager_port(managerport);
                        https.setProtocol(protocol);
                        https.setProxy_ip(proxyip);
                        https.setProxy_port(proxyport);
                        https.setSite(siteId);
                        https.setSite_id_server(serverSite);
                        try {
                            httpsDao.modify(https);
                            HttpsProcess https_modify_process = new HttpsProcess();
                            https_modify_process.init(managerip,Integer.parseInt(managerport),proxyip,Integer.parseInt(proxyport),site.getCert_path(),site.getKey_path(),
                                    serSite.getCert_path(),serSite.getKey_path());
                            if(SiteContextLoaderServlet.httpsProcesses.get(https_modify_process)==null){
//                                https_modify_process.start();
                                SiteContextLoaderServlet.httpsProcesses.put(https_modify_process,https_modify_process);
                            }
                            List<Https> httpList =httpsDao.findAllTHttp();
                            try{
                                convertNginxTHttpConf(httpList,thttp_path);
                            }catch (Exception e){
                                logger.info("转换nginx配置文件不成功!");
                            }
                            msg ="https代理,更新成功!";
                            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction","更新BS管理端配置成功!");
                            save_flag = true;
                        } catch (Exception e) {
                            msg ="更新代理失败!";
                        }
                    }
                }

            }
        }   else {
            flag = httpsDao.findByManagerAndProt( /*managerip,*/ managerport);
            if(!flag){
                if("https".equals(oldProtocol)){
                    //停止以前的http
                    Site oldSite = siteDao.findById(Integer.parseInt(oldSiteId));
                    Site oldServerSite = siteDao.findById(Integer.parseInt(oldServerSiteId));
                    HttpsProcess httpsProcess = new HttpsProcess();
                    httpsProcess.init(oldManagerip,Integer.parseInt(oldManagerPort),oldProxyip,Integer.parseInt(oldManagerPort),oldSite.getCert_path(),oldSite.getKey_path(),
                            oldServerSite.getCert_path(),oldServerSite.getKey_path());
                    if(SiteContextLoaderServlet.httpsProcesses.get(httpsProcess)!=null){
                        SiteContextLoaderServlet.httpsProcesses.get(httpsProcess).stop();
                        SiteContextLoaderServlet.httpsProcesses.remove(httpsProcess);
                    }
                    //停止完成
                    if(protocol.equals("http")){
                        Https https = new Https();
                        https.setId(Integer.parseInt(id));
                        https.setManager_ip(managerip);
                        https.setManager_port(managerport);
                        https.setProtocol(protocol);
                        https.setProxy_ip(proxyip);
                        https.setProxy_port(proxyport);
                        try {
                            httpsDao.modify(https);
                            List<Https> httpList =httpsDao.findAllHttp();
                            try{
                                convertNginxConf(httpList,nginx_path);
                            }catch (Exception e){
                                logger.info("转换nginx配置文件不成功!");
                            }
                            save_flag = true;
                            msg ="http管理配置成功";
                            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                        } catch (Exception e) {
                            msg ="http管理配置失败";
                            logger.error("http管理配置失败:"+e);
                        }
                    }else if(protocol.equals("tcp")){
                        Https https = new Https();
                        https.setId(Integer.parseInt(id));
                        https.setManager_ip(managerip);
                        https.setManager_port(managerport);
                        https.setProtocol(protocol);
                        https.setProxy_ip(proxyip);
                        https.setProxy_port(proxyport);
                        try {
                            httpsDao.modify(https);
                            List<Https> httpList =httpsDao.findAllTcp();
                            try{
                                convertNginxTcpConf(httpList,tcp_path);
                            }catch (Exception e){
                                logger.info("转换nginx tcp 配置文件不成功!");
                            }
                            save_flag = true;
                            msg ="tcp 管理配置成功";
                            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                        } catch (Exception e) {
                            msg ="tcp 管理配置失败";
                            logger.error("tcp 管理配置失败:"+e);
                        }
                    }else if(protocol.equals("thttp")){
                        Https https = new Https();
                        https.setId(Integer.parseInt(id));
                        https.setManager_ip(managerip);
                        https.setManager_port(managerport);
                        https.setProtocol(protocol);
//                    https.setProxy_ip(proxyip);
//                    https.setProxy_port(proxyport);
                        try {
                            httpsDao.modify(https);
                            List<Https> httpList =httpsDao.findAllTHttp();
                            try{
                                convertNginxTHttpConf(httpList,thttp_path);
                            }catch (Exception e){
                                logger.info("转换nginx 透明代理 配置文件不成功!");
                            }
                            save_flag = true;
                            msg ="透明代理 配置成功";
                            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                        } catch (Exception e) {
                            msg ="透明代理 配置失败";
                            logger.error("透明代理 配置失败:"+e);
                        }
                    }else {
                        String siteId = request.getParameter("site");
                        String serverSite = request.getParameter("serverSite");
                        Site site = siteDao.findById(Integer.parseInt(siteId));
                        Site serSite = siteDao.findById(Integer.parseInt(serverSite));
                        if(site!=null){
                            Https https = new Https();
                            https.setId(Integer.parseInt(id));
                            https.setManager_ip(managerip);
                            https.setManager_port(managerport);
                            https.setProtocol(protocol);
                            https.setProxy_ip(proxyip);
                            https.setProxy_port(proxyport);
                            https.setSite(siteId);
                            https.setSite_id_server(serverSite);
                            try {
                                httpsDao.modify(https);
                                HttpsProcess https_modify_process = new HttpsProcess();
                                https_modify_process.init(managerip,Integer.parseInt(managerport),proxyip,Integer.parseInt(proxyport),site.getCert_path(),site.getKey_path(),
                                        serSite.getCert_path(),serSite.getKey_path());
                                if(SiteContextLoaderServlet.httpsProcesses.get(https_modify_process)==null){
//                                https_modify_process.start();
                                    SiteContextLoaderServlet.httpsProcesses.put(https_modify_process,https_modify_process);
                                }

                                msg ="https代理,更新成功!";
                                logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction","更新BS管理端配置成功!");
                                save_flag = true;
                            } catch (Exception e) {
                                msg ="更新代理失败!";
                            }
                        }
                    }
                } else if("http".equals(oldProtocol)) {
                    if(protocol.equals("http")){
                        Https https = new Https();
                        https.setId(Integer.parseInt(id));
                        https.setManager_ip(managerip);
                        https.setManager_port(managerport);
                        https.setProtocol(protocol);
                        https.setProxy_ip(proxyip);
                        https.setProxy_port(proxyport);
                        try {
                            httpsDao.modify(https);
                            List<Https> httpList =httpsDao.findAllHttp();
                            try{
                                convertNginxConf(httpList,nginx_path);
                            }catch (Exception e){
                                logger.info("转换nginx配置文件不成功!");
                            }
                            save_flag = true;
                            msg ="http管理配置成功";
                            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "更新BS管理端配置成功!");
                        } catch (Exception e) {
                            msg ="http管理配置失败";
                            logger.error("http管理配置失败:"+e);
                        }
                    }else if(protocol.equals("tcp")){
                        Https https = new Https();
                        https.setId(Integer.parseInt(id));
                        https.setManager_ip(managerip);
                        https.setManager_port(managerport);
                        https.setProtocol(protocol);
                        https.setProxy_ip(proxyip);
                        https.setProxy_port(proxyport);
                        try {
                            httpsDao.modify(https);
                            List<Https> httpList =httpsDao.findAllTcp();
                            List<Https> http_List =httpsDao.findAllHttp();
                            try{
                                convertNginxTcpConf(httpList,tcp_path);
                                convertNginxConf(http_List,nginx_path);
                            }catch (Exception e){
                                logger.info("转换nginx tcp 配置文件不成功!");
                            }
                            save_flag = true;
                            msg ="tcp 管理配置成功";
                            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                        } catch (Exception e) {
                            msg ="tcp 管理配置失败";
                            logger.error("tcp 管理配置失败:"+e);
                        }
                    }else if(protocol.equals("thttp")){
                        Https https = new Https();
                        https.setId(Integer.parseInt(id));
                        https.setManager_ip(managerip);
                        https.setManager_port(managerport);
                        https.setProtocol(protocol);
//                        https.setProxy_ip(proxyip);
//                        https.setProxy_port(proxyport);
                        try {
                            httpsDao.modify(https);
                            List<Https> httpList =httpsDao.findAllHttp();
                            List<Https> http_List =httpsDao.findAllTHttp();
                            try{
                                convertNginxTHttpConf(http_List,thttp_path);
                                convertNginxConf(httpList,nginx_path);
                            }catch (Exception e){
                                logger.info("转换nginx 透明代理 配置文件不成功!");
                            }
                            save_flag = true;
                            msg ="透明代理 配置成功";
                            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                        } catch (Exception e) {
                            msg ="透明代理 配置失败";
                            logger.error("透明代理 配置失败:"+e);
                        }
                    }else {
                        String siteId = request.getParameter("site");
                        String serverSite = request.getParameter("serverSite");
                        Site site = siteDao.findById(Integer.parseInt(siteId));
                        Site serSite = siteDao.findById(Integer.parseInt(serverSite));
                        if(site!=null){
                            Https https = new Https();
                            https.setId(Integer.parseInt(id));
                            https.setManager_ip(managerip);
                            https.setManager_port(managerport);
                            https.setProtocol(protocol);
                            https.setProxy_ip(proxyip);
                            https.setProxy_port(proxyport);
                            https.setSite(siteId);
                            https.setSite_id_server(serverSite);
                            try {
                                httpsDao.modify(https);
                                HttpsProcess https_modify_process = new HttpsProcess();
                                https_modify_process.init(managerip,Integer.parseInt(managerport),proxyip,Integer.parseInt(proxyport),site.getCert_path(),site.getKey_path(),
                                        serSite.getCert_path(),serSite.getKey_path());
                                if(SiteContextLoaderServlet.httpsProcesses.get(https_modify_process)==null){
//                                https_modify_process.start();
                                    SiteContextLoaderServlet.httpsProcesses.put(https_modify_process,https_modify_process);
                                }
                                List<Https> httpList =httpsDao.findAllHttp();
                                try{
                                    convertNginxConf(httpList,nginx_path);
                                }catch (Exception e){
                                    logger.info("转换nginx配置文件不成功!");
                                }
                                msg ="https代理,更新成功!";
                                logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction","更新BS管理端配置成功!");
                                save_flag = true;
                            } catch (Exception e) {
                                msg ="更新代理失败!";
                            }
                        }
                    }
                } else if("tcp".equals(oldProtocol)){
                    if(protocol.equals("http")){
                        Https https = new Https();
                        https.setId(Integer.parseInt(id));
                        https.setManager_ip(managerip);
                        https.setManager_port(managerport);
                        https.setProtocol(protocol);
                        https.setProxy_ip(proxyip);
                        https.setProxy_port(proxyport);
                        try {
                            httpsDao.modify(https);
                            List<Https> httpList =httpsDao.findAllHttp();
                            List<Https> tcpList = httpsDao.findAllTcp();
                            try{
                                convertNginxConf(httpList,nginx_path);
                                convertNginxTcpConf(tcpList,tcp_path);
                            }catch (Exception e){
                                logger.info("转换nginx配置文件不成功!");
                            }
                            save_flag = true;
                            msg ="http管理配置成功";
                            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "更新BS管理端配置成功!");
                        } catch (Exception e) {
                            msg ="http管理配置失败";
                            logger.error("http管理配置失败:"+e);
                        }
                    }else if(protocol.equals("tcp")){
                        Https https = new Https();
                        https.setId(Integer.parseInt(id));
                        https.setManager_ip(managerip);
                        https.setManager_port(managerport);
                        https.setProtocol(protocol);
                        https.setProxy_ip(proxyip);
                        https.setProxy_port(proxyport);
                        try {
                            httpsDao.modify(https);
                            List<Https> httpList =httpsDao.findAllTcp();
                            try{
                                convertNginxTcpConf(httpList,tcp_path);
                            }catch (Exception e){
                                logger.info("转换nginx tcp 配置文件不成功!");
                            }
                            save_flag = true;
                            msg ="tcp 管理配置成功";
                            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                        } catch (Exception e) {
                            msg ="tcp 管理配置失败";
                            logger.error("tcp 管理配置失败:"+e);
                        }
                    }else if(protocol.equals("thttp")){
                        Https https = new Https();
                        https.setId(Integer.parseInt(id));
                        https.setManager_ip(managerip);
                        https.setManager_port(managerport);
                        https.setProtocol(protocol);
//                        https.setProxy_ip(proxyip);
//                        https.setProxy_port(proxyport);
                        try {
                            httpsDao.modify(https);
                            List<Https> httpList =httpsDao.findAllTcp();
                            List<Https> http_List =httpsDao.findAllTHttp();
                            try{
                                convertNginxTHttpConf(http_List,thttp_path);
                                convertNginxTcpConf(httpList,tcp_path);
                            }catch (Exception e){
                                logger.info("转换nginx 透明代理 配置文件不成功!");
                            }
                            save_flag = true;
                            msg ="透明代理 配置成功";
                            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                        } catch (Exception e) {
                            msg ="透明代理 配置失败";
                            logger.error("透明代理 配置失败:"+e);
                        }
                    }else {
                        String siteId = request.getParameter("site");
                        String serverSite = request.getParameter("serverSite");
                        Site site = siteDao.findById(Integer.parseInt(siteId));
                        Site serSite = siteDao.findById(Integer.parseInt(serverSite));
                        if(site!=null){
                            Https https = new Https();
                            https.setId(Integer.parseInt(id));
                            https.setManager_ip(managerip);
                            https.setManager_port(managerport);
                            https.setProtocol(protocol);
                            https.setProxy_ip(proxyip);
                            https.setProxy_port(proxyport);
                            https.setSite(siteId);
                            https.setSite_id_server(serverSite);
                            try {
                                httpsDao.modify(https);
                                HttpsProcess https_modify_process = new HttpsProcess();
                                https_modify_process.init(managerip,Integer.parseInt(managerport),proxyip,Integer.parseInt(proxyport),site.getCert_path(),site.getKey_path(),
                                        serSite.getCert_path(),serSite.getKey_path());
                                if(SiteContextLoaderServlet.httpsProcesses.get(https_modify_process)==null){
//                                https_modify_process.start();
                                    SiteContextLoaderServlet.httpsProcesses.put(https_modify_process,https_modify_process);
                                }
                                List<Https> httpList =httpsDao.findAllTcp();
                                try{
                                    convertNginxTcpConf(httpList,tcp_path);
                                }catch (Exception e){
                                    logger.info("转换nginx配置文件不成功!");
                                }
                                msg ="https代理,更新成功!";
                                logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction","更新BS管理端配置成功!");
                                save_flag = true;
                            } catch (Exception e) {
                                msg ="更新代理失败!";
                            }
                        }
                    }
                } else if("thttp".equals(oldProtocol)){
                    if(protocol.equals("http")){
                        Https https = new Https();
                        https.setId(Integer.parseInt(id));
                        https.setManager_ip(managerip);
                        https.setManager_port(managerport);
                        https.setProtocol(protocol);
                        https.setProxy_ip(proxyip);
                        https.setProxy_port(proxyport);
                        try {
                            httpsDao.modify(https);
                            List<Https> httpList =httpsDao.findAllHttp();
                            List<Https> http_List =httpsDao.findAllTHttp();
                            try{
                                convertNginxConf(httpList,nginx_path);
                                convertNginxTHttpConf(http_List,thttp_path);
                            }catch (Exception e){
                                logger.info("转换nginx配置文件不成功!");
                            }
                            save_flag = true;
                            msg ="http管理配置成功";
                            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "更新BS管理端配置成功!");
                        } catch (Exception e) {
                            msg ="http管理配置失败";
                            logger.error("http管理配置失败:"+e);
                        }
                    }else if(protocol.equals("tcp")){
                        Https https = new Https();
                        https.setId(Integer.parseInt(id));
                        https.setManager_ip(managerip);
                        https.setManager_port(managerport);
                        https.setProtocol(protocol);
                        https.setProxy_ip(proxyip);
                        https.setProxy_port(proxyport);
                        try {
                            httpsDao.modify(https);
                            List<Https> httpList =httpsDao.findAllTcp();
                            List<Https> http_List =httpsDao.findAllTHttp();
                            try{
                                convertNginxTcpConf(httpList,tcp_path);
                                convertNginxTHttpConf(http_List,thttp_path);
                            }catch (Exception e){
                                logger.info("转换nginx tcp 配置文件不成功!");
                            }
                            save_flag = true;
                            msg ="tcp 管理配置成功";
                            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                        } catch (Exception e) {
                            msg ="tcp 管理配置失败";
                            logger.error("tcp 管理配置失败:"+e);
                        }
                    }else if(protocol.equals("thttp")){
                        Https https = new Https();
                        https.setId(Integer.parseInt(id));
                        https.setManager_ip(managerip);
                        https.setManager_port(managerport);
                        https.setProtocol(protocol);
//                        https.setProxy_ip(proxyip);
//                        https.setProxy_port(proxyport);
                        try {
                            httpsDao.modify(https);
                            List<Https> http_List =httpsDao.findAllTHttp();
                            try{
                                convertNginxTHttpConf(http_List,thttp_path);
                            }catch (Exception e){
                                logger.info("转换nginx 透明代理 配置文件不成功!");
                            }
                            save_flag = true;
                            msg ="透明代理 配置成功";
                            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction", "添加BS管理端配置成功!");
                        } catch (Exception e) {
                            msg ="透明代理 配置失败";
                            logger.error("透明代理 配置失败:"+e);
                        }
                    }else {
                        String siteId = request.getParameter("site");
                        String serverSite = request.getParameter("serverSite");
                        Site site = siteDao.findById(Integer.parseInt(siteId));
                        Site serSite = siteDao.findById(Integer.parseInt(serverSite));
                        if(site!=null){
                            Https https = new Https();
                            https.setId(Integer.parseInt(id));
                            https.setManager_ip(managerip);
                            https.setManager_port(managerport);
                            https.setProtocol(protocol);
                            https.setProxy_ip(proxyip);
                            https.setProxy_port(proxyport);
                            https.setSite(siteId);
                            https.setSite_id_server(serverSite);
                            try {
                                httpsDao.modify(https);
                                HttpsProcess https_modify_process = new HttpsProcess();
                                https_modify_process.init(managerip,Integer.parseInt(managerport),proxyip,Integer.parseInt(proxyport),site.getCert_path(),site.getKey_path(),
                                        serSite.getCert_path(),serSite.getKey_path());
                                if(SiteContextLoaderServlet.httpsProcesses.get(https_modify_process)==null){
//                                https_modify_process.start();
                                    SiteContextLoaderServlet.httpsProcesses.put(https_modify_process,https_modify_process);
                                }
                                List<Https> httpList =httpsDao.findAllTHttp();
                                try{
                                    convertNginxTHttpConf(httpList,thttp_path);
                                }catch (Exception e){
                                    logger.info("转换nginx配置文件不成功!");
                                }
                                msg ="https代理,更新成功!";
                                logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "BsManagerConfigAction","更新BS管理端配置成功!");
                                save_flag = true;
                            } catch (Exception e) {
                                msg ="更新代理失败!";
                            }
                        }
                    }

                }
            } else {
                msg ="管理端口冲突!";
            }
        }
        if(save_flag){
            json = "{success:true,msg:'"+msg+"'}";
        }else {
            json = "{success:false,msg:'"+msg+"'}";
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }
    
    public void convertNginxConf(List<Https> httpList,String outputFilePath)throws IOException{
//       int  maxConnect = License.getMaxConnect();
        int maxConnect =0 ;
        StringBuilder sb = new StringBuilder("");
        if(httpList!=null&&httpList.size()>0){
            for (int i=0;i<httpList.size();i++){
//                Element manager = managers.get(i);
                Https https = httpList.get(i);
                String managerIp =https.getManager_ip(); //manager.attributeValue("ip");
                String managerPort = https.getManager_port();//manager.attributeValue("port");
                String managerProtocol = https.getProtocol();//manager.attributeValue("protocol");
                //                List<Element> certs  = null;
                //                List<Element> private_keys  = null;
                //                if(managerProtocol.equals("https")){
                //                    certs  = manager.elements("cert");
                //                    private_keys  = manager.elements("private_key");
                //                }
//                Element pro_element = manager.element("proxy");
                String proxyIp = https.getProxy_ip();//pro_element.attributeValue("ip");
                String proxyPort = https.getProxy_port();//pro_element.attributeValue("port");

                //加入语句
                sb.append("server {").append("\n");
                sb.append("listen       "+managerIp+":"+managerPort+";").append("\n");
                sb.append("charset  utf-8;").append("\n");
                //                if(managerProtocol.equals("https")){
                //                    sb.append("ssl on;\n");
                //                    for (Element c:certs){
                //                        sb.append("ssl_certificate "+c.getText()+";\n");
                //                    }
                //                    for (Element k:private_keys){
                //                        sb.append("ssl_certificate_key "+k.getText()+";\n" );
                //                    }
                ////                sb.append("#ssl_client_certificate /usr/local/nginx/ssl/ROOT.crt;\n" );
                //                    sb.append("ssl_session_timeout  5m;  \n" );
                ////                sb.append("#ssl_verify_client on;\n" );
                ////                sb.append("#ssl_protocols  SSLv2 SSLv3 TLSv1;\n" );
                ////                sb.append("#ssl_ciphers ALL:!ADH:!EXPORT56:RC4+RSA:+HIGH:+MEDIUM:+LOW:+SSLv2:+EXP;\n" );
                ////                sb.append("#ssl_prefer_server_ciphers on;\n" );
                //                }
                sb.append("location /  {  \n");
                if(maxConnect > 0){
                    sb.append("limit_conn maxconn "+maxConnect+";\n");
                }else {
                    sb.append("limit_conn maxconn "+30000+";\n");
                }
                //            sb.append("#if ($ssl_client_verify != SUCCESS) {\n");
                //            sb.append("#      return 403;\n");
                //            sb.append("#      break;\n");
                //            sb.append("#      }\n");
                sb.append("proxy_pass "+managerProtocol+"://"+proxyIp+":"+proxyPort+";\n" );
                sb.append("proxy_redirect default ;\n");
                sb.append("proxy_set_header Host  $host:"+managerPort+";\n");
                sb.append("proxy_set_header X-Real-IP $remote_addr;\n" );
                sb.append("proxy_set_header REMOTE-HOST $remote_addr;\n" );
                sb.append("proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;\n" );
                sb.append("proxy_set_header X_FORWARDED_HOST $host;\n" );
                sb.append("proxy_cache pnc;\n");
                //                sb.append("proxy_temp_path /temp;\n");
                sb.append("proxy_cache_key \"$request_uri$request_body\";\n");
                sb.append("proxy_cache_methods GET POST;\n");
                sb.append("proxy_cache_valid 200 304 1m;\n");

                sb.append("}").append("\n");
                sb.append("}").append("\n");
            }
        }
        File file = new File(outputFilePath) ;
        FileOutputStream out = new FileOutputStream(file);
        out.write(sb.toString().getBytes());
        out.flush();
        out.close();
    }

    private void convertNginxTcpConf(List<Https> tcpList, String tcp_path)throws IOException{
//        int maxConnect =0 ;
        StringBuilder sb = new StringBuilder("");
        if(tcpList!=null&&tcpList.size()>0){
            for (int i=0;i<tcpList.size();i++){
                Https https = tcpList.get(i);
                String managerIp =https.getManager_ip();
                String managerPort = https.getManager_port();
//                String managerProtocol = https.getProtocol();
                String proxyIp = https.getProxy_ip();
                String proxyPort = https.getProxy_port();
                //加入语句
                sb.append("upstream cluster"+i+"{").append("\n");
                sb.append("server "+proxyIp+":"+proxyPort+";").append("\n");
                sb.append("check interval=3000 rise=2 fall=5 timeout=1000;").append("\n");
                sb.append("}").append("\n");

                sb.append("server {").append("\n");
                sb.append("listen "+managerIp+":"+managerPort+";").append("\n");
                sb.append("proxy_pass cluster"+i+";").append("\n");
                sb.append("}").append("\n");
            }
        }
        File file = new File(tcp_path) ;
        FileOutputStream out = new FileOutputStream(file);
        out.write(sb.toString().getBytes());
        out.flush();
        out.close();
    }

    private void convertNginxTHttpConf(List<Https> tcpList, String thttpPath)throws IOException{
//        int maxConnect =0 ;
        StringBuilder sb = new StringBuilder("");
        if(tcpList!=null&&tcpList.size()>0){
            for (int i=0;i<tcpList.size();i++){
                Https https = tcpList.get(i);
                String managerIp =https.getManager_ip();
                String managerPort = https.getManager_port();
//                String managerProtocol = https.getProtocol();
//                String proxyIp = https.getProxy_ip();
//                String proxyPort = https.getProxy_port();
                //加入语句
//                sb.append("server {").append("\n");
//                sb.append("listen "+managerIp+":"+managerPort+";").append("\n");
//                sb.append("resolver 8.8.8.8;").append("\n");
//                sb.append("location / {").append("\n");
//                sb.append("proxy_pass $scheme://$http_host$request_uri;").append("\n");
//                sb.append("proxy_set_header Host $http_host;").append("\n");
//                sb.append("proxy_buffers 256 4K;").append("\n");
//                sb.append("proxy_max_temp_file_size 0;").append("\n");
//                sb.append("proxy_connect_timeout 30;").append("\n");
//                sb.append("proxy_cache_valid 200 302 10m;").append("\n");
//                sb.append("proxy_cache_valid 301 1h;").append("\n");
//                sb.append("proxy_cache_valid any 1m;").append("\n");
//                sb.append("}").append("\n");
//                sb.append("}").append("\n");
                sb.append("http_port "+managerIp+":"+managerPort).append("\n");

            }
        }
        File file = new File(thttpPath) ;
        FileOutputStream out = new FileOutputStream(file);
        out.write(sb.toString().getBytes());
        out.flush();
        out.close();
    }

}
