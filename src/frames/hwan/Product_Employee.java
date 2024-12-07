package frames.hwan;

import data.DBMS;
import data.LoginData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Product_Employee extends javax.swing.JFrame {
    Vector<String> columnName;
    Vector<Vector<String>> rowData;
    JTable table;
    DefaultTableModel model;
    JScrollPane tableSP;

    String product_name, product_price, product_date, sell_state;
    String deptName;
    double sell_price;

    public Product_Employee(String emp_num, String name) {
        setTitle(deptName + "부서의 " + name + " 사원이 판매한 목록");
        Container ct = getContentPane();
        ct.setLayout(new BorderLayout(10, 20));

        JPanel center = new JPanel();
        ct.add(center, BorderLayout.CENTER);
        columnName.add("상품 이름");
        columnName.add("가격");
        columnName.add("판매한 갯수");
        columnName.add("판매일");
        columnName.add("총 판매액");

        try (ResultSet rs = DBMS.DB.executeQuery("select * from team_work.v_sell where seller_num = '"
                + emp_num + "'")) {
            deptName = LoginData.deptString.get(rs.getInt("dept_id"));
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                product_name = rs.getString("product_name");
                product_price = rs.getString("product_price");
                product_date = rs.getString("product_date");
                sell_state = rs.getString("sell_state");
                sell_price = Integer.parseInt(product_price) * Integer.parseInt(sell_state);

                row.add(product_name);
                row.add(product_price);
                row.add(product_date);
                row.add(sell_state);
                row.add(String.valueOf(sell_price));

                model.addRow(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        model = new DefaultTableModel(rowData, columnName);
        table = new JTable(model);
        tableSP = new JScrollPane(table);
    }
}