package com.hzih.bsps.web.action.bs;
import com.hzih.bsps.utils.Dom4jUtil;
import com.inetec.common.security.License;
import org.dom4j.Document;
import org.dom4j.Element;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-24
 * Time: 下午4:37
 * To change this template use File | Settings | File Templates.
 */
public class BsProxyConvertConfig {

    public static  void convert(String config_path,String outputFilePath) throws IOException {
        int maxConnect = 0;
        maxConnect = License.getMaxConnect();
        Document doc = Dom4jUtil.getDocument(config_path);
        if(doc!=null){
        List<Element> managers = doc.selectNodes("/root/manager[@protocol='http']");
        StringBuilder sb = new StringBuilder();
            if(managers!=null){
                for (int i=0;i<managers.size();i++){
                    Element manager = managers.get(i);
                    String managerIp = manager.attributeValue("ip");
                    String managerPort = manager.attributeValue("port");
                    String managerProtocol = manager.attributeValue("protocol");
        //            List<Element> certs  = null;
        //            List<Element> private_keys  = null;
        //            if(managerProtocol.equals("https")){
        //                certs  = manager.elements("cert");
        //                private_keys  = manager.elements("private_key");
        //            }
                    Element pro_element = manager.element("proxy");
                    String proxyIp = pro_element.attributeValue("ip");
                    String proxyPort = pro_element.attributeValue("port");
                    //加入语句
                    sb.append("server {").append("\n");
                    sb.append("listen       "+managerIp+":"+managerPort+";").append("\n");
                    sb.append("charset  utf-8;").append("\n");
        //            if(managerProtocol.equals("https")){
        //                sb.append("ssl on;\n");
        //                for (Element c:certs){
        //                    sb.append("ssl_certificate "+c.getText()+";\n");
        //                }
        //                for (Element k:private_keys){
        //                    sb.append("ssl_certificate_key "+k.getText()+";\n" );
        //                }
        //                sb.append("#ssl_client_certificate "+StringContext.nginxPath+"/ssl/ROOT.crt;\n" );
        //                sb.append("ssl_session_timeout  5m;  \n" );
        //                sb.append("#ssl_verify_client on;\n" );
        //                sb.append("#ssl_protocols  SSLv2 SSLv3 TLSv1;\n" );
        //                sb.append("#ssl_ciphers ALL:!ADH:!EXPORT56:RC4+RSA:+HIGH:+MEDIUM:+LOW:+SSLv2:+EXP;\n" );
        //                sb.append("#ssl_prefer_server_ciphers on;\n" );
        //            }
                        sb.append("location /  {  \n");
                        if(maxConnect > 0){
                            sb.append("limit_conn maxconn "+maxConnect+";\n");
                        }else {
                            sb.append("limit_conn maxconn "+3000+";\n");
                        }
        //            sb.append("#if ($ssl_client_verify != SUCCESS) {\n");
        //            sb.append("#      return 403;\n");
        //            sb.append("#      break;\n");
        //            sb.append("#      }\n");
                        sb.append("proxy_pass "+managerProtocol+"://"+proxyIp+":"+proxyPort+";\n" );
                        sb.append("proxy_redirect default ;\n");
                        sb.append("proxy_set_header Host  $host:"+managerPort+";\n");
                        sb.append("proxy_pass_header X-Real-IP;\n" );
                        sb.append("proxy_pass_header REMOTE-HOST;\n" );
                        sb.append("proxy_pass_header X-Forwarded-For;\n" );
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
    }
}
