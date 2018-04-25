package connection;

import java.sql.*;

public class Reports {
    DataSource dataSource;
    Connection newCon;

    {
        try {
            dataSource = new DataSource();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void run() throws SQLException {
        newCon = dataSource.getConn();
        tripDetail();
    }

    public void tripDetail() throws SQLException {
        /******************/
        /* JDBC Statement */
        /******************/

        System.out.println("\n");
        System.out.println("--------------");
        System.out.println("JDBC Statement");
        System.out.println("--------------");

        String query1 = "SELECT * "
                + "FROM SkiClub ";

        System.out.println("\nStatement: " + query1 + "\n");

        Statement stmt1 = newCon.createStatement ();
        ResultSet rset1 = stmt1.executeQuery(query1);
        while (rset1.next ()) {
            System.out.println(rset1.getString("first") + "  " + rset1.getString("last"));
        }

        // Release the statement and result set
        stmt1.close();
        rset1.close();
    }

}
