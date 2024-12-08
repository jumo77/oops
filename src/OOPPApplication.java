import data.DBMS;
import frames.jumo.BudgetRequestFrame;

public class OOPPApplication {
    public static void main(String[] args) {
        DBMS.connect();
//        CreateBudgetRequestFrame f = new CreateBudgetRequestFrame();
        BudgetRequestFrame f = new BudgetRequestFrame();
        f.show();
    }
}
