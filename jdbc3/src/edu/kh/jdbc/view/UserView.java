package edu.kh.jdbc.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.dto.User;
import edu.kh.jdbc.service.UserService;

public class UserView {
	
	// 필드
	private Scanner sc = new Scanner(System.in);
	private UserService service = new UserService();
	

	// 메서드
	
	
	/** User 관리 프로그램 메인 메뉴
	 */
	public void mainMenu() {
		
		int input = 0;
		
		do {
			// 하위 단에서 예외가 발생하면 다 여기까지 와라
			
			try {
				
				System.out.println("\n===== User 관리 프로그램 =====\n");
				System.out.println("1. User 등록(INSERT)");
				System.out.println("2. User 전체 조회(SELECT)");
				System.out.println("3. User 중 이름에 검색어가 포함된 회원 조회 (SELECT)");
				System.out.println("4. USER_NO를 입력 받아 일치하는 User 조회(SELECT)");
				System.out.println("5. USER_NO를 입력 받아 일치하는 User 삭제(DELETE)");
				System.out.println("6. ID, PW가 일치하는 회원이 있을 경우 이름 수정(UPDATE)");
				System.out.println("7. User 등록(아이디 중복 검사)");
				System.out.println("8. 여러 User 등록하기");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine(); // 버퍼에 남은 개행문자 제거
				
				switch(input) {
				case 1:insertUser(); break;
				case 2: selectAll(); break;
				case 3: selectName(); break;

				case 4: selectUser(); break;
				case 5: deleteUser(); break;
				case 6: updateName(); break;
				case 7: insertUser2(); break;
				case 8: multiInsertUser(); break;
				
				case 0 : System.out.println("\n[프로그램 종료]\n 감사합니다!"); break;
				default: System.out.println("\n[메뉴 번호만 입력하세요]\n");
				}
				
				System.out.println("\n-------------------------------------\n");
				
			} catch (InputMismatchException e) {
				// Scanner를 이용한 입력 시 자료형이 잘못된 경우
				System.out.println("\n***잘못 입력 하셨습니다***\n");
				
				input = -1; // 잘못 입력해서 while문 멈추는걸 방지
				sc.nextLine(); // 입력 버퍼에 남아있는 잘못된 문자 제거
				
			} catch (Exception e) {
				// 발생되는 예외를 모두 해당 catch 구문으로 모아서 처리
				e.printStackTrace();
			}
			
		}while(input != 0);
		
	} // mainMenu() 종료
	


	

	
	
	/** 8. 여러 User등록하기
	 * 
	 */
	private void multiInsertUser() throws Exception {
		System.out.println("\n======8. 여러 User 등록하기)");
		
		/* 
		 몇명을 등록할지 먼저 물어봐 => 해당 명수에 대해 for문을 돌려
		 첫 USERID입력:
		 => 중복 시 금지 또는 사용가능한 아이디라고 알려 줌
		 첫 PW입력:
		 첫 이름 입력:
	 
		 두번째 USERID입력:
		 => 중복 시 금지 또는 사용가능한 아이디라고 알려 줌
		 두번째 PW입력:
		 두번째 이름 입력:		 
		 * */			
		
		// 둘 중 하나라도 안 되면 롤백
		// 전체 삽입이 다 성공한 뒤에만 커밋
	
		System.out.print("몇 명의 유저를 한번에 등록하실래요?: ");
	
		
	}







	/**
	 * 7. User 등록 (아이디 중복 검사해서 중복 안되면 삽입)
	 */
	private void insertUser2() throws Exception{
		System.out.println("\n======User 등록(아이디 중복 검사)");

		
	
	}







	/**
	 * 아이디와 비밀번호가 일치하는 회원이 존재할 경우 이름을 수정한다.
	 */
	private void updateName() throws Exception {
		System.out.println("ID, PW가 일치하는 회원이 있을 경우 이름 수정(UPDATE)");
	
		
	}







	/** 5. USER_NO를 입력받아 일치하는 User를 삭제
	 * @throws Exception
	 */
	private void deleteUser() throws Exception {
		System.out.println("\n======USER_NO를 입력 받아 일치하는 User 삭제(DELETE)");
		

		
	}







	/**
	 * 4. USER_NO를 입력받아 일치하는 유저를 조회
	 */
	private void selectUser() throws Exception {
		System.out.println("\n======USER_NO를 입력 받아 일치하는 User 조회(SELECT)");
		
	
		
	}







	/**
	 * 3. User 중 이름에 검색어가 포함된 회원 조회
	 * 
	 */
	private void selectName() throws Exception { 
		System.out.println("\n=====User 중 이름에 검색어가 포함된 회원 조회===== ");
		
	}







	/**
	 * 2. User전체 조회 관련 view (SELECT)
	 * 
	 * 
	 */
	private void selectAll() throws Exception {
		System.out.println("\n====== 전체 유저 목록 =====");
	
		
		
	}







	/**
	 * 1. insert하되 7번과는 달리 중복 검사 여부와 상관 없이 삽입
	 * user 등록 관련 view
	 */
	private void insertUser() throws Exception {
		System.out.println("\n====== 1. 유저 등록 =====");
		

	
	}
	
	
	
	
	
	

	/**
	 * JDBCTemplate 사용 테스트
	 */
	public void test() throws Exception {
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

