import data.DBMS;
import data.LoginData;
import frames.hwan.Login;

import javax.swing.*;

public class OOPPApplication {

    public static void main(String[] args) {
        DBMS.connect();
        LoginData.init();
        Login win1 = new Login("로그인");
        win1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        win1.setSize(670, 350);
        win1.setLocation(100, 200);
        win1.setVisible(true);
    }
}