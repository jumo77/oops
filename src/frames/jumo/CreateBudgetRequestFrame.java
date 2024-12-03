package frames.jumo;

import components.RightArraganedLabel;
import data.DBMS;
import data.Literals;
import frames.hwan.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreateBudgetRequestFrame extends JFrame implements ActionListener {

    private final JTextField date, amount, reason;
    private JComboBox<String> dept;
    private JButton submit;
    private String[] depts;

    public CreateBudgetRequestFrame(String title){
        super();
        depts = getDept();
        setBackground(Color.BLACK);
        Container ct = getContentPane();
        ct.setLayout(null);
        ct.setBackground(Color.BLACK);
        ct.setName(title);
        submit = new JButton("예산 신고");
        submit.addActionListener(this);
        RightArraganedLabel _date = new RightArraganedLabel("입출금 일자");      date = new JTextField();
        RightArraganedLabel _amount = new RightArraganedLabel("입출금 금액");    amount = new JTextField();
        RightArraganedLabel _reason = new RightArraganedLabel("사용 목적");     reason = new JTextField();
        RightArraganedLabel _dept = new RightArraganedLabel("사용 부서");       dept = new JComboBox<>(depts);
        JPanel l = new JPanel(); JPanel r = new JPanel();
        JPanel ll = new JPanel();JPanel rr = new JPanel();
        GridLayout g = new GridLayout(4, 1, Literals.H_GAP, Literals.V_GAP);
        l.setLayout(g); r.setLayout(g);
        g = new GridLayout(1, 1, Literals.H_GAP, Literals.V_GAP);
        ll.setLayout(g); rr.setLayout(g);
        l.setBounds(0,0,Literals.LABEL_PANEL_WIDTH,Literals.TEXT_FIELD_HEIGHT*3);
        r.setBounds(350,0,Literals.FORM_PANEL_WIDTH,Literals.TEXT_FIELD_HEIGHT*3);
        ll.setBounds(Literals.BORDER, Literals.BORDER,
                l.getWidth() + Literals.BORDER , l.getHeight() + Literals.BORDER);
        rr.setBounds(l.getWidth()+ Literals.BORDER*3, Literals.BORDER,
                r.getWidth() + Literals.BORDER , r.getHeight() + Literals.BORDER);

        setBounds(0,0,rr.getWidth()+rr.getX() ,370);

        //!!

        Literals.SET_THEME(submit, _date, _amount, _reason, date, amount, reason, l, r, ll, rr);
        Literals.REMOVE_BORDER(_date, _amount, _reason);

        //!!


        l.setBorder(new LineBorder(Color.BLACK, Literals.BORDER));
        r.setBorder(new LineBorder(Color.BLACK, Literals.BORDER));
        l.add(_date);   r.add(date);
        l.add(_amount); r.add(amount);
        l.add(_reason); r.add(reason);
        l.add(_dept);   r.add(dept);
        ll.add(l);      rr.add(r);
        ct.add(ll);     ct.add(rr);
        submit.setBounds(rr.getWidth()+rr.getX()-200, getHeight()-100, 200, 50);
        ct.add(submit);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (depts.length <= 1){
            MessageDialog messageDialog =
                    new MessageDialog(this, "에러", true, "부서에 문제가 있습니다");
            messageDialog.show();
        }
        else {
            try {
                DBMS.DB.executeQuery("insert into budged(dept, date, amount, reason) values(?, ?, ?, ?)");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String[] getDept(){
        List<String> depts = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = DBMS.DB.executeQuery("select name from dept");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (rs == null) return new String[] {"부서를 불러올 수 없습니다."};
        try {
            while (rs.next()){
                depts.add(rs.getString("name"/*부서명*/));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return depts.toArray(new String[0]);
    }
}
