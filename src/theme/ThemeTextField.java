package theme;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ThemeTextField extends JTextField {
    public ThemeTextField(){
        super();
        setForeground(Color.WHITE);
        setBorder(new LineBorder(Color.WHITE, 1, true));
        setFont(new Font("serif", Font.PLAIN, 30));
    }
}
