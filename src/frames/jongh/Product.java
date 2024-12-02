package frames.jongh;
    import java.awt.*;
    import java.awt.event.*;
    import javax.swing.*;
    import javax.swing.table.*;
    import java.util.*;

    class ProductMain extends JFrame implements ActionListener,MouseListener {
        Vector<String> columnName;
        Vector<Vector<String>> rowData;
        JTable table = null;
        DefaultTableModel model = null;

        JLabel NameLable, PriceLable, GroupLable;
        JTextField Product_Name, Product_Price, Group_Add;
        JButton Group_AddButton, Group_Remove, Add, Remove, Repair;

        JScrollPane SP;
        int row;


        ProductMain() {
            Container c = getContentPane();
            c.setLayout(new BorderLayout());
            JPanel top = new JPanel();
            JPanel center = new JPanel();
            c.add(top, BorderLayout.NORTH);
            c.add(center, BorderLayout.CENTER);

            columnName = new Vector<String>();
            columnName.add("제품 코드");
            columnName.add("제품명");
            columnName.add("분류");
            columnName.add("가격");
            columnName.add("개발일");
            columnName.add("사원 코드");

            rowData = new Vector<Vector<String>>();
            model = new DefaultTableModel(rowData, columnName);

            table = new JTable(model);
            SP = new JScrollPane(table);

            NameLable = new JLabel("제품 :");
            PriceLable = new JLabel("가격 :");
            GroupLable = new JLabel("분류 :");

            Product_Name = new JTextField(100);
            Product_Price = new JTextField(100);
            Group_Add = new JTextField(100);

            Group_AddButton = new JButton("분류 추가");
            Group_Remove = new JButton("분류 삭제");

            Group_AddButton.addActionListener(this);
            Group_Remove.addActionListener(this);

            Add = new JButton("등록");
            Remove = new JButton("삭제");
            Repair = new JButton("수정");

            Add.addActionListener(this);
            Remove.addActionListener(this);
            Repair.addActionListener(this);

            center.setLayout(null);

            NameLable.setBounds(600, 200, 100, 20);
            PriceLable.setBounds(600, 300, 100, 20);
            GroupLable.setBounds(600, 400, 100, 20);

            Product_Name.setBounds(640,200,300,30);
            Product_Price.setBounds(640,300,300,30);
            Group_Add.setBounds(640,400,300,30);

            Add.setBounds(600,660,100,30);
            Remove.setBounds(730,660,100,30);
            Repair.setBounds(860,660,100,30);
            Group_AddButton.setBounds(650,500,100,30);
            Group_Remove.setBounds(800,500,100,30);
            SP.setBounds(20,150,500,520); //센터 위치

            center.add(Group_AddButton);
            center.add(Group_Remove);
            center.add(Add);
            center.add(Remove);
            center.add(Repair);
            center.add(Product_Name);
            center.add(Product_Price);
            center.add(Group_Add);
            center.add(SP);
            center.add(NameLable);
            center.add(PriceLable);
            center.add(GroupLable);
        }
        public void actionPerformed(ActionEvent e) {
            String name = (String)Product_Name.getText();
            String price = (String)Product_Price.getText();
            String add = (String)Group_Add.getText();

            Vector<String> txt = new Vector<String>();
            txt.add(name); txt.add(price); txt.add(add);

            if(e.getActionCommand().equals("등록"))
                rowData.add(txt);

            else if (e.getActionCommand().equals("삭제")){
                rowData.remove(row); clearInputData();}
                else if (e.getActionCommand().equals("수정")) {
                    rowData.remove(row); rowData.add(row,txt);}
                else {
                        clearInputData();}
                    table.updateUI();

        }
    void clearInputData() {
        Product_Name.setText("");
        Product_Price.setText("");
        Group_Add.setText("");
    }
    public void mouseClicked(MouseEvent e) {
            row = table.getSelectedRow();
            Product_Name.setText((String)model.getValueAt(row,2));
            Product_Price.setText((String)model.getValueAt(row,3));
            Group_Add.setText((String)model.getValueAt(row,4));
    }
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        }
        class Product {
        public static void main(String[] args) {
            ProductMain frame = new ProductMain();
            frame.setTitle("Product");
            frame.setSize(1000,800);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.show();
            }
        }
