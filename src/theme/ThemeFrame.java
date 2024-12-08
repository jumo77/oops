package theme;

import javax.swing.*;
import java.awt.*;

public class ThemeFrame extends JFrame {
    public ThemeFrame(){
        super();
        Container c = getContentPane();
        c.setLayout(null);
        c.setBackground(Color.BLACK);
        c.setForeground(Color.WHITE);
        this.setLocationRelativeTo(null);
    }
}
