package com.hzih.ssl.core.proxy.mina;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

/**
 * Handles the server to proxy part of the proxied connection.
 *
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev$, $Date$
 *
 */
public class ServerToProxyIoHandler extends AbstractProxyIoHandler {
    private Logger logger = Logger.getLogger(getClass());

    public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {
        logger.warn("Tcp client Proxy new  Session close.", throwable);
    }
}