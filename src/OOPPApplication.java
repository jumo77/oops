import data.DBMS;
import frames.hwan.Login;
import frames.hwan.ManageEmployee;

import javax.swing.*;

public class OOPPApplication {

    public static void main(String[] args) {
        DBMS.connect();
        Login win1 = new Login("로그인");
        win1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        win1.setSize(670, 350);
        win1.setLocation(100, 200);
        win1.setVisible(true);

        ManageEmployee win3 = new ManageEmployee("인사관리");
        win3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        win3.setSize(800, 700);
        win3.setLocation(100, 200);
        win3.setVisible(true);
    }
}