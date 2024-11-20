package frames.hwan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Login extends JFrame implements ActionListener {
    JTextField cNum;
    JPasswordField pwd;
    JButton loginBtn, cancelBtn;

    Login(String title) {
        super();
        setTitle(title);
        Container ct = getContentPane();
        ct.setLayout(null);
        JLabel l1 = new JLabel("사번 :");
        cNum = new JPasswordField(8);
        l1.setBounds(0, 70, 70, 30);
        cNum.setBounds(90, 70, 120, 30);
        ct.add(l1);
        ct.add(cNum);

        JLabel l2 = new JLabel("비밀번호 :");
        pwd = new JPasswordField(20);
        l2.setBounds(0, 110, 70, 30);
        pwd.setBounds(90, 110, 120, 30);
        ct.add(l2);
        ct.add(pwd);

        loginBtn = new JButton("로그인");
        cancelBtn = new JButton("취소");
        cancelBtn.addActionListener(this);
        loginBtn.setBounds(10, 190, 80, 30);
        cancelBtn.setBounds(120, 190, 80, 30);
        ct.add(loginBtn);
        ct.add(cancelBtn);
    }

    public void actionPerformed(ActionEvent ae) {
        String s = ae.getActionCommand();
        if (s.equals("취소")) {
            this.dispose();
        } else {
        }//로그인 버튼 누를 시 db에서 id, 비밀번호 찾기 db 연동 필요
    }
}

