package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

// 문제: 아이디, 비밀번호 이름을 유저로부터 입력받아 아이디와 비밀번호가 일치하는 사용자가 존재한다면 그 이름을 수정한다. 즉 UPDATE구문을 사용하라는 이야기
// PreparedStatement를 이용하고 commit과 rollback을 성공 실패시에 각각 해야 하며 성공 시 '수정성공' 실패 시 '아이디 또는 비밀번호 불일치'라는 메시지를 출력해야 한다

public class JDBCExmaple6 {

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
			// ResultSet rs = null;
			Scanner sc = null;
			
			try {
				// 2단계
				// drivermanager로 connection타입의 객체를 생성한다.
				Class.forName("oracle.jdbc.driver.OracleDriver"); // 전제1) 오라클 드라이버를 메모리에 로드하는 과정
				
				
				String url = "jdbc:oracle:thin:@localhost:1521:XE"; // DB에 연결할 수 있는 주소
				String userName = "kh";
				String password = "kh1234";	
				conn = DriverManager.getConnection(url, userName, password); // 전제2) DB의 연결정보를 설정 => 커넥션에 대한 객체를 반환
				
				System.out.println(conn);
				
				
				// 3단계
				conn.setAutoCommit(false);
				
				sc = new Scanner(System.in);
				
				System.out.print("아이디 입력: ");
				String id = sc.nextLine();				
				
				System.out.print("비밀번호 입력: ");
				String pw = sc.nextLine();
								
				System.out.print("이름 입력: ");
				String name = sc.nextLine();	
				
				
				String sql = """
						UPDATE TB_USER 
						SET USER_NAME = ? 
						WHERE USER_ID = ? AND USER_PW = ? 
						""";
				
				
				// 4단계: PreparedStatement 객체를 생성 (위에서 위치 holder를 이용했으므로 반드시)
				// -> 객체생성과 동시에 SQL이 담겨 짐 => 미리 ?라는 위치홀더에 값을 받을 준비를 해야하기 때문
				
				
				// stmt = conn.createStatement(); 이후 stmt.executeQuerey(sql)
			
				// 과 같이 빈 버스를 만들고 나서 보낼 때 승객 sql을 싣는 것이 아니라
				
				pstmt = conn.prepareStatement(sql);
				// 객체 생성과 동시에 승객 sql을 실어야만 한다.
				
				// 5단계: ?위치홀더에 적절한 값을 대입한다
				
				//pstmt.set자료형(?의 순서, 대입값)
				
				pstmt.setString(1,name);
				pstmt.setString(2,id);
				pstmt.setString(3,pw);

				
				/*
				 > DML 수행 전에 해야 할 것: COMMIT과 ROLLBACK 
				 => 자동으로 문 하나마다 COMMIT될 일이 없게 만들어야 한다. 커밋 여부를 개발자가 제어하고 싶기 때문
				 * */
				
				
				
			//	executeUpdate()는 DML => 행의 개수를 반환. 실패시 0을 반환
			//	executeQuery()는 DQL => resultset을 반환
				
				
				// pstmt에서는 Query를 하던 Update를 하던 매개변수 자리를 비워놓아야 한다!
				
				// 6단계: SQL수행 후 결과를 반환 받는다
				int result = pstmt.executeUpdate();
				// excecuteQuery일 경우 resultSet, 즉 뭔진 몰라도 행과 열의 교차점 값에 해당하는 객체를 반환한다 => 인자로 sql로 들어가야 함
				// excecuteUpdate일 경우 숫자만 반환 => 인자가 비어있어야 함

				
				
				// 7단계: result값에 따른 결과 처리 + 트랜잭션의 제어 처리
				if(result>0) {
					// insert에 만약 성공했다면
					System.out.println(name+"님을 수정성공");
					
					// 삽입에 성공할 시 commit을 반드시 수행하고 싶다.
					conn.commit();
					// DB에 INSERT한 것을 영구반영하겠다
				}
			
				else {
					System.out.println("수정 실패");
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
