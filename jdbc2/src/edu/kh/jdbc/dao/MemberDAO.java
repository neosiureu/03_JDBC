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

import edu.kh.jdbc.dto.Member;
import edu.kh.jdbc.dto.Todo;
import edu.kh.jdbc.dto.User;


public class MemberDAO {
	// 필드
	
	public int idCheck(Connection conn, String memberId) throws Exception {
		int result = 0;
		String sql = "SELECT COUNT(*) FROM TB_MEMBER WHERE MEMBER_ID = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, memberId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) result = rs.getInt(1);
			rs.close();
		}
		return result;
	}

	public int enroll(Connection conn, Member member) throws Exception {
		int result = 0;
		String sql = "INSERT INTO TB_MEMBER VALUES(SEQ_MEMBER_NO.NEXTVAL, ?, ?, ?, DEFAULT)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			result = pstmt.executeUpdate();
		}
		return result;
	}

	public Member login(Connection conn, String memberId, String memberPw) throws Exception {
		Member member = null;

		String sql = """
			SELECT * FROM TB_MEMBER
			WHERE MEMBER_ID = ? AND MEMBER_PW = ?
		""";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPw);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				member = new Member();
				member.setMemberNo(rs.getInt("MEMBER_NO"));
				member.setMemberId(rs.getString("MEMBER_ID"));
				member.setMemberPw(rs.getString("MEMBER_PW"));
				member.setMemberName(rs.getString("MEMBER_NAME"));
				member.setEnrollDate(rs.getString("ENROLL_DATE"));
			}
		}
		return member;
	}


	public List<Todo> selectTodoList(Connection conn, int memberNo) throws Exception {
		List<Todo> list = new ArrayList<Todo>();

		String sql = """
			SELECT TODO_NO, TODO_TITLE, TODO_COMPLETE, 
			       TO_CHAR(ENROLL_DATE, 'YYYY-MM-DD') AS ENROLL_DATE
			FROM TB_TODO
			WHERE MEMBER_NO = ?
			ORDER BY TODO_NO
		""";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, memberNo);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Todo todo = new Todo();
				todo.setTodoNo(rs.getInt("TODO_NO"));
				todo.setTitle(rs.getString("TODO_TITLE"));
				todo.setComplete(rs.getString("TODO_COMPLETE")); // 그대로 넣기
				todo.setWriteDate(rs.getString("ENROLL_DATE"));

				list.add(todo);
			}
		}

		return list;
	}

	public int insertTodo(Connection conn, Todo todo, int memberNo) throws Exception {
		int result = 0;

		String sql = """
			INSERT INTO TB_TODO
			VALUES (SEQ_TODO_NO.NEXTVAL, ?, ?, DEFAULT, ?)
		""";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, todo.getTitle());
			pstmt.setString(2, todo.getComplete());
			pstmt.setInt(3, memberNo); // FK

			result = pstmt.executeUpdate();
		}

		return result;
	}


}
