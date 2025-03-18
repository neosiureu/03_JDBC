package edu.kh.jdbc.view;

import java.util.Scanner;

import edu.kh.jdbc.dto.User;
import edu.kh.jdbc.service.UserService;

// View: 사용자와 직접 상호작용하는 화면 UI를 담당
// 입력을 받고 결과를 출력하는 역할

public class UserView {
	
	// 필드
	private Scanner sc = new Scanner(System.in);
	private UserService service = new UserService();
	

	// 메서드
	
	/**
	 * JDBCTemplate 사용 테스트
	 */
	public void test() {
		// 입력된 ID와 일치하는 USER정보 조회
		System.out.print("ID 입력: " );
		String input  = sc.next();
		
		// 서비스를 호출 후 결과를 반환 받는다
		User user= service.selectId(input);
		// input을 전달해야 오른쪽으로 진행되며 DB와 비교할 수 있음
		// DB에서 받아올 타입을 User로 지정
		
		if(user==null) {
			
			System.out.println("없어용");
		}
		else {
		
			System.out.println(user);
		}
	}
	
	
	
}
