package edu.kh.jdbc.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dao.MemberDAO;
import edu.kh.jdbc.dto.Member;
import edu.kh.jdbc.dto.Todo;


// MVC 중 Model은 Service DAO DTO
// Service = 비즈니스 로직을 처리하는 계층 
// => 데이터를 가공원하는 모양으로 변형, 트랜잭션 관리 (커밋과 롤백)
// JDBC템플릿이라고 만든 것에서 Service DAO와 많이 소통하게 된다

public class MemberService {
	private MemberDAO dao = new MemberDAO();


	/*
	 > 서비스 메서드 = 제일 먼저 커넥션을 항상 생성한다!!

	2) 내가 하고 싶은 로직을 만든다

	3) DAO단의 메서드를 호출하고 결과를 반환 받는다.

	4) DML을 했다면 commit/ rollback

	5) 다 쓴 커넥션에 대한 자원 반환 (close메서드 호출)

	6) 결과를 상위 단인 view에게 리턴
	 * */

	public int idCheck(String memberId) throws Exception {
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.idCheck(conn, memberId);
		JDBCTemplate.close(conn);
		return result;
	}

	public int enroll(Member member) throws Exception {
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.enroll(conn, member);
		if (result > 0) JDBCTemplate.commit(conn);
		else JDBCTemplate.rollback(conn);
		JDBCTemplate.close(conn);
		return result;
	}

	public Member login(String memberId, String memberPw) throws Exception {
		Connection conn = JDBCTemplate.getConnection();

		Member member = dao.login(conn, memberId, memberPw);

		JDBCTemplate.close(conn);
		return member;
	}

	public List<Todo> selectTodoList(int memberNo) throws Exception {
		Connection conn = JDBCTemplate.getConnection();

		List<Todo> list = dao.selectTodoList(conn, memberNo);

		JDBCTemplate.close(conn);
		return list;
	}

	public int insertTodo(Todo todo, int memberNo) throws Exception {
		Connection conn = JDBCTemplate.getConnection();

		int result = dao.insertTodo(conn, todo, memberNo);

		if (result > 0) JDBCTemplate.commit(conn);
		else JDBCTemplate.rollback(conn);

		JDBCTemplate.close(conn);
		return result;
	}
	
	
}
