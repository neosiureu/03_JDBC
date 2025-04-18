package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample4 {
// 부서 이름을 입력받아 해당 부서에 근무하는 사원의 사번, 이름, 부서명, 직급명을 구하되 반드시 직급코드에 대해 오름차순으로 조회한다
	
	/* 예시
	 [실행화면]
	 부서명 입력: 총무부
	 // 200/ 선동일 / 총무부 / 대표
	 .... 와 같이 출력
	 
	 만일 부서명을 입력하는 경우 일치하는 부서가 없다고 출력
	 * */
	
	// hint: SQL에서 문자열은 양쪽에 반드시 홑따옴표 '' 가 필요하다
	
	// ex) 총무부라고 입력하면 같은 것으로 '총무부'를 써야 한다
	
	
	public static void main(String[] args) {
		Connection  conn = null;
		Statement  stmt = null;
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
			System.out.print("부서 이름:");
			String input = sc.nextLine();
			
			// SELECT EMP_ID , EMP_NAME, DEPT_TITLE,  JOB_NAME   FROM EMPLOYEE JOIN JOB  USING  (JOB_CODE) JOIN DEPARTMENT ON (EMPLOYEE.DEPT_CODE = DEPARTMENT.DEPT_ID )  WHERE JOB.JOB_NAME = '부사장' ;
			
			
			String sql  = "SELECT EMP_ID , EMP_NAME, DEPT_TITLE,  JOB_NAME   FROM EMPLOYEE JOIN JOB  ON (JOB.JOB_CODE = EMPLOYEE.JOB_CODE) LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID )  "
					+ "WHERE DEPT_TITLE = '" + input  +"' ORDER BY EMPLOYEE.JOB_CODE " ;
			// 홑따옴표를 양쪽에 넣어준 것
			
				
			// 4단계
			
			stmt  = conn.createStatement();
			
			
			//5단계
			
			rs = stmt.executeQuery(sql);
		
			//6단계
			
		
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
			
			if(!rs.next()) // 조회 결과가 없다면
			{
				System.out.println("일치하는 부서가 없습니다.");
				return;
				// 다만 finally는 수행하므로 메모리 관리에는 문제 X
				
				// 다음 포인터에 조회 결과가 없어야 하므로 한 행만 존재하는 경우에도 이것이 발생함
				
			
			}
			
			
			/*
			// 커서가 한 번 이미 아래로 내려가 있기 때문에 이렇게 하면 오류
			while(rs.next()) {
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_Name");

				System.out.printf("%s / %s / %s / %s \n", empId,empName,deptTitle, jobName);
			}
			
			*/
			
			
			do {
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_Name");

				System.out.printf("%s / %s / %s / %s \n", empId,empName,deptTitle, jobName);
				
			}
			while(rs.next());
			
			
			
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			// 7) 사용완료된 JDBC 객체의 자원을 close한다
			
			try {
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			
			}
		}
		
		
	}
	
	
	

	
	

}
