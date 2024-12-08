package theme;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ThemeButton extends JButton {
    
    public ThemeButton(String text, Icon icon){
        super(text, icon);
        setForeground(Color.WHITE);
        setBackground(Color.BLACK);
        setFont(getFont().deriveFont(30.0f));
        setBorder(new LineBorder(Color.WHITE, 2, true));
        setSize(200, 50);
    }
    
    public ThemeButton(String text) {
        this(text, null);
    }
}
