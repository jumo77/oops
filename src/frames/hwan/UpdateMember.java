package frames.hwan;

import data.DBMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdateMember extends JFrame implements ActionListener {
    JTextField name, tel_number, salary, dept_id;
    JPasswordField pwd;
    String code[] = {"010", "070", "02", "031", "032"};
    JComboBox tel;
    JButton update, cancel, delete;

    public UpdateMember(String title, String emp_name, String emp_tel, String emp_salary, String emp_dept, String password) {
        setTitle(title);
        Container ct = getContentPane();

        ct.setLayout(new BorderLayout(0, 20));
        JPanel top = new JPanel();
        top.setLayout(new GridLayout(7, 1));

        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l2 = new JLabel("Password :");
        pwd = new JPasswordField(20);
        p2.add(l2);
        p2.add(pwd);

        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l3 = new JLabel("이름       :");
        name = new JTextField(8);
        p3.add(l3);
        p3.add(name);

        JPanel p4 = new JPanel();
        p4.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l4 = new JLabel("연락처     :");
        tel = new JComboBox(code);
        tel_number = new JTextField(10);
        p4.add(l4);
        p4.add(tel);
        p4.add(tel_number);

        JPanel p5 = new JPanel();
        p5.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l5 = new JLabel("연봉(단위 만) :");
        salary = new JTextField(10);
        p5.add(l5);
        p5.add(salary);

        JPanel p6 = new JPanel();
        p6.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l6 = new JLabel("부서명   :");
        dept_id = new JTextField(10);
        p6.add(l6);
        p6.add(dept_id);

        top.add(p2);
        top.add(p3);
        top.add(p4);
        top.add(p5);
        top.add(p6);

        ct.add(top, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        update = new JButton("확인");
        cancel = new JButton("취소");
        delete = new JButton("퇴사");
        update.addActionListener(this);
        cancel.addActionListener(this);
        delete.addActionListener(this);
        bottom.add(update);
        bottom.add(cancel);
        bottom.add(delete);
        ct.add(bottom, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent ae) {
        String s = ae.getActionCommand();
        if (s.equals("취소")) {
            this.dispose();
        } else if (s.equals("확인")) {
            try {
                DBMS.DB.executeUpdate("UPDATE team_work.employee where emp_num = "+" SET emp_name = '"+name.getText()+"', emp_password = '"
                        +pwd.getPassword()+"', " + "emp_tel = '"+tel.getSelectedItem()+tel_number.getText()+"', emp_salary = '"+
                        salary.getText()+"', dept_id = "+dept_id.getText()+";");//회원 정보 수정
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            MessageDialog md = new MessageDialog(this, "정보 변경", true, "정보가 변경되었습니다.");
            md.setLocation(900, 300);
            md.setVisible(true);
        } else {
            try {
                DBMS.DB.executeUpdate("DELETE FROM team_work.employee where emp_num = ");//sql 문 작성하기
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}