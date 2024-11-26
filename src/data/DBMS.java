package data;

import java.sql.*;

public class DBMS {
    private static Connection c;
    public static Statement DB;

    public static void connect() {
        try {
            Class.forName(Literals. DB_DRIVER);
            c = DriverManager.getConnection(Literals.DB_SERVER, Literals.DB_USERID, Literals.DB_USERPW);
            DB = c.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet select(String sql) {

        Statement stmt;
        stmt = DB;
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insert(String sql){ //혹은 excute로 하고 입력을 insert, select등을 받아서 if로 하는것도?
        Statement stmt;
        stmt = DB;
        try{
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void close() {
        try {
            if (c != null) {
                c.close();
                c = null;
                System.out.println("MySQL Closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}