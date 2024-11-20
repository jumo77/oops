package components;

import theme.ThemeLabel;

import javax.swing.*;
import java.awt.*;

public class RightArraganedLabel extends ThemeLabel {

    public RightArraganedLabel(String content){
        super(content);
        this.setHorizontalAlignment(JLabel.RIGHT);
    }

}