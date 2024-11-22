package frames.hwan;

import java.sql.*;

public class Jdbconnector {

    final String jdbc_url = "jdbc:mysql://club-named-rapid.xyz:3306/team_work";
    final String sql_username = "hwan";
    final String sql_password = "asdf";
    Connection con;
    public Jdbconnector() {
        try {
            con = DriverManager.getConnection(jdbc_url, sql_username, sql_password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet query(String sql) {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
