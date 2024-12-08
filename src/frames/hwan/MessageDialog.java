package frames.hwan;

import theme.ThemeButton;
import theme.ThemeLabel;
import theme.ThemePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageDialog extends JDialog implements ActionListener {
    ThemeButton ok;

    public MessageDialog(JFrame parent, String title, boolean mode, String msg) {
        super(parent, title, mode);
        setBounds(parent.getX() + parent.getWidth() / 2 - 50,
                parent.getY() + parent.getHeight()/2 -50,
                100, 100);
        ThemePanel pc = new ThemePanel();
        ThemeLabel label = new ThemeLabel(msg);
        pc.add(label);
        add(pc, BorderLayout.CENTER);
        ThemePanel ps = new ThemePanel();
        ok = new ThemeButton("OK");
        ok.addActionListener(this);
        ps.add(ok);
        add(ps, BorderLayout.SOUTH);
        pack();
    }

    public void actionPerformed(ActionEvent ae) {
        dispose();
    }
}
