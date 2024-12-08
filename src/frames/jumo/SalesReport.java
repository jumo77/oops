package frames.jumo;

import components.PieChart;
import data.Literals;
import theme.ThemeFrame;

import java.awt.*;

public class SalesReport extends ThemeFrame {
    public SalesReport(){
        super();
        setTitle("매출 보고");
        setSize(PieChart.W*4, PieChart.H*2);

        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        c.add(new PieChart("분기별 매출", "v_sales"));

        Literals.PLACE_CENTER(this,PieChart.W*4,PieChart.H*2);
    }
}
