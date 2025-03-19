package edu.kh.jdbc.service;

import java.sql.Connection;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dao.UserDAO;
import edu.kh.jdbc.dto.User;

public class UserService {
	private UserDAO dao = new UserDAO();

	public User selectId(String input) throws Exception{
		//서비스 메서드 = 제일 먼저 커넥션을 항상 생성한다!!
		Connection conn = JDBCTemplate.getConnection();

		//2) 내가 하고 싶은 로직을 만든다
		
		
		//3) DAO단의 메서드를 호출하고 결과를 반환 받는다.

		User user = dao.selectId(input,conn);
		
		
		//4) DML을 했다면 commit/ rollback
		
		
		//5) 다 쓴 커넥션에 대한 자원 반환 (close메서드 호출)
		
		
		//6) 결과를 상위 단인 view에게 리턴

		return null;
	}
	
	

}
	//서비스 메서드 = 제일 먼저 커넥션을 항상 생성한다!!


	//2) 내가 하고 싶은 로직을 만든다
	
	
	//3) DAO단의 메서드를 호출하고 결과를 반환 받는다.

	
	//4) DML을 했다면 commit/ rollback
	
	
	//5) 다 쓴 커넥션에 대한 자원 반환 (close메서드 호출)
	
	
	//6) 결과를 상위 단인 view에게 리턴