package edu.kh.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static edu.kh.jdbc.common.JDBCTemplate.*;
// import static? 지정된 경로에 존재하는 static구문을 모두 얻어와 
// 클래스명.메서드명()이 아닌 그냥 메서드명()만 작성해도 호출이 가능하게 함

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dto.User;

//MVC 중 Model은 Service DAO DTO
// 데이터가 저장된 곳에 접근하는 용도의 객체
// -> DB에 접근하여 Java에서 원하는 결과를 얻기 위해 SQL을 수행하고 결과를 반환받는 역할

public class UserDAO {
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

	/**
	 * @param conn: DB연결 정보가 담겨있는 Connection 객체
	 * @param user: 입력 받은 id, pw, name이 세팅된 객체
	 * @return Insert한 결과 행의 개수
	 */
	public int insertUser(Connection conn, User user) throws Exception {
		// 1. 결과를 저장하는 변수를 선언

		int result = 0;
		
		try {

			// 2. SQL문의 작성
			// SQL수행중에 예외가 발생하긴 할텐데 
			// catch로 처리하지 않고 throws를 이용해 호출부로 던짐
			
			String sql = """
					INSERT INTO TB_USER 
VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT ) 
					
					""";
			//안에 세미콜론 없음 주의. 밖에는 있어야
			
			// 3.PreparedStatement객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. 물음표 위치에 값을 세팅
			
			pstmt.setString(1, user.getUserId()); // 이미 입력했던 ID값이 들어옴
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());
			
			
			// 5. sql문을 수행 후 결과를 반환 받기
			
			result = pstmt.executeUpdate(); // 버스에 sql문을 태워 보냈으므로 
			//그 결과를 result로 전달하여 반환할 수 있도록
			
		} 
		
		finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	/** 2. User 전체 조회하는 DAO
	 * @param conn
	 * @return 유저 객체가 담긴 리스트
	 */
	public List selectAllUser(Connection conn) throws Exception {
		// 1. 결과를 저장하는 변수를 선언
		List<User> userList = new ArrayList<User>();
		
		try {
			// 2. SQL문의 작성
			
			// String sql = "SELECT * FROM TB_USER"  
			// enroll date를 변환하기 위해 *을 쓰지 않는다
			
			String sql = """
					SELECT USER_NO, USER_ID, USER_PW, TB_USER.USER_NAME, TO_CHAR(ENROLL_DATE, ' YYYY "년" MM"월" DD "일" ' ) ENROLL_DATE  FROM TB_USER ORDER BY USER_NO
					""";
			
			
			// 3.PreparedStatement객체 생성
					
			pstmt  =conn.prepareStatement(sql);
			// 4. 물음표 위치에 값을 세팅
							
			// 5. sql문을 수행 후 결과를 반환 받기
			rs = pstmt.executeQuery();
			
			// 6. 조회 결과를 한 행씩 접근하여 얻어온다
			while (rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				// 위 sql문을 설정할 때 이미 SELECT문에서 
				// TO_CHAR로 변환한 상태로 sql문이 작성되었기 때문에 그냥 String으로 받아도 됨
				
				// User 객체를 생성하여 일단 세팅하고
				User user = new User(userNo, userId, userPw, userName, enrollDate);
				
				userList.add(user);
				// 일단 세팅하고 그를 ArrayList에 담아야함

			}			
			
			// select 시 잘 모르면 while, 한 행만 나오면 if
			
		} 

		finally {
		 JDBCTemplate.close(rs);
		 JDBCTemplate.close(pstmt);
		}
		
		
	
				

		

		
		return userList ;
	}

	

	public List selectName(Connection conn, String keyword) throws Exception {
		// 1. 결과를 저장하는 변수를 선언
		
		List <User>  serachList  = new ArrayList<User>();
		
		
		try {
			
			// 2. SQL문의 작성
			
			String sql = """
					
					SELECT USER_NO, USER_ID, USER_PW, TB_USER.USER_NAME, TO_CHAR(ENROLL_DATE, ' YYYY "년" MM"월" DD "일" ' ) ENROLL_DATE  
					FROM TB_USER WHERE USER_NAME LIKE '%' || ? || '%'  ORDER BY USER_NO	
					""";
			
			// 물음표가 진짜 물음표가 아님을 강조하기 위해
			
			
			// 3.PreparedStatement객체 생성
			pstmt = conn.prepareStatement(sql);
			
			
			// 4. 물음표 위치에 값을 세팅
			
			pstmt.setString(1,keyword);
			

					
			// 5. sql문을 수행 후 결과를 반환 받기
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				User user = new User(userNo, userId, userPw, userName, enrollDate);
				serachList.add(user);
				
			}
			
			
			
		} 
		
		finally 
		{
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			
			
		}
		
		


		
		return serachList ;
	}

	
	
	/** 4. USER_NO를 입력받아 정확히 일치하는 유저만 조회
	 * @param conn
	 * @param input
	 * @return User객체이지만 null일 수 있음
	 */
	public User selectUser(Connection conn, int input) throws Exception {

		// 1. 결과를 저장하는 변수를 선언
		User user = null;


		
		try {
			// 2. SQL문의 작성
			
			String sql = """
					SELECT USER_NO, USER_ID, USER_PW, TB_USER.USER_NAME, TO_CHAR(ENROLL_DATE, ' YYYY "년" MM"월" DD "일" ' ) ENROLL_DATE  
					FROM TB_USER WHERE USER_NO = ? 
					""";
			// 3.PreparedStatement객체 생성
			
			pstmt = conn.prepareStatement(sql);
			
			
			
			// 4. 물음표 위치에 값을 세팅
			
			pstmt.setInt(1, input);
					
			// 5. sql문을 수행 후 결과를 반환 받기
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				user = new User(userNo, userId, userPw, userName, enrollDate);
				
			}
			
			
			

			
		} 
		
		finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			
		}
		
		
		
		
		return user;
		
	}


	/** USER_NO를 입력받아 일치하는 User를 삭제하는 DAO
	 * @param conn
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public int deletetUser(Connection conn, int input) throws Exception {
		// 1. 결과를 저장하는 변수를 선언
		
		int result=0;
		
		try {
		// 2. SQL문의 작성
		
		String sql = """
				DELETE FROM TB_USER WHERE USER_No = ?
				""";
		// 3.PreparedStatement객체 생성
		
		pstmt = conn.prepareStatement(sql);

		
		// 4. 물음표 위치에 값을 세팅
		

		pstmt.setInt(1, input);
		
		
		
		// 5. sql문을 수행 후 결과를 반환 받기 (while또는 if)
		
		result = pstmt.executeUpdate();
		}
		
		finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	

	/** ID와 PW가 일치하는 회원의 USER_NO만을 반환하는 DAO
	 * @param conn
	 * @param id
	 * @param pw
	 * @return UserNo
	 */
	public int selectUser(Connection conn, String id, String pw) throws Exception {
		int userNo =0;

		// 1. 결과를 저장하는 변수를 선언

	
		
		try {
			// 2. SQL문의 작성
			
			String sql  = """
				 SELECT USER_NO FROM TB_USER WHERE USER_ID = ? AND USER_PW = ?
					""";
			
			// 3.PreparedStatement객체 생성
			
			pstmt = conn.prepareStatement(sql);
			// 4. 물음표 위치에 값을 세팅

			pstmt.setString(1,id );
			pstmt.setString(2,pw );
			
			rs = pstmt.executeQuery();

			// 5. sql문을 수행 후 결과를 반환 받기 (while또는 if)

			// 조회된 행은 무조건 한 행
			if(rs.next()) {
				userNo = rs.getInt("USER_NO");
			}
			
		} 
		
		finally {
			close(rs);
			close(pstmt);
		}
		
		return userNo;
	}

	
	
	/** userNo가 일치할 경우 일치하는 회원의 이름을 수정하는 DAO
	 * @param conn
	 * @param userNewName
	 * @param userNo
	 * @return
	 */
	public int updateName(Connection conn, String userNewName, int userNo) throws Exception {
		
		int result =0;
		
		try {
			String sql = """ 
					UPDATE TB_USER SET USER_NAME = ?  WHERE USER_NO = ?
					""";
			
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userNewName);
			pstmt.setInt(2, userNo);

			result = pstmt.executeUpdate();
			
		} 

		finally {
			close(pstmt);
		}
		

		return result;
	}

	/** 아이디를 중복인지 확인해주는 DAO로 0이나 1이라는 count를 반환
	 * @param conn
	 * @param userId
	 * @return
	 */
	public int idCheck(Connection conn, String userId) throws Exception {
		int count =0;

		// 1. 결과를 저장하는 변수를 선언
		
		try {
			// 2. SQL문의 작성
			
			String sql ="""
					SELECT COUNT(*) FROM TB_USER WHERE USER_ID = ?
					""";
			
			
			// count(*)를 이용해야 반환 값이 하나인가 0인가가 나옴
			
			
			// 3.PreparedStatement객체 생성
			
			pstmt = conn.prepareStatement(sql);
			
			// 4. 물음표 위치에 값을 세팅

			pstmt.setString(1, userId);
								
			
			
			// 5. sql문을 수행 후 결과를 반환 받기 (while또는 if)
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
				// 조회된 컬럼 순서번호를 이용해 1번의 컬럼 값을 얻어 온다
				
			}
			
			
			
		} 
		
		finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}

		
		
		
		
		return count;
	}
	

		
				
		
	



	
	


	
			// 1. 결과를 저장하는 변수를 선언
	
			// 2. SQL문의 작성
			
			// 3.PreparedStatement객체 생성
			
			// 4. 물음표 위치에 값을 세팅
					
			// 5. sql문을 수행 후 결과를 반환 받기 (while또는 if)
			

	
	
	
	
	
	// 메서드
	
	

	
	
}
