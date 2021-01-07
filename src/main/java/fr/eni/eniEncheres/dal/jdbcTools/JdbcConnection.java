package fr.eni.eniEncheres.dal.jdbcTools;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcConnection {

    private static String dbUrl;
    private static String dbDriver;
    private static String dbUser;
    private static String dbPassword;
    private static String jdbcInterceptors;

    private static DataSource dataSource = new DataSource();
    private static Connection connection;

    static {
        dbUrl = Settings.getProperty("dbUrl");
        dbDriver = Settings.getProperty("dbDriver");
        dbUser = Settings.getProperty("dbUser");
        dbPassword = Settings.getProperty("dbPassword");
        jdbcInterceptors = Settings.getProperty("jdbcInterceptors");

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

    public static Connection connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = dataSource.getConnection();
                System.out.println("Connection OK");
            } catch (SQLException e) {
                throw new SQLException(e.getMessage(), e);
            }
        }
        return connection;
    }

    public static void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

}
