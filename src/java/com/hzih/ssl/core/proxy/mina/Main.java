package com.hzih.ssl.core.proxy.mina;

/**
 * (<b>Entry point</b>) Demonstrates how to write a very simple tunneling proxy
 * using MINA. The proxy only logs all data passing through it. This is only
 * suitable for text based protocols since received data will be converted into
 * strings before being logged.
 * <p>
 * Start a proxy like this:<br/>
 * <code>org.apache.mina.example.proxy.Main 12345 www.google.com 80</code><br/>
 * and open <a href="http://localhost:12345">http://localhost:12345</a> in a
 * browser window.
 * </p>
 *
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev$, $Date$
 */
public class Main {

/*    public static void main(String[] args) throws Exception {
//        if (args.length != 3) {
//            System.out.println(Main.class.getName()
//                    + " <proxy-port> <server-hostname> <server-port>");
//            return;
//        }
        // Create TCP/IP acceptor.
        NioSocketAcceptor acceptor = new NioSocketAcceptor();

        // Create TCP/IP connector.
        IoConnector connector = new NioSocketConnector();

        // Set connect timeout.
        connector.setConnectTimeoutMillis(30*1000L);

        ClientToProxyIoHandler handler = new ClientToProxyIoHandler(connector,
                new InetSocketAddress("192.168.3.208", 445));

        // Start proxy.
        acceptor.setHandler(handler);
        acceptor.bind(new InetSocketAddress(445));
//      acceptor.unbind(new InetSocketAddress(445));
        System.out.println("Listening on port " + 445);
    }*/
    
    public static void main(String args[])throws Exception{
//        MinaProxyService minaProxyService = new MinaProxyService();
//        minaProxyService.config("172.16.2.8", 445, "192.168.3.208", 445, 5000);
//        minaProxyService.start();
//        MinaProxyService minaProxyService2 = new MinaProxyService();
//        minaProxyService2.config("172.16.2.8",81,"192.168.3.208",81,5000);
//        minaProxyService2.start();
//        HttpsAuthServer server = new HttpsAuthServer();
//        server.init("0.0.0.0", 9528, "E:/fartec/ichange/bs/security/Device.jks", "123qwe");
//        server.setLogHelper(new LogHelper(), "H3CV3", 445);   //跳转设置
//        server.start();
    }

}