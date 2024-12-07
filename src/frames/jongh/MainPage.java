package frames.jongh;

import data.LoginData;
import frames.hwan.ManageEmployee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame implements ActionListener {

    private JFrame mainFrame;
    private JPanel mainPanel;

    public MainPage() {

        mainFrame = new JFrame("메인 화면");
        mainFrame.setSize(900, 800); // MainFrame 크기 설정
        mainFrame.setLayout(null);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(null);
        mainPanel.setBounds(0, 0, 1000, 800);

        // 제목 라벨
        JLabel title_name = new JLabel("4만원 회계 관리");
        title_name.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        title_name.setBounds(50, 20, 250, 30);

        JLabel user_title = new JLabel("사원 정보");
        Font font = user_title.getFont();
        Font fontsize = font.deriveFont(25f);
        user_title.setFont(fontsize);
        user_title.setBounds(100, 50, 200, 30);
        System.out.println(LoginData.grade);
        JLabel user = new JLabel("<html><br><br>사원 코드:" + LoginData.id + " <br><br>사원 이름: " + LoginData.name + "<br><br>직급: " +
                LoginData.gradeString.get(LoginData.grade) + " <br><br>");
        user.setBounds(20, 20, 300, 200);
        Font font2 = user.getFont();
        Font fontsize2 = font2.deriveFont(12f);
        user.setFont(fontsize2);
        user.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel photo = new JLabel("사진");
        photo.setBounds(350, 480, 500, 250);
        photo.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        JLabel menu = new JLabel("<<메뉴>>");
        menu.setBounds(140, 200, 300, 200);

        JButton menuButton1 = new JButton("개발");
        menuButton1.setBounds(20, 360, 300, 40);

        JButton menuButton2 = new JButton("입/출금 신고 내역");
        menuButton2.setBounds(20, 420, 300, 40);

        JButton menuButton3 = new JButton("예산 승인 내역");
        menuButton3.setBounds(20, 480, 300, 40);

        JButton menuButton4 = new JButton("인사");
        menuButton4.setBounds(20, 540, 300, 40);
        menuButton4.addActionListener(this);

        if(LoginData.grade<4) menuButton4.setEnabled(false);

        mainPanel.add(user_title);
        mainPanel.add(title_name);
        mainPanel.add(user);
        mainPanel.add(photo);
        mainPanel.add(menuButton1);
        mainPanel.add(menuButton2);
        mainPanel.add(menuButton3);
        mainPanel.add(menuButton4);
        mainPanel.add(menu);
        mainFrame.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("인사")) {
            ManageEmployee win3 = new ManageEmployee("인사관리");
            win3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            win3.setSize(800, 700);
            win3.setLocation(100, 200);
            win3.setVisible(true);
        }

    }
    public void show() {
        mainFrame.setVisible(true);
    }
}
