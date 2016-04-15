package com.hzih.ssl.core.proxy.mina;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-10
 * Time: 下午4:26
 * To change this template use File | Settings | File Templates.
 */
public class MinaProxyService  extends Thread {
    public static final Logger logger = Logger.getLogger(MinaProxyService.class);
    private String ip;
    private int port;
    private String proxy_host;
    private int proxy_port;
    private int timeout;
    private boolean is_run = false;

    private NioSocketAcceptor acceptor;
    private IoConnector connector;

    public boolean isRun() {
        return is_run;
    }



    public void config( String ip, int port,String proxy_host, int proxy_port, int timeout) {
        this.proxy_host = proxy_host;
        this.proxy_port = proxy_port;
        this.ip = ip;
        this.port = port;
        this.timeout = timeout;


    }

    public void run() {
        is_run = true;
        try {
            startProxy();
        } catch (Exception e) {
            logger.warn("The Proxy service Run error.port:" + proxy_port, e);
        }
    }

    public void close() {
        is_run = false;
        if (acceptor != null) {
            if (ip != null)
                acceptor.unbind(new InetSocketAddress(ip, port));
            else
                acceptor.unbind(new InetSocketAddress(port));
            acceptor.dispose();
        }
        logger.info("Tcp Proxy service Run stop.");
    }


    private void startProxy() throws Exception {
        // Create TCP/IP acceptor.
         acceptor = new NioSocketAcceptor();
        // Create TCP/IP connector.
         connector = new NioSocketConnector();
        // Set connect timeout.
        connector.setConnectTimeoutMillis(timeout);

        ClientToProxyIoHandler handler = new ClientToProxyIoHandler(connector,new InetSocketAddress(proxy_host, proxy_port));
        // Start proxy.
        acceptor.setHandler(handler);

        acceptor.bind(new InetSocketAddress(ip, port));

        logger.info("listening on port " + port);
    }
}
