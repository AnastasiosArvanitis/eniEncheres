package fr.eni.eniEncheres.dal.jdbcTools;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;

public class JdbcConnection {

    private static String dbUrl = "jdbc:sqlserver://localhost:1433;databaseName=NORTHWND";
    private static String dbDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String dbUser = "sa";
    private static String dbPassword = "flox123";
    private static String jdbcInterceptors = "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
            "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer";

    private static DataSource dataSource = new DataSource();
    private static Connection connection;

    static {
        PoolProperties poolProps = new PoolProperties();
        poolProps.setUrl(dbUrl);
        poolProps.setDriverClassName(dbDriver);
        poolProps.setUsername(dbUser);
        poolProps.setPassword(dbPassword);
        poolProps.setMaxActive(100);
        poolProps.setInitialSize(10);
        poolProps.setMaxWait(10000);
        poolProps.setJdbcInterceptors(jdbcInterceptors);
        dataSource.setPoolProperties(poolProps);
    }

}
