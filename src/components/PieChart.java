package components;

import data.DBMS;
import data.Literals;
import data.mpo.Sales;
import theme.ThemeButton;
import theme.ThemeLabel;
import theme.ThemePanel;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PieChart extends ThemePanel {

    private final String table;
    private final List<String> groups = new ArrayList<>();
    private final List<Sales> salesList = new ArrayList<>();

    public static final int W = 500, H = 700;

    public PieChart(String title, String _table, String... _groups) {
        super();
        table = _table;
        groups.addAll(Arrays.asList(_groups));
        setSize(W, H);
        ThemeButton l = new ThemeButton(title);
        l.setSize(getWidth(), Literals.TEXT_FIELD_HEIGHT);
        l.setLocation(0, getHeight() - l.getHeight());
        l.setHorizontalAlignment(ThemeLabel.CENTER);
        l.setVerticalAlignment(ThemeLabel.CENTER);
        add(l);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int diameter = W, startAngle = 0, total;

        StringBuilder groupBy = new StringBuilder();
        groupBy.append(" group by ");
        if (!groups.isEmpty()) {
            for (String group : groups) {
                groupBy.append(group);
                groupBy.append(",");
            }
        }
        try {
            ResultSet r = DBMS.DB.executeQuery(
                    "select year(date) as year, quarter(date) as quarter, sum(product_price) as sum from " + table
                            + groupBy + "year(date), quarter(date);");
            while (r.next()) {
                salesList.add(new Sales(r.getInt("year") + "/"
                        + r.getInt("quarter"), r.getInt("sum")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        total = sum();
        Color[] colors = {
                new Color(255, 0, 0), new Color(255, 127, 0), new Color(255, 255, 0), new Color(127, 255, 0),
                new Color(0, 255, 0), new Color(0, 255, 127), new Color(0, 255, 255), new Color(0, 127, 255),
                new Color(0, 0, 255), new Color(127, 0, 255), new Color(255, 0, 255), new Color(255, 0, 127),
        };

        for (int i = 0; i < salesList.size(); i++) {
            int angle = (int) (salesList.get(i).price * 360.0 / total);
            g.setColor(colors[i%12]);
            g.fillArc(diameter / 2, diameter / 2, diameter, diameter, startAngle, angle);
            startAngle += angle;
        }

    }

    private int sum(){
        return salesList.stream().
                mapToInt(Sales::getPrice)
                .sum();
    }


}
