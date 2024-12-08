package data;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Literals {
    public static final int H_GAP = 1;
    public static final int V_GAP = 10;
    public static final int BORDER = 10;
    public static final int BUGGY_H = 30;

    public static final int LABEL_PANEL_WIDTH = 250;
    public static final int FORM_PANEL_WIDTH = 600;
    public static final int TEXT_FIELD_HEIGHT = 70;

    public static final String DB_SERVER = "jdbc:mysql://club-named-rapid.xyz:3306/team_work";
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_USERID = "hwan";
    public static final String DB_USERPW = "asdf";

    public static void SET_THEME(JComponent... js) {
        for (JComponent j : js) {
            j.setBackground(Color.BLACK);
            j.setForeground(Color.WHITE);
            j.setFont(j.getFont().deriveFont(30.0f));
        }
    }
}
