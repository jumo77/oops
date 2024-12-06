package frames.hwan;

import data.DBMS;
import data.LoginData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class UpdateMember extends JFrame implements ActionListener {
    JTextField name, tel_number, salary;
    JPasswordField pwd;
    String code[] = {"010", "070", "02", "031", "032"};
    JComboBox tel, dept;
    JButton update, cancel, delete;
    Vector<String> rowData;
    ManageEmployee manageEmployee;


    public UpdateMember(String title, Vector<String> rowData, ManageEmployee manageEmployee) {
        setTitle(title);
        Container ct = getContentPane();
        this.rowData = rowData;
        this.manageEmployee = manageEmployee;

        ct.setLayout(new BorderLayout(0, 20));
        JPanel top = new JPanel();
        top.setLayout(new GridLayout(7, 1));

        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l2 = new JLabel("Password :");
        pwd = new JPasswordField(20);
        pwd.setText(rowData.get(7));//클릭한 정보 가져오기
        p2.add(l2);
        p2.add(pwd);

        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l3 = new JLabel("이름       :");
        name = new JTextField(8);
        name.setText(rowData.get(0));//클릭한 정보 가져오기
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
        salary.setText(rowData.get(6));//클릭한 정보들 가져오기
        p5.add(l5);
        p5.add(salary);

        JPanel p6 = new JPanel();
        p6.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l6 = new JLabel("부서명   :");
        dept = new JComboBox(new String[]{"부서 없음","개발", "총무", "홍보", "영업", "인사"});
        dept.setSelectedItem(rowData.get(3));//클릭한 정보 가져오기
        System.out.println(LoginData.deptStringInv.get(rowData.get(3)));
        p6.add(l6);
        p6.add(dept);

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
                DBMS.DB.executeUpdate("UPDATE team_work.employee SET " +
                        "emp_name = '" + name.getText() + "', " +
                        "emp_password = '" + pwd.getText() + "', " +
                        "emp_tel = '" + tel.getSelectedItem() + tel_number.getText() + "', " +
                        "emp_salary = " + salary.getText() + ", " +
                        "dept_id = " + LoginData.deptStringInv.get(dept.getSelectedItem()) + " " +
                        "where emp_num = "+rowData.get(2)+";");//회원 정보 수정
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            MessageDialog md = new MessageDialog(this, "정보 변경", true, "정보가 변경되었습니다.");
            md.setLocation(900, 300);
            md.setVisible(true);
            manageEmployee.FetchDatabase();
            this.dispose();
        } else {
            try {
                DBMS.DB.executeUpdate("DELETE FROM team_work.employee where emp_num = " +rowData.get(2));//sql 문 작성하기
                MessageDialog md = new MessageDialog(this, "퇴사", true, "퇴사가 완료되었습니다");
                md.setLocation(900, 300);
                md.setVisible(true);
                manageEmployee.FetchDatabase();
                this.dispose();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}