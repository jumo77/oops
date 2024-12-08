package frames.jongh;

import javax.swing.*;

public class Product {

    private JFrame frame;

    public Product() {

        frame = new JFrame("Product");
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void show() {
        frame.setVisible(true);
    }
}
