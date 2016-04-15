package com.hzih.ssl.core.util;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-9-28
 * Time: 上午10:06
 * To change this template use File | Settings | File Templates.
 */
public class GetXMLPort {
    private static final Logger logger = Logger.getLogger(GetXMLPort.class);
    public static String getPort(){
        String text="/configuration/Server/WebServer/InvalidHostnameAction";
        String filename=ConfigXML.readBindPort();
//        String filename="/usr/app/vpn/data/xml/config_vpn.xml";
        String port=null;
        Document document = null;
        Element root=null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(filename));  //读取XML文件,获得document对象
            root = document.getRootElement();  //获取根节点
        } catch (Exception ex) {
            logger.error("读取XML文件,获得document对象失败");
            logger.error(ex);
        }
        List roots = root.selectNodes(text);
        Iterator iter=roots.iterator();
        while(iter.hasNext()){
            Element element=(Element)iter.next();
            if(element.getName().equals("InvalidHostnameAction"))  {
                port= element.getText();
                return port;
            }
            logger.info(port);
        }
        return null;
    }
}
