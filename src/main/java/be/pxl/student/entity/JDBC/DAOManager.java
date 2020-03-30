package be.pxl.student.entity.JDBC;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOManager {

    private static Logger logger = LogManager.getLogger( DAOManager.class );

    private String url;
    private String username;
    private String password;

    private Connection connection;

    public DAOManager(String url) {
        this.url = url;
    }

    public DAOManager(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        if(connection == null || connection.isClosed()){
            connection = DriverManager.getConnection( url );
            connection.setAutoCommit( false );
        }
        return connection;
    }

    public void close() {
        try{
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e){
            logger.warn("Error closing connection", e);
        }
    }


    public void commit() throws SQLException {
        if(connection != null){
            connection.commit();
        }
    }

    public void rollback(Exception originalException){
        if (connection != null){
            try {
                connection.rollback();
            } catch (SQLException e){
                logger.warn( "Rollback failed", originalException );
            }
        }
    }
}
