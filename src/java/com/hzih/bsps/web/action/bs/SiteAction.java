package com.hzih.bsps.web.action.bs;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsps.dao.HttpsDao;
import com.hzih.bsps.dao.SiteDao;
import com.hzih.bsps.domain.Site;
import com.hzih.bsps.service.LogService;
import com.hzih.bsps.utils.FileUtil;
import com.hzih.bsps.utils.StringContext;
import com.hzih.bsps.web.SessionUtils;
import com.hzih.bsps.web.action.ActionBase;
import com.inetec.common.util.Proc;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-22
 * Time: 下午3:07
 * To change this template use File | Settings | File Templates.
 */
public class SiteAction extends ActionSupport {
    private Logger logger = Logger.getLogger(SiteAction.class);
    private HttpsDao httpsDao;

    public HttpsDao getHttpsDao() {
        return httpsDao;
    }

    public void setHttpsDao(HttpsDao httpsDao) {
        this.httpsDao = httpsDao;
    }

    private File crtFile;
    private String crtFileFileName;
    // 使用列表保存多个上传文件的MIME类型
    private String crtFileContentType;
    private File keyFile;
    private String keyFileFileName;
    // 使用列表保存多个上传文件的MIME类型
    private String keyFileContentType;


    public File getCrtFile() {
        return crtFile;
    }

    public void setCrtFile(File crtFile) {
        this.crtFile = crtFile;
    }

    public String getCrtFileFileName() {
        return crtFileFileName;
    }

    public void setCrtFileFileName(String crtFileFileName) {
        this.crtFileFileName = crtFileFileName;
    }

    public String getCrtFileContentType() {
        return crtFileContentType;
    }

    public void setCrtFileContentType(String crtFileContentType) {
        this.crtFileContentType = crtFileContentType;
    }

    public File getKeyFile() {
        return keyFile;
    }

    public void setKeyFile(File keyFile) {
        this.keyFile = keyFile;
    }

    public String getKeyFileFileName() {
        return keyFileFileName;
    }

    public void setKeyFileFileName(String keyFileFileName) {
        this.keyFileFileName = keyFileFileName;
    }

    public String getKeyFileContentType() {
        return keyFileContentType;
    }

    public void setKeyFileContentType(String keyFileContentType) {
        this.keyFileContentType = keyFileContentType;
    }

    private LogService logService;
    private Site  site;
    private int start;
    private int limit;

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
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

    private SiteDao siteDao;

    public SiteDao getSiteDao() {
        return siteDao;
    }

    public void setSiteDao(SiteDao siteDao) {
        this.siteDao = siteDao;
    }

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public boolean saveUploadFile(File uploadFile,String outFilePath){
        File file = new File(outFilePath);
        if(!file.exists()){
            file.delete();
        }
        FileInputStream in= null;
        try {
            in = new FileInputStream(uploadFile);
            FileOutputStream out= new FileOutputStream(file);
            byte[] b = new byte[1024];
            int count = -1;
            while((count = in.read(b)) != -1){
                out.write(b);
            }
            out.flush();
            out.close();
        } catch (Exception e) {
           return false;
        }
        return true;
    }



    public boolean spliePfxFile(String savePath,String pfxPwd){
        String name = "User";
        String pfx = savePath +"User.pfx";
        String pem = savePath+name+".pem";
        String cert = savePath+name+".crt";
        String private_key = savePath+name+".key";
        if(pfxPwd==null||pfxPwd.equals("")){
            boolean flag =CertUtils.execute("openssl pkcs12 -in "+pfx+" -passin pass: -nocerts -out "+pem+" -passout pass:123456");
            if(flag){
                boolean ex = CertUtils.execute("openssl pkcs12 -in " +pfx+" -passin pass: -clcerts -nokeys -out "+cert);
                if(ex){
                    CertUtils.execute("openssl rsa -in "+pem+" -passin pass:123456 -out "+private_key);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage());
                    }
                    Proc cat_proc = new Proc();
                    String cat_command = "sh "+ StringContext.systemPath+"/bsshell/cat.sh "+
                            cert+" " +
                            private_key+" "+
                            pem;
                    cat_proc.exec(cat_command);
                }
            }
        } else {
            boolean flag =CertUtils.execute("openssl pkcs12 -in "+pfx+" -passin pass:"+pfxPwd +" -nocerts -out "+pem+" -passout pass:123456");
            if(flag){
                boolean ex = CertUtils.execute("openssl pkcs12 -in " +pfx+" -passin pass:"+pfxPwd+" -clcerts -nokeys -out "+cert);
                if(ex){
                    CertUtils.execute("openssl rsa -in "+pem+" -passin pass:123456 -out "+private_key);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage());
                    }
                    Proc cat_proc = new Proc();
                    String cat_command = "sh "+ StringContext.systemPath+"/bsshell/cat.sh "+
                            cert+" " +
                            private_key+" "+
                            pem;
                    cat_proc.exec(cat_command);
                }
            }
        }
        if(new File(pem).exists() && new File(cert).exists() && new File(private_key).exists()) {
            return true;
        }
        return false;
    }
    
    
    public String add() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String pwd = request.getParameter("pwd");
        String json = "{success:false}";
        boolean flag = false;
        String dir = StringContext.systemPath+"/ssl/"+site.getSite_name()+"/";
        File directory = new File(dir);
        if(!directory.exists()) {
            directory.mkdirs();
        }
        try{
            boolean crtFlag = saveUploadFile(crtFile,dir+"ROOT.crt");
            if(crtFlag){
                boolean keyFlag = saveUploadFile(keyFile,dir+"User.pfx");
                if(keyFlag){
                   boolean splitFlag =spliePfxFile(dir,pwd);
                    if(splitFlag){
                         site.setCert_path(dir+"ROOT.crt");
                         site.setKey_path(dir+"User.pem");
                         flag = siteDao.add(site);
                    }
                }
            }
            if(flag){
                json= "{success:true,msg:'新增站点信息成功'}";
                logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","用户新增站点信息成功");
            }  else {
                File file = new File(dir);
                file.delete();
                json = "{success:false,msg:'新增站点信息失败'}";
                logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户新增站点信息失败");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }
    
    public String checkSite()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String site_name = request.getParameter("site_name");
        String json = "{success:false}";
        try{
            boolean flag = siteDao.check_site_name(site_name);
            if(flag){
                json= "{success:true,msg:'false'}";
                logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "SiteAction","站点名称已存在");
            }  else {
                json = "{success:true,msg:'true'}";
                logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "SiteAction", "允许加入站点");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

//    public String modify()throws Exception{
//        HttpServletRequest request = ServletActionContext.getRequest();
//        HttpServletResponse response = ServletActionContext.getResponse();
//        ActionBase actionBase = new ActionBase();
//        String result = actionBase.actionBegin(request);
//        String json = "{success:false}";
//        String pwd = request.getParameter("pwd");
//        boolean flag = false;
//        String dir = StringContext.systemPath+"/ssl/"+site.getSite_name()+"/";
//        try{
//            boolean crtFlag = saveUploadFile(crtFile,dir+"ROOT.crt");
//            if(crtFlag){
//                boolean keyFlag = saveUploadFile(keyFile,dir+"User.pfx");
//                if(keyFlag){
//                    boolean splitFlag =spliePfxFile(dir,pwd);
//                    if(splitFlag){
//                        flag = siteDao.modify(site);
//                    }
//                }
//            }
//            if(flag){
//                json= "{success:true,msg:'修改站点信息成功'}";
//                logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","用户修改站点信息信息成功");
//            }  else {
//                json = "{success:false,msg:'修改站点信息失败'}";
//                logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户修改站点信息失败");
//            }
//        }catch (Exception e){
//            logger.error(e.getMessage());
//        }
//        actionBase.actionEnd(response, json, result);
//        return null;
//    }

    public String delete()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        String id = request.getParameter("id");
        try{
            boolean flag = httpsDao.checkSiteBlock(id);
            if(flag){
                json= "{success:true,msg:'站点正在被引用!请删除引用后重试'}";
            }else {
                Site site1 = siteDao.findById(Integer.parseInt(id));
                if(site1!=null){
                    boolean del_flag = siteDao.delete(site1);
                    if(del_flag){
                        String dir = StringContext.systemPath+"/ssl/"+site1.getSite_name()+"/";
                        File directory = new File(dir);
                        if(directory.exists()) {
                            boolean delete_flag = FileUtil.delFolder(dir);
                            if(delete_flag){
                                json= "{success:true,msg:'删除站点成功!'}";
                                logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","用户删除站点信息成功");
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            json = "{success:false,msg:'删除站点失败'}";
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户删除站点信息失败");
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    /**
     * 查找
     * @return
     * @throws Exception
     */
    public String findByPages() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        PageResult pageResult =  siteDao.findByPages(start,limit);
        if(pageResult!=null){
            List<Site> list = pageResult.getResults();
            int count =  pageResult.getAllResultsAmount();
            if(list!=null){
                String  json= "{success:true,total:" + count + ",rows:[";
                Iterator<Site> raUserIterator = list.iterator();
                while (raUserIterator.hasNext()){
                    Site log = raUserIterator.next();
                    if(raUserIterator.hasNext()){
                        json += "{" +
                                "site:'"+log.getId()+"',"+
                                "site_name:'"+log.getSite_name()+"'"+
                                "},";
                    }else {
                        json += "{" +
                                "site:'"+log.getId() +"'," +
                                "site_name:'"+log.getSite_name()+"'"+
                                "}";
                    }
                }
                json += "]}";
                actionBase.actionEnd(response, json, result);
            }
        }
        return null;
    }
    
    
    public String findBySiteStore()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        PageResult pageResult =  siteDao.findByPages(start,limit);
        if(pageResult!=null){
            List<Site> list = pageResult.getResults();
            int count =  pageResult.getAllResultsAmount();
            if(list!=null){
                String  json= "{success:true,total:" + count + ",rows:[";
                Iterator<Site> raUserIterator = list.iterator();
                while (raUserIterator.hasNext()){
                    Site log = raUserIterator.next();
                    if(raUserIterator.hasNext()){
                        json += "{" +
                                "site:'"+log.getId()+"',"+
                                "site_name:'"+log.getSite_name()+"'"+
                                "},";
                    }else {
                        json += "{" +
                                "site:'"+log.getId() +"'," +
                                "site_name:'"+log.getSite_name()+"'"+
                                "}";
                    }
                }
                json += "]}";
                actionBase.actionEnd(response, json, result);
            }
        }
        return null;
    }
    
    public String findServerCrt()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String site_name = request.getParameter("site_name");
        String root = StringContext.systemPath+"/ssl/"+site_name+"/User.crt";
        File file = new File(root);
        String  json= "{success:true,total:" + 1 + ",rows:[";
        if(file.exists()){
            CertificateFactory  certificatefactory = CertificateFactory.getInstance("X.509");
            FileInputStream server = new FileInputStream(file);
            X509Certificate cert = (X509Certificate)certificatefactory.generateCertificate(server);
            String  subject  = cert.getSubjectX500Principal().getName();
            String  notBefore=String.valueOf(cert.getNotBefore());//得到开始有效日期
            String notAfter=String.valueOf(cert. getNotAfter());//得到截止日期
            String serialNumber=cert.getSerialNumber().toString(16).toUpperCase();//得到序列号   16进制
            int version =cert.getVersion();
            String cn = cert.getIssuerDN().toString();
                    json += "{" +
                            "name:'版本( Version ):',"+
                            "content:' V"+version+"'"+
                            "},";
                    json += "{" +
                            "name:'序列号( Serial Number ):'," +
                            "content:'"+serialNumber+"'"+
                            "},";
                    json += "{" +
                            "name:'颁发者( CN ):'," +
                            "content:'"+cn+"'"+
                            "},";
                    json += "{" +
                            "name:'有效起始时间( Not Before ):'," +
                            "content:'"+notBefore.toString()+"'"+
                            "},";
                    json += "{" +
                            "name:'有效终止时间( Not After ):'," +
                            "content:'"+notAfter.toString()+"'"+
                            "},";
                    json += "{" +
                            "name:'主题( Subject ):'," +
                            "content:'"+subject+"'"+
                            "}";
        }else {
            json += "{" +
                    "name:'版本( Version ):',"+
                    "content:'未发现证书,可能已被删除'"+
                    "},";
            json += "{" +
                    "name:'序列号( Serial Number ):'," +
                    "content:'未发现证书,可能已被删除'"+
                    "},";
            json += "{" +
                    "name:'颁发者( CN ):'," +
                    "content:'未发现证书,可能已被删除'"+
                    "},";
            json += "{" +
                    "name:'有效起始时间( Not Before ):'," +
                    "content:'未发现证书,可能已被删除'"+
                    "},";
            json += "{" +
                    "name:'有效终止时间( Not After ):'," +
                    "content:'未发现证书,可能已被删除'"+
                    "},";
            json += "{" +
                    "name:'主题( Subject ):'," +
                    "content:'未发现证书,可能已被删除'"+
                    "}";
        }
        json += "]}";
        actionBase.actionEnd(response, json, result);
        return null;
    }
    
    public String findTrustCrt()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String site_name = request.getParameter("site_name");
        String root = StringContext.systemPath+"/ssl/"+site_name+"/ROOT.crt";
        File file = new File(root);
        String  json= "{success:true,total:" + 1 + ",rows:[";
        if(file.exists()){
            CertificateFactory  certificatefactory = CertificateFactory.getInstance("X.509");
            FileInputStream server = new FileInputStream(file);
            X509Certificate cert = (X509Certificate)certificatefactory.generateCertificate(server);
            String  subject  = cert.getSubjectX500Principal().getName();
            String  notBefore=String.valueOf(cert.getNotBefore());//得到开始有效日期
            String notAfter=String.valueOf(cert. getNotAfter());//得到截止日期
            String serialNumber=cert.getSerialNumber().toString(16).toUpperCase();//得到序列号   16进制
            int version =cert.getVersion();
            String cn = cert.getIssuerDN().toString();
            json += "{" +
                    "name:'版本( Version ):',"+
                    "content:' V"+version+"'"+
                    "},";
            json += "{" +
                    "name:'序列号( Serial Number ):'," +
                    "content:'"+serialNumber+"'"+
                    "},";
            json += "{" +
                    "name:'颁发者( CN ):'," +
                    "content:'"+cn+"'"+
                    "},";
            json += "{" +
                    "name:'有效起始时间( Not Before ):'," +
                    "content:'"+notBefore.toString()+"'"+
                    "},";
            json += "{" +
                    "name:'有效终止时间( Not After ):'," +
                    "content:'"+notAfter.toString()+"'"+
                    "},";
            json += "{" +
                    "name:'主题( Subject ):'," +
                    "content:'"+subject+"'"+
                    "}";

        }else {
            json += "{" +
                    "name:'版本( Version ):',"+
                    "content:'未发现证书,可能已被删除'"+
                    "},";
            json += "{" +
                    "name:'序列号( Serial Number ):'," +
                    "content:'未发现证书,可能已被删除'"+
                    "},";
            json += "{" +
                    "name:'颁发者( CN ):'," +
                    "content:'未发现证书,可能已被删除'"+
                    "},";
            json += "{" +
                    "name:'有效起始时间( Not Before ):'," +
                    "content:'未发现证书,可能已被删除'"+
                    "},";
            json += "{" +
                    "name:'有效终止时间( Not After ):'," +
                    "content:'未发现证书,可能已被删除'"+
                    "},";
            json += "{" +
                    "name:'主题( Subject ):'," +
                    "content:'未发现证书,可能已被删除'"+
                    "}";
        }
        json += "]}";
        actionBase.actionEnd(response, json, result);
        return null;
    }

}
