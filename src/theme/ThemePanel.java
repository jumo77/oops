package theme;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ThemePanel extends JPanel {
    public ThemePanel(){
        super();
        setBackground(Color.BLACK);
        setBorder(new LineBorder(Color.WHITE, 2, true));
    }
}
