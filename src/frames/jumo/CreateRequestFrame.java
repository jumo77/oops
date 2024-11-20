package frames.jumo;

import components.RightArraganedLabel;
import data.Literals;
import theme.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateRequestFrame extends ThemeFrame implements ActionListener {

    private ThemeTextField date, amount, reason;
    private ThemeButton submit;

    public CreateRequestFrame(String title){
        super();
        Container ct = getContentPane();
        ct.setLayout(null);
        ct.setBackground(Color.BLACK);
        ct.setName(title);
        RightArraganedLabel _date = new RightArraganedLabel("입출금 일자");      date = new ThemeTextField();
        RightArraganedLabel _amount = new RightArraganedLabel("입출금 금액");    amount = new ThemeTextField();
        RightArraganedLabel _reason = new RightArraganedLabel("사용 목적");     reason = new ThemeTextField();
        ThemePanel l = new ThemePanel(); ThemePanel r = new ThemePanel();
        GridLayout g =new GridLayout(3, 1, Literals.H_GAP, Literals.V_GAP);
        l.setLayout(g); r.setLayout(g);
        l.setBounds(0,0,300,200); r.setBounds(350,0,600,200);
        l.add(_date);   r.add(date);
        l.add(_amount); r.add(amount);
        l.add(_reason); r.add(reason);
        ct.add(l);      ct.add(r);
        submit = new ThemeButton("예산 신고");
        submit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
