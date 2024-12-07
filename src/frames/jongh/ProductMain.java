package frames.jongh;

import data.DBMS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

class ProductMain implements ActionListener {

    private JPanel mainPanel;
    private Vector<String> columnName;
    private Vector<Vector<String>> rowData;
    private JTable table;
    private DefaultTableModel model;

    private JTextField productName, productPrice, newGroupName;
    private JComboBox<String> groupComboBox;
    private JButton addButton, removeButton, repairButton, registerGroupButton, refreshButton;

    public ProductMain() {
        mainPanel = new JPanel(null);

        columnName = new Vector<>();
        columnName.add("제품 코드");
        columnName.add("제품명");
        columnName.add("분류");
        columnName.add("가격");
        columnName.add("개발일");
        columnName.add("사원 코드");

        rowData = new Vector<>();
        model = new DefaultTableModel(rowData, columnName);
        table = new JTable(model);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(20, 150, 500, 520);
        mainPanel.add(tableScrollPane);

        JLabel nameLabel = new JLabel("제품:");
        JLabel priceLabel = new JLabel("가격:");
        JLabel groupLabel = new JLabel("분류:");
        JLabel newGroupLabel = new JLabel("새 분류 등록:");

        productName = new JTextField();
        productPrice = new JTextField();
        groupComboBox = new JComboBox<>();
        newGroupName = new JTextField();

        nameLabel.setBounds(600, 200, 100, 20);
        productName.setBounds(640, 200, 300, 30);

        priceLabel.setBounds(600, 300, 100, 20);
        productPrice.setBounds(640, 300, 300, 30);

        groupLabel.setBounds(600, 400, 100, 20);
        groupComboBox.setBounds(640, 400, 300, 30);

        newGroupLabel.setBounds(600, 500, 100, 20);
        newGroupName.setBounds(640, 500, 300, 30);

        mainPanel.add(nameLabel);
        mainPanel.add(productName);
        mainPanel.add(priceLabel);
        mainPanel.add(productPrice);
        mainPanel.add(groupLabel);
        mainPanel.add(groupComboBox);
        mainPanel.add(newGroupLabel);
        mainPanel.add(newGroupName);

        // 버튼
        addButton = new JButton("등록");
        removeButton = new JButton("삭제");
        repairButton = new JButton("수정");
        registerGroupButton = new JButton("분류 등록");
        refreshButton = new JButton("새로고침");

        addButton.setBounds(600, 660, 100, 30);
        removeButton.setBounds(730, 660, 100, 30);
        repairButton.setBounds(860, 660, 100, 30);
        registerGroupButton.setBounds(860, 500, 100, 30);
        refreshButton.setBounds(860, 100, 100, 30);

        mainPanel.add(addButton);
        mainPanel.add(removeButton);
        mainPanel.add(repairButton);
        mainPanel.add(registerGroupButton);
        mainPanel.add(refreshButton);


        addButton.addActionListener(this);
        registerGroupButton.addActionListener(this);
        refreshButton.addActionListener(this);


        loadGroups();
        loadProducts();
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            registerProduct();
        } else if (e.getSource() == registerGroupButton) {
            registerGroup();
        } else if (e.getSource() == refreshButton) {
            loadProducts(); // 새로고침 버튼으로 테이블 데이터 로드
        }
    }

    private void registerProduct() {
        String name = productName.getText().trim();
        String groupName = (String) groupComboBox.getSelectedItem(); // 선택된 분류
        String price = productPrice.getText().trim();

        if (name.isEmpty() || groupName == null || price.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "모든 필드를 입력해야 합니다.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int groupID = getGroupIDFromName(groupName);
            if (groupID == -1) {
                JOptionPane.showMessageDialog(mainPanel, "유효하지 않은 분류입니다.", "오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int productPriceValue = Integer.parseInt(price);
            String productCode = gProductCode();

            String query = String.format(
                    "INSERT INTO team_work.product (product_code, product_name, product_date, product_price, groupID) " +
                            "VALUES ('%s', '%s', NOW(), %d, %d)",
                    productCode, name, productPriceValue, groupID
            );

            DBMS.DB.executeUpdate(query);

            JOptionPane.showMessageDialog(mainPanel, "제품이 등록되었습니다.", "등록 성공", JOptionPane.INFORMATION_MESSAGE);

            loadProducts();
            clearInputFields();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainPanel, "데이터베이스 오류: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainPanel, "가격 필드는 숫자로 입력해야 합니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registerGroup() {
        String groupName = newGroupName.getText().trim();

        if (groupName.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "분류명을 입력해야 합니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = String.format("INSERT INTO team_work.`group` (group_name) VALUES ('%s')", groupName);
            DBMS.DB.executeUpdate(query);
            JOptionPane.showMessageDialog(mainPanel, "분류가 등록되었습니다.", "등록 성공", JOptionPane.INFORMATION_MESSAGE);
            newGroupName.setText("");
            loadGroups();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainPanel, "데이터베이스 오류: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private int getGroupIDFromName(String groupName) throws SQLException {
        String query = String.format("SELECT groupID FROM team_work.`group` WHERE group_name = '%s'", groupName);
        ResultSet rs = DBMS.DB.executeQuery(query);
        if (rs.next()) {
            return rs.getInt("groupID");
        }
        return -1;
    }

    private String gProductCode() {
        try {
            String query = "SELECT MAX(product_code) FROM team_work.product";
            ResultSet rs = DBMS.DB.executeQuery(query);
            if (rs.next()) {
                String maxCode = rs.getString(1);
                if (maxCode != null) {
                    int nextCode = Integer.parseInt(maxCode) + 1;
                    return String.format("%05d", nextCode);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainPanel, "제품 코드 생성 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        return "00001";
    }

    private void loadGroups() {
        groupComboBox.removeAllItems();
        try {
            String query = "SELECT group_name FROM team_work.`group`";
            ResultSet rs = DBMS.DB.executeQuery(query);
            while (rs.next()) {
                groupComboBox.addItem(rs.getString("group_name"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainPanel, "분류를 불러오는 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void loadProducts() {
        rowData.clear();
        try {
            String query = "SELECT p.product_code, p.product_name, g.group_name, p.product_price, p.product_date, p.emp_num " +
                    "FROM team_work.product p " +
                    "JOIN team_work.`group` g ON p.groupID = g.groupID";
            ResultSet rs = DBMS.DB.executeQuery(query);

            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(rs.getString("product_code"));
                row.add(rs.getString("product_name"));
                row.add(rs.getString("group_name"));
                row.add(String.valueOf(rs.getInt("product_price")));
                row.add(rs.getString("product_date"));
                row.add(rs.getString("emp_num"));
                rowData.add(row);
            }
            table.updateUI();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainPanel, "제품 데이터를 불러오는 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void clearInputFields() {
        productName.setText("");
        productPrice.setText("");
        groupComboBox.setSelectedIndex(-1);
    }
}
