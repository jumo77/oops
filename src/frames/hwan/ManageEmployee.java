package frames.hwan;

import data.DBMS;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static data.LoginData.*;

public class ManageEmployee extends JFrame implements ActionListener{
    Vector<String> columnName;
    Vector<Vector<String>> rowData;
    JTable table;
    DefaultTableModel model;
    JTextField search;
    JComboBox searchComboBox, sortComboBox;
    JButton newMember, cancel, updateMember, informMember;

    String dataString[] = {"이름", "입사일", "사번", "직급", "부서명"};

    String emp_name, emp_date, emp_num, emp_dept, emp_tel, emp_grade, emp_salary, emp_password;

    JScrollPane tableSP;

    public ManageEmployee(String title) {
        setTitle(title);
        Container ct = getContentPane();
        ct.setLayout(new BorderLayout());
        JPanel top = new JPanel();
        JPanel center = new JPanel();
        JPanel bottom = new JPanel();
        JPanel right = new JPanel();
        ct.add(top, BorderLayout.NORTH);
        ct.add(center, BorderLayout.CENTER);
        ct.add(bottom, BorderLayout.SOUTH);
        ct.add(right, BorderLayout.EAST);



        columnName = new Vector<>();
        columnName.add("사원명");
        columnName.add("입사일");
        columnName.add("사번");
        columnName.add("부서명");
        columnName.add("전화번호");
        columnName.add("직급");
        columnName.add("연봉(단위 만)");
        columnName.add("비밀번호");

        center.setLayout(new GridLayout(2, 1));


        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.LEFT));
        sortComboBox = new JComboBox(dataString);
        sortComboBox.addActionListener(this);
        p1.add(sortComboBox);
        center.add(p1);

        dataStringInv = new HashMap<>();
        for (int i = 0; i < dataString.length; i++) { //데이터를 가져왔을 때 주소 구하고 저장
            dataStringInv.put(dataString[i], i);
        }
        rowData = new Vector<Vector<String>>();

        model = new DefaultTableModel(rowData, columnName);
        table = new JTable(model);
        tableSP = new JScrollPane(table);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter); //sorter을 선언하고 테이블에 지정

        FetchDatabase();

        center.setLayout(new FlowLayout());
        center.add(tableSP);

        right.setLayout(new FlowLayout());
        searchComboBox = new JComboBox(dataString);
        searchComboBox.addActionListener(this);
        search = new JTextField(8);
        search.addActionListener(this);
        right.add(searchComboBox);
        right.add(search);
        RowFilter<DefaultTableModel, Object> customFilter = new RowFilter<DefaultTableModel, Object>() { //필터 선언
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ?> entry) {
                String selectedColumn = (String) searchComboBox.getSelectedItem(); //검색 콤보박스에서 값 추출
                int columnIndex = dataStringInv.get(selectedColumn); //필터에 들어간 값의 열을 저장

                String value = (String) entry.getValue(columnIndex); //entry가 필터에 들어갈 값이고 저장한 열을 통해서 값을 불러옴
                String searchText = search.getText().trim().toLowerCase(); //필터링 해주고 싶은 값을 구해주는 문자열

                return value != null && value.toLowerCase().contains(searchText); //필터링을 해준 값을 반환
            }
        };
        sorter.setRowFilter(customFilter);
        searchComboBox.addActionListener(e -> sorter.setRowFilter(customFilter)); //콤보박스에 이벤트 추가
        search.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { //값을 입력했을 때 실행
                sorter.setRowFilter(customFilter); //필터를 적용
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { //값을 지웠을 때
                sorter.setRowFilter(customFilter);
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { //텍스트 변경을 제외한 나머지(추상 클래스라서 무조건 선언해야하는 값)
            }
        });

        informMember = new JButton("판매 항목 보기"); //판매 항목 새 창 열기
        informMember.addActionListener(this);
        bottom.add(informMember);

        updateMember = new JButton("수정"); //수정 가능한 창 새로 열기
        updateMember.addActionListener(this);
        bottom.add(updateMember);

        newMember = new JButton("회원 가입");
        newMember.addActionListener(this);
        bottom.add(newMember);

        cancel = new JButton("돌아가기");
        cancel.addActionListener(this);
        bottom.add(cancel);
    }

    public void FetchDatabase() {
        try (ResultSet rs = DBMS.DB.executeQuery("select * from team_work.employee")) {
            rowData.clear();
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                emp_name = rs.getString("emp_name");
                emp_date = rs.getString("emp_date");
                emp_num = rs.getString("emp_num");
                emp_dept = rs.getString("dept_id");
                emp_tel = rs.getString("emp_tel");
                emp_grade = rs.getString("emp_grade");
                emp_salary = rs.getString("emp_salary");
                emp_password = rs.getString("emp_password");

                String gradeName = gradeString.get(Integer.parseInt(emp_grade));
                String deptName = deptString.get(Integer.parseInt(emp_dept));

                row.add(emp_name);
                row.add(emp_date);
                row.add(emp_num);
                row.add(deptName);
                row.add(emp_tel);
                row.add(gradeName);
                row.add(emp_salary);
                row.add(emp_password);
                rowData.add(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        model.fireTableDataChanged(); //모델을 수정하지 않으면 오류가 발생함
        table.updateUI(); //UI 업뎃이 있다면 해야하기 때문에
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String target = Objects.requireNonNull(sortComboBox.getSelectedItem()).toString();
        switch (target) {
            case "이름":
                rowData.sort(Comparator.comparing(a -> a.get(0))); //사실 정렬은 sorter을 이용해서 되는걸 이후에 알았지만
                break;
            case "사번":
                rowData.sort(Comparator.comparing(a -> a.get(2))); //정렬하는 코드도 작성하고 싶어서 작성함
                break;
            case "입사일":
                rowData.sort(Comparator.comparing(a -> a.get(1)));
                break;
            case "직급":
                rowData.sort(Comparator.comparing(a -> gradeStringInv.get(a.get(5)))); //사번을 int형으로 변경 후에 정렬
                break;
            case "부서명":
                rowData.sort(Comparator.comparing(a -> a.get(3)));
                break;
        }
        table.updateUI();
        String s = ae.getActionCommand();
        if (s.equals("회원 가입")) {
            NewMember win1 = new NewMember("회원가입", this);
            win1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            win1.setSize(370, 400);
            win1.setLocation(800, 200);
            win1.setVisible(true);
        } else if (s.equals("돌아가기")) {
            //메인화면 출력
            this.dispose();
        } else if (s.equals("수정")) {
            int selected = table.getSelectedRow();
            if (selected < 0)
                return;
            UpdateMember win2 = new UpdateMember("정보 수정", rowData.get(selected), this);
            win2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            win2.setSize(370, 400);
            win2.setLocation(800, 200);
            win2.setVisible(true);
        } else{
            Product_Employee win3 = new Product_Employee(rowData.get(table.getSelectedRow()).get(2),
                    rowData.get(table.getSelectedRow()).get(0));
            win3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            win3.setSize(400, 600);
            win3.setLocation(800, 400);
            win3.setVisible(true);
        }
    }
}