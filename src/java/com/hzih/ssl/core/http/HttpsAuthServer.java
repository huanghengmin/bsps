package com.hzih.ssl.core.http;

import com.hzih.ssl.core.util.ConfigXML;
import com.inetec.common.http.ssl.utils.HttpServerProtocolCodecFactory;
import com.inetec.common.logs.LogHelper;
import com.inetec.common.security.filter.IpAuthFilter;
import com.inetec.common.security.filter.IpMacFilter;
import com.inetec.common.video.user.VideoUserLoadTask;
import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.ssl.SslContextFactory;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Hashtable;


/**
 * Created by IntelliJ IDEA.
 * User: bluesky
 * Date: 12-2-22
 * Time: 下午2:20
 * To change this template use File | Settings | File Templates.
 */
public class HttpsAuthServer extends Thread {
    public static final String VERSION_STRING = "Https Auth Server Ver2.0";
    private static final Logger logger = Logger.getLogger(HttpsAuthServer.class);
    private boolean isRuning = false;
    private SocketAcceptor acceptor;
    private InetSocketAddress serverAddress;
    private static final String KEY_MANAGER_FACTORY_ALGORITHM;
    public String host;
    public int port;
    public int webport;
    private static final int account_max = 300;
    private int authcount;
    private Hashtable auth0 = new Hashtable();
    private Hashtable auth1 = new Hashtable();
    private Hashtable auth2 = new Hashtable();

    private int ipmacFilter = 0;
    private String type;
    private LogHelper m_log;

    public void setLogHelper(LogHelper logHelper, String type, int webport) {
        m_log = logHelper;
        this.type = type;
        this.webport = webport;
    }

    static {
        String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
        if (algorithm == null) {
            algorithm = KeyManagerFactory.getDefaultAlgorithm();
        }
        KEY_MANAGER_FACTORY_ALGORITHM = algorithm;
    }


  public void init(String host, int port, String file, String password) {
        //创建服务器端连接器
        acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors()+1);
        serverAddress = new InetSocketAddress(host, port);
        acceptor.getSessionConfig().setSoLinger(0);
        acceptor.getSessionConfig().setReuseAddress(true);
        this.host = host;
        this.port = port;
        SSLContext key = null;
        int x=file.lastIndexOf(".") ;
        String s=file.substring(x+1,file.length()) ;
        //获取默认过滤器
        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
        try {
            SslContextFactory sslcontextFactory = new SslContextFactory();
            sslcontextFactory.setProtocol("TLS");
            KeyStore ks = null;
            if(s.equals("p12")||s.equals("pfx")){
                ks = KeyStore.getInstance("pkcs12");
                ks.load(new FileInputStream(file), password.toCharArray());
            }
            if (s.equals("jks")||s.equals("keystore")){
                ks = KeyStore.getInstance("JKS");
                ks.load(new FileInputStream(file), password.toCharArray());
            }

            KeyStore jks = KeyStore.getInstance("JKS");
            jks.load(new FileInputStream(ConfigXML.readKeystoreTrust()), ConfigXML.readKeystoreTrustPwd().toCharArray());
//            jks.load(new FileInputStream("E:/fartec/ichange/bs/jre/lib/security/cacerts"), "changeit".toCharArray());
            for (Enumeration<String> t = ks.aliases(); t.hasMoreElements(); ) {
                String alias = t.nextElement();
                logger.info("@:" + alias);
                if (ks.isKeyEntry(alias)) {
                    Certificate[] a = ks.getCertificateChain(alias);
                    for (int i = 0; i < a.length; i++) {
                        X509Certificate x509 = (X509Certificate) a[i];
                        logger.info(x509.getSubjectX500Principal().getName().toString());
                        if (i > 0)
                        jks.setCertificateEntry(x509.getSubjectDN().toString(), x509);
                        logger.info(ks.getCertificateAlias(x509));
                    }
                }
            }
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password.toCharArray());
            TrustManagerFactory tmFact = TrustManagerFactory.getInstance("SunX509");
            tmFact.init(jks);
            sslcontextFactory.setTrustManagerFactory(tmFact);
            sslcontextFactory.setTrustManagerFactoryKeyStore(jks);         //可信证书
            sslcontextFactory.setKeyManagerFactoryKeyStore(ks);              //匹配证书
            sslcontextFactory.setKeyManagerFactoryKeyStorePassword(password);
            key = sslcontextFactory.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("Https AuthServer init error", e);
        }
        //设置加密过滤器
        SslFilter sslFilter = new SslFilter(key, true);
        //设置客户连接时需要验证客户端证书
        sslFilter.setNeedClientAuth(true);
        chain.addLast("sslFilter", sslFilter);
        //设置编码过滤器和按行读取数据模式
        chain.addLast("codec",new ProtocolCodecFilter(new HttpServerProtocolCodecFactory()));
        chain.addLast("logger", new LoggingFilter());
        //设置事件处理器
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 50);
        acceptor.setHandler(new HttpServerHandler(this));
        //服务绑定到此端口号
        if (logger.isDebugEnabled())
            logger.debug("安全认证服务器已启动， 等待连接...");
            logger.info("安全认证服务器已启动， 等待连接...");
    }

    /**
     *  获取用户id
     */
    public boolean checkUserId(String userid, String ip, int port) {
        boolean result = false;
        //检测用是否已认证
        result = IpAuthFilter.checkUserIdAndIpIsAuthed(userid, ip);
        if (result) {
            //检测用是当前用户是否有开该应用的访问权限
            if (checkUserOfApp(userid, ip, port)) {

                //IpAuthFilter.removeCache(userid, ip);
                auditInfo("视频终端用户身份验证", userid, ip, port + "", "0");
                if (checkUserPriority(userid, ip, port)) {
                    IpAuthFilter.removeCache(userid, ip);
                    IpAuthFilter.addCache(userid, ip);
                } else {
                    if (auth0.size() > 0 && auth0.containsKey(ip)) {
                        auth0.remove(ip);
                        authcount--;
                    }
                    if (auth1.size() > 0 && auth1.containsKey(ip)) {
                        auth1.remove(ip);
                        authcount--;
                    }
                    if (auth2.size() > 0 && auth2.containsKey(ip)) {
                        auth2.remove(ip);
                        authcount--;
                    }
                    IpAuthFilter.removeCache(userid, ip);
                    return false;
                }
                return true;
            } else {
                IpAuthFilter.getObject();
                IpAuthFilter.removeCache(userid, ip);
                return false;
            }
        }

        //chech ip mac is ipwhile ipblack

        result = IpMacFilter.checkIpAndMacIsVaild(ipmacFilter, ip);
        boolean userapp = checkUserOfApp(userid, ip, port);

        boolean usercheck = false;
        usercheck = VideoUserLoadTask.checkUser(userid);
        if (!usercheck) {
            auditAlert("视频终端用户身份验证", userid, ip, port + "", "0010");
        }
        if (userapp && usercheck && result) {
            IpAuthFilter.getObject();
            if (checkUserPriority(userid, ip, port))
                IpAuthFilter.addCache(userid, ip);
            else {
//                System.out.println("check not insert session:" + IpAuthFilter.checkUserIdAndIpIsAuthed(userid, ip));
                return false;
            }
            IpAuthFilter.addCache(userid, ip);
//            System.out.println("check value:" + IpAuthFilter.checkUserIdAndIpIsAuthed(userid, ip));
            result = true;
        }
        if (result) {
            auditInfo("视频终端用户身份验证", userid, ip, port + "", "0");
        }

        return result;

    }

    /**
     * 列出用户可以访问的资源
     */
    public boolean checkUserOfApp(String userid, String ip, int port) {
        boolean result = false;

        //check ip mac is authed;
        VideoUserLoadTask.init(30);
        VideoUserLoadTask.getVideoUserLoadTask().init(false);
        result = VideoUserLoadTask.getVideoUserLoadTask().checkUserOFApp(userid, type);
        if (result) {
            return result;
        } else {
            auditAlert("视频终端用户身份验证不具有访问当前应用的权限", userid, ip, port + "", "0010");
        }
        return result;

    }

    /**
     *
     */
    public boolean checkUserPriority(String userid, String ip, int port) {
        boolean result = false;

        if (authcount < account_max) {
            if (VideoUserLoadTask.getUserPriority(userid) == 0) {
                auth0.put(ip, userid);
            }
            if (VideoUserLoadTask.getUserPriority(userid) == 1) {
                auth1.put(ip, userid);
            }
            if (VideoUserLoadTask.getUserPriority(userid) == 2) {
                auth2.put(ip, userid);
            }
            authcount++;
            return true;
        } else {
            int current = VideoUserLoadTask.getUserPriority(userid);
            if (current == 0) {
                auditAlert("视频接入用户数超过设定值，您无权插队", userid, ip, port + "", "0010");
                result = false;
            }
            if (current == 1) {
                if (auth0.size() > 0) {
                    String key = (String) auth0.keys().nextElement();
                    auth0.remove(key);
                    authcount--;
                    IpAuthFilter.removeCache("", key);
                    logger.info("剔除用户成功！：" + key);
                    result = true;
                } else {

                    result = false;
                }
            }
            if (current == 2) {
                if (auth0.size() > 0) {
                    String key = (String) auth0.keys().nextElement();
                    auth0.remove(key);
                    IpAuthFilter.removeCache("", key);
                    authcount--;
                    logger.info("剔除用户成功！：" + key);
                    result = true;
                }
                if (!result) {
                    if (auth1.size() > 0) {
                        String key = (String) auth1.keys().nextElement();
                        auth1.remove(key);
                        authcount--;
                        IpAuthFilter.removeCache("", key);
                        logger.info("剔除用户成功！：" + key);
                        result = true;
                    }
                }
            }
        }

        if (result) {
            authcount++;
            logger.info("视频接入用高级权限插对成功：" + userid);
            auditInfo("视频终端用户身份验证成功", userid, ip, port + "", "0");
            return result;

        } else {
            auditAlert("视频接入用户数超过设定值,您无权插队", userid, ip, port + "", "0010");
        }
        return result;

    }

    public boolean isRuning() {
        return isRuning;
    }

    private void startServer() {
        try {
            acceptor.bind(serverAddress);
        } catch (IOException e) {
            logger.warn("Https Auth Server Start error!", e);
        }
    }

    public void run() {
        isRuning = true;
        startServer();
        while (isRuning) {
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                //okay no process
            }
        }
    }

    public void close() {
        isRuning = false;
        acceptor.unbind(serverAddress);

    }

    public void auditAlert(String operator, String userid, String ip, String cport, String status) {
        StringBuffer buff = new StringBuffer();
        buff.append("用户身份认证审计信息：");
        buff.append(operator);
        m_log.setOperate(operator);
        m_log.setSource_id(userid);
        m_log.setClient_ip(ip);
        m_log.setSource_ip(ip);
        m_log.setSource_port(cport);
        m_log.setStatusCode(status);
        m_log.setUserName(userid);
        //m_log.setRecordCount(1 + "");
        m_log.warn(buff.toString());
    }

    public void auditInfo(String operator, String userid, String ip, String cport, String status) {
        StringBuffer buff = new StringBuffer();
        buff.append("用户身份认证审计信息：");
        buff.append(operator);
        m_log.setOperate(operator);
        m_log.setSource_id(userid);
        m_log.setClient_ip(ip);
        m_log.setSource_ip(ip);
        m_log.setSource_port(cport);
        m_log.setStatusCode(status);
        m_log.setUserName(userid);
        //m_log.setRecordCount(1 + "");
        m_log.info(buff.toString());
    }

//    public static void main(String arg[]) throws Exception {
//        HttpsAuthServer server = new HttpsAuthServer();
//        server.init(ConfigXML.readBindAddress(), Integer.parseInt(ConfigXML.readBindPort()), ConfigXML.readKeystore(), ConfigXML.readKeystorePwd());
////      server.setLogHelper(new LogHelper(), "H3CV3", 8443);   //跳转设置
//        server.start();
//    }
}