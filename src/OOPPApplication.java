import data.DBMS;
import frames.hwan.Login;
import frames.hwan.ManageEmp;
import frames.hwan.NewMember;

import javax.swing.*;

public class OOPPApplication {

    public static void main(String[] args) {
        DBMS.connect();
        Login win1 = new Login("로그인");
        win1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        win1.setSize(670, 350);
        win1.setLocation(100, 200);
        win1.setVisible(true);

        NewMember win2 = new NewMember("회원가입");
        win2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        win2.setSize(370, 400);
        win2.setLocation(800, 200);
        win2.setVisible(true);

        ManageEmp win3 = new ManageEmp("인사관리");
        win3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        win3.setSize(800, 700);
        win3.setLocation(100, 200);
        win3.setVisible(true);
    }
}