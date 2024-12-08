package components;

import data.DBMS;
import data.Literals;
import data.LoginData;
import theme.ThemeLabel;
import theme.ThemePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class BudgetListItem extends ThemePanel implements ActionListener {

    JCheckBox resultSetter;
    int id;

    public static int width = 650;
    public static int height = 50;

    public BudgetListItem(int _id, String _dept, String _amount, String _reason, String _date, String _result, boolean a) {

        id = _id;
        setLayout(null);
        setBounds(0,0,width, height);

        ThemeLabel dept = new ThemeLabel(_dept);
        ThemeLabel amount = new ThemeLabel(_amount);
        ThemeLabel reason = new ThemeLabel(_reason);
        ThemeLabel date = new ThemeLabel(_date);
        ThemeLabel result = new ThemeLabel(_result);
        dept.setHorizontalAlignment(ThemeLabel.CENTER);
        amount.setHorizontalAlignment(ThemeLabel.RIGHT);
        reason.setHorizontalAlignment(ThemeLabel.CENTER);
        date.setHorizontalAlignment(ThemeLabel.CENTER);
        result.setHorizontalAlignment(ThemeLabel.CENTER);
        dept.setBounds(0, 0, 100, height);
        amount.setBounds(dept.getX()+dept.getWidth(), 0, 100, height);
        reason.setBounds(amount.getX()+amount.getWidth(), 0, 200, height);
        date.setBounds(reason.getX()+reason.getWidth(), 0, 150, height);
        result.setBounds(date.getX()+date.getWidth(), 0, 100, height);

        resultSetter = new JCheckBox();
        if (Objects.equals(_result, "승인")) {
            resultSetter.setSelected(true);
            resultSetter.disable();
        }else{
            resultSetter.addActionListener(this);
        }

        ThemePanel line = new ThemePanel();
        line.setBounds(0, height, width, height);

        add(dept);
        add(amount);
        add(reason);
        add(date);
        if (Objects.equals(LoginData.dept, "총무") && a) {
            add(resultSetter);
        } else {
            add(result);
        }
        add(line);
    }

    public BudgetListItem(int _id, String _dept, String _amount, String _reason, String _date, String _result) {
        this(_id, _dept, _amount, _reason, _date, _result, false);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            DBMS.DB.executeQuery("insert into budget_response('responder_id', 'result_id, request_id') " +
                    "values("+LoginData.id+","+2+","+id+")");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
