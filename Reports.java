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
     public void StudentDetail() throws SQLException {
        /******************/
        /* JDBC Statement */
        /******************/

        System.out.println("\n");
        System.out.println("--------------");
        System.out.println("JDBC Statement");
        System.out.println("--------------");

        String query1 = "SELECT distinct sc.first, sc.last, ca.RID, p.Payment, cr.TID, t.resort, t.sun_date, t.city, t.state, cr.unit_no, cr.bldg "
        		+ "FROM SkiClub sc "
        		+ "inner join Condo_Assign ca "
        		+ "on sc.MID = ca.MID "
        		+ "inner join Payment p on "
        		+ "ca.mid = p.mid "
        		+ "inner join Condo_Reservation cr on "
        		+ "p.RID = cr.RID "
        		+ "inner join Trip t on "
        		+ "cr.tid = t.tid "
        		+ "Where first = 'Matt'";

        System.out.println("\nStatement: " + query1 + "\n");

        Statement stmt1 = newCon.createStatement ();
        ResultSet rset1 = stmt1.executeQuery(query1);
        while (rset1.next ()) {
            System.out.println("Student Name: " + rset1.getString("first") + " " + rset1.getString("last") 
            		+ "\nResort Information: " + rset1.getString("resort") + " " + rset1.getDate("sun_date") + " "
            		+ rset1.getString("city") + ", " + rset1.getString("state") + " " 
            		+ "\nRoom Information: " + rset1.getString("RID") + " " + rset1.getString("unit_no")+ " " + rset1.getString("bldg") + " " 
            		+ rset1.getString("tid") + " " + "\nPayment: " +  rset1.getString("payment")+ "\n\n");
        }
        // Release the statement and result set
        stmt1.close();
        rset1.close();
    }

}
