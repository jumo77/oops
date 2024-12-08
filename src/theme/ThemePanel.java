package theme;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ThemePanel extends JPanel {

    public ThemePanel(LayoutManager layout, boolean isDoubleBuffered){
        super(layout, isDoubleBuffered);
        setBackground(Color.BLACK);
        setBorder(new LineBorder(Color.WHITE, 2, true));
    }

    public ThemePanel(){
        this(null, true);
    }

    public ThemePanel(LayoutManager layout){
        this(layout, true);
    }
}
