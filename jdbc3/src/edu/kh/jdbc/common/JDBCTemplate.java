package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/*
 > Template이란? 

JDBC에서 필요한 내용을 미리 만들어 둠 

> JDBC템플릿: JDBC와 관련된 것을 미리 작성해둔 클래스

* Connection생성 (Connection Statement ResultSet)
* AutoCommit(false)
* commit rollback
* 각종 자원을 반환하는 close()


****** 중요 **********

어디서라도 JDBCTemplate 클레스를 객체로 만들지 않고도 메서드를 사용할 수 있도록 하기 위해 
모든 메서드를 public static으로 선언
이것이 싱글톤의 법칙
 * */

/**
 * 
 */
public class JDBCTemplate {
	// 회원 관리 프로그램의 공통 사용 부분
	
	private static Connection conn = null;
	// -> static 메서드에서 사용할 static 필드의 선언

	/**
	 * 
	 * 호출 시 Connection 객체를 생성하여 반환하는 메서드 + 오토 커밋을 false로 하는 것 역시 여기에서 하기로 함
	 * @return conn
	 */
	public static Connection getConnection() {
		
		try {
			// 가장 먼저 Connection이 아직 안 만들어진 상태인지 따진다
			// 이전에 참조하던 Connection객체가 존재하고 아직 close()된 상태가 아니라면 새로 만들지 않고 기존 Connection의 반환
			// DB연결 필요시 마다 매번 여기저기서 getConnection을 호출하지만 새로운 커넥션을 매번 만들 필요는 없다.
			// null이 아니라면 , 그리고 아직 반환되어 닫힌 상태가 아니라면
			if (conn !=null && !conn.isClosed()) {
				return conn;
			}
			
			
			// 1. Properties 객체를 생성한다. 즉 xml에 있는 값들을 읽어와야 한다
			Properties prop = new Properties();
			
			// 2. Properties가 제공하는 메서드를 이용하여 driver.xml파일의 내용을 읽어온다
			String filePath = "driver.xml";
			prop.loadFromXML(new FileInputStream(filePath));
					
			// 3. prop에 저장된 값을 이용하여 Connection 객체를 생성한다
			Class.forName(prop.getProperty("driver"));
			
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("userName"), prop.getProperty("password"));
			
			//4. 추가적으로 커넥션이 만들어지자마자 autocommit을 끄자
			conn.setAutoCommit(false);		
			
			
		} 
		catch (Exception e) 
		{
			System.out.println("커넥션 생성 중 예외가 발생");
			e.printStackTrace();
		}
		return conn;

	}
	
	
	
	/**
	 * DML 시 commit rollback할 때 
	 * 클래스명.메서드명을 부르는데 위에서 지금까지 이용하던 커넥션 객체가 있었을 것이다.
	 * 
	 * 가령 Connection conn = JDBCTemplate.getConnection
	 * 
	 * => JDBCTemplate.commit(conn)
	 * 
	 */
	
	
	/** 전달받은 커넥션에서 수행한 SQL을 Commit하는 함수
	 * @param conn
	 */
	public static void commit(Connection conn) {		
		try {
			
			if(conn!=null && !conn.isClosed()) // 널포인터를 가리키거나 닫혀있지 않았을 때만
			conn.commit();			
		} 
		
		catch (Exception e) {
			System.out.println("커밋 중 예외 발생");
			e.printStackTrace();
		}
		
		
		
	}

	
	/**
	 * 전달받은 커넥션에서 수행한 SQL을 롤백하는 메서드
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		try {			
			if(conn!=null && !conn.isClosed()) // 널포인터를 가리키거나 닫혀있지 않았을 때만
			conn.rollback();			
		} 
		
		catch (Exception e) {
			System.out.println("롤백 중 예외 발생");
			e.printStackTrace();
		}
		
	}
	
	// Connection Statement 또는 preparedStatement, ResultSet => 이를 해제하는 함수를 만들자
	
	
	// Connection 해제
	public static void close(Connection conn) {
		try {
			if(conn!=null && !conn.isClosed()) conn.close();
			
		} 
		catch (Exception e) {
			System.out.println("커넥션을 닫는 중에 예외가 발생");
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 *  Statement 해제 => 다형성 업캐스팅 법칙에 따라 PreparedStatement는 만들 필요가 없음
	 *  부모만 인자로 넣어도 자식 객체를 가리킬 수 있음. 
	 *  즉 stmt는 Statement일수도 PreparedStatement일수도
	 * @param stmt
	 */
	


	public static void close(Statement stmt) {
		try {
			if(stmt!=null && !stmt.isClosed() )
			stmt.close();
		} 
		
		catch (Exception e) {
			System.out.println("Statement를 닫는 중에 예외가 발생");
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	public static void close(ResultSet rs) {
		try {
			if(rs!=null && !rs.isClosed()) rs.close();
			
		} 
		catch (Exception e) {
			System.out.println("커넥션을 닫는 중에 예외가 발생");
			e.printStackTrace();
		}
		
	}
	
	
	

}

