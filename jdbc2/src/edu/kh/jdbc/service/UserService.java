package edu.kh.jdbc.service;

import java.sql.Connection;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dao.UserDAO;
import edu.kh.jdbc.dto.User;

// MVC 중 Model은 Service DAO DTO
// Service = 비즈니스 로직을 처리하는 계층 
// => 데이터를 가공원하는 모양으로 변형, 트랜잭션 관리 (커밋과 롤백)
// JDBC템플릿이라고 만든 것에서 Service DAO와 많이 소통하게 된다


public class UserService {
	// 필드
	private UserDAO dao = new UserDAO();

	
	
	/**
	 * 전달받은 아이디와 일치하는 User정보를 반환하는 서비스
	 * @param input (View라는 단에서 입력된 아이디)
	 * @return 아이디가 일치하는 회원 정보가 담긴 User객체를 리턴 
	 * 이는 DAO단에서 받았을 듯 
	 * 다만 없다면 null자체를 리턴
	 */
	public User selectId(String input) {

		/*
		 > 서비스 메서드 = 제일 먼저 커넥션을 항상 생성한다!!

		2) 내가 하고 싶은 로직을 만든다

		3) DAO단의 메서드를 호출하고 결과를 반환 받는다.

		4) DML을 했다면 commit/ rollback

		5) 다 쓴 커넥션에 대한 자원 반환 (close메서드 호출)

		6) 결과를 상위 단인 view에게 리턴
		 * */
		
		Connection conn  = JDBCTemplate.getConnection(); // JDBC 템플릿 할 때 이미 커넥션을 하게 하는 템플릿을 작성해놨음
		// 1번 = 커넥션 생성
		
		// 2번
		
		// 3번 = DAO 메서드를 호출하고 반환
		User user =  dao.selectId(conn,input);
		// 인자로는 커넥션 + 뷰에서 내려받은 input
		
		
		// 4번 = DML 안했으로 생략
		
		// 5번 = 다 쓴 커넥션에 대한 자원을 반환한다
		
		JDBCTemplate.close(conn);
		
		// 6번 = 결과를 view 리턴
		
		return user;
		
	}


}

