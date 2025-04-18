package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample1 {
	public static void main(String[] args) {
		
		/*
		 
		 > what is JDBC?
  
Java Data Base Connectivity의 약자
Java에서 DB에 연결할 수 있게 해주는 Java API 
  
Java에서 DB에 연결하기 쉽도록 만들어둔 코드 
  
=> 이러한 코드들은 전부 java.sql패키지에 존재하며 외부 패키지이다. 
  
위에서 classpath에 추가해두었기 때문에 사용 가능하다.
  
Java코드를 이용하여 EMPLOYEE 테이블에서 사번, 이름, 부서코드, 직급 코드, 급여, 입사일 조회한다. 
  
**DBEAVER에서 하는 것이 아니라 이클립스 콘솔에 출력해보자!**
  
  
다만 이를 위해 DB에 접속하기 위해 연결 정보를 출력해야 한다. 
  
드라이버의 종류, DB를 사용하는 컴퓨터의 IP와 포트번호, DBMS 종류, EMPLOYEE테이블이 존재하는 계정과 그 비밀번호 등을 다 설정해야 한다.
		 
		 * */
	
		
	// 1. JDBC 객체의 주소를 담을 참조용 변수 선언

		/*
		java.sql.Connection => 특정 DBMS와 연결하기 위한 정보를 저장한 객체.
		쉽게 말해 DBEAVER에서 사용하는 DB연결을 해주는 것과 동일한 역할을 하는 객체 
		(ctrl + shift+ n 이후 oracle선택하고 ip주소와 포트번호 XE 설정 유저 이름, 비밀번호를 설정했었다 => 이를 자바에서 하겠다는 것)
		driver settings에서 OJDBC10버전을 설정했었다.
		
		이 모든것을 connection이라는 객체에서 한다 (DB의 서버 주소, 포트번호, DB의 이름, 계정명, 비밀번호)
		*/
		
		Connection conn = null;
		
		
		
		// java.sql.Statement
		
		// 1) SELECT와 같은 명령어를 DB로 전달해야 한다. SQL이라는 객체를 자바에서 DB로 전달하는 역할을 하는 것이 Statement
		// 2) DB에서 SQL을 수행한 결과를 반환 받아오는 역할 역시 Statement가 한다.
		
		Statement stmt = null;
		
		//java.sql.ReulstSet 
		// 결과값 자체를 저장하는 객체
		ResultSet rs = null;
		
		
	
		//  2. DriverManage객체를 이용하여 Connection객체를 생성한다
		
		/*
		 java.sql.DriverManager
		 - DB연결 정보와 JDBC 드라이버를 이용하여 원하는 DB와 연결할 수 있는 Connection 객체를 생성하는 객체이다 
		 
		 
		 DB연결 정보 + JDBC 드라이버이므로 JDBC드라이버를 일단 메모리에 로드해야 함
		 
		 2-1) 오라클이라는 JDBC 드라이버 객체를 메모리에 로드 해두기.
		 
		 */
		
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
		
		// Class.forNamae("패키지이름+클래스이름") => Class라는 클래스에 내장된 정적 메서드로 forName이라는 메서드가 있다 
		// => OracleDriver라는 클래스를 프로그램 시작부터 끝까지 어디서든 쓸 수 있게 한다
		// 클래스를 읽어 메모리에 로드 시킴
		// JVM이 프로그램 동작에 사용할 객체를 생성하는 구문
				
		
		/*
		 orcale.jabc.driver.OracleDriver 
		 => Oracle DBMS 연결 시 필요한 코드가 담겨있는 클래스 
		 => ojbc 라이브러리 내에 존재 (Oracle에서 만들어 제공하는 클래스임)
		 */
		
		//		2-2) DB연결 정보 작성
		
		String type = "jdbc:oracle:thin:@"; // 라는 드라이버의 종류가 있다
		String host = "localhost"; // DB가 깔린 서버 컴퓨터의 IP주소 또는 도메인의 주소를 넣는다. localhost로 현재 컴퓨터를 가리킬 수 있다.
		
		String port = ":1521"; // 프로그램 연결을 위한 port번호
		String dbName= ":XE"; // DBMS 자체의 이름 (XE = eXpress Edition)

		

		// jdbc:oracle:thin:@ localhost :1521 :XE가 만들어짐
		
		// 계정 이름에 대한 정보
		
		String userName = "kh"; // 사용자의 계정
		String password = "kh1234"; // 사용자의 비밀번호
		
		
		// 2-3) 2-2의 데이터베이스 연결 정보와 2-1의 DriverManager를 이용해서 Connection 객체를 생성
		
		
		conn = DriverManager.getConnection(type+host+ port+ dbName, userName, password);
		
		// jdbc:oracle:thin:@ localhost :1521 :XE에다가  계정 + 비밀번호
		
		System.out.println(conn);
		
		
		// 3) select와 같은 SQL문 작성
		
		/*
		 주의사항 
		 => JDBC코드에서 SQL문을 작성할 때 세미콜론은 절대 찍어선 안 됨 (습관성 주의) 
		 다만 자바 코드를 마무리할 때는 세미콜론 써야 한다. 문장의 마지막에 쓰지 말라는 것
		 
		 why? 디비버에서도 세미콜론까지가 하나의 SQL문이기 때문에 항상 찍었었는데?
		 
		 sql 명령어가 올바르게 종료되지 않았다며 exception 발생
		 
		 목표: EMPLOYEE 테이블에서 사번, 이름, 부서코드, 직급코드, 급여, 입사일을 조회한다
		 똑같이 하면 된다
		  */
		
		String sql = "SELECT EMP_ID, EMP_NAME, DEPT_CODE, JOB_CODE, SALARY, HIRE_DATE "
				+ "FROM EMPLOYEE";
		
		
		
		// 4) Statement라는 버스의 역할을 하는 객체를 생성한다
		
		// String sql을 자바에서 태워 버스를 DB로 보내는 문을 생성
		 
		// 디비버에서는 ResultSet을 만들어 Statement버스에 태워 그를 자바로 되돌려보낸다
		
		
		stmt = conn.createStatement();
		
		// 연결된 DB에 SQL을 전달하고 결과를 반환받을 Statement 객체를 현재 생성해둔 것
		
		// 이제 그 버스를 보낸다
		
		// 5. Statement 객체를 이용해서 SQL을 수행한 후 결과를 반환 받는다
		
		rs= stmt.executeQuery(sql); // select를 포함한 이 문장을 인자로 준다
		// 결과로 resultset이 나온다. 이를 반환하게 됨 => select 조회 결과를 저장할 rs에 저장
		
		// 1) ResultSet Statement.excuteQuery(sql문) => sql이 SELECT문일 때 결과로 ResultSet이라는 타입의 객체를 반환한다
		// 2) Statement객체가 제공하는 메서드 중 Statement.executeUpate(sql) 
		// => sql이 DML (INSERT UPDATE DELETE)일 떄 실행하는 메서드 => 이는 int형을 반환한다. 실제로 삽입되거나 수정되거나 삭제된 행의 개수를 반환 (10행이 사라졌으면 10이 반환)
		
		
		/*
		
		 6. 조회 결과가 담겨있는 ResultSet을 출력할 것이다
		 cursor라는 기능을 이용해 한 행씩 접근하여 각 행에 작성된 컬럼의 값을 얻어온다
		 커서는 데이터를 한 줄씩 순차적으로 참조하게 된다
		 boolean ResultSet.next(): 커서를 다음 행으로 이동시킨 후 이동된 행에 값이 있으면 true, 없으면 false를 반환한다
		 맨 처음 호출 시 첫 행부터 시작한다
		 
		 */
		
		
		while(rs.next()) {
			/* 컬럼의 값을 뽑아오는 get***메서드
			 ResultSet.get자료형(컬럼명 | 순서);
			 현재 행에서 지정된 컬럼의 값을 얻어와서 반환된다
			 -> 지정된 자료형의 형태로 값이 반환된다. 물론 get뒤 잘못된 함수이름이 나오면 예외가 발생
			
			java에서 String => db에서 CHAR VARCHAR2
			java에서 int long => db에서 NUMBER (정수만 저장된 컬럼)
			java에서 float double => NUMBER(정수 + 실수)
			그러나 DB에서 DATE 타입은 자바에서 없으므로 java.sql.Date라는 타입을 써야 한다.
			*/
			
			String empid = rs.getString("EMP_ID"); // DB에서 EMP_ID를 VARCHAR2타입으로 얻어와 자바 String타입에 저장하겠다
			String empName = rs.getString("EMP_NAME");
			String deptCode = rs.getString("DEPT_CODE");
			String jobCode = rs.getString("JOB_CODE");
			int salary = rs.getInt("SALARY");
			Date hireDate = rs.getDate("HIRE_DATE");
			
			System.out.printf("사번 : %s / 이름 : %s / 부서코드 : %s / 직급코드 : %s  / 급여 : %d  / 입사일 : %s \n", empid, empName, deptCode, jobCode, salary, hireDate.toString()  );
			// while문이 한 번 돌 때마다 이 출력문을 순서대로 출력하게 된다
			
		}
		
		}
		catch (ClassNotFoundException e) {
			System.out.println("해당 클래스를 찾을 수 없습니다.");

			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			
			/*
			 > 7) 마지막으로 finally를 통해 사용종료된 JDBC의 객체를 반환한다 
				여기까지 conn stmt rs를 반환해야 한다.
				특히 생성된 객체의 순서와 역순으로 close하는 것을 원칙으로 한다
				자원반환을 하지 않으면 DB와 연결된 Connection이 그대로 남아있어 다른 자바 프로그램에 추가적으로 연결되지 못할 수 있음 => DBMS는 최대 커넥션 수가 제한되어 있다/
			 */
			try {
				
				// 만들어진 역순으로 close수행
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
	
	}
	
	

}
