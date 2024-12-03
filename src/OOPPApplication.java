import data.DBMS;
import frames.jumo.CreateBudgetRequestFrame;

public class OOPPApplication {
    public static void main(String[] args) {
        DBMS.connect();
        CreateBudgetRequestFrame f = new CreateBudgetRequestFrame("예산 신고");
        f.show();
    }
}
