package data;

import java.sql.*;

public class DBMS {
    private static Connection c;
    public static Statement DB;

    public static void connect(){
        try {
            Class.forName(Literals.DB_DRIVER);
            c = DriverManager.getConnection(Literals.DB_SERVER, Literals.DB_USERID, Literals.DB_USERPW);
            DB = c.createStatement();
            System.out.println("MySQL Connected!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            if (c!=null){
                c.close();
                c = null;
                System.out.println("MySQL Closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
