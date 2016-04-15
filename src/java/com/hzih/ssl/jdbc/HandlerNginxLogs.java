package com.hzih.ssl.jdbc;

import com.hzih.bsps.syslog.SysLogSend;
import com.hzih.bsps.utils.StringContext;
import org.apache.log4j.Logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-29
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 */
public class HandlerNginxLogs {
//    private static String liunx_access_path = "E:/fartec/ichange/bs/nginx/logs/";
    private String liunx_access_path = StringContext.nginxPath +"/logs/";
    private Logger logger = Logger.getLogger(HandlerNginxLogs.class);

    //得到访问日志路径
    public String getFilePath(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,   -1);
        String yesterday = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
        String path =liunx_access_path+"access_"+yesterday+".log";
        File file = new File(liunx_access_path);
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File f : files){
                String fileName =   f.getName();
                if(!(fileName.equals("access.log")||fileName.equals("error.log")||fileName.equals("access_"+yesterday+".log"))){
                    f.delete();
                }
            }
        }
        return path;
    }
    
    //按行读取发送日志
    public void readLine(String file_path){
        FileReader fr = null;
        BufferedReader br = null;
        try{
            try{
                fr = new FileReader(file_path);// 建立FileReader对象，并实例化为fr
            }
            catch (FileNotFoundException e){
               logger.info("读取文件错误:"+e.getMessage());
            }
            br = new BufferedReader(fr);// 建立BufferedReader对象，并实例化为br
            String line = br.readLine();// 从文件读取一行字符串
//            String[] keyvalues = line.split(" ");
//            String  equid="";
//            for (int i=0;i<=keyvalues.length;i++)   {
//                if (keyvalues[i].startsWith("time=")) {
//                     equid = keyvalues[i].substring("time=".length());
//                }
//            }
            if(line!=null)
            SysLogSend.sysLog(line);
            while (line != null){      // 判断读取到的字符串是否不为空
                line = br.readLine();// 从文件中继续读取一行数据
                if(line!=null)
                SysLogSend.sysLog(line);
            }
        }
        catch (IOException e){
            logger.info(e.getMessage());
        }
        finally{
            try{
                if (br != null)
                    br.close();// 关闭BufferedReader对象
                if (fr != null)
                    fr.close();// 关闭文件
            }
            catch (IOException e){
                logger.info(e.getMessage());
            }
        }
    }
    
//    public static void main(String args[]){
//        HandlerNginxLogs handlerNginxLogs = new HandlerNginxLogs();
//        String path = handlerNginxLogs.getFilePath();
//        handlerNginxLogs.readLine(path);
//    }
}
