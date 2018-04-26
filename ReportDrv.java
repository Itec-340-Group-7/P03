package connection;

import java.sql.SQLException;
/**
 * @author Steven Horton - Completed the tripDetail query ensuring it returned the proper values and coded/formatted queries for the reports.java
 * @author Austin Crockett - coded the ReportDrv.java and the condoDetail, financialDetail queries ensuring they returned the proper values
 * @author Colin Ryan - coded the DataSource.java and studentDetail query ensuring it returned the proper values
 * @date 4/26/2018
 * ITEC 340 - P03
 * ReportDrv.java
 */
public class ReportDrv {
    private static Reports report;

    static {
        try {
            report = new Reports();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args) throws SQLException {
        report.run();
    }
}
