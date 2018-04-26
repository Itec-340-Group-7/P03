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
        //  studentDetail();
        dataSource.closeConn();
    }
    private int countTrips() throws SQLException {
        String query =  "SELECT DISTINCT COUNT(TID) as tripCount "
                +"FROM TRIP";
        int count = 0;

        Statement stmt1 = newCon.createStatement();
        ResultSet rset1 = stmt1.executeQuery(query);

        while (rset1.next ()) {
            count = rset1.getInt("tripCount");
        }
        return count;
    }

    private String getQuery1(int i){
        return "SELECT * "
                + "FROM Trip "
                + "WHERE TID = " +i;
    }

    private String getQuery2(int i){
        return "SELECT sc.first, sc.last, cr.name, sum(p.payment) as totalPaid "
                + "FROM Condo_Assign ca "
                + "inner join Condo_Reservation cr on ca.RID = cr.RID "
                + "inner join SkiClub sc on ca.mid = sc.mid "
                + "inner join Payment p on ca.mid = p.mid and ca.rid = p.rid "
                + "WHERE cr.TID = " +i + " and p.mid = ca.mid "
                +" Group by sc.first, sc.last, cr.name";
    }

    public void tripDetail() throws SQLException {
        for(int i = 1; i <= countTrips(); i++ ){
            String query1 = getQuery1(i);
            String query2 = getQuery2(i);

            Statement stmt1 = newCon.createStatement();
            ResultSet rset1 = stmt1.executeQuery(query1);
            String header1 = ("\tTRIP " +i +"\n" +"--Resort--Date--City--State--\n");
            while (rset1.next ()) {
                header1 +=
                        rset1.getString("Resort") +" " + rset1.getDate("Sun_date") +" " + rset1.getString("city") +" "  + rset1.getString("State") +"\n";
            }
            System.out.print(header1);
            // Release the statement and result set
            stmt1.close();
            rset1.close();

            Statement stmt2 = newCon.createStatement();
            ResultSet rset2 = stmt2.executeQuery(query2);

            while (rset2.next ()) {
                System.out.print(
                        rset2.getString("first") +" "+rset2.getString("last") +" " +rset2.getString("name") +" " +rset2.getString("totalPaid")+"\n"
                );
            }
            stmt2.close();
            rset2.close();
        }
    }
    public void condominiumDetail() throws SQLException{

        for(int i = 1; i <= countTrips(); i++ ){

        }
    }
    public void studentDetail() throws SQLException {
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

        //System.out.println("\nStatement: " + query1 + "\n");

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
