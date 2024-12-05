package data;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Literals {
    public static final int H_GAP = 1;
    public static final int V_GAP = 10;
    public static final int BORDER = 10;

    public static final int LABEL_PANEL_WIDTH = 250;
    public static final int FORM_PANEL_WIDTH = 600;
    public static final int TEXT_FIELD_HEIGHT = 70;

    public static final String DB_SERVER = "jdbc:mysql://club-named-rapid.xyz:3306/team_work";
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_USERID = "hwan";
    public static final String DB_USERPW = "asdf";

    public static void SET_THEME(JComponent... js) {
        try {

            for (JComponent j : js) {
                j.setBackground(new Color(
                        Color.WHITE.getRed() - j.getBackground().getRed(),
                        Color.WHITE.getGreen() - j.getBackground().getGreen(),
                        Color.WHITE.getBlue() - j.getBackground().getBlue()
                ));
                j.setForeground(new Color(
                        Color.WHITE.getRed() - j.getForeground().getRed(),
                        Color.WHITE.getGreen() - j.getForeground().getGreen(),
                        Color.WHITE.getBlue() - j.getForeground().getBlue()
                ));
                j.setBorder(new LineBorder(Color.WHITE, 2, true));
                j.setFont(j.getFont().deriveFont(30.0f));
            }
        }catch (Exception e){

        }
    }

    public static void REMOVE_BORDER(JLabel... js){
        for (JLabel j : js){
            j.setBorder(null);
        }
    }

    public static void PLACE_CENTER(JFrame j, int width, int height){
        j.setSize(width, height);
        j.setLocationRelativeTo(null);
    }
}