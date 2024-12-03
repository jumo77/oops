package components;

import data.DBMS;
import data.Literals;
import data.LoginData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class BudgetListItem extends JPanel implements ActionListener {

    JCheckBox resultSetter;
    int id;

    public BudgetListItem(int _id, String _dept, String _amount, String _reason, String _date, String _result, boolean a) {

        id = _id;

        setLayout(null);

        JLabel dept = new JLabel(_dept);
        JLabel amount = new JLabel(_amount);
        JLabel reason = new JLabel(_reason);
        JLabel date = new JLabel(_date);
        JLabel result = new JLabel(_result);
        dept.setHorizontalAlignment(JLabel.CENTER);
        amount.setHorizontalAlignment(JLabel.CENTER);
        reason.setHorizontalAlignment(JLabel.CENTER);
        date.setHorizontalAlignment(JLabel.CENTER);
        result.setHorizontalAlignment(JLabel.CENTER);
        dept.setBounds(0, 0, 50, 30);
        amount.setBounds(50, 0, 100, 30);
        reason.setBounds(150, 0, 150, 30);
        date.setBounds(300, 0, 100, 30);
        result.setBounds(400, 0, 20, 30);

        resultSetter = new JCheckBox();
        if (Objects.equals(_result, "승인")) {
            resultSetter.setSelected(true);
            resultSetter.disable();
        }else{
            resultSetter.addActionListener(this);
        }

        JPanel line = new JPanel();
        line.setBounds(0, 31, 420, 31);

        Literals.SET_THEME(dept, amount, reason, date, result, resultSetter, line);

        this.add(dept);
        this.add(amount);
        this.add(reason);
        this.add(date);
//        if (LoginData.dept == "총무" && a) {
//            this.add(resultSetter);
//        } else {
//            this.add(result);
//        }
//        this.add(line);
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
