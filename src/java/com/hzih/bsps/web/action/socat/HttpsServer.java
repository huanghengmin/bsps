package com.hzih.bsps.web.action.socat;

//import com.hzih.bsms.utils.Dom4jUtil;
//import com.hzih.bsms.utils.StringContext;
import com.hzih.bsps.utils.StringContext;
import com.hzih.bsps.web.servlet.SiteContextLoaderServlet;
import com.hzih.myjfree.ProcessUtil;
import com.hzih.ssl.jdbc.JDBCUtil;
import com.inetec.common.util.Proc;
import org.apache.log4j.Logger;
//import org.dom4j.Document;
//import org.dom4j.Element;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-21
 * Time: 下午9:20
 * To change this template use File | Settings | File Templates.
 */
public class HttpsServer {
    private static Logger logger = Logger.getLogger(HttpsServer.class);
    //  private static  String config_path = StringContext.systemPath+ File.separator+"bsconfig"+File.separator+"bsMs.xml";
    public static void reads(){/*
        Document doc = Dom4jUtil.getDocument(config_path);
        if(doc!=null){
            List<Element> https = doc.selectNodes("/root/manager[@protocol='https']");
            for (int i=0;i<https.size();i++){
                Element http = https.get(i);
                String managerIp = http.attributeValue("ip");
                String managerPort = http.attributeValue("port");
//                String managerProtocol = http.attributeValue("protocol");
                Element pro_element = http.element("proxy");
                Element cert = http.element("cert");
                Element private_key = http.element("private_key");
                String proxyIp = pro_element.attributeValue("ip");
                String proxyPort = pro_element.attributeValue("port");
                HttpsProcess httpsProcess = new HttpsProcess();
                httpsProcess.init(managerIp,Integer.parseInt(managerPort),proxyIp,Integer.parseInt(proxyPort),cert.getText(),private_key.getText());
//                httpsProcess.start();
                if(SiteContextLoaderServlet.httpsProcesses.get(httpsProcess)==null){
                    SiteContextLoaderServlet.httpsProcesses.put(httpsProcess,httpsProcess);
                }*//* else {
                    SiteContextLoaderServlet.httpsProcesses.get(httpsProcess).stop();
                }*//*
            }
        }*/
        //返回用户名的记录
        JDBCUtil jdbcUtil = new JDBCUtil();
        List<Map<String,Object>> mapList = jdbcUtil.queryToList("select *from https where protocol='https'");
        for (Map<String,Object> map : mapList){
//              Object id = map.get("id");
            Object proxy_ip = map.get("proxy_ip");
            Object proxy_port = map.get("proxy_port");
            Object manager_ip = map.get("manager_ip");
            Object manager_port = map.get("manager_port");
//                Object protocol = map.get("protocol");
            Object site_id = map.get("site_id");
            Object server_site_id = map.get("site_id_server");

            List<Map<String,Object>> site_list = jdbcUtil.queryToList("select *from site where id='"+site_id+"'");
            List<Map<String,Object>> server_site_id_list = jdbcUtil.queryToList("select *from site where id='"+server_site_id+"'");

            if(site_list.size()>0){
                Map<String,Object> site = site_list.get(0);
                Map<String,Object> site_server = server_site_id_list.get(0);
                HttpsProcess httpsProcess = new HttpsProcess();
                httpsProcess.init(manager_ip.toString(),
                        Integer.parseInt(manager_port.toString()),
                        proxy_ip.toString(),
                        Integer.parseInt(proxy_port.toString()),
                        site.get("cert_path").toString(),
                        site.get("key_path").toString(),
                        site_server.get("cert_path").toString(),
                        site_server.get("key_path").toString()
                        );
                if(SiteContextLoaderServlet.httpsProcesses.get(httpsProcess)==null){
                    SiteContextLoaderServlet.httpsProcesses.put(httpsProcess,httpsProcess);
                } /*else {
                    if(SiteContextLoaderServlet.httpsProcesses.get(httpsProcess).isRun())
                        SiteContextLoaderServlet.httpsProcesses.get(httpsProcess).stop();
                }*/
            }
        }
    }

//    public static void killAllSocat(){
//        ProcessUtil processUtil = new ProcessUtil();
//        processUtil.getLinuxByCommand("sh "+ StringContext.systemPath+"/bsshell/killallsocat.sh");
//    }

    public static void stop(){
//        if(SiteContextLoaderServlet.httpsProcesses!=null){
            reads();
            Set<HttpsProcess> httpsProcesses = SiteContextLoaderServlet.httpsProcesses.keySet();
            for (Iterator it = httpsProcesses.iterator(); it.hasNext();) {
                HttpsProcess s = (HttpsProcess) it.next();
                if(s.isRun()){
                    s.stop();
                    SiteContextLoaderServlet.httpsProcesses.put(s,s) ;
                }
//                logger.info(SiteContextLoaderServlet.httpsProcesses.get(s).toString());
            }
//            killAllSocat();
            logger.info("停止https代理成功");
//        }
    }

    public static void start(){
//        if(SiteContextLoaderServlet.httpsProcesses!=null){
            reads();
//            killAllSocat();
            Set<HttpsProcess> httpsProcesses = SiteContextLoaderServlet.httpsProcesses.keySet();
            for (Iterator it = httpsProcesses.iterator(); it.hasNext();) {
                HttpsProcess s = (HttpsProcess) it.next();
                if(!s.isRun()){
                     s.start();
                     SiteContextLoaderServlet.httpsProcesses.put(s,s) ;
                }
//                logger.info(SiteContextLoaderServlet.httpsProcesses.get(s).toString());
            }
            logger.info("开启https代理成功");
//        }
    }

    public static void reload(){
//        if(SiteContextLoaderServlet.httpsProcesses!=null){
            reads();
//            killAllSocat();
            Set<HttpsProcess> httpsProcesses = SiteContextLoaderServlet.httpsProcesses.keySet();
            for (Iterator it = httpsProcesses.iterator(); it.hasNext();) {
                HttpsProcess s = (HttpsProcess) it.next();
               if(!s.isRun()){
                   s.start();
                   SiteContextLoaderServlet.httpsProcesses.put(s,s) ;
               }
//               logger.info(SiteContextLoaderServlet.httpsProcesses.get(s).toString());
            }
            logger.info("重启https代理成功");
//        }
    }
}
