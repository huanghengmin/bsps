package com.hzih.ssl.jdbc;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright  2004</p>
 * <p>Company: 吕培文</p>
 * @author not attributable
 * @version 1.0
 */
public class LPWDatabaseOperation
{
    /**
     * 使用Oracle格式的DriverManager
     * @see LPWDatabaseOperation#getUseContextType
     */
    public final int useOracleDriverManager = 0;
    /**
     * 使用Sybase格式的DriverManager
     * @see LPWDatabaseOperation#getUseContextType
     */
    public final int useSybaseDriverManager = 1;
    /**
     * 使用MySQL格式的DriverManager
     * @see LPWDatabaseOperation#getUseContextType
     */
    public final int useMysqlDriverManager = 2;
    /**
     * 使用SQLServer格式的DriverManager
     * @see LPWDatabaseOperation#getUseContextType
     */
    public final int useSqlserverDriverManager = 3;
    /**
     * 使用DB2格式的DriverManager
     * @see LPWDatabaseOperation#getUseContextType
     */
    public final int useDb2DriverManager = 4;
    /**
     * 使用Informix格式的DriverManager
     * @see LPWDatabaseOperation#getUseContextType
     */
    public final int useInformixDriverManager = 5;
    /**
     * 使用PostgreSQL格式的DriverManager
     * @see LPWDatabaseOperation#getUseContextType
     */
    public final int usePostgresqlDriverManager = 6;
    /**
     * 使用Jdbc-Odbc-Bridge格式的DriverManager
     * @see LPWDatabaseOperation#getUseContextType
     */
    public final int useJdbcOdbcBridge = 7;
    /**
     * 使用Tomcate格式的DataSource
     * @see LPWDatabaseOperation#getUseContextType
     */
    public final int useTomcateDataSource = 8;
    /**
     * 使用WebLogic格式的DataSource
     * @see LPWDatabaseOperation#getUseContextType
     */
    public final int useWeblogicDataSource = 9;
    /**
     * 使用WebSphere格式的DataSource
     * @see LPWDatabaseOperation#getUseContextType
     */
    public final int useWebsphereDataSource = 10;
    ////////////////////////////////////////////////////////////////////////////
    private String[] driverManagerType;
    private int useContextType;
    private int pageSize,pageCount,absolutePage,recordCount;
    ////////////////////////////////////////////////////////////////////////////
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    /**
     * 构造LPWDatabaseOperation。
     * 在构造时需选择所使用的环境类型，
     * LPWDatabaseOperation支持多种主流的DriverManager驱动及DataSource环境，
     * 并定义了一些常用DriverManager和DataSource的标准格式，
     * 其中包括Oracle、Sybase、MySQL、SQLServer等主流数据库的DriverManager格式，
     * 以及Jdbc-Odbc-Bridge的标准格式，
     * 同时还提供了Tomcate、WebLogic和WebSphere下DataSource的标准格式，
     * 程序员在使用时只需要选择相应的常量即可。
     * @param useContextType int<br>使用的环境类型
     * @see LPWDatabaseOperation#getUseContextType
     */
    public LPWDatabaseOperation(int useContextType)
    {
        if(useContextType<0) useContextType = 0;
        if(useContextType>7) useContextType = 7;
        this.useContextType = useContextType;
        ////////////////////////////////////////////////////////////////////////
        this.driverManagerType[this.useOracleDriverManager] = new String("oracle.jdbc.driver.OracleDriver");
        this.driverManagerType[this.useSybaseDriverManager] = new String("com.sybase.jdbc.SybDriver");
        this.driverManagerType[this.useMysqlDriverManager] = new String("com.mysql.jdbc.Driver");
        this.driverManagerType[this.useSqlserverDriverManager] = new String("com.microsoft.jdbc.sqlserver.SQLServerDriver");
        this.driverManagerType[this.useDb2DriverManager] = new String("com.ibm.db2.jdbc.app.DB2Driver");
        this.driverManagerType[this.useInformixDriverManager] = new String("com.informix.jdbc.IfxDriver");
        this.driverManagerType[this.usePostgresqlDriverManager] = new String("org.postgresql.Driver");
        this.driverManagerType[this.useJdbcOdbcBridge] = new String("sun.jdbc.odbc.JdbcOdbcDrive");
        ////////////////////////////////////////////////////////////////////////
        this.pageSize = 20;
        this.pageCount = 0;
        this.absolutePage = 0;
        this.recordCount = 0;
        ////////////////////////////////////////////////////////////////////////
        this.connection = null;
        this.preparedStatement = null;
        this.resultSet = null;
    }

    /**
     * 打开数据库，需要给该方法提供数据库的URL地址、用户名称及用户密码。
     * 成功打开数据库后便可通过executeSQL方法对数据库进行操作。
     * @param databaseURL String<br>数据库的URL地址，如果使用DataSource则为数据库的JNDI-Name
     * @param userName String<br>用户名称
     * @param password String<br>用户密码
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     * @throws javax.naming.NamingException
     */
    public void openDatabase(String databaseURL,String userName,String password)
            throws java.sql.SQLException,java.lang.ClassNotFoundException,javax.naming.NamingException
    {
        if(this.useContextType>=0 && this.useContextType<=this.useJdbcOdbcBridge)
        {
            Class.forName(this.driverManagerType[this.useContextType]);
            this.connection = DriverManager.getConnection(databaseURL,userName,password);
        }
        else if(this.useContextType==this.useTomcateDataSource)
        {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource)context.lookup(databaseURL);
            this.connection = dataSource.getConnection(userName,password);
        }
        else if(this.useContextType==this.useWeblogicDataSource)
        {
        }
        else if(this.useContextType==this.useWebsphereDataSource)
        {
        }
    }

    /**
     * 执行SQL语句，可以是Select、Insert、Delete、Update中的任何一个。
     * @param sql String<br>欲被执行的SQL语句
     * @return ResultSet<br>如果执行查询操作，则返回该查询操作的ResultSet；如果执行的是其它操作，则返回null。
     * @throws java.sql.SQLException
     */
    public ResultSet executeSQL(String sql) throws java.sql.SQLException
    {
        sql = sql.trim();
        this.preparedStatement = this.connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        if(sql.substring(0,1).equalsIgnoreCase("s"))
        {
            this.resultSet = this.preparedStatement.executeQuery(sql);
            return this.resultSet;
        }
        else
        {
            this.preparedStatement.executeUpdate(sql);
            return null;
        }
    }

    /**
     * 关闭数据库
     * @throws java.sql.SQLException
     */
    public void closeDatabase() throws java.sql.SQLException
    {
        if(this.resultSet!=null)
        {
            this.resultSet.close();
        }
        if(this.preparedStatement!=null)
        {
            this.preparedStatement.close();
        }
        if(this.connection!=null)
        {
            this.connection.close();
        }
    }

    /**
     * 获得当前使用的环境类型。
     * 这些值由LPWDatabaseOperation定义：<br>
     * useOracleDriverManager - 使用Oracle格式的DriverManager<br>
     * useSybaseDriverManager - 使用Sybase格式的DriverManager<br>
     * useMysqlDriverManager - 使用MySQL格式的DriverManager<br>
     * useSqlserverDriverManager - 使用SQLServer格式的DriverManager<br>
     * useDb2DriverManager - 使用DB2格式的DriverManager<br>
     * useInformixDriverManager - 使用Informix格式的DriverManager<br>
     * usePostgresqlDriverManager - 使用PostgreSQL格式的DriverManager<br>
     * useJdbcOdbcBridge - 使用Jdbc-Odbc-Bridge格式的DriverManager<br>
     * useTomcateDataSource - 使用Tomcate格式的DataSource<br>
     * useWeblogicDataSource - 使用WebLogic格式的DataSource<br>
     * useWebsphereDataSource - 使用WebSphere格式的DataSource
     * @see LPWDatabaseOperation#useOracleDriverManager
     * @see LPWDatabaseOperation#useSybaseDriverManager
     * @see LPWDatabaseOperation#useMysqlDriverManager
     * @see LPWDatabaseOperation#useSqlserverDriverManager
     * @see LPWDatabaseOperation#useDb2DriverManager
     * @see LPWDatabaseOperation#useInformixDriverManager
     * @see LPWDatabaseOperation#usePostgresqlDriverManager
     * @see LPWDatabaseOperation#useJdbcOdbcBridge
     * @see LPWDatabaseOperation#useTomcateDataSource
     * @see LPWDatabaseOperation#useWeblogicDataSource
     * @see LPWDatabaseOperation#useWebsphereDataSource
     * @return int<br>当前使用的环境类型
     */
    public int getUseContextType()
    {
        return useContextType;
    }

    /**
     * 设置当前将要显示的页码，每页显示的数据行数由setPageSize方法的参数值决定，
     * 当输入的页码值小于1时，将显示第一页数据；
     * 当输入的页码值大于最后一页的页码值时，将显示最后一页。
     * 需要注意的是，通过setAbsolutePage来对结果集进行定位，不会使查询结果集发生改变，
     * 而只是对结果集中的指针（cursor）进行了定位。
     * 因此如果程序员想得到真正的分页效果，应参照如下程序实现相应的功能：<br>
     * LPWDatabaseOperation database;<br>
     * ···<br>
     * ResultSet resultSet = database.executeSQL("Select * From Table");<br>
     * ···<br>
     * database.setPageSize(20);<br>
     * database.setAbsolutePage(2);<br>
     * for(int i=1;i<=database.getPageSize();i++)<br>
     * {<br>
     *     ···<br>
     *     if(resultSet.isLast())<br>
     *     {<br>
     *         break;<br>
     *     }<br>
     *     resultSet.next();<br>
     * }
     * @see LPWDatabaseOperation#setPageSize
     * @param absolutePage int<br>欲显示的页码值
     */
    public void setAbsolutePage(int absolutePage)
    {
        this.getPageCount();
        if(absolutePage<1) absolutePage = 1;
        if(absolutePage>this.pageCount) absolutePage = this.pageCount;
        this.absolutePage = absolutePage;
    }
    /**
     * 获得当前显示的页码值
     * @return int<br>当前显示的页码值
     */
    public int getAbsolutePage()
    {
        return absolutePage;
    }

    /**
     * 设置每页显示记录的条数。
     * 当输入的参数值小于1时，每页显示一条记录；
     * 当输入的参数值大于记录总数时，将显示所有数据；
     * 默认情况下每页将显示20条记录。
     * 程序员可通过setAbsolutePage方法改变当前显示的页码。
     * @see LPWDatabaseOperation#setAbsolutePage
     * @param pageSize int<br>每页显示数据的行数
     */
    public void setPageSize(int pageSize)
    {
        if(pageSize<1) pageSize = 1;
        this.pageSize = pageSize;
    }
    /**
     * 获得每页显示记录的条数
     * @return int<br>每页显示记录的条数
     */
    public int getPageSize()
    {
        return pageSize;
    }

    /**
     * 获得记录结果集显示的总的页数，其值为：总的记录数/每页显示的记录数[+1]。
     * @return int<br>记录结果集显示的总的页数
     */
    public int getPageCount()
    {
        this.getRecordCout();
        this.pageCount = this.recordCount/this.pageSize;
        if((this.recordCount%this.pageSize!=0)) this.pageCount++;
        return pageCount;
    }

    /**
     * 获得记录结果集总的记录数
     * @return int<br>记录结果集总的记录数
     */
    public int getRecordCout()
    {
        try
        {
            if(this.resultSet.last())
            {
                this.recordCount = this.resultSet.getRow();
            }
            else
            {
                this.recordCount = 0;
            }
        }
        catch(java.sql.SQLException e)
        {
        }
        finally
        {
            return this.recordCount;
        }
    }
} 
