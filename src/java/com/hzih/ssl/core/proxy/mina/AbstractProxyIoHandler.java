package com.hzih.ssl.core.proxy.mina;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class of {@link org.apache.mina.core.service.IoHandler} classes which handle
 * proxied connections.
 *
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev$, $Date$
 *
 */
public abstract class AbstractProxyIoHandler extends IoHandlerAdapter {
    private static final Charset CHARSET = Charset.forName("UTF-8");
    public static final String OTHER_IO_SESSION = AbstractProxyIoHandler.class.getName()+".OtherIoSession";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        session.suspendRead();
        session.suspendWrite();
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        if (session.getAttribute( OTHER_IO_SESSION ) != null) {
            IoSession sess = (IoSession) session.getAttribute(OTHER_IO_SESSION);
            sess.setAttribute(OTHER_IO_SESSION, null);
            sess.close(false);
            session.setAttribute(OTHER_IO_SESSION, null);
        }
    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        IoBuffer rb = (IoBuffer) message;
        IoBuffer wb = IoBuffer.allocate(rb.remaining());
        rb.mark();
        wb.put(rb);
        wb.flip();
        ((IoSession) session.getAttribute(OTHER_IO_SESSION)).write(wb);
        rb.reset();
//        logger.info(rb.getString(CHARSET.newDecoder()));
    }
}