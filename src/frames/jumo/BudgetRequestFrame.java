package frames.jumo;

import components.BudgetListItem;
import data.DBMS;
import data.Literals;
import theme.ThemeButton;
import theme.ThemeFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BudgetRequestFrame extends ThemeFrame implements ActionListener {

    public BudgetRequestFrame(){
        super();
        setTitle("예산 사용 내역");
        Container c = getContentPane();
        c.setLayout(null);
        c.setBackground(Color.BLACK);

        int border = Literals.BORDER,
                width = BudgetListItem.width,
                height = BudgetListItem.height;

        BudgetListItem head = new BudgetListItem
                (0, "부서", "예산액", "사유", "날짜", "결과");
        head.setLocation(border, border);
        c.add(head);

        JScrollPane main = new JScrollPane();
        main.setBounds(border, height + border, width, height * 7);


        java.util.List<BudgetListItem> budgetList = new ArrayList<>();

        try {
            ResultSet r = DBMS.DB.executeQuery("select * from v_budget where result = '승인'");
            while (r.next()){
                budgetList.add(new BudgetListItem(r.getInt("id"), r.getString("dept"),
                        Integer.toString(r.getInt("amount")), r.getString("reason"),
                        r.getDate("date").toString(), r.getString("result"), true));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for(BudgetListItem b: budgetList){
            main.add(b);
        }
        c.add(main);

        ThemeButton add = new ThemeButton("예산 신고");
        add.addActionListener(this);
        c.add(add);

        Literals.SET_THEME(main.getViewport());
        setSize(width + border * 2, height * 11);
        add.setLocation(getWidth() - add.getWidth() -border,
                getHeight() - add.getHeight() -border - Literals.BUGGY_H);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CreateBudgetRequestFrame c = new CreateBudgetRequestFrame();
        c.show();
    }
}
