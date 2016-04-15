package com.hzih.bsps.web.action.proxy;

import com.hzih.bsps.utils.StringContext;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.io.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-3-13
 * Time: 下午4:12
 * To change this template use File | Settings | File Templates.
 */
public class TestXml  {
    public static void main(String[] args) {
        SAXReader sr = new SAXReader();
        Document doc = null;
        try {
            doc = sr.read(new File(StringContext.systemPath+File.separator+"bsconfig"+File.separator+"bsproxy3.xml"));
        } catch (DocumentException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Element root = doc.getRootElement();
//        List listhp = root.elements("httpsproxy");
//        for(Object o: listhp) {
//            Element ehp = (Element)o;
//            System.out.println(ehp.element("applyname").getText());
////            List list = ehp.elements();
////            for(int i=0; i<list.size();i++) {
////                Object obj = list.get(i);
////                Element e = (Element)obj;
////                System.out.println(e.getText());
////            }
//        }
//
//        Element addehp = root.addElement("httpsproxy");
//        addehp.addElement("applyname").setText("asdfgfggffdfdfdsfsd");
//
//        XMLWriter writer = null;
//        try {
//            writer = new XMLWriter( new FileOutputStream( new File("d:/bsproxy.xml" )));
//            writer.write(doc);
//            writer.close();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }

        List listem = root.elements("manager");
        for(int i=0;i<listem.size();i++) {
            Element em = (Element)listem.get(i);
            String mip = em.attributeValue("ip");
            String mport = em.attributeValue("port");
            Element ep = em.element("proxy");
            String pip = ep.attributeValue("ip");
            String pport = ep.attributeValue("port");
            List listurl = ep.elements("url");
//            System.out.println(listurl.size());
            for(int j=0;j<listurl.size();j++) {
                Element eurl = (Element) listurl.get(j);
                String appname = eurl.attributeValue("name");
                String appurl = eurl.getText();
//                System.out.println(mip+" "+mport+" "+pip+" "+pport+" "+appname+" "+appurl);
            }


        }
        int numlist=0;
        for(Object o : listem) {
            Element em = (Element)o;
            numlist = numlist + em.element("proxy").elements("url").size();
//            System.out.println(numlist);
        }
//        System.out.println(numlist);
//        System.out.println(root.elements("url").size());
    }
}
