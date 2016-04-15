package com.hzih.ssl.jdbc;

import org.apache.log4j.Logger;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-28
 * Time: 下午3:15
 * To change this template use File | Settings | File Templates.
 */
public class UserControls {
    private Logger logger = Logger.getLogger(UserControls.class);

    public String controls(Certificate[] a,String resp){
        String subjectDN = null;
        String username = null;
        String cn = null;
        String userID = null;
        StringBuilder js = new StringBuilder();
        if (a.length >= 1) {
            X509Certificate x509 = (X509Certificate) a[0];
            subjectDN = x509.getSubjectDN().getName();
            if (subjectDN != null) {
                    cn = subjectDN.split(",")[0];
                logger.info("subjectDN:" + x509.getSubjectX500Principal().getName().toString());
                if (cn.split(" ").length == 2) {
                     String n = cn.split(" ")[0];
                    if(n.contains("=")){
                        username = n.substring(n.lastIndexOf("=")+1,n.length());
                    }
                     userID = cn.split(" ")[1];
                }
                logger.info("userID:" + userID);
            }
            JDBCUserOperation jdbcUserOperation = new JDBCUserOperation();
            List<Map<String,Object>> resources = jdbcUserOperation.getAllResourcesByUser(username);
            List<String> urls = jdbcUserOperation.getAllUrls(resources);
            if(urls.size()>0&&urls!=null){
                resp = resp + cn + " 证书验证通过,可以正常访问资源!";
                js.append("<html><title>可访问资源</title><body></br>");
                for (String url:urls){
                    logger.info(resp);
//                    js = "<script language=\"javascript\" type=\"text/javascript\">           \n" +
//                            "window.location.href=\"" +url + "/\";     \n" +
//                            "</script>";
                     js.append("<a href="+url+">"+url+"</a></br>");
                }
                js.append("</body>").append("</html>");
            }else {
                resp = resp + cn + " 没有可以访问的资源,请联系管理员!";
                logger.info(resp);
            }
        }
        return js.toString();
    }
}
