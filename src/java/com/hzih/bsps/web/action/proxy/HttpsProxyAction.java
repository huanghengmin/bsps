package com.hzih.bsps.web.action.proxy;

import com.hzih.bsps.utils.StringContext;
import com.hzih.bsps.web.action.ActionBase;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-3-7
 * Time: 上午9:57
 * To change this template use File | Settings | File Templates.
 */
public class HttpsProxyAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(HttpsProxyAction.class);

    private String ids;
    private String applyname;
    private String serveip;
    private String serveport;
    private String applyip;
    private String applyport;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getApplyname() {
        return applyname;
    }

    public void setApplyname(String applyname) {
        this.applyname = applyname;
    }

    public String getServeip() {
        return serveip;
    }

    public void setServeip(String serveip) {
        this.serveip = serveip;
    }

    public String getServeport() {
        return serveport;
    }

    public void setServeport(String serveport) {
        this.serveport = serveport;
    }

    public String getApplyip() {
        return applyip;
    }

    public void setApplyip(String applyip) {
        this.applyip = applyip;
    }

    public String getApplyport() {
        return applyport;
    }

    public void setApplyport(String applyport) {
        this.applyport = applyport;
    }

    public String findHttpsProxys() throws Exception {
        String jsons = null;
        StringBuffer jsonb = new StringBuffer();
        SAXReader sr = new SAXReader();
        Document doc = null;
        try {
            doc = sr.read(new File(StringContext.systemPath+File.separator+"bsconfig"+File.separator+"bsproxy.xml"));
        } catch (DocumentException e) {
            logger.error(e.getMessage());
        }
        Element root = doc.getRootElement();
        List listhp = root.elements("httpsproxy");
        try{
        jsonb.append("{success:true,'hplist':").append(listhp.size()).append(",'hprow':[");
        for(int i=0;i<listhp.size();i++){
            Element ehp = (Element)listhp.get(i);
            jsonb.append("{id:").append(i+1).append(",applyname:'").append(ehp.element("applyname").getText());
            jsonb.append("',serveip:'").append(ehp.element("serveip").getText()).append("',serveport:'").append(ehp.element("serveport").getText());
            jsonb.append("',mapaddress:'").append(ehp.element("serveip").getText()+":"+ehp.element("serveport").getText()).append("',applyip:'").append(ehp.element("applyip").getText());
            jsonb.append("',applyport:'").append(ehp.element("applyport").getText()).append("',applyaddress:'").append(ehp.element("applyip").getText()+":"+ehp.element("applyport").getText());
            jsonb.append("',description:'").append(ehp.element("applyname").getText()).append("'},");
        }
        if(null==listhp||0==listhp.size()) {
            jsonb.deleteCharAt(jsonb.length()-1);
        }
        jsonb.append("]}");
        jsons = jsonb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
//        jsons = "{success:true,'hplist':1,'hprow':[" +
//                "{id:1,applyname:'国家',serveip:'C',serveport:'asfd',mapaddress:'1.1.1.1:5656',applyip:'C',applyport:'asfd',applyaddress:'2.2.2.2:0000'}" +
//                "]}";
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return null;
    }

    public String addHttpsProxy() throws IOException, DocumentException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        String json = null;
        SAXReader sr = new SAXReader();
        Document doc = null;
        doc = sr.read(new File(StringContext.systemPath+File.separator+"bsconfig"+File.separator+"bsproxy.xml"));
        Element root = doc.getRootElement();
        List listhp = root.elements("httpsproxy");
        Boolean flagcf = false;
        for(Object o:listhp) {
            Element ehp = (Element)o;
            if(applyname.endsWith(ehp.element("applyname").getText())){
                flagcf = true;
            }
        }
        if(flagcf){
            msg = "ssadd";
            json = "{success:true,msg:'"+msg+"'}";
        }else {
            try{
                Element addehp = root.addElement("httpsproxy");
                addehp.addElement("applyname").setText(applyname);
                addehp.addElement("serveip").setText(serveip);
                addehp.addElement("serveport").setText(serveport);
                addehp.addElement("applyip").setText(applyip);
                addehp.addElement("applyport").setText(applyport);
                //格式化XML
                OutputFormat pretty = OutputFormat.createPrettyPrint();
                pretty.setIndent("  ");//设置缩进
                XMLWriter writer = null;
                writer = new XMLWriter( new FileOutputStream( new File(StringContext.systemPath+File.separator+"bsconfig"+File.separator+"bsproxy.xml" )),pretty);
                writer.write(doc);
                writer.close();
                msg = "增加成功";
            } catch (Exception e){
                logger.error(e);
                msg = "增加失败";
            }
            json = "{success:true,msg:'"+msg+"'}";
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String delHttpsProxy() throws IOException, DocumentException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        SAXReader sr = new SAXReader();
        Document doc = null;
        doc = sr.read(new File(StringContext.systemPath+File.separator+"bsconfig"+File.separator+"bsproxy.xml"));
        Element root = doc.getRootElement();
        List listhp = root.elements("httpsproxy");
        try{
            String[] idarray = ids.split(",");
            for(String idstr : idarray) {
                int idint = Integer.parseInt(idstr);
                root.remove((Element) listhp.get(idint-1));
            }
            //格式化XML
            OutputFormat pretty = OutputFormat.createPrettyPrint();
            pretty.setIndent("  ");//设置缩进
            XMLWriter writer = new XMLWriter( new FileOutputStream( new File(StringContext.systemPath+File.separator+"bsconfig"+File.separator+"bsproxy.xml" )),pretty);
            writer.write(doc);
            writer.close();
            msg = "删除成功";
        } catch (Exception e){
            msg = "删除失败";
        }
        String json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }
}
