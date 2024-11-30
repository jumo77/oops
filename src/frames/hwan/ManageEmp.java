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

public class ManageEmp extends JFrame implements ActionListener {
    Vector<String> columnName;
    Vector<Vector<String>> rowData;
    JTable table;
    DefaultTableModel model;
    JTextField search;
        JComboBox searchComboBox, sortComboBox;

    String dataString[] = {"이름", "입사일", "사번", "직급", "부서명"};
    HashMap<String, Integer> dataStringInv = new HashMap<String, Integer>() {{ //해시맵(데이터 형 변환) 이걸 통해서 데이터>어느 열을 갈지 변환
        put("이름", 0); //테이블에서 열 순서를 int로 가져오기 때문에 직접 지정한 숫자로 열을 선택(클래스 선언했으면 필요 없었음)
        put("입사일", 1);
        put("사번", 2);
        put("직급", 5);
        put("부서명", 3);
    }};
    ArrayList<String> gradeString;
    HashMap<String, Integer> gradeStringInv; //직급 변환(정렬을 편하게 하기 위해서)
    String emp_name, emp_date, emp_num, emp_dept, emp_tel, emp_grade, emp_salary, emp_password;

    JScrollPane tableSP;

    public ManageEmp(String title) {
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

        top.setLayout(new FlowLayout());
        sortComboBox = new JComboBox(dataString);
        sortComboBox.addActionListener(this);
        top.add(sortComboBox);

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

        gradeString = new ArrayList<>();
        gradeStringInv = new HashMap<>();
        try (ResultSet grade = DBMS.select("select * from team_work.emp_grade order by id")) {
            while (grade.next()) {
                gradeString.add(grade.getString(2)); //직급을 입력받고(2번째 열)
                gradeStringInv.put(grade.getString(2), Integer.parseInt(grade.getString(1))); //직급을 String으로 변환
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        try (ResultSet rs = DBMS.select("select * from team_work.employee")) {
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

                row.add(emp_name);
                row.add(emp_date);
                row.add(emp_num);
                row.add(emp_dept);
                row.add(emp_tel);
                row.add(gradeName);
                row.add(emp_salary);
                row.add(emp_password);

                rowData.add(row);
                table.updateUI();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String target = Objects.requireNonNull(sortComboBox.getSelectedItem()).toString();
        switch (target) {
            case "이름":
                rowData.sort(Comparator.comparing(a -> a.get(0)));
                break;
            case "사번":
                rowData.sort(Comparator.comparing(a -> a.get(2)));
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
    }
}