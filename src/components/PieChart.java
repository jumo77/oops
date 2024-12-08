package components;

import data.DBMS;
import data.Literals;
import data.mpo.Sales;
import frames.jumo.Report;
import theme.ThemeButton;
import theme.ThemeLabel;
import theme.ThemePanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PieChart extends ThemePanel {

    private final String table;
    private final List<Sales> salesList = new ArrayList<>();

    public static final int W = 500, H = 700;

    public PieChart(String title, String _table) {
        super();
        table = _table;
        setSize(W, H);
        ThemeButton button = new ThemeButton(title);
        button.setSize(getWidth(), Literals.TEXT_FIELD_HEIGHT);
        button.setLocation(0, getHeight() - button.getHeight());
        button.setHorizontalAlignment(ThemeLabel.CENTER);
        button.setVerticalAlignment(ThemeLabel.CENTER);
        button.addActionListener(e -> {
            Report f = new Report(table);
            f.show();
        });
        add(button);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int diameter = W, startAngle = 0, total;

        try {
            ResultSet r = DBMS.DB.executeQuery(
                    "select * from " + table+" ; ");
            while (r.next()) {
                salesList.add(new Sales(r.getString("placeholder"), r.getInt("sum")));
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
