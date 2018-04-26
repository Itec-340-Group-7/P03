package connection;

import java.sql.*;

/**
 * Steven Horton
 * Austin Crockett
 * Colin Ryan
 *
 */
public class DataSource {
    private String user = "";
    private String pass = "";
    private Connection conn;

    public DataSource() throws SQLException {

            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection("jdbc:oracle:thin:@Picard2:1521:itec2", user, pass);
    }

    public Connection getConn(){
        return conn;
    }

    public void closeConn() throws SQLException {
        conn.close();
    }
}
