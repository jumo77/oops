package frames.hwan;

import data.DBMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateMember extends JFrame implements ActionListener { //회원가입은 인사관리 창에서 사용할 예정
    JTextField name, tel_number, salary, dept_id; //회원가입 전화번호를 넣을지 뺄지 고민
    JPasswordField pwd;
    String code[] = {"010", "070", "02", "031", "032"};
    JComboBox tel;
    JButton update, cancel, delete;

    public UpdateMember(String title, String emp_name, String emp_tel, String emp_salary, String emp_dept, String password) {
        setTitle(title);
        Container ct = getContentPane();

        ct.setLayout(new BorderLayout(0, 20));
        JPanel top = new JPanel();
        top.setLayout(new GridLayout(6, 1));
        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.LEFT));

        Panel p2 = new Panel();
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

        top.add(p1);
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
            DBMS.insert("");//회원 정보 수정
            MessageDialog md = new MessageDialog(this, "정보 변경", true, "정보가 변경되었습니다.");
            md.setLocation(900, 300);
            md.setVisible(true);
        } else {
            DBMS.insert("UPDATE employee SET");//sql 문 작성하기
        }
    }
}