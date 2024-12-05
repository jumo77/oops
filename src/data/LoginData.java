package data;

import java.util.HashMap;

public class LoginData {
    public static int id;
    public static String name;
    public static int grade;
    public static int dept;

    public static HashMap<String, Integer> dataStringInv = new HashMap<String, Integer>() {{ //해시맵(데이터 형 변환) 이걸 통해서 데이터>어느 열을 갈지 변환
        put("이름", 0); //테이블에서 열 순서를 int로 가져오기 때문에 직접 지정한 숫자로 열을 선택(클래스 선언했으면 필요 없었음)
        put("입사일", 1);
        put("사번", 2);
        put("직급", 5);
        put("부서명", 3);
    }};
    public static HashMap<String, Integer> gradeStringInv, deptStringInv; //직급, 부서 숫자로 변환(정렬을 편하게 하기 위해서)

}