package frames.jumo;

import components.BudgetListItem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BudgetResuestFrame extends JFrame {
    public BudgetResuestFrame(){
        setTitle("예산 사용 내역");

        JPanel list = new JPanel(new FlowLayout(FlowLayout.CENTER));

        BudgetListItem head = new BudgetListItem
                (0, "부서", "예산액", "사유", "날짜", "결과");

        java.util.List<BudgetListItem> budgetList = new ArrayList<>();

    }
}
