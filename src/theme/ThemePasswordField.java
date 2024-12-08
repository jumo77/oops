package theme;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.Document;
import java.awt.*;

public class ThemePasswordField extends JPasswordField {

    public ThemePasswordField(Document doc, String txt, int columns){
        super(doc, txt, columns);
        setForeground(Color.WHITE);
        setBorder(new LineBorder(Color.WHITE, 1, true));
    }

    public ThemePasswordField(int columns) {
        this(null, null, columns);
    }

    public ThemePasswordField(){
        this(null, null, 0);
    }

}
