package com.hzih.bsps.web.servlet;

import com.inetec.common.util.Proc;
import org.apache.log4j.Logger;

import java.util.TimerTask;

public class CheckStatusTask extends TimerTask {
    private Logger log = Logger.getLogger(CheckStatusTask.class);

    public CheckStatusTask() {
    }

    @Override
    public void run() {
        log.info("****************检测后台服务状态*****************");
        Proc proc= new Proc();
        //proc.exec("service squid start");
        try {
            Thread.sleep(1000*2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        proc.exec("service squid status");
        String msg_on = proc.getOutput();
//            logger.info(msg_on);
//            if(msg_on.contains("No running copy")) {
        if (msg_on.contains("is running")) {
           // msg = "1";
        }else {
           // msg = "0";
            proc.exec("service squid start");
        }
    }

} 