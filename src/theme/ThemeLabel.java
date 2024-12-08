package theme;

import javax.swing.*;
import java.awt.*;

public class ThemeLabel extends JLabel {
    
    public ThemeLabel(String text, Icon icon, int horizontalAlignment){
        super(text, icon, horizontalAlignment);
        setForeground(Color.WHITE);
        setFont(getFont().deriveFont(30.0f));
    }
    
    public ThemeLabel(String text){
        this(text, null, LEADING);
    }

    public ThemeLabel(String text, int horizontalAlignment){
        this(text, null, horizontalAlignment);
    }
}
