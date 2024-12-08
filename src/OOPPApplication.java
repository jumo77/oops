import data.DBMS;
import frames.jumo.BudgetRequestFrame;
import frames.jumo.SalesReport;

public class OOPPApplication {
    public static void main(String[] args) {
        DBMS.connect();
//        CreateBudgetRequestFrame f = new CreateBudgetRequestFrame();
        BudgetRequestFrame f = new BudgetRequestFrame();
//        SalesReport f= new SalesReport();
        f.show();
    }
}
