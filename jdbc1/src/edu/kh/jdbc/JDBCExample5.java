package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample5 {

	public static void main(String[] args) {
		// 아이디, 비밀번호, 이름을 입력받아 TB_USER 테이블이라는 것을 만들어 삽입하기
		/*
		 * > what is PreparedStatment?  
  
java.sql.PreparedStatment
SQL 중간에 ? (위치 홀더)가 존재 => 이를 작성하여 ?자리에 java에서의 변수 값을 대입할 준비가 되어 있는 Statement객체
  
장점1) SQL 작성이 간단해 짐

장점2) ? 위치에 값 대입 시 자료형에 맞는 형태의 리터럴이 자동 대입
  가령 ex4의 예시에서 ''를 문자열 양쪽에 붙일 필요가 없음
  
String을 그냥 대입만 하면 홑따옴표 여부를 신경쓸 필요 없이 그냥 ' '를 붙여준다.
int를 대입하면 Number형으로 자동으로 값만 알아서 들어 감


장점3) 성능과 속도 면에서의 우위
  
PreparedStatement는 Statment의 자식이기에 기존 멤버와 메서드를 그대로 물려받음

> 정보
 Statement도 SELECT INSERT DELETE UPDATE전부 다 가능하다. 
 PrepareStatement역시 쿼리문이 전부 가능하다
		 * */
		
		
		
		
		// 아이디 비번 이름 입력
		
		// 1. JDBC 객체 참조변수를 선언한다
		Connection conn = null;
		PreparedStatement pstmt = null;
		// SELECT가 아니라서 ResultSet이라는 객체는 선언 자체가 불필요
		Scanner sc = null;
		
		try {
			// 2단계
			// drivermanager로 connection타입의 객체를 생성한다.
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:XE";			
			String userName = "kh";
			String password = "kh1234";
	
			conn = DriverManager.getConnection(url, userName, password);
			System.out.println(conn);
			
			
			// 3단계
			sc = new Scanner(System.in);
			
			System.out.println("아이디 입력: ");
			String id = sc.nextLine();
			
			
			System.out.println("비밀번호 입력: ");
			String pw = sc.nextLine();
			
			
			System.out.println("이름 입력: ");
			String name = sc.nextLine();	
			
			
			String sql = """
					INSERT INTO TB_USER 
					VALUES(SEQ_USER_NO.NEXTVAL, ? , ?, ?, DEFAULT )
					""";
			
			
			// 4단계: PreparedStatement 객체를 생성 (위에서 위치 holder를 이용했으므로 반드시)
			// -> 객체생성과 동시에 SQL이 담겨 짐 => 미리 ?라는 위치홀더에 값을 받을 준비를 해야하기 때문
			
			
			// stmt = conn.createStatement(); 이후 stmt.executeQuerey(sql)
		
			// 과 같이 빈 버스를 만들고 나서 보낼 때 승객 sql을 싣는 것이 아니라
			
			pstmt = conn.prepareStatement(sql);
			// 객체 생성과 동시에 승객 sql을 실어야만 한다.
			
			// 5단계: ?위치홀더에 적절한 값을 대입한다
			
			//pstmt.set자료형(?의 순서, 대입값)
			
			pstmt.setString(1,id);
			pstmt.setString(2,pw);
			pstmt.setString(3,name);
			
			/*
			 > DML 수행 전에 해야 할 것: COMMIT과 ROLLBACK 
			 => 자동으로 문 하나마다 COMMIT될 일이 없게 만들어야 한다. 커밋 여부를 개발자가 제어하고 싶기 때문
			 * */
			
			conn.setAutoCommit(false);
			
			
		//	executeUpdate()는 DML => 행의 개수를 반환. 실패시 0을 반환
		//	executeQuery()는 DQL => resultset을 반환
			
			
			// pstmt에서는 Query를 하던 Update를 하던 매개변수 자리를 비워놓아야 한다!
			
			
			// 6단계: SQL수행 후 결과를 반환 받는다

			int result = pstmt.executeUpdate();
			// 7단계: result값에 따른 결과 처리 + 트랜잭션의 제어 처리
			if(result>0) {
				// insert에 만약 성공했다면
				System.out.println(name+"님이 추가되었습니다");
				
				// 삽입에 성공할 시 commit을 반드시 수행하고 싶다.
				conn.commit();
				// DB에 INSERT한 것을 영구반영하겠다
			}
			else {
				System.out.println("추가 실패");
				conn.rollback();
			}

			
			
		} 
		catch (Exception e) {
			
			e.printStackTrace();
		}
		
		finally {
			// 8단계: 사용한 JDBC객체의 자원을 반환한다
			
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
				if(sc!=null) sc.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		
		
		
		
		
	}

}
