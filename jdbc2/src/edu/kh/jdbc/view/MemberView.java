package edu.kh.jdbc.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.dto.Member;
import edu.kh.jdbc.dto.Todo;
import edu.kh.jdbc.dto.User;
import edu.kh.jdbc.service.MemberService;
import edu.kh.jdbc.service.UserService;

public class MemberView {
	// 필드
	private Scanner sc = new Scanner(System.in);
	private MemberService service = new MemberService();
	private Member loginMember = null;
	private MemberService mservice = new MemberService();



	// 메서드
	
	
	
	/** User 관리 프로그램 메인 메뉴
	 */
	public void mainMenu2() {
		
		int input = 0;
		
		do {
			try {
				
				System.out.println("\n===== User 관리 프로그램 =====\n");
				System.out.println("1. 회원가입");
				System.out.println("2. 로그인");
				System.out.println("3. 내 TODO 전체 조회 (번호 제목, 완료여부, 작성일)");
				System.out.println("4. 새로운 TODO의 삽입");
				System.out.println("5. TODO의 제목과 내용을 수정");
				System.out.println("6. TODO의 완료 여부 변경");
				System.out.println("7. TODO의 삭제");
				System.out.println("8. 로그아웃");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine(); // 버퍼에 남은 개행문자 제거
				
				switch(input) {
				case 1:enroll(); break;
				case 2: login(); break;
				case 3: selectTodo(); break;
				case 4: insertTodo(); break;
//				case 5: updateTodo(); break;
//				case 6: updateYN(); break;
//				case 7: deleteTodo(); break;
//				case 8: logout(); break;
	
				case 0 : System.out.println("\n[프로그램 종료]\n"); break;
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


	



	private void enroll() throws Exception {
		System.out.println("\n===== 1. 회원가입 =====");

		String memberId = null;

		while (true) {
			System.out.print("아이디 입력: ");
			memberId = sc.next();

			// 아이디 중복 검사
			int result = service.idCheck(memberId);

			if (result == 0) {
				System.out.println("사용 가능한 아이디입니다.");
				break;
			} else {
				System.out.println("이미 사용 중인 아이디입니다. 다시 입력해주세요.");
			}
		}

		System.out.print("비밀번호 입력: ");
		String memberPw = sc.next();

		System.out.print("이름 입력: ");
		String memberName = sc.next();

		// DTO에 정보 담기
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		member.setMemberName(memberName);

		// INSERT 요청
		int result = service.enroll(member);

		if (result > 0) {
			System.out.println("회원가입이 완료되었습니다!");
		} else {
			System.out.println("회원가입 실패");
		}
	}

	private void login() throws Exception {
		System.out.println("\n===== 2. 로그인 =====");

		System.out.print("아이디 입력: ");
		String memberId = sc.next();

		System.out.print("비밀번호 입력: ");
		String memberPw = sc.next();

		// 서비스 호출
		Member member = service.login(memberId, memberPw);

		if (member != null) {
			System.out.println("[로그인 성공] " + member.getMemberName() + "님 환영합니다!");
			loginMember = member; // 현재 로그인한 회원 정보 저장
		} else {
			System.out.println("[로그인 실패] 아이디 또는 비밀번호가 일치하지 않습니다.");
		}
	}
	
	
	private void selectTodo() throws Exception {
		System.out.println("\n===== 3. 전체 TODO 리스트 출력 =====");

		if (loginMember == null) {
			System.out.println("먼저 로그인 해주세요.");
			return;
		}

		// 로그인한 회원 번호 가져오기
		int memberNo = loginMember.getMemberNo();

		// 서비스 호출 → TODO 리스트 받기
		List<Todo> todoList = mservice.selectTodoList(memberNo);

		// 결과 출력
		if (todoList.isEmpty()) {
			System.out.println("등록된 할 일이 없습니다.");
		} else {
			for (Todo todo : todoList) {
				System.out.printf("[%d] %s / 완료여부: %s / 작성일: %s\n",
					todo.getTodoNo(),
					todo.getTitle(),
					todo.getComplete(),  // ← 그대로 출력
					todo.getWriteDate());
			}
		}
	}
	

	private void insertTodo() throws Exception {
		System.out.println("\n===== 4. TODO 추가 =====");

		if (loginMember == null) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		// 제목 입력
		System.out.print("할 일 제목 입력: ");
		String title = sc.nextLine();

		// 완료 여부 입력 (Y/N만 허용)
		String complete;
		while (true) {
			System.out.print("완료 여부 (Y/N): ");
			complete = sc.next().toUpperCase();

			if (complete.equals("Y") || complete.equals("N")) break;
			System.out.println("Y 또는 N만 입력해주세요.");
		}

		// Todo DTO에 담기
		Todo todo = new Todo();
		todo.setTitle(title);
		todo.setComplete(complete);

		// 현재 로그인한 회원 번호도 전달 필요!
		int memberNo = loginMember.getMemberNo();

		// 서비스 호출
		int result = service.insertTodo(todo, memberNo);

		if (result > 0) {
			System.out.println("할 일이 추가되었습니다!");
		} else {
			System.out.println("할 일 추가 실패");
		}
	}


}
