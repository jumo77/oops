package frames.hwan;

import data.DBMS;
import data.LoginData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewMember extends JFrame implements ActionListener { //회원가입은 인사관리 창에서 사용할 예정
    JTextField companynumber, name, tel_number, salary, empDate; //회원가입 전화번호를 넣을지 뺄지 고민
    JPasswordField pwd;
    String code[] = {"010", "070", "02", "031", "032"};
    JComboBox tel, dept;
    JButton check, b1, b2;
    ManageEmployee manageEmployee;

    public NewMember(String title, ManageEmployee Data) {
        manageEmployee = Data;
        setTitle(title);
        Container ct = getContentPane();

        ct.setLayout(new BorderLayout(0, 20));
        JPanel top = new JPanel();
        top.setLayout(new GridLayout(7, 1));

        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l1 = new JLabel("입사일         :");
        empDate = new JTextField(10);
        p1.add(l1);
        p1.add(empDate);

        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l2 = new JLabel("사번        :"); // ID 중복 체크 버튼을 없앨지 고민
        companynumber = new JTextField(8);
        check = new JButton("사번 중복 체크");
        check.addActionListener(this);
        p2.add(l2);
        p2.add(companynumber);
        p2.add(check);

        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l3 = new JLabel("Password :");
        pwd = new JPasswordField(20);
        p3.add(l3);
        p3.add(pwd);

        JPanel p4 = new JPanel();
        p4.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l4 = new JLabel("이름       :");
        name = new JTextField(8);
        p4.add(l4);
        p4.add(name);

        JPanel p5 = new JPanel();
        p5.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l5 = new JLabel("연락처     :");
        tel = new JComboBox(code);
        tel_number = new JTextField(10);
        p5.add(l5);
        p5.add(tel);
        p5.add(tel_number);

        JPanel p6 = new JPanel();
        p6.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l6 = new JLabel("연봉(단위 만) :");
        salary = new JTextField(10);
        p6.add(l6);
        p6.add(salary);

        JPanel p7 = new JPanel();
        p7.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l7 = new JLabel("부서명   :");
        dept = new JComboBox(new String[]{"개발", "총무", "홍보", "영업", "인사"});
        p7.add(l7);
        p7.add(dept);

        top.add(p1);
        top.add(p2);
        top.add(p3);
        top.add(p4);
        top.add(p5);
        top.add(p6);
        top.add(p7);

        ct.add(top, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        b1 = new JButton("확인");
        b2 = new JButton("취소");
        b1.addActionListener(this);
        b2.addActionListener(this);
        bottom.add(b1);
        bottom.add(b2);
        b1.setEnabled(false);
        ct.add(bottom, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent ae) { //회원가입 이후에 데이터베이스에 값을 전달해아함
        String s = ae.getActionCommand();
        if (s.equals("취소")) {
            this.dispose();
        } else if (s.equals("사번 중복 체크")) {

            try (ResultSet rs = DBMS.DB.executeQuery("select * from team_work.employee where emp_num = " + Integer.parseInt(companynumber
                    .getText()))) {
                try {
                    if (!rs.isBeforeFirst()) {
                        MessageDialog md = new MessageDialog(this, "사번 중복 체크", true, "사용할 수 있는 사번입니다.");
                        md.setLocation(900, 300);
                        md.setVisible(true);
                        b1.setEnabled(true);
                    } else {
                        MessageDialog md = new MessageDialog(this, "사번 중복 체크", true, "사용할 수 없는 사번입니다.");
                        md.setLocation(900, 300);
                        md.setVisible(true);
                        b1.setEnabled(false);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (s.equals("확인")) {
            try {
                DBMS.DB.executeUpdate("INSERT INTO team_work.employee (emp_name, emp_num, emp_date, emp_password, emp_grade, emp_salary, emp_tel, dept_id) " +
                        "VALUES ('" + name.getText() + "', " + Integer.parseInt(companynumber.getText()) + ", " + Integer.parseInt(empDate.getText()) + ", '" + new String(pwd.getPassword()) + "', " +
                        "1, " + Integer.parseInt(salary.getText()) + ", '" + tel.getSelectedItem() + tel_number.getText() + "','" +
                        LoginData.deptStringInv.get(dept.getSelectedItem()) + "');");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            MessageDialog md = new MessageDialog(this, "계정 생성", true, "계정이 생성되었습니다.");
            md.setLocation(900, 300);
            md.setVisible(true);
            this.dispose();
            manageEmployee.FetchDatabase();
        }
    }
}