package com.hzih.ssl.core.util;

import com.hzih.bsps.utils.StringContext;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-10-22
 * Time: 上午10:13
 * To change this template use File | Settings | File Templates.
 */
public class ConfigXML {
    private final static Logger logger = Logger.getLogger(ConfigXML.class);

    public static Document load() {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            String config_path = StringContext.systemPath+"/config/bsconfig.xml";
            //读取XML文件,获得document对象
            document = saxReader.read(new File(config_path));
        } catch (Exception ex) {
            return null;
        }
        return document;
    }
    private static String getConfigText(String text) {
        Document document = load();
        if(document!=null){
            Element element =(Element)document.selectSingleNode(text);
            if(element!=null){
                return element.getText();
            }
        }
        return null;
    }

    public static String readBindAddress() {
        String text="/configuration/server/bindAddress";
        return getConfigText(text);
    }
    public static String readBindPort() {
        String text="/configuration/server/bindPort";
        return getConfigText(text);
    }
    public static String readKeystore() {
        String text="/configuration/server/keystore";
        return getConfigText(text);
    }
    public static String readKeystorePwd() {
        String text="/configuration/server/keystorePwd";
        return getConfigText(text);
    }
    public static String readKeystoreTrust() {
        String text="/configuration/server/keystoreTrust";
        return getConfigText(text);
    }
    public static String readKeystoreTrustPwd() {
        String text="/configuration/server/keystoreTrustPwd";
        return getConfigText(text);
    }

}
