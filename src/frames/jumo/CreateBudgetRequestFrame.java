package frames.jumo;

import components.RightArraganedLabel;
import data.DBMS;
import data.Literals;
import frames.hwan.*;
import theme.ThemeButton;
import theme.ThemeFrame;
import theme.ThemePanel;
import theme.ThemeTextField;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CreateBudgetRequestFrame extends ThemeFrame implements ActionListener {

    private final ThemeTextField reason;
    private final JFormattedTextField amount;
    private final JComboBox<String> dept;
    private final String[] depts;

    public CreateBudgetRequestFrame(){
        super();

        setTitle("예산 신고");
        
        int border = Literals.BORDER;

        Container ct = getContentPane();
        ct.setLayout(null);
        ct.setBackground(Color.BLACK);
        RightArraganedLabel _amount = new RightArraganedLabel("입출금 금액");
        amount = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
        RightArraganedLabel _reason = new RightArraganedLabel("사용 목적");
        reason = new ThemeTextField();
        RightArraganedLabel _dept = new RightArraganedLabel("사용 부서");
        depts = getDept();
        dept = new JComboBox<>(depts);
        ThemePanel l = new ThemePanel(); ThemePanel r = new ThemePanel();
        ThemePanel ll = new ThemePanel();ThemePanel rr = new ThemePanel();
        GridLayout g = new GridLayout(3, 1, Literals.H_GAP, Literals.V_GAP);
        l.setLayout(g); r.setLayout(g);
        g = new GridLayout(1, 1, Literals.H_GAP, Literals.V_GAP);
        ll.setLayout(g); rr.setLayout(g);
        l.setBounds(0,0,Literals.LABEL_PANEL_WIDTH,Literals.TEXT_FIELD_HEIGHT*3);
        r.setBounds(Literals.LABEL_PANEL_WIDTH + 50,0,Literals.FORM_PANEL_WIDTH,Literals.TEXT_FIELD_HEIGHT*3);
        ll.setBounds(border, border,
                l.getWidth() + border , l.getHeight() + border);
        rr.setBounds(l.getWidth()+ border*3, border,
                r.getWidth() + border , r.getHeight() + border);

        setSize(rr.getWidth()+rr.getX()+border ,350);

        ThemeButton submit = new ThemeButton("예산 신고");
        submit.addActionListener(this);

        l.setBorder(new LineBorder(Color.BLACK, border));
        r.setBorder(new LineBorder(Color.BLACK, border));
        l.add(_dept);   r.add(dept);
        l.add(_amount); r.add(amount);
        l.add(_reason); r.add(reason);
        ll.add(l);      rr.add(r);
        ct.add(ll);     ct.add(rr);

        submit.setLocation(getWidth() - submit.getWidth() - border,
                getHeight() - submit.getHeight() - border - Literals.BUGGY_H);
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
                DBMS.DB.executeQuery("insert into budget_request(dept, amount, reason) values("
                        +dept.getSelectedIndex()+", "+Integer.parseInt(amount.getText())+", "+", "+reason.getText()+")");
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
