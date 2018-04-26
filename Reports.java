package connection;

import java.sql.*;
/**
 * @author Steven Horton - Completed the tripDetail query ensuring it returned the proper values and coded/formatted queries for the reports.java
 * @author Austin Crockett - coded the ReportDrv.java and the condoDetail, financialDetail queries ensuring they returned the proper values
 * @author Colin Ryan - coded the DataSource.java and studentDetail query ensuring it returned the proper values
 * @date 4/26/2018
 * ITEC 340 - P03
 * Reports.java
 */
public class Reports {
    private DataSource  dataSource;
    private Connection newCon;

    private String financialFormat = "|%1$-16s|%2$-10s|%3$-10s|%4$-17s|\n";
    private String tripFormat = "|%1$-16s|%2$-10s|%3$-16s|%4$-5s|\n";
    private String tripFormat1 = "|%1$-8s|%2$-12s|%3$-16s|%4$-10s|\n";
    private String condoFormat = "|%1$-4s|%2$-18s|%3$-4s|%4$-4s|%5$-5s|%6$-10s|\n";
    private String studentFormat = "|%1$-8s|%2$-12s|%3$-16s|%4$-10s|%5$-12s|%6$-5s|%7$-17s|%8$-4s|%9$-4s|%10$-4s|%11$-5s|%12$-5s|\n";

    private String financialDetailBanner =
            "*********************\n"
                    +"********************\n"
                    +"**Financial Detail**\n"
                    +"********************\n"
                    +"********************\n";
    private String tripDetailBanner =
            "***************\n"
                    + "***************\n"
                    + "**TRIP DETAIL**\n"
                    + "***************\n"
                    + "***************\n";
    private String condoDetailBanner =
            "****************\n"
                    + "****************\n"
                    + "**Condo Detail**\n"
                    + "****************\n"
                    + "****************\n";
    private String studentDetailBanner =
            "******************\n"
                    + "******************\n"
                    + "**Student Detail**\n"
                    + "******************\n"
                    + "******************\n";

    /**
     * generic constructor that allows access to the DataSource class
     *
     * @throws SQLException
     */
    public Reports() throws SQLException {
        dataSource = new DataSource();
    }

    /**
     * run method creates a temporary connection from the
     * DataSource class and runs the methods responsible
     * for fetching the required information
     *
     * @throws SQLException
     */
    public void run() throws SQLException {
        newCon = dataSource.getConn();
        tripDetail();//finished
        condominiumDetail(); //finished
        studentDetail(); //finished
        financialDetail(); //finished
        dataSource.closeConn();
    }

    /**
     * tripDetail Method list each planned trip along with the students attending
     *
     * @throws SQLException
     */
    private void tripDetail() throws SQLException {
        System.out.print(tripDetailBanner);
        for (int i = 1; i <= countTrips(); i++) {
            String query1 = getQuery1(i);
            String query2 = getQuery2(i);

            Statement stmt1 = newCon.createStatement();
            ResultSet rset1 = stmt1.executeQuery(query1);
            String table = "TRIP " + i + "\n"
                    + String.format(tripFormat, "Resort", "Date", "City", "State")
                    + String.format(tripFormat, "----------------", "----------", "----------------", "-----");
            while (rset1.next()) {
                table +=
                        String.format(tripFormat, rset1.getString("Resort"), rset1.getDate("Sun_date"), rset1.getString("city"), rset1.getString("State"));
            }
            table += "\n";
            System.out.print(table);
            // Release the statement and result set
            stmt1.close();
            rset1.close();

            Statement stmt2 = newCon.createStatement();
            ResultSet rset2 = stmt2.executeQuery(query2);
            String table2 = String.format(tripFormat1, "First", "Last", "Name", "Total Paid")
                    + String.format(tripFormat1, "--------", "------------", "----------------", "----------");
            while (rset2.next()) {
                table2 +=
                        String.format(tripFormat1, rset2.getString("first"), rset2.getString("last"), rset2.getString("name"), rset2.getInt("totalPaid"));
            }
            table2 += "\n";
            System.out.print(table2);
            stmt2.close();
            rset2.close();
        }
    }

    /**
     * condominiumDetail lists each condo with how
     * many students have reserved and how much has been paid so far
     *
     * @throws SQLException
     */
    private void condominiumDetail() throws SQLException {

        System.out.print(condoDetailBanner);
        for (int i = 1; i <= countTrips(); i++) {

            String query1 = getQuery3(i);

            Statement stmt1 = newCon.createStatement();

            ResultSet rset1 = stmt1.executeQuery(query1);
            String table = "TRIP " + i + "\n"
                    + String.format(condoFormat, "ROOM", "NAME", "UNIT", "BLDG", "COUNT", "TOTAL PAID")
                    + String.format(condoFormat, "----", "------------------", "----", "----", "-----", "----------");
            ;

            while (rset1.next()) {
                table += String.format(condoFormat, rset1.getString("Room"), rset1.getString("Name"), rset1.getString("unit_no"), rset1.getString("bldg"), countInRooms(rset1.getString("Room")), rset1.getInt("reserPaid"));
            }
            table += "\n";
            System.out.print(table);
            // Release the statement and result set
            stmt1.close();
            rset1.close();
        }
    }

    /**
     * studentDetail gives each students full name
     * which resort they are going to, the condo they are assigned
     * to and how much they have paid and how much they owe
     *
     * @throws SQLException
     */
    private void studentDetail() throws SQLException {
        System.out.print(studentDetailBanner);

        for (int i = 1; i <= countTrips(); i++) {
            String query1 = getQuery4(i);

            Statement stmt1 = newCon.createStatement();
            ResultSet rset1 = stmt1.executeQuery(query1);
            String table = "Trip " + i + "\n"
                    + String.format(studentFormat, "FIRST", "LAST", "RESORT", "DATE", "CITY", "STATE", "CONDO", "ROOM", "UNIT", "BLDG", "PAID", "OWES")
                    + String.format(studentFormat, "--------", "------------", "----------------", "----------", "------------", "-----", "-----------------", "----", "----", "----", "-----", "-----");
            while (rset1.next()) {
                table += String.format(studentFormat, rset1.getString("First"), rset1.getString("LAST"),
                        rset1.getString("RESORT"), rset1.getDate("sun_date"),
                        rset1.getString("city"), rset1.getString("state"), rset1.getString("name"),
                        rset1.getString("rid"), rset1.getString("unit_no"), rset1.getString("bldg"), rset1.getInt("PAID"), 100 - rset1.getInt("PAID"));
            }
            table += "\n";
            System.out.print(table);
            // Release the statement and result set
            stmt1.close();
            rset1.close();
        }
    }

    /**
     * financialDetail method gives a break down
     * of who much has been paid, how much is owed per resort for each trip
     * @throws SQLException
     */
    private void financialDetail() throws SQLException {
        System.out.print(financialDetailBanner);

        for (int i = 1; i <= countTrips(); i++) {
            String query1 = getQuery5(i);

            Statement stmt1 = newCon.createStatement();

            ResultSet rset1 = stmt1.executeQuery(query1);
            String table = "TRIP " + i + "\n"
                    + String.format(financialFormat, "Resort Name", "Total Owed", "Total Paid", "Remaining Balance")
                    + String.format(financialFormat, "----------------", "----------", "----------", "-----------------");

            while (rset1.next()) {
                table += String.format(financialFormat, rset1.getString("Resort"), stdPerTrip(i) * 100, rset1.getInt("Paid"), stdPerTrip(i) * 100 - rset1.getInt("Paid"));

            }
            table += "\n";
            System.out.print(table);
            // Release the statement and result set
            stmt1.close();
            rset1.close();
        }
    }

    /**
     * stdPerTrip is a helper method that counts how many student there are per trip
     * @param i - trip number
     * @return - returns number of student
     * @throws SQLException
     */
    private int stdPerTrip(int i) throws SQLException {
        String query = "SELECT COUNT(ca.RID) as Count "
                + "FROM Condo_assign ca "
                +"inner join condo_reservation cr on ca.rid = cr.rid "
                +"where  cr.tid = " +i;
        int count = 0;

        Statement stmt1 = newCon.createStatement();
        ResultSet rset1 = stmt1.executeQuery(query);

        while (rset1.next()) {
            count = rset1.getInt("Count");
        }
        return count;
    }

    /**
     * countTrips count total trips
     * @return returns number of trips
     * @throws SQLException
     */
    private int countTrips() throws SQLException {
        String query = "SELECT COUNT(TID) as tripCount "
                + "FROM TRIP ";
        int count = 0;

        Statement stmt1 = newCon.createStatement();
        ResultSet rset1 = stmt1.executeQuery(query);

        while (rset1.next()) {
            count = rset1.getInt("tripCount");
        }
        return count;
    }

    /**
     * countInRooms method counts how many students are in each room
     * @param i
     * @return returns number per room
     * @throws SQLException
     */
    private int countInRooms(String i) throws SQLException {
        String query = "SELECT  Count(RID) as inRoom "
                + "FROM Condo_Assign "
                +"Where rid = '" +i +"'";
        int count = 0;

        Statement stmt = newCon.createStatement();
        ResultSet rset = stmt.executeQuery(query);

        while (rset.next()) {
            count = rset.getInt("inRoom");
        }
        return count;
    }

    private String getQuery1(int i) {
        return "SELECT * "
                + "FROM Trip "
                + "WHERE TID = " + i;
    }

    private String getQuery2(int i) {
        return "SELECT sc.first, sc.last, cr.name, sum(p.payment) as totalPaid "
                + "FROM Condo_Assign ca "
                + "inner join Condo_Reservation cr on ca.RID = cr.RID "
                + "inner join SkiClub sc on ca.mid = sc.mid "
                + "inner join Payment p on ca.mid = p.mid and ca.rid = p.rid "
                + "WHERE cr.TID = " + i + " and p.mid = ca.mid "
                + " Group by sc.first, sc.last, cr.name";
    }

    private String getQuery3(int i){
        String query = "SELECT Distinct cr.rid as Room, cr.name, cr.unit_no, cr.bldg, sum(p.payment) as reserPaid "
                + "FROM Condo_Assign ca "
                + "inner join Condo_Reservation cr on ca.RID = cr.RID "
                + "inner join SkiClub sc on ca.mid = sc.mid "
                + "inner join Payment p on ca.mid = p.mid and ca.rid = p.rid "
                + "WHERE cr.TID = " + i
                + "group by cr.rid, cr.name, cr.unit_no, cr.bldg "
                +"order by cr.rid ";
        return query;
    }

    private String getQuery4(int i){
        String query = "SELECT  sc.first, sc.last, cr.name, t.resort, t.sun_date, t.city, t.state, cr.name, cr.rid, cr.unit_no, cr.bldg, sum(p.payment)AS PAID "
                + "FROM SkiClub sc "
                + "inner join Condo_Assign ca on sc.MID = ca.MID "
                + "inner join Payment p on ca.mid = p.mid "
                + "inner join Condo_Reservation cr on p.RID = cr.RId "
                + "inner join Trip t on cr.tid = t.tid "
                + "where  cr.tid = " + i + " and sc.mid = ca.mid and p.mid = ca.mid  and p.rid = ca.rid "
                + "group by sc.first, sc.last, cr.name, t.resort, t.sun_date, t.city, t.state, cr.name, cr.rid, cr.unit_no, cr.bldg ";
        return query;
    }

    private String getQuery5(int i){
        String query = "SELECT t.resort, Sum(p.payment) AS Paid "
                + "FROM SkiClub sc "
                + "inner join Condo_Assign ca on sc.MID = ca.MID "
                + "inner join Payment p on ca.mid = p.mid "
                + "inner join Condo_Reservation cr on p.RID = cr.RId "
                + "inner join Trip t on cr.tid = t.tid "
                + "where  t.tid = " +i +" and ca.rid = p.rid and ca.mid = p.mid "
                + "group by t.TID, t.Resort ";
        return query;
    }
}
