package theme;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.Document;
import java.awt.*;

public class ThemeTextField extends JTextField {

    public ThemeTextField(Document doc, String text, int column){
        super(doc, text, column);
        setForeground(Color.WHITE);
        setBorder(new LineBorder(Color.WHITE, 1, true));
        setFont(new Font("serif", Font.PLAIN, 30));
    }

    public ThemeTextField(){
        this(null, null, 0);
    }

    public ThemeTextField(int column){
        this(null, null, column);
    }

    public ThemeTextField(String text){
        this(null, text, 0);
    }
}
