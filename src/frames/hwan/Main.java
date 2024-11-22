package frames.hwan;

import javax.swing.*;

public class Main {
    public static Jdbconnector db;
    public static void main(String[] args) {
        db = new Jdbconnector();
        Login win1 = new Login("로그인");
        win1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        win1.setSize(670, 350);
        win1.setLocation(100, 200);
        win1.show();

        NewMember win2 = new NewMember("회원가입");
        win2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        win2.setSize(370, 350);
        win2.setLocation(800, 200);
        win2.show();
    }
}