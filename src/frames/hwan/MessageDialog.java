package frames.hwan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageDialog extends JDialog implements ActionListener {
    JButton ok;

    public MessageDialog(JFrame parent, String title, boolean mode, String msg) {
        super(parent, title, mode);
        setBounds(parent.getX() + parent.getWidth() / 2 - 50,
                parent.getY() + parent.getHeight()/2 -50,
                100, 100);
        JPanel pc = new JPanel();
        JLabel label = new JLabel(msg);
        pc.add(label);
        add(pc, BorderLayout.CENTER);
        JPanel ps = new JPanel();
        ok = new JButton("OK");
        ok.addActionListener(this);
        ps.add(ok);
        add(ps, BorderLayout.SOUTH);
        pack();
    }

    public void actionPerformed(ActionEvent ae) {
        dispose();
    }
}

