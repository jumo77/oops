package frames.hwan;

import data.DBMS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ManageEmp extends JFrame implements ActionListener {
    Vector<String> columnName;
    Vector<Vector<String>> rowData;
    JTable table;
    DefaultTableModel model;
    JTextField search;
    JButton searchButton;
    JComboBox searchComboBox, sortComboBox;

    String searchString[] = {"이름", "사번", "직급", "부서명"};
    String emp_name, emp_date, emp_num, emp_dept, emp_tel, emp_grade, emp_salary, emp_password;

    JScrollPane tableSP;
    int row;

    public ManageEmp(String title) {
        setTitle(title);
        Container ct = getContentPane();
        ct.setLayout(new BorderLayout());
        JPanel top = new JPanel();
        JPanel center = new JPanel();
        JPanel bottom = new JPanel();
        JPanel right = new JPanel();
        ct.add(top, BorderLayout.NORTH);
        ct.add(center, BorderLayout.CENTER);
        ct.add(bottom, BorderLayout.SOUTH);
        ct.add(right, BorderLayout.EAST);

        columnName = new Vector<>();
        columnName.add("사원명");
        columnName.add("입사일");
        columnName.add("사번");
        columnName.add("부서명");
        columnName.add("전화번호");
        columnName.add("직급");
        columnName.add("연봉(단위 만)");
        columnName.add("비밀번호");

        top.setLayout(new FlowLayout());
        sortComboBox = new JComboBox(searchString);
        sortComboBox.addActionListener(this);
        top.add(sortComboBox);

        rowData = new Vector<Vector<String>>();

        model = new DefaultTableModel(rowData, columnName);
        table = new JTable(model);
        tableSP = new JScrollPane(table);

        try (ResultSet rs = DBMS.select("select * from team_work.employee")) {
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                emp_name = rs.getString("emp_name");
                emp_date = rs.getString("emp_date");
                emp_num = rs.getString("emp_num");
                emp_dept = rs.getString("dept_id");
                emp_tel = rs.getString("emp_tel");
                emp_grade = rs.getString("emp_grade");
                emp_salary = rs.getString("emp_salary");
                emp_password = rs.getString("emp_password");

                String gradeName = null;
                switch (emp_grade) {
                    case "1":
                        gradeName = "사원";
                        break;
                    case "2":
                        gradeName = "주임";
                        break;
                    case "3":
                        gradeName = "대리";
                        break;
                    case "4":
                        gradeName = "과장";
                        break;
                    case "5":
                        gradeName = "차장";
                        break;
                    case "6":
                        gradeName = "부장";
                        break;
                }
                row.add(emp_name);
                row.add(emp_date);
                row.add(emp_num);
                row.add(emp_dept);
                row.add(emp_tel);
                row.add(gradeName);
                row.add(emp_salary);
                row.add(emp_password);

                rowData.add(row);
                table.updateUI();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        center.setLayout(new FlowLayout());
        center.add(tableSP);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}