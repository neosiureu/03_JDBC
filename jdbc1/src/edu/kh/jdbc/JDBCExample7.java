package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class JDBCExample7 {

	public static void main(String[] args) {
		
		// EMPLOYEE	테이블에서
		// 사번, 이름, 성별, 급여, 직급명, 부서명을 조회
		// 단, 입력 받은 조건에 맞는 결과만 조회하고 정렬할 것
				
		// - 조건 1 : 성별 (M, F)
		// - 조건 2 : 급여 범위
		// - 조건 3 : 급여 오름차순/내림차순
				
		// [실행화면]
		// 조회할 성별(M/F) : F
		// 급여 범위(최소, 최대 순서로 작성) :
		// 3000000
		// 4000000
		// 급여 정렬(1.ASC, 2.DESC) : 2
				
		// 사번 | 이름   | 성별 | 급여    | 직급명 | 부서명
		//--------------------------------------------------------
		// 218  | 이오리 | F    | 3890000 | 사원   | 없음
		// 203  | 송은희 | F    | 3800000 | 차장   | 해외영업2부
		// 212  | 장쯔위 | F    | 3550000 | 대리   | 기술지원부
		// 222  | 이태림 | F    | 3436240 | 대리   | 기술지원부
		// 207  | 하이유 | F    | 3200000 | 과장   | 해외영업1부
		// 210  | 윤은해 | F    | 3000000 | 사원   | 해외영업1부


		Connection  conn = null;
		PreparedStatement  pstmt = null;
		ResultSet rs  = null;
		Scanner sc = null;
		
		try {
			
			//2단계
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String type = "jdbc:oracle:thin:@";
			String host  = "localhost";
			String port  = ":1521";
			String dName  = ":XE";
			
			String userName = "kh";
			String password = "kh1234";
	
			conn = DriverManager.getConnection(type+ host + port + dName, userName, password);
			System.out.println(conn);
			
			
			// 3단계
			
			sc = new Scanner(System.in);
			System.out.print("조회할 성별 (M/F): ");
			char gender = sc.next().toUpperCase().charAt(0);
			System.out.println("급여 범위를 최대 최소 순서로 입력하세요: ");
			int minsal =sc.nextInt();
			int maxsal =sc.nextInt();
			System.out.print("급여 정렬 (1.ASC, 2.DESC)");
			int orderInt =sc.nextInt();
			String order  = (orderInt== 1 )? "ASC": "DESC";


			String sql = """
				    SELECT EMP_ID 사번, EMP_NAME 이름,
				           DECODE(substr(emp_no, 8, 1), '1', 'M', '2', 'F') 성별,
				           salary 급여, job_name 직급명,
				           NVL(dept_title, '없음') 부서명
				    FROM EMPLOYEE
				         JOIN job USING (job_code)
				         LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = EMPLOYEE.DEPT_CODE)
				    WHERE (SALARY >= ?)
				      AND (SALARY <= ?)
				      AND DECODE(substr(emp_no, 8, 1), '1', 'M', '2', 'F') = '""" + gender +
				      		
				      		"""
				    'ORDER BY SALARY """ + " " + order;
			
			
			// SELECT EMP_ID , EMP_NAME, DEPT_TITLE,  JOB_NAME   FROM EMPLOYEE JOIN JOB  USING  (JOB_CODE) JOIN DEPARTMENT ON (EMPLOYEE.DEPT_CODE = DEPARTMENT.DEPT_ID )  WHERE JOB.JOB_NAME = '부사장' ;
			
			

				
			// 4단계
			
			pstmt  = conn.prepareStatement(sql);
			
			
			//5단계
			
			pstmt.setInt(1,minsal);
			pstmt.setInt(2,maxsal);
		
			//6단계
			
			rs = pstmt.executeQuery();
			
			

		
			/*
			
			// solution1) flag의 이용
			
			boolean flag  = true;
			// 있으면 false, 없으면 true
			
			
			while(rs.next()) {
				flag = false;
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_Name");

				System.out.printf("%s / %s / %s / %s \n", empId,empName,deptTitle, jobName);
			}
			if(flag) {
				System.out.println("해당 부서의 사람이 없습니다.");
			}
			// 조회된 행이 하나도 없기 때문
			*/
			
			
			// solution2) null이면 바로 return하기
			
//			if(!rs.next()) // 조회 결과가 없다면
//			{
//				System.out.println("일치하는 부서가 없습니다.");
//				return;
//				// 다만 finally는 수행하므로 메모리 관리에는 문제 X
//				
//				// 다음 포인터에 조회 결과가 없어야 하므로 한 행만 존재하는 경우에도 이것이 발생함
//				
//			
//			}
			
			
			boolean flag = true;
			System.out.println("사번 | 이름   | 성별 | 급여   |직급명 | 부서명");

			while (rs.next()) {
                flag = false; // 첫 턴을 돌았다는 것 자체가 이미 한 행이라도 값이 있는 행이 있다는 것
                // 조회 컬럼 값 가져오기
                String empId = rs.getString("사번"); //컬럼의 이름, 별칭, 조회한 컬럼의 순서가 모두 인자로 올 수 있음
                String empName = rs.getString("이름");
                String gen = rs.getString("성별");
                int salary = rs.getInt("급여");
                String jobName = rs.getString("직급명");
                String deptTitle = rs.getString("부서명");

                System.out.printf("%-4s | %3s | %-4s | %7d | %-3s  | %s \n",
						empId, empName, gen, salary, jobName, deptTitle);
            }
			
			if(flag) System.out.println("조회 결과 없음");
			
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			// 7) 사용완료된 JDBC 객체의 자원을 close한다
			
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			
			}
		}
		

	}

}
