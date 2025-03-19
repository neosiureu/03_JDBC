package edu.kh.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dto.User;

public class UserDAO {
	
	private ResultSet rs = null;

	private Statement stmt = null;

	private PreparedStatement pstmt = null;
	
	
	

	public  User selectId(String input, Connection conn) throws Exception {
		// 1. 결과를 저장하는 변수를 선언

		User user = null;

	
		
		
		// 3.PreparedStatement객체 생성
		
		try {
			
			// 2. SQL문의 작성

			String sql = """
					SELECT * FROM TB_USER WHERE USER_ID = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			

			// 4. 물음표 위치에 값을 세팅
			
			pstmt.setString(1, input);
			
			rs  = pstmt.executeQuery();
		
					
			// 5. sql문을 수행 후 결과를 반환 받기 (while또는 if)
			
			if(rs.next()) {
				
				
			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;


	}
	
	
	

}







