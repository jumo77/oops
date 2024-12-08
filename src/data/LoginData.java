package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginData {
    public static int id;
    public static String name;
    public static int grade;
    public static int dept;
    public static ArrayList<String> gradeString, deptString;

    public static void init() {
        System.out.println("Initializing Table Data");
        gradeString = new ArrayList<>();
        gradeString.add("에러");//0번째를 비우기 위해(리스트는 0번째가 있으므로)
        gradeStringInv = new HashMap<>();
        try (
                ResultSet rs = DBMS.DB.executeQuery("select * from team_work.emp_grade order by id")) {
            while (rs.next()) {
                gradeString.add(rs.getString(2)); //직급을 입력받고(2번째 열)
                gradeStringInv.put(rs.getString(2), Integer.parseInt(rs.getString(1))); //직급을 String으로 변환
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }

        deptString = new ArrayList<>();
        deptString.add("에러");//0번째를 비우기 위해(리스트는 0번째가 있으므로)
        deptStringInv = new HashMap<>();
        try (
                ResultSet rs = DBMS.DB.executeQuery("select * from team_work.dept order by id")) {
            while (rs.next()) {
                deptString.add(rs.getString(2));
                deptStringInv.put(rs.getString(2), Integer.parseInt(rs.getString(1)));
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Done Initializing Table Data");
    }

    public static HashMap<String, Integer> dataStringInv = new HashMap<String, Integer>() {{ //해시맵(데이터 형 변환) 이걸 통해서 데이터>어느 열을 갈지 변환
        put("이름", 0); //테이블에서 열 순서를 int로 가져오기 때문에 직접 지정한 숫자로 열을 선택(클래스 선언했으면 필요 없었음)
        put("입사일", 1);
        put("사번", 2);
        put("직급", 5);
        put("부서명", 3);
    }};
    public static HashMap<String, Integer> gradeStringInv, deptStringInv; //직급, 부서 숫자로 변환(정렬을 편하게 하기 위해서)


}