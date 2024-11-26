package frames.hwan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MessageDialog extends JDialog implements ActionListener {
    JButton ok;

    MessageDialog(JFrame parent, String title, boolean mode, String msg) {
        super(parent, title, mode);
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
