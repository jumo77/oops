package theme;

import javax.swing.*;
import java.awt.*;

public class ThemeLabel extends JLabel {

    public ThemeLabel(String text){
        super(text);
        this.setForeground(Color.WHITE);
        setFont(getFont().deriveFont(30.0f));
    }
}
