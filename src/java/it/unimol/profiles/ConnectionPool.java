package it.unimol.profiles;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 *
 * @author Stefano
 */
public class ConnectionPool {

    private static ConnectionPool singletonConnectionPool;

    private DataSource dataSource;

    private ConnectionPool() {
        PoolProperties p = new PoolProperties();
        p.setUrl(ParametriDatabase.DB_URL);
        p.setDriverClassName("com.mysql.jdbc.Driver");
        p.setUsername(ParametriDatabase.DB_USERNAME);
        p.setPassword(ParametriDatabase.DB_PASSWORD);
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
                + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        dataSource = new DataSource();
        dataSource.setPoolProperties(p);
    }

    public static ConnectionPool getInstance() {
        if (singletonConnectionPool == null) {
            singletonConnectionPool = new ConnectionPool();
        }
        return singletonConnectionPool;
    }

    public Connection getConnection() throws SQLException {
            return dataSource.getConnection();
    }
}
