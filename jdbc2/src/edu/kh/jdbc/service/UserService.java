package edu.kh.jdbc.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dao.UserDAO;
import edu.kh.jdbc.dto.Member;
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



	/**
	 * 1. 유저를 등록하는 서비스
	 * @param user: 입력받은 아이디, 패스워드, 이름이 다 세팅
	 * @return 결과 행의 개수
	 */
	
	public int insertUser(User user) throws Exception {
		/*
		 > 서비스 메서드 = 제일 먼저 커넥션을 항상 생성한다!!
		 * */
		
		Connection conn  = JDBCTemplate.getConnection();
		
		//2) 내가 하고 싶은 로직을 만든다
		
		
		//3) DAO단의 메서드를 호출하고 결과를 반환 받는다.

		
		
		int result =  dao.insertUser(conn,user);
		

		
		//4) DML을 했다면 commit/ rollback
		
		if(result>0) JDBCTemplate.commit(conn);
		else JDBCTemplate.rollback(conn);
		
		//5) 다 쓴 커넥션에 대한 자원 반환 (close메서드 호출)
		JDBCTemplate.close(conn);
		
		
		//6) 결과를 상위 단인 view에게 리턴
		
		
		return result;

	}



	/** 2. 유저 전체를 조회하는 서비스
	 * @return 조회된 유저들이 담긴 모든 리스트
	 * @throws Exception 
	 */
	public List<User> selectAllUser  () throws Exception { 
		// > 서비스 메서드 = 제일 먼저 커넥션을 항상 생성한다!!
		
		Connection conn = JDBCTemplate.getConnection();
		
		//2) 내가 하고 싶은 로직을 만든다

				
		//3) DAO단의 메서드를 호출하고 결과를 반환 받는다.
		List userList = dao.selectAllUser(conn);
		
				
		//4) DML을 했다면 commit/ rollback
				
				
		//5) 다 쓴 커넥션에 대한 자원 반환 (close메서드 호출)
				
		JDBCTemplate.close(conn);

		//6) 결과를 상위 단인 view에게 리턴
			
		
		
		return userList;
	}



	/** 3. User중 이름에 검색어가 포함된 회원 조회 서비스
	 * @param keyword
	 * @return searchList: 조회된 회원의 리스트
	 */
	public List<User> selectName(String keyword) throws Exception {
		// 서비스 메서드 = 제일 먼저 커넥션을 항상 생성한다!!
		Connection conn = JDBCTemplate.getConnection() ;
		
		//2) 내가 하고 싶은 로직을 만든다

		
		//3) DAO단의 메서드를 호출하고 결과를 반환 받는다.
		
		List searchList = dao.selectName(conn, keyword);	
		//4) DML을 했다면 commit/ rollback
		
		
		//5) 다 쓴 커넥션에 대한 자원 반환 (close메서드 호출)
		
		JDBCTemplate.close(conn);
		
		//6) 결과를 상위 단인 view에게 리턴
		return searchList;
	}



	/** 4. USER_NO를 입력받아 정확히 일치하는 유저만 조회
	 * @param input
	 * @return 조회된 회원의 정보 또는 NULL
	 */
	public User selectUser(int input) throws Exception {
		// 서비스 메서드 = 제일 먼저 커넥션을 항상 생성한다!!
		Connection conn = JDBCTemplate.getConnection();
		 
		//2) 내가 하고 싶은 로직을 만든다
		
		
		//3) DAO단의 메서드를 호출하고 결과를 반환 받는다.

		User user = dao.selectUser(conn, input);
		
		
		//4) DML을 했다면 commit/ rollback
		
		
		//5) 다 쓴 커넥션에 대한 자원 반환 (close메서드 호출)
		
		JDBCTemplate.close(conn);

		//6) 결과를 상위 단인 view에게 리턴

		return user;
		
	}



	/** USER_NO를 입력받아 일치하는 User를 삭제하는 서비스
	 * @param result
	 * @return
	 * @throws Exception
	 */
	public int deleteUser(int input) throws Exception {
		// 서비스 메서드 = 제일 먼저 커넥션을 항상 생성한다!!
		 
		Connection conn = JDBCTemplate.getConnection();

		//2) 내가 하고 싶은 로직을 만든다
		
		
		//3) DAO단의 메서드를 호출하고 결과를 반환 받는다.

		int result =  dao.deletetUser(conn,input);

		//4) DML을 했다면 commit/ rollback
		
		// if(result>0) JDBCTemplate.commit(conn);
		// else JDBCTemplate.rollback(conn);
	
		
		//5) 다 쓴 커넥션에 대한 자원 반환 (close메서드 호출)
		JDBCTemplate.close(conn);
		
		//6) 결과를 상위 단인 view에게 리턴
		
		return result;
	}



	/** 아이디와 비밀번호가 일치하는 회원이 존재할 경우 이름을 조회하여 반환하는 서비스
	 * @param id
	 * @param pw
	 * @return
	 */
	public int selectUserNo(String id, String pw) throws Exception {
		// 서비스 메서드 = 제일 먼저 커넥션을 항상 생성한다!!
		Connection conn  = JDBCTemplate.getConnection();
		
		
		//2) 내가 하고 싶은 로직을 만든다
		
		
		//3) DAO단의 메서드를 호출하고 결과를 반환 받는다.

		int userNo = dao.selectUser(conn,id,pw);
		
		//4) DML을 했다면 commit/ rollback
		
		// 일단 select가 맞음
		
		//5) 다 쓴 커넥션에 대한 자원 반환 (close메서드 호출)
		
		JDBCTemplate.close(conn);
		
		//6) 결과를 상위 단인 view에게 리턴

		return userNo;
	}



	/** 유저의 숫자가 일치하는 이름 수정의 서비스 (selectUserNo과 한 세트)
	 * @param userNewName
	 * @param userNo
	 * @return result
	 */
	public int updateName(String userNewName, int userNo) throws Exception {
	
		// 서비스 메서드 = 제일 먼저 커넥션을 항상 생성한다!!
		Connection conn  = JDBCTemplate.getConnection();
		
		//2) 내가 하고 싶은 로직을 만든다
	
		
		//3) DAO단의 메서드를 호출하고 결과를 반환 받는다.
		int result = dao.updateName(conn, userNewName , userNo);

		
		//4) DML을 했다면 commit/ rollback
		
		if(result>0) JDBCTemplate.commit(conn);
		else JDBCTemplate.rollback(conn);
		
		//5) 다 쓴 커넥션에 대한 자원 반환 (close메서드 호출)
		
		JDBCTemplate.close(conn);
		//6) 결과를 상위 단인 view에게 리턴
		return  result;
	}



	/**
	 * @param userId 아이디의 중복을 확인
	 * @return count
	 */
	public int idCheck(String userId) throws Exception {
		// 서비스 메서드 = 제일 먼저 커넥션을 항상 생성한다!!
		
		Connection conn = JDBCTemplate.getConnection();
		
		//2) 내가 하고 싶은 로직을 만든다
		
		
		
		//3) DAO단의 메서드를 호출하고 결과를 반환 받는다.
		int count = dao.idCheck(conn, userId);

		
		//4) DML을 했다면 commit/ rollback
		
		
		//5) 다 쓴 커넥션에 대한 자원 반환 (close메서드 호출)
		
		JDBCTemplate.close(conn);
		
		//6) 결과를 상위 단인 view에게 리턴
		
		
		
		

		return count;
	}



	/** 상위 userList로부터 받은 모든 user 객체를 insert하는 서비스
	 * @param userList
	 * @return
	 */
	public int multiInsertUser(List<User> userList) throws Exception {

		// 서비스 메서드 = 제일 먼저 커넥션을 항상 생성한다!!
		
		Connection conn = JDBCTemplate.getConnection();
		
		// 다중 INSERT방법 (SQL이용한 다중 insert 또는 자바 단에서의 반복을 이용  )
		
		// JDBC이므로 반복문을 이용한 다중 insert
		
		
		
			
		//2) 내가 하고 싶은 로직을 만든다
		
		int count =0; // 다중 insert에서 삽입 성공한 행의 개수를 누적시킨다.
		
		
		
		//3) DAO단의 메서드를 호출하고 결과를 반환 받는다.

		// 반복문을 이용하여 행 하나씩 리스트에서 꺼내서 삽입
		
				for(User u: userList) {
					int result = dao.insertUser(conn, u);
					count+=result; // 성공했으면 하나가 들어나고 아니면 안 늘어남
					
				}
		
		//4) DML을 했다면 commit/ rollback
		
		
		// 전체 삽입 성공시에만 성공
		if(count== userList.size()) {
			JDBCTemplate.commit(conn);
		}
		
		else {
			JDBCTemplate.rollback(conn);
		}
				
		//5) 다 쓴 커넥션에 대한 자원 반환 (close메서드 호출)
		
				
		JDBCTemplate.close(conn);		
		
		
		//6) 결과를 상위 단인 view에게 리턴
		
		return count;
	}



	public int enroll(Member member) {
		// TODO Auto-generated method stub
		return 0;
	}



	





	
	
	
	
	// 서비스 메서드 = 제일 먼저 커넥션을 항상 생성한다!!
	 
		
	//2) 내가 하고 싶은 로직을 만든다
	
	
	//3) DAO단의 메서드를 호출하고 결과를 반환 받는다.

	
	//4) DML을 했다면 commit/ rollback
	
	
	//5) 다 쓴 커넥션에 대한 자원 반환 (close메서드 호출)
	
	
	//6) 결과를 상위 단인 view에게 리턴
}

