package connection;

import java.sql.SQLException;

public class ReportDrv {
    private static Reports report = new Reports();

    public static void main(String [] args) throws SQLException {
        report.run();
    }
}
