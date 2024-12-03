package frames.jumo;

import components.BudgetListItem;

import javax.swing.*;

public class BudgetResuestFrame extends JFrame {
    public BudgetResuestFrame(){
        setTitle("예산 사용 내역");

        BudgetListItem head = new BudgetListItem
                (0, "부서", "예산액", "사유", "날짜", "결과");

    }
}
