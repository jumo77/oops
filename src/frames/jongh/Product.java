package frames.jongh;

import javax.swing.*;

public class Product {

    private static JFrame frame;

    public Product(String title) {

        frame = new JFrame(title);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ProductMain promain = new ProductMain();
        frame.add(promain.getPanel());
    }

    public static void show() {
        frame.setVisible(true);
    }
}