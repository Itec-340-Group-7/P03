package connection;

import java.sql.*;

public class DataSource {
    private String user = "shorton6";
    private String pass = "Itecpassword";
    private Connection conn;

    public DataSource() throws SQLException {

            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
           conn = DriverManager.getConnection("jdbc:oracle:thin:@Picard2:1521:itec2", user, pass);
            System.out.print("connection");
    }

    public Connection getConn(){
        return conn;
    }

    public void closeConn() throws SQLException {
        conn.close();
    }
}
