/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package com.hzih.ssl.core.http;
import com.hzih.ssl.jdbc.UserControls;
import com.inetec.common.http.ssl.utils.HttpResponseMessage;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.ssl.SslFilter;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import java.security.cert.Certificate;

/**
 * An {@link org.apache.mina.core.service.IoHandler} for HTTP.
 *
 * @author The Apache Directory Project (mina-dev@directory.apache.org)
 * @version $Rev: 555855 $, $Date: 2007-07-13 12:19:00 +0900 (Fri, 13 Jul 2007) $
 */
public class HttpServerHandler extends IoHandlerAdapter {
    private static final Logger logger = Logger.getLogger(HttpServerHandler.class);
    private int idleTimeout = 50;
    private HttpsAuthServer server;

    public HttpServerHandler(HttpsAuthServer server) {
        this.server = server;
    }

    @Override
    public void sessionOpened(IoSession session) {
        session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, idleTimeout);
        logger.info("[TLS Server]>> sessionCreated");
    }


    public void sessionClosed(IoSession session) throws Exception {
         session.close(true);
        logger.info("[TLS Server]>> sessionClosed");
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        // Check that we can service the request context
        SSLSession sslSession = (SSLSession) session.getAttribute(SslFilter.SSL_SESSION);
        String resp = "公安数字证书用户";
        Certificate[] a = new Certificate[0];
        try {
            a = sslSession.getPeerCertificates();
        } catch (SSLPeerUnverifiedException e) {
            logger.warn("读取SSL证书出错", e);
        }
        UserControls userControls = new UserControls();
        String js = userControls.controls(a,resp);
/*      String name = null;
        String cn = null;
        String userid = null;
        String js = "";
        if (a.length >= 1) {
            X509Certificate x509 = (X509Certificate) a[0];
            name = x509.getSubjectDN().getName();
            if (name != null) {
                cn = name.split(",")[0];
                logger.info("name:" + x509.getSubjectDN().getName());
                //if (i > 0)
                if (cn.split(" ").length == 2)
                    userid = cn.split(" ")[1];
                logger.info("userid:" + userid);
            }
            String host = null;
            int port = 0;
            if (sslSession.getPeerHost() == null) {
                host = IpMac.getMinaRemoteIp(session.getRemoteAddress().toString());

            }
            if (sslSession.getPeerPort() == -1) {
                port = Integer.parseInt(IpMac.getMinaRemotePort(session.getRemoteAddress().toString()));
            }
            // 校验用户id
            if (server.checkUserId(userid, host, port)) {
                resp = resp + cn + " 证书验证通过,可以正常访问视频资源!";
                logger.info(resp);
                js = "<script language=\"javascript\" type=\"text/javascript\">           \n" +
                        "window.location.href=\"http://" + server.host + ":" + server.webport + "/\";     \n" +
                        "</script>";
            } else {
                //校验用户权限
                if (!server.checkUserPriority(userid, host, port)) {

                    resp = resp + cn + " 证书验证通过,当前资源紧张，您的优先级不够，插队失败，请稍后重试!";
                    js = "<script language=\"javascript\" type=\"text/javascript\">           \n" +
                            "alert(\"" + userid + " 证书验证通过,当前资源紧张，您的优先级不够，插队失败，请稍后重试!!\");" +
                            "</script>";
                    logger.info(resp);
                } else {
                    resp = resp + cn + " 证书验证通过,还不是视频接入用户或无访问该应用的权限，请联系管理员!";
                    js = "<script language=\"javascript\" type=\"text/javascript\">           \n" +
                            "alert(\"" + userid + " 证书验证通过,还不是视频接入用户或无访问当前应用的权限,，请联系管理员!\");" +
                            "</script>";
                    logger.info(resp);
                }
            }
        }*/
        HttpResponseMessage response = new HttpResponseMessage();
        response.setResponseCode(HttpResponseMessage.HTTP_STATUS_SUCCESS);
        //response.appendBody(resp);
        response.appendBody(js);
        session.write(response);
        logger.info(resp);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        session.close(true);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        session.close(true);
    }
}