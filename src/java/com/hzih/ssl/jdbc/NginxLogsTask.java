package com.hzih.ssl.jdbc;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-29
 * Time: 下午2:57
 * To change this template use File | Settings | File Templates.
 */
public class NginxLogsTask extends java.util.TimerTask{
    public NginxLogsTask() {
    }
    public void run() {
         //对nginx日志处理
        HandlerNginxLogs handler = new HandlerNginxLogs();
        String path = handler.getFilePath();
        if(!path.equals("")&&path!=null){
            handler.readLine(path);
        }
    }
}
