package edu.kh.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static edu.kh.jdbc.common.JDBCTemplate.*;
// import static? 지정된 경로에 존재하는 static구문을 모두 얻어와 
// 클래스명.메서드명()이 아닌 그냥 메서드명()만 작성해도 호출이 가능하게 함


import edu.kh.jdbc.dto.User;

public class UserDAO2 {
	// 필드
	
		// DB에 접근 관련된 JDBC 객체 참조 변수를 미리 선언한다
		private Statement stmt =null;
		private PreparedStatement pstmt =null;
		private ResultSet rs =null;
		
		/**
		 * 전달받은 Connection을 이용해서 DB에 접근하여 
		 * 전달받은 아이디, input과 일치하는 User정보에 대해 DB와 조회
		 * @param conn 이란? Service 단에서 생성한 커넥션 객체
		 * @param input 이란? View단에서 생성한 ID이므로 이를 이용해서 DB에 접근해야
		 * @return ID가 일치하는 회원의 User를 되돌려주거나 또는 null을 반환
		 */
		public User selectId(Connection conn, String input) {

			//1. 결과를 저장하는 변수를 선언
			// User를 리턴해야 하는데 일단은 null
			User user = null;
			
			try {
				// 2. SQL문의 작성
				String sql = "SELECT * FROM TB_USER WHERE USER_ID = ?";
				// 물음표를 쓴 것 보니 Prepared를 쓰겠다는 말이겠군
				
				// 3.PreparedStatement객체 생성
				
				pstmt = conn.prepareStatement(sql);
				
				// 4. 물음표 위치에 값을 세팅
				
				pstmt.setString(1,input);
				
				// 5. sql문을 수행 후 결과를 반환 받기
				
				rs = pstmt.executeQuery();
				
				
				/*6. 아이디는 고유한 값일 확률이 높으므로 while자체를 쓸 필요가 없고 if로 처리한다
				 조회 결과가 있을 경우 중복되는 아이디가 없다고 가정
				 한행만 조회되기 때문에 while문보다는 if를 사용하는 것이 효과적이다.
				 */
				
				if(rs.next()) {
					// 한명이라도 있다면, 즉 첫 행에 데이터가 존재한다면 if문안으로 들어가므로 
					// 각 컬럼에 대해 값을 얻어올 수 있다 
					int userNo= rs.getInt("USER_NO");
					String userId= rs.getString("USER_ID");
					String userPw= rs.getString("USER_PW");
					String userName= rs.getString("USER_NAME");
					// 다만 ENROLL_DATE는 좀 특별하지
					
					Date enroDate = rs.getDate("ENROLL_DATE");
					// 일단 java.sql에 있는 값을 가져와서 나중에 문자열로 바꿀게
					
					// 조회된 컬럼 값을 이용하여 User라는 객체를 생성한다. 
					// 아까 만든 User.java에서 만든 dto역할을 하는 클래스가 이래서 필요하다.
					// 실제로 User 클래스에 필드를 보면 여기서 반환하는 타입과 타입이 동일함
					
					user = new User(userNo,userId,userPw,userName, 
							enroDate.toString()
							);
					}

				
			} 
			catch (Exception e) {
			e.printStackTrace();
			}
			finally {
				// 템플릿에서 정의한 것을 통해 이제 사용한 JDBC의 객체의 자원을 close하기로 함
				
				
				// JDBCTemplate.close(rs);
				// JDBCTemplate.close(pstmt);
				
				close(rs); // 여기서 rs를 처음에 null로 선언했으니 여기서 닫아야 한다
				close(pstmt); // 여기서 pstmt를 처음에 null로 선언했으니 여기서 닫아야 한다
				// 다형성 업캐스팅 덕에 stmt를 인자로 한 close함수가 실행되게 된다
				
				// Conncection은 여기서 안 만들었으니 닫을 수 없다.

				
			}
			
			return user;
		}
		


}
