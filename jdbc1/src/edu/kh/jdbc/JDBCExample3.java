package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample3 {
	public static void main(String[] args) {
		
		
		// 문제: 최소 급여와 최대 급여를 입력받아 그 사이에 있는 사원의 사원 이름 급여를 급여 내림차순으로 조회한다
		
		
		
		// 1) JDBC에서 사용할 객체 3개의 참조형변수를 선언한다
		
		Connection conn = null; // DB의 연결 정보를 저장하는 객체
		Statement stmt = null; // SQL과의 버스, 문을 수행학 결과를 반환한다
		ResultSet rs = null; // SELECT를 수행한 결과를 저장한다.(DML이라면 필요 없음)
		Scanner sc = null; // 키보드 입력용 객체
		
		try {
			
			// 2) DriverManager를 이용하여 Connection의 객체를 생성한다
			
			// 2-1) Oracle JDBC 드라이버 객체를 메모리에 로드한다
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			
			// 2-2) DB의 연결 정보를 작성한다
			
			String type = "jdbc:oracle:thin:@"; // 라는 드라이버의 종류가 있다
			String host = "localhost"; // DB가 깔린 서버 컴퓨터의 IP주소 또는 도메인의 주소를 넣는다. localhost로 현재 컴퓨터를 가리킬 수 있다.
			
			String port = ":1521"; // 프로그램 연결을 위한 port번호
			String dbName= ":XE"; // DBMS 자체의 이름 (XE = eXpress Edition)

			

			// jdbc:oracle:thin:@ localhost :1521 :XE가 만들어짐
			
			// 계정 이름에 대한 정보
			
			String userName = "kh"; // 사용자의 계정
			String password = "kh1234"; // 사용자의 비밀번호
			
			
			
			// 2-3) 매니저와 연결정보를 이용하여 커넥션을 생성한다
			
			conn = DriverManager.getConnection(type+host+port+dbName, userName, password );
			
			// 맨 앞은 url주소 => connection을 생성하여 담을 참조변수 conn
			System.out.println(conn);
			
			
			
		
			
			
			
			
			
			// 3) SQL문에 대한 작성
			
			// 입력받은 급여이므로 Scanner
			// int input이라는 변수에 Salary를 담아야 함 
			
			
			sc = new Scanner(System.in);
			System.out.print("최소 금액 입력:");
			int minsal = sc.nextInt();
			
			System.out.print("최대 금액 입력:");
			int maxsal = sc.nextInt();
			
			// String sql = "SELECT EMP_ID, EMP_NAME, SALARY "  + "FROM EMPLOYEE "+ "WHERE SALARY >= " + minsal +"AND SALARY <=" + maxsal + ORDER BY  SALARY DESC  ; 
			
			String sql = """ 
					SELECT EMP_ID, EMP_NAME, SALARY FROM EMPLOYEE WHERE SALARY BETWEEN 
							"""
							
					+ minsal + "AND " + maxsal + "ORDER BY  SALARY DESC";
			
			// 자동으로 개행과 공백을 포함하며 문자열의 연결이 자동으로 처리 됨 => 기존처럼 +연산자를 통해 문자열을 연결할 필요가 없다.
			
			
			//4) Statemenmt 객체 생성
			
			stmt = conn.createStatement();

			
			
			
			//5) Statement 객체를 이용하여 SQL을 수행한 후 결과를 반환한다
			
			rs = stmt.executeQuery(sql);
			
			
			
			
			// 6) 조회 결과가 담겨있는 ResultSet을 
			// 커서를 이용해 1행씩 접근하고 그 결과로 각 행에 작성된 컬럼 값을 얻어온다
			// while반복문으로 데이터를 꺼내어 출력한다
			
			while(rs.next()) {
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_Name");
				int salary = rs.getInt("SALARY");
				

				System.out.printf("%s / %s / %d 원\n", empId,empName,salary );
				
			}
			
			
		
			
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
				if(sc!=null) sc.close();

			} catch (Exception e) {
				e.printStackTrace();

			}
			
		}

		
		
	}

}
