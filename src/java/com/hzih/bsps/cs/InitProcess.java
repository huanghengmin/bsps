package com.hzih.bsps.cs;

import com.inetec.common.util.OSInfo;
import org.apache.commons.exec.*;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-8-14
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */

/**
 * init iptables
 */
public class InitProcess {
    public static Logger logger = Logger.getLogger(InitProcess.class);
    private boolean isRuning = false;
    private Executor executor = new DefaultExecutor();
    private ExecuteWatchdog watchdog;
    private CommandLine command;
    private DefaultExecuteResultHandler Handler = new DefaultExecuteResultHandler();
    private ShutdownHookProcessDestroyer processDestroyer = new ShutdownHookProcessDestroyer();

    public boolean isRun() {
        return isRuning;
    }

    public void init() {
        if (OSInfo.getOSInfo().isLinux()) {
            command = new CommandLine("echo \"1\" >/proc/sys/net/ipv4/ip_forward");
            logger.info(command.toString());
        }
    }
    public void initClear(){
        if (OSInfo.getOSInfo().isLinux()) {
            command = new CommandLine("/sbin/iptables");
            command.addArgument("-tnat");
            command.addArgument("-F");
            logger.info(command.toString());
        }
    }

    public void start() {
        if(command != null){
            isRuning = true;
            executor.setExitValue(1);
            watchdog=new ExecuteWatchdog(-1);
            executor.setWatchdog(watchdog);
            executor.setProcessDestroyer(processDestroyer);
            Handler.onProcessComplete(0);
            try {
                executor.execute(command, Handler);
                logger.warn("execute success");
            } catch (IOException e) {
                logger.warn("VideoProcess run IOException:", e);
            }
        }
   }

    public void stop() {
        isRuning = false;
        watchdog.destroyProcess();
        watchdog.killedProcess();
        watchdog.stop();
    }
}
