package com.hzih.bsps.web.action.socat;

import com.inetec.common.util.OSInfo;
import org.apache.commons.exec.*;
import org.apache.log4j.Logger;
import org.apache.commons.exec.DefaultExecuteResultHandler;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-20
 * Time: 下午6:06
 * To change this template use File | Settings | File Templates.
 */
public class HttpsProcess implements Serializable {
    public static Logger logger = Logger.getLogger(HttpsProcess.class);
    private boolean isRuning = false;
    private Executor executor = new DefaultExecutor();
    private ExecuteWatchdog watchdog;
    private CommandLine command;
    private DefaultExecuteResultHandler Handler = new DefaultExecuteResultHandler();
    private ShutdownHookProcessDestroyer processDestroyer = new ShutdownHookProcessDestroyer();
    private String listen_ip;
    private int listen_port;
    private String proxy_ip;
    private int proxy_port;
    private String cafile;
    private String cert;
    private String server_cafile;
    private String server_cert;

    @Override
    public String toString() {
        return "HttpsProcess{" +
                "isRuning=" + isRuning +
                ", executor=" + executor +
                ", watchdog=" + watchdog +
                ", command=" + command +
                ", listen_ip='" + listen_ip + '\'' +
                ", listen_port=" + listen_port +
                ", proxy_ip='" + proxy_ip + '\'' +
                ", proxy_port=" + proxy_port +
                ", cafile='" + cafile + '\'' +
                ", cert='" + cert + '\'' +
                '}';
    }

    public boolean isRun() {
        return isRuning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HttpsProcess)) return false;

        HttpsProcess that = (HttpsProcess) o;

        if (listen_port != that.listen_port) return false;
        if (proxy_port != that.proxy_port) return false;
        if (cafile != null ? !cafile.equals(that.cafile) : that.cafile != null) return false;
        if (cert != null ? !cert.equals(that.cert) : that.cert != null) return false;
        if (listen_ip != null ? !listen_ip.equals(that.listen_ip) : that.listen_ip != null) return false;
        if (proxy_ip != null ? !proxy_ip.equals(that.proxy_ip) : that.proxy_ip != null) return false;
        if (server_cafile != null ? !server_cafile.equals(that.server_cafile) : that.server_cafile != null)
            return false;
        if (server_cert != null ? !server_cert.equals(that.server_cert) : that.server_cert != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = listen_ip != null ? listen_ip.hashCode() : 0;
        result = 31 * result + listen_port;
        result = 31 * result + (proxy_ip != null ? proxy_ip.hashCode() : 0);
        result = 31 * result + proxy_port;
        result = 31 * result + (cafile != null ? cafile.hashCode() : 0);
        result = 31 * result + (cert != null ? cert.hashCode() : 0);
        result = 31 * result + (server_cafile != null ? server_cafile.hashCode() : 0);
        result = 31 * result + (server_cert != null ? server_cert.hashCode() : 0);
        return result;
    }

    public void init(String listen_ip, int listen_port, String proxy_ip, int proxy_port, String cafile, String cert, String server_cafile, String server_cert) {
        this.listen_ip = listen_ip;
        this.listen_port = listen_port;
        this.proxy_ip = proxy_ip;
        this.proxy_port = proxy_port;
        this.cafile = cafile;
        this.cert = cert;
        this.server_cafile = server_cafile;
        this.server_cert = server_cert;

        if (OSInfo.getOSInfo().isWin()) {
            command = new CommandLine("socat.exe");
        }
        if (OSInfo.getOSInfo().isLinux()) {
            command = new CommandLine("/usr/bin/socat");
        }
        command.addArgument(
                "openssl-listen:" + listen_port
                        + ",bind=" + listen_ip + ","
                        + "cafile=" + cafile + ","
                        + "cert=" + cert + ","
                        + "reuseaddr,fork"
        );
        command.addArgument("SSL:" + proxy_ip
                + ":" + proxy_port + ","
                + "cafile=" + server_cafile + ","
                + "cert=" + server_cert);

        }


    public void start() {
        isRuning = true;
        executor.setExitValue(1);
        watchdog=new ExecuteWatchdog(-1);
        if (OSInfo.getOSInfo().isWin())
            executor.setWorkingDirectory(new File("d:/ssl"));
        executor.setWatchdog(watchdog);
        executor.setProcessDestroyer(processDestroyer);
        Handler.onProcessComplete(0);
        try {
            executor.execute(command, Handler);
        } catch (IOException e) {
            logger.warn("httpsProcess run IOException:", e);
        }
    }

    public void stop() {
        isRuning = false;
        watchdog.killedProcess();
        watchdog.destroyProcess();
        watchdog.stop();

    }

    public static void main(String arg[]) throws Exception {
        HttpsProcess video = new HttpsProcess();
        video.init("172.16.2.8", 1234, "192.168.1.115", 8443, "ROOT2.crt", "mmac.pem", "ROOT.crt", "User.pem");
        video.start();
        int n = 0;
        while (n < 1) {
            n++;
            Thread.sleep(1000);
        }
        video.stop();
//        video.start();
    }

}
