package data;

import data.mpo.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DBMS {
    private Connection c;

    public void connect(){
        try {
            c = DriverManager.getConnection(Literals.DB_SERVER, Literals.DB_USERID, Literals.DB_USERPW);
            System.out.println("MySQL Connected!");
        } catch (SQLException e) {
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
