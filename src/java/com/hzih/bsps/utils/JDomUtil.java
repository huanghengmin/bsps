package com.hzih.bsps.utils;

import com.hzih.bsps.entity.IpPort;
import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.DOMBuilder;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class JDomUtil {
    private static Logger logger=Logger.getLogger(JDomUtil.class);
    private static final  String ip="ip";
    private static final String port="port";
    private static final String ipPort="ipPort";
    private static final String ipPorts="ipPorts";

    public List<IpPort> findAll(String xmlFilePath) {
        List<IpPort>  ipPortsLists = null;
        Document document = buildFromFile(xmlFilePath);
        if(document!=null){
            Element ipPorts=document.getRootElement();
                if(ipPorts!=null){
                List<Element> ipPortObject= ipPorts.getChildren(JDomUtil.ipPort);
                if(ipPortObject!=null){
                    ipPortsLists = new ArrayList<IpPort>();
                    Iterator<Element> its=ipPortObject.iterator();
                    while (its.hasNext()){
                        Element obj=its.next();
                        IpPort newIpPort= new IpPort(obj.getAttributeValue(JDomUtil.ip),
                                Integer.parseInt(obj.getAttributeValue(JDomUtil.port)));
                        ipPortsLists.add(newIpPort);
                    }
                }
            }
        }
        return ipPortsLists;
    }

    public  boolean exist(String xmlFilePath,IpPort ipPort){
        boolean  flag=false;
        Document document = buildFromFile(xmlFilePath);
        if(document!=null){
            Element ipPorts=document.getRootElement();
            List<Element> ipPortObject= ipPorts.getChildren(JDomUtil.ipPort);
            if(!ipPortObject.isEmpty()&&ipPortObject!=null){
                Iterator<Element> its=ipPortObject.iterator();
                while (its.hasNext()){
                    Element obj=its.next();
                    if((obj.getAttributeValue(JDomUtil.ip).equals(ipPort.getIp()))&&(obj.getAttributeValue(JDomUtil.port).equals(String.valueOf(ipPort.getPort())))){
                        flag=true;
                    }
                }
             }
        }
        return flag;
    }


    public  boolean delete(String xmlFilePath,IpPort ipPort){
        boolean  flag = false;
        Document document = buildFromFile(xmlFilePath);
        if(document!=null){
            Element ipPorts=document.getRootElement();
            List<Element> ipPortObject= ipPorts.getChildren(JDomUtil.ipPort);
            if(!ipPortObject.isEmpty()&&ipPortObject!=null){
                Iterator<Element> its=ipPortObject.iterator();
                while (its.hasNext()){
                    Element obj=its.next();
                    if((obj.getAttributeValue(JDomUtil.ip).equals(ipPort.getIp()))&&(obj.getAttributeValue(JDomUtil.port).equals(String.valueOf(ipPort.getPort())))){
                        ipPorts.removeContent(obj);
                        outputToFile(document,xmlFilePath);
                        return true;
                    }
                }
            }
        }
        return flag;
    }

    public String update(String xmlFilePath,IpPort ipPort,IpPort newIpPort){
        Document document = buildFromFile(xmlFilePath);
        if(document!=null){
            Element ipPorts=document.getRootElement();
            List<Element> ipPortObject= ipPorts.getChildren(JDomUtil.ipPort);
            if(!ipPortObject.isEmpty()&&ipPortObject!=null){
                Iterator<Element> its=ipPortObject.iterator();
                while (its.hasNext()){
                    Element obj=its.next();
                    Attribute ipAttribute = obj.getAttribute(JDomUtil.ip);
                    Attribute portAttribute = obj.getAttribute(JDomUtil.port);
                    if((ipAttribute.getValue().equals(ipPort.getIp()))&&(portAttribute.getValue().equals(String.valueOf(ipPort.getPort())))){
                        if(!exist(xmlFilePath,newIpPort)){
                            Attribute newIp=new Attribute(JDomUtil.ip,newIpPort.getIp());
                            Attribute newPort=new Attribute(JDomUtil.port,String.valueOf(newIpPort.getPort()));
                            obj.setAttribute(newIp);
                            obj.setAttribute(newPort);
                            outputToFile(document,xmlFilePath);
                        } else {
                            return "ip-"+ipPort.getIp()+"**port-"+ipPort.getPort()+"**目标记录已经存在!";
                        }
                        return "true";
                    }
                }
            }
        }
        return "更新出错!";
    }

    public  String add(String xmlFilePath,IpPort ipPort){
        Document document = buildFromFile(xmlFilePath);
        if(document!=null){
            if(!exist(xmlFilePath,ipPort)){
                 Element ipPorts=document.getRootElement();
                Element ipPortObject = new Element(JDomUtil.ipPort);
                Attribute ip=new Attribute(JDomUtil.ip,ipPort.getIp());
                Attribute port=new Attribute(JDomUtil.port,String.valueOf(ipPort.getPort()));
                ipPortObject.setAttribute(ip);
                ipPortObject.setAttribute(port);
                ipPorts.addContent(ipPortObject);
                outputToFile(document,xmlFilePath);
                return "true";
            }else {
                logger.info("记录已经存在!!!"+ipPort.toString());
                return "ip-"+ipPort.getIp()+"**port-"+ipPort.getPort()+"**记录已经存在!";
            }
        }else{
            Element ipPorts=new Element(JDomUtil.ipPorts);
            Element ipPortObject=new Element(JDomUtil.ipPort);
            Attribute ip=new Attribute(JDomUtil.ip,ipPort.getIp());
            Attribute port=new Attribute(JDomUtil.port,String.valueOf(ipPort.getPort()));
            ipPortObject.setAttribute(ip);
            ipPortObject.setAttribute(port);
            ipPorts.addContent(ipPortObject);
            Document doc=new Document(ipPorts);
            outputToFile(doc,xmlFilePath);
            return "true";
        }
    }

	public  Document buildFromFile(String filePath) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document anotherDocument = builder.build(new File(filePath));
			return anotherDocument;
		} catch (JDOMException e) {
			logger.error(e.getMessage());
		} catch (NullPointerException e) {
            logger.error(e.getMessage());
		} catch (IOException e) {
            logger.error(e.getMessage());
		}
		return null;
	}

	public  Document buildFromFile(InputStream inputStream) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document anotherDocument = builder.build(inputStream);
			return anotherDocument;
		} catch (JDOMException e) {
            logger.info(e.getMessage());
		} catch (NullPointerException e) {
            logger.info(e.getMessage());
		} catch (IOException e) {
            logger.info(e.getMessage());
		}
		return null;
	}

	public  Document buildFromXMLString(String xmlString) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document anotherDocument = builder
					.build(new StringReader(xmlString));
			return anotherDocument;
		} catch (JDOMException e) {
            logger.info(e.getMessage());
		} catch (NullPointerException e) {
            logger.info(e.getMessage());
		} catch (IOException e) {
            logger.info(e.getMessage());
		}
		return null;
	}

	public  void outputToStdout(Document myDocument) {
		outputToStdout(myDocument, "UTF-8");
	}

	public  void outputToStdout(Document myDocument, String encoding) {
		try {

			XMLOutputter outputter = new XMLOutputter();
			Format fm = Format.getRawFormat();
			fm.setEncoding(encoding);
			outputter.setFormat(fm);
			outputter.output(myDocument, System.out);
		} catch (IOException e) {
            logger.info(e.getMessage());
		}
	}


	public  String outputToString(Document document) {
		return outputToString(document, "UTF-8");
	}


	public  String outputToString(Document document, String encoding) {
		ByteArrayOutputStream byteRep = new ByteArrayOutputStream();
		XMLOutputter outputter = new XMLOutputter();
		Format fm = Format.getRawFormat();
		fm.setEncoding(encoding);
		outputter.setFormat(fm);
		try {
			outputter.output(document, byteRep);
		} catch (Exception e) {
            logger.info(e.getMessage());
		}
		return byteRep.toString();
	}


	public  String outputToString(List<Object> list, String encoding) {
		ByteArrayOutputStream byteRep = new ByteArrayOutputStream();
		XMLOutputter outputter = new XMLOutputter();
		Format fm = Format.getRawFormat();
		fm.setEncoding(encoding);
		outputter.setFormat(fm);
		try {
			outputter.output(list, byteRep);
		} catch (Exception e) {
            logger.info(e.getMessage());
		}
		return byteRep.toString();
	}


	public  void outputToFile(Document myDocument, String filePath) {
		outputToFile(myDocument, filePath, "UTF-8");
	}


	public  void outputToFile(Document myDocument, String filePath,String encoding) {
		try {
			XMLOutputter outputter = new XMLOutputter();
			Format fm = Format.getRawFormat();
            fm.setExpandEmptyElements(true);
			fm.setEncoding(encoding);
			outputter.setFormat(fm);
			FileWriter writer = new FileWriter(filePath);
			outputter.output(myDocument, writer);
			writer.close();

		} catch (IOException e) {
            logger.info(e.getMessage());
		}
	}

}