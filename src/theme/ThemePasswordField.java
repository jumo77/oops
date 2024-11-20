package theme;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ThemePasswordField extends JPasswordField {

    private ThemePasswordField(int column) {
        super(column);
        setForeground(Color.WHITE);
        setBorder(new LineBorder(Color.WHITE, 1, true));
    }

}
