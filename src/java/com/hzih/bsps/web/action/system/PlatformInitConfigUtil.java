package com.hzih.bsps.web.action.system;

import com.hzih.bsps.utils.StringContext;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-13
 * Time: 下午3:45
 * To change this template use File | Settings | File Templates.
 */
public class PlatformInitConfigUtil {

    private static Logger logger=Logger.getLogger(PlatformInitConfigUtil.class);

    public  static String config = "config";
    public  static String ms_pre = "ms_pre";
    public  static String ms_post = "ms_post";
//    public  static String gap_pre = "gap_pre";
//    public  static String gap_post = "gap_post";
//    public  static String ps_pre = "ps_pre";
//    public  static String ps_post = "ps_post";
    private static String path = StringContext.systemPath+"/config/platformInit.xml";

    public static String getAttribute(String attributeName){
        SAXReader saxReader = new SAXReader();
        Document doc=null;
        try {
            doc =saxReader.read(new File(path));
        } catch (DocumentException e) {
            logger.error(e.getMessage());
        }
        Element root = doc.getRootElement();
        String result = root.attributeValue(attributeName);
        return result;
    }

    public static void save(String ms_pre, String ms_post/*, String gap_pre, String gap_post*//*, String ps_pre, String ps_post*/){
        Document doc= DocumentHelper.createDocument();
        Element root=doc.addElement(PlatformInitConfigUtil.config);
        root.addAttribute(PlatformInitConfigUtil.ms_pre,ms_pre);
        root.addAttribute(PlatformInitConfigUtil.ms_post,ms_post);
//        root.addAttribute(PlatformInitConfigUtil.gap_pre,gap_pre);
//        root.addAttribute(PlatformInitConfigUtil.gap_post,gap_post);
//        root.addAttribute(PlatformInitConfigUtil.ps_pre,ps_pre);
//        root.addAttribute(PlatformInitConfigUtil.ps_post,ps_post);
        OutputFormat outputFormat=new OutputFormat("",true);
        try {
            XMLWriter xmlWriter=new XMLWriter(new FileOutputStream(new File(path)),outputFormat);
            try {
                xmlWriter.write(doc);
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        } catch (UnsupportedEncodingException e) {
            logger.info(e.getMessage());
        } catch (FileNotFoundException e) {
            logger.info(e.getMessage());
        }
    }

}
