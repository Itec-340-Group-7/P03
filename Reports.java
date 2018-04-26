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
        //tripDetail();//finished
        condominiumDetail();
        //  studentDetail();
        dataSource.closeConn();
    }
    private int countTrips() throws SQLException {
        String query =  "SELECT COUNT(TID) as tripCount "
                +"FROM TRIP ";
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
        String format = "|%1$-16s|%2$-10s|%3$-15s|%4$-5s|\n";
        String format1 = "|%1$-8s|%2$-12s|%3$-15s|%4$-10s|\n";
        String tripDetailBanner =    "***************\n"
                +"***************\n"
                +"**TRIP DETAIL**\n"
                +"***************\n"
                +"***************\n";
        System.out.print(tripDetailBanner);
        for(int i = 1; i <= countTrips(); i++ ){
            String query1 = getQuery1(i);
            String query2 = getQuery2(i);

            Statement stmt1 = newCon.createStatement();
            ResultSet rset1 = stmt1.executeQuery(query1);
            String table = "TRIP " +i +"\n"
                    + String.format(format, "Resort", "Date", "City", "State")
                    +String.format(format,"----------------","----------","---------------","-----");
            while (rset1.next ()) {
                table +=
                        String.format(format, rset1.getString("Resort"), rset1.getDate("Sun_date"), rset1.getString("city") , rset1.getString("State"));
            }
            table += "\n";
            System.out.print(table);
            // Release the statement and result set
            stmt1.close();
            rset1.close();

            Statement stmt2 = newCon.createStatement();
            ResultSet rset2 = stmt2.executeQuery(query2);
            String table2 = String.format(format1,"First","Last","Name","Total Paid")
                    +String.format(format1,"--------","------------","---------------","----------");
            while (rset2.next ()) {
                table2 +=
                        String.format(format1, rset2.getString("first"), rset2.getString("last"), rset2.getString("name"), rset2.getInt("totalPaid"));
            }
            table2 += "\n";
            System.out.print(table2);
            stmt2.close();
            rset2.close();
        }
    }
    public void condominiumDetail() throws SQLException{
        String format = "|%1$-4s|%2$-18s|%3$-4s|%4$-4s|%5$-5s|%6$-10s|\n";
        String condoDetailBanner =    "****************\n"
                +"****************\n"
                +"**Condo Detail**\n"
                +"****************\n"
                +"****************\n";
        System.out.print(condoDetailBanner);
        for(int i = 1; i <= countTrips(); i++ ){

            String query1 = "SELECT cr.rid as Room, cr.name, cr.unit_no, cr.bldg, count(p.rid) as roomCount, sum(p.payment) as reserPaid "
                    + "FROM Condo_Assign ca "
                    + "inner join Condo_Reservation cr on ca.RID = cr.RID "
                    + "inner join SkiClub sc on ca.mid = sc.mid "
                    + "inner join Payment p on ca.mid = p.mid and ca.rid = p.rid "
                    + "WHERE cr.TID = " +i + " and p.mid = ca.mid "
                    +"group by cr.rid, cr.name, cr.unit_no, cr.bldg ";


            Statement stmt1 = newCon.createStatement();

            ResultSet rset1 = stmt1.executeQuery(query1);
            String table = "TRIP " +i +"\n"
                    +String.format(format, "ROOM","NAME","UNIT","BLDG","COUNT","TOTAL PAID")
                    +String.format(format,"----","------------------","----","----","-----","----------");;

            while (rset1.next ()) {
                table+= String.format(format, rset1.getString("Room"), rset1.getString("Name"), rset1.getString("unit_no"), rset1.getString("bldg"), rset1.getInt("roomCount"), rset1.getInt("reserPaid"));
            }
            table += "\n";
            System.out.print(table);
            // Release the statement and result set
            stmt1.close();
            rset1.close();
        }
    }
    public void studentDetail() throws SQLException {

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
