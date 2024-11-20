package frames.hwan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Register extends JFrame implements ActionListener {
    JTextField cNum, name, phoneNum, address;
    JPasswordField pwd;
    String phoneHeads[] = {"010", "02", "031", "042", "070"};
    JComboBox tel;
    JButton check, b1, b2;

    Register(String title) {
        setTitle(title);
        Container ct = getContentPane();

        ct.setLayout(new BorderLayout(0, 20));
        JPanel top = new JPanel();
        top.setLayout(new GridLayout(5, 2));

        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l1 = new JLabel("사번        :"); // ID 중복 체크 버튼을 없앨지 고민
        cNum = new JTextField(8);
        check = new JButton("사번 중복 체크");
        check.addActionListener(this);
        p1.add(l1);
        p1.add(cNum);
        p1.add(check);

        Panel p2 = new Panel();
        p2.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l2 = new JLabel("비밀번호 :");
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
        tel = new JComboBox(phoneHeads);
        phoneNum = new JTextField(10);
        p4.add(l4);
        p4.add(tel);
        p4.add(phoneNum);

        JPanel p5 = new JPanel();
        p5.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel l5 = new JLabel("주소        :");
        address = new JTextField(20);
        p5.add(l5);
        p5.add(address);

        top.add(p1);
        top.add(p2);
        top.add(p3);
        top.add(p4);
        top.add(p5);

        ct.add(top, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        b1 = new JButton("확인");
        b2 = new JButton("취소");
        b2.addActionListener(this);
        bottom.add(b1);
        bottom.add(b2);
        ct.add(bottom, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent ae) { //회원가입 이후에 데이터베이스에 값을 전달해아함
        String s = ae.getActionCommand();
        if (s.equals("취소")) {
            this.dispose();
        } else if (s.equals("ID 중복 체크")) { //ID 중복 체크는 사번 중복 체크로 이름을 바꾸면 됨 또한 버튼 or 최종 생성 시 중복 체크를 하게 해도 됨
            MessageDialog md = new MessageDialog(this, "사번 중복 체크", true, "사용할 수 있는 사번입니다.");
            md.show();
        } else {
        }
    }
}

