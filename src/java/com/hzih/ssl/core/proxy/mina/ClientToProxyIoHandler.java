package com.hzih.ssl.core.proxy.mina;

import java.net.SocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;

/**
 * Handles the client to proxy part of the proxied connection.
 *
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev$, $Date$
 *
 */
public class ClientToProxyIoHandler extends AbstractProxyIoHandler {
    private final ServerToProxyIoHandler connectorHandler = new ServerToProxyIoHandler();
    private Logger logger = Logger.getLogger(getClass());

    private final IoConnector connector;

    private final SocketAddress remoteAddress;

    public ClientToProxyIoHandler(IoConnector connector,
                                  SocketAddress remoteAddress) {
        this.connector = connector;
        this.remoteAddress = remoteAddress;
        connector.setHandler(connectorHandler);
    }

    public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {
        logger.warn("Tcp client Proxy new  Session close.", throwable);
    }

    @Override
    public void sessionOpened(final IoSession session) throws Exception {
        connector.connect(remoteAddress).addListener(new IoFutureListener<ConnectFuture>() {
            public void operationComplete(ConnectFuture future) {
                try {
                    future.getSession().setAttribute(OTHER_IO_SESSION, session);
                    session.setAttribute(OTHER_IO_SESSION, future.getSession());
                    IoSession session2 = future.getSession();
                    session2.resumeRead();
                    session2.resumeWrite();
                } catch (RuntimeIoException e) {
                    // Connect failed
                    session.close(true);
                } finally {
                    session.resumeRead();
                    session.resumeWrite();
                }
            }
        });
    }






}