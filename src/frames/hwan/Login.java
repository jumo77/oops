package frames.hwan;

import data.DBMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame implements ActionListener {
    JTextField empnumber;
    JTextField passwd;
    JButton b1, b2;
    Container ct = getContentPane();

    public Login(String title) {
        setTitle(title);
        ct.setLayout(null);
        JLabel l1 = new JLabel("사번 :");
        empnumber = new JTextField(8);
        l1.setBounds(400, 70, 70, 30);
        empnumber.setBounds(490, 70, 120, 30);
        ct.add(l1);
        ct.add(empnumber);

        JLabel l2 = new JLabel("비밀번호 :");
        passwd = new JTextField(20);
        l2.setBounds(400, 110, 70, 30);
        passwd.setBounds(490, 110, 120, 30);
        ct.add(l2);
        ct.add(passwd);

        b1 = new JButton("로그인");
        b2 = new JButton("취소");
        b1.addActionListener(this);
        b2.addActionListener(this);
        b1.setBounds(410, 190, 80, 30);
        b2.setBounds(520, 190, 80, 30);
        ct.add(b1);
        ct.add(b2);

    }

    public void actionPerformed(ActionEvent ae) {
        String s = ae.getActionCommand();
        if (s.equals("취소")) {
            this.dispose();
        } else if (s.equals("로그인")) {
            int emp_number = Integer.parseInt(empnumber.getText());
            String password = passwd.getText();

            try (ResultSet rs = DBMS.select("select * from team_work.employee where emp_num = "+emp_number)) {
                if(rs.next()) {
                    if (password.equals(rs.getString("emp_password"))) {
                        MessageDialog md = new MessageDialog(this, "로그인 완료", true, "로그인 되었습니다.");
                        md.setSize(200, 100);
                        md.setLocation(400, 400);
                        md.setVisible(true);
                        LoginData.id = Integer.parseInt(this.empnumber.getText());
                        LoginData.name = rs.getString("emp_name");
                        LoginData.dept = rs.getInt("dept_id");
                        LoginData.grade = rs.getInt("emp_grade");
                        //메인으로 넘어가게 교체(그 열의 정보를 메인화면으로 넘어가게끔 코딩)
                        this.dispose();
                    } else if (!password.equals(rs.getString("emp_password"))) {
                        MessageDialog md = new MessageDialog(this, "로그인 실패", true, "비밀번호가 일치하지 않습니다.");
                        md.setSize(200, 100);
                        md.setLocation(400, 400);
                        md.setVisible(true);
                    }
                }
                else {
                    MessageDialog md = new MessageDialog(this, "로그인 실패", true, "계정이 존재하지 않습니다");
                    md.setSize(200, 100);
                    md.setLocation(400, 400);
                    md.setVisible(true);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}