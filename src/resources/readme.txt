1.部署到tomcat下,在../conf/context.xml中的<Context>标签中添加或修改 <Resource/>如下:
<Resource name="jdbc/monitorDS"
         auth="Container"
         type="javax.sql.DataSource"
         maxActive="100"
         maxIdle="50"
         maxWait="5000"
         username="root"
         password="123456"
         driverClassName="com.mysql.jdbc.Driver"
         url="jdbc:mysql://192.168.1.246:3306/pubweb?characterEncoding=utf8"/>
<Resource/>说明:属性对应到config.properties