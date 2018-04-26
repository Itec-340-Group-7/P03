package connection;

import java.sql.*;

/**
 * @author Steven Horton - Completed the tripDetail query ensuring it returned the proper values and coded/formatted queries for the reports.java
 * @author Austin Crockett - coded the ReportDrv.java and the condoDetail, financialDetail queries ensuring they returned the proper values
 * @author Colin Ryan - coded the DataSource.java and studentDetail query ensuring it returned the proper values
 * @date 4/26/2018
 * ITEC 340 - P03
 * DataSource.java
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
