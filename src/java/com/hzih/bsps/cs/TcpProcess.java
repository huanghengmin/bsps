package com.hzih.bsps.cs;

import com.hzih.bsps.web.action.system.PlatformInitConfigUtil;
import com.inetec.common.util.OSInfo;
import org.apache.commons.exec.*;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-20
 * Time: 下午6:06
 * To change this template use File | Settings | File Templates.
 */
public class TcpProcess {
//    private static final String post_src = "192.168.4.209";
    public static Logger logger = Logger.getLogger(TcpProcess.class);
    private boolean isRuning = false;
    private Executor executor = new DefaultExecutor();
    private ExecuteWatchdog watchdog;
    private CommandLine command;
    private DefaultExecuteResultHandler Handler = new DefaultExecuteResultHandler();
    private ShutdownHookProcessDestroyer processDestroyer = new ShutdownHookProcessDestroyer();

    public boolean isRun() {
        return isRuning;
    }

    public void initPreRouting(String sourceIp, String sourcePort,String distIp, String distPort) {
        if (OSInfo.getOSInfo().isLinux()) {
            command = new CommandLine("/sbin/iptables");
            command.addArgument("-t");
            command.addArgument("nat");
            command.addArgument("-A");
            command.addArgument("PREROUTING");
            command.addArgument("-d");
            command.addArgument(sourceIp);
            command.addArgument("-p");
            command.addArgument("tcp");
            command.addArgument("-m");
            command.addArgument("tcp");
            command.addArgument("--dport");
            command.addArgument(sourcePort);
            command.addArgument("-j");
            command.addArgument("DNAT");
            command.addArgument("--to-destination");
            command.addArgument(distIp + ":" + distPort);
            command.addArgument("LOG");
            command.addArgument("--log-level");
            command.addArgument("5");
            command.addArgument("--log-prefix");
            command.addArgument("Tcp Proxy "+sourceIp+":"+sourcePort+" to "+distIp+":"+distPort);
        }
        if(command != null){
            logger.info(command.toString());
        }
    }
    public void clearPreRouting(String sourceIp, String sourcePort,String distIp, String distPort) {
        if (OSInfo.getOSInfo().isLinux()) {
            command = new CommandLine("/sbin/iptables");
            command.addArgument("-t");
            command.addArgument("nat");
            command.addArgument("-D");
            command.addArgument("PREROUTING");
            command.addArgument("-d");
            command.addArgument(sourceIp);
            command.addArgument("-p");
            command.addArgument("tcp");
            command.addArgument("-m");
            command.addArgument("tcp");
            command.addArgument("--dport");
            command.addArgument(sourcePort);
            command.addArgument("-j");
            command.addArgument("DNAT");
            command.addArgument("--to-destination");
            command.addArgument(distIp + ":" + distPort);
            command.addArgument("LOG");
            command.addArgument("--log-level");
            command.addArgument("5");
            command.addArgument("--log-prefix");
            command.addArgument("Tcp Proxy "+sourceIp+":"+sourcePort+" to "+distIp+":"+distPort);
        }
        if(command != null){
            logger.info(command.toString());
        }
    }

    public void initPostRouting(String distIp,String sourceIp) {
        if (OSInfo.getOSInfo().isLinux()) {
            command = new CommandLine("/sbin/iptables");
            command.addArgument("-t");
            command.addArgument("nat");
            command.addArgument("-A");
            command.addArgument("POSTROUTING");
            command.addArgument("-d");
            command.addArgument(distIp);
            command.addArgument("-j");
            command.addArgument("SNAT");
            command.addArgument("--to-source");
//            command.addArgument(sourceIp);
            if(sourceIp.equals(PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post))){
                command.addArgument(PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_pre));
            }else
                command.addArgument(PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post));
        }
        if(command != null){
            logger.info(command.toString());
        }
    }

    public void clearPostRouting(String distIp,String sourceIp) {
        if (OSInfo.getOSInfo().isLinux()) {
            command = new CommandLine("/sbin/iptables");
            command.addArgument("-t");
            command.addArgument("nat");
            command.addArgument("-D");
            command.addArgument("POSTROUTING");
            command.addArgument("-d");
            command.addArgument(distIp);
            command.addArgument("-j");
            command.addArgument("SNAT");
            command.addArgument("--to-source");
            //            command.addArgument(sourceIp);
            if(sourceIp.equals(PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post))){
                command.addArgument(PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_pre));
            }else
                command.addArgument(PlatformInitConfigUtil.getAttribute(PlatformInitConfigUtil.ms_post));
        }
        if(command != null){
            logger.info(command.toString());
        }
    }

    public void start() {
        if(command != null){
            isRuning = true;
            executor.setExitValue(1);
//            watchdog=new ExecuteWatchdog(-1);
//            executor.setWatchdog(watchdog);
//            executor.setProcessDestroyer(processDestroyer);
            Handler.onProcessComplete(0);
            try {
                logger.info(command);
                executor.execute(command, Handler);
                logger.warn("execute success");
            } catch (IOException e) {
                logger.warn("VideoProcess run IOException:", e);
            }
        }
    }

    public void stop() {
        isRuning = false;
        watchdog.killedProcess();
        watchdog.destroyProcess();
        watchdog.stop();
    }
}
