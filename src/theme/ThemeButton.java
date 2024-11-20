package theme;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ThemeButton extends JButton {
    public ThemeButton(String text){
        super(text);
        setBorder(new LineBorder(Color.WHITE, 2, true));
    }
}
