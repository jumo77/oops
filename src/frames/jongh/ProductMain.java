package frames.jongh;

import theme.ThemeButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

class ProductMain extends JFrame implements ActionListener, MouseListener {
    Vector<String> columnName;
    Vector<Vector<String>> rowData;
    JTable table = null;
    DefaultTableModel model = null;

    JLabel NameLable, PriceLable, GroupLable;
    JTextField Product_Name, Product_Price, Group_Add, S_P_Name, S_P_Price, S_P_Group;
    ThemeButton Group_AddButton, Group_Remove, Add, Remove, Repair, S_Button;
    JComboBox dateoption;
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
        S_P_Name = new JTextField(100);
        S_P_Price = new JTextField(100);
        S_P_Group = new JTextField(100);

        dateoption = new JComboBox<>(new String[]{"오름차순","내림차순"});

        S_Button =  new ThemeButton("검색");

        Group_AddButton = new ThemeButton("분류 추가");
        Group_Remove = new ThemeButton("분류 삭제");

        Group_AddButton.addActionListener(this);
        Group_Remove.addActionListener(this);
        S_Button.addActionListener(this);

        Add = new ThemeButton("등록");
        Remove = new ThemeButton("삭제");
        Repair = new ThemeButton("수정");

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

        add(top,BorderLayout.NORTH);
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
        top.add(S_Button);
        top.add(dateoption);
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


//        public void actionS(ActionListener e){
//            if(e.getSource== S_Button){
//                String product = S_P_Name.getText().trim().toLowerCase();
//                String price = S_P_Price.getText().trim();
//                String combo = S_P_Group.getText().trim().toLowerCase();
//                String date = (String)dateoption.getSelectedItem();
//
//                Vector<Vector<String>> Result = search1(S_P_Name, S_P_Price, S_P_Group, dateoption);
//
//                model.setDataVector(Result, columnName);
//            }
//        }
//        private Vector<Vector<String>> search1(String product, String price, String group, String date) {
//            Vector<Vector<String>> match = new Vector<>();
//            Vector<Vector<String>> nomatch = new Vector<>();
//
//            for (Vector<String> row : rowData) {
//                boolean isMatch = true;
//
//                if (!product.isEmpty() && !row.get(1).toLowerCase().contains(product)) {
//                    isMatch = false;
//                }
//                if (!group.isEmpty() && !row.get(2).toLowerCase().contains(group)) {
//                    isMatch = false;
//                }
//                if (!price.isEmpty() && !row.get(3).toLowerCase().equals(price)) {
//                    isMatch = false;
//                }
//                if (isMatch) {
//                    match.add(row);
//                } else {
//                    nomatch.add(row);
//                }
//            }
//            Comparator<Vector<String>> comparator = (o1, o2) -> {
//                String s1 = o1.get(4);
//                String s2 = o2.get(4);
//                int s3 = s1.compareTo(s2);
//                return "오름차순".equals(date)?s3 : -s3;
//            };
//            match.sort(comparator);
//            nomatch.sort((o1,o2) -> o2.get(4).compareTo(o1.get(4)));
//
//            match.addAll(nomatch);
//            return match;
//        }
}