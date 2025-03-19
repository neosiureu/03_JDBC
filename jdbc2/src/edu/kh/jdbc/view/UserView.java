package edu.kh.jdbc.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dto.User;
import edu.kh.jdbc.service.UserService;

// View: 사용자와 직접 상호작용하는 화면 UI를 담당
// 입력을 받고 결과를 출력하는 역할

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
		int input = sc.nextInt();
		sc.nextLine(); // 혹시 모를 사태를 대비해 int뒤에 Line을 받을 수도 있잖아
		
		// user라는 dto에 셋을 묶고 각 user들을 리스트로 묶어 보내고 싶다
		
		List <User> userList = new ArrayList<User>();
		// 입력받을 회원정보를 저장할 리스트 객체 생성
		
		for(int i=0; i<input; i++) {
			String userId = null; // 입력된 아이디를 저장할 변수
			
			while(true) {
				
				System.out.println((i+1)+"번째 ID입력: ");
				userId = sc.next();
				// 이 안에서 select해서 있으면
				
				// 입력받은 userId가 중복인지 검사하는 서비스(select) 호출 후
				// 결과를 정수로? 반환 받는다. 중복이면 무조건 1이 들어오도록 아니면 0
				int count = service.idCheck(userId);
				// 검사해야하므로 받은 아이디를 넘겨줘야 한다.
				
				if(count==0) {
					System.out.println("사용 가능한 아이디");
					break;
				}
				
				System.out.println("이미 사용중인 아이디입니다. 다시 입력해주세요.");
				
				
			}
			
			// 아이디와 비밀번호를 입력받도록 하고 1번을 배끼자
			System.out.println((i+1)+"번째의 등록할 비밀번호는? : ");
			String userPassword = sc.next();
			
			System.out.println((i+1)+"번째의 등록할 이름은? : ");
			String userName = sc.next();
			
			
			System.out.println("-----------------------------------");

			//위에서 입력받은 값 3개를 한 번에 묶어서 전달하도록 User 객체를 생성하고 userList에 추가
			
			User user = new User();
			user.setUserId(userId);
			user.setUserPw(userPassword);
			user.setUserName(userName);
			
			// userList에 여기서 생성한 것을 add해야 함
			
			userList.add(user);
			
		}// for문의 마지막은 이 괄호입니다
		
		// 위 for를 통해 입력받은 사용자에 대해 insert
		// 결과는 삽입된 행의 숫자를 받도록 한다 => 그거랑 같아야만 commit할 수 있으니까
		int result = service.multiInsertUser(userList);
		
		if(result == userList.size()) {
			System.out.println("전체 삽입을 성공했습니다.");
			
		}
		
		else {
			System.out.println("모종의 이유로 삽입에 실패!");
		}
		
		
		
		
	}







	/**
	 * 7. User 등록 (아이디 중복 검사해서 중복 안되면 삽입)
	 */
	private void insertUser2() throws Exception{
		System.out.println("\n======7. User 등록(아이디 중복 검사)");
		// db에 ID가 이미 있다면 즉 user01과 같은 아이디가 있다면 넘어가도록
		
		String userId = null; // 입력된 아이디를 저장할 변수
		
		while(true) {
			
			System.out.println("ID입력: ");
			userId = sc.next();
			// 이 안에서 select해서 있으면
			
			// 입력받은 userId가 중복인지 검사하는 서비스(select) 호출 후
			// 결과를 정수로? 반환 받는다. 중복이면 무조건 1이 들어오도록 아니면 0
			int count = service.idCheck(userId);
			// 검사해야하므로 받은 아이디를 넘겨줘야 한다.
			
			if(count==0) {
				System.out.println("사용 가능한 아이디");
				break;
			}
			
			System.out.println(userId+" 는 이미 사용중인 아이디입니다. 다시 입력해주세요.");
			
			
		}
		
		// 아이디와 비밀번호를 입력받도록 하고 1번을 배끼자
		System.out.println("등록할 비밀번호는? : ");
		String userPassword = sc.next();
		
		System.out.println("등록할 이름은? : ");
		String userName = sc.next();
		
		// 3개를 담기가 귀찮아서 직접 데이터를 묶자 => User의 dto에 묶자.
		// 그렇게 해서 user 객체를 넘기겠다는 것
		User user = new User();
		user.setUserId(userId);
		user.setUserPw(userPassword);
		user.setUserName(userName);
		
		// 결과 행의 개수
		
	     int result= service.insertUser(user);
	     
	     // 반환된 result 결과에 따라 출력할 내용을 선택
	     
	     if(result>0) System.out.println("\n " + userId + " 라는 아이디로 사용자가 등록되었습니다."); 
	     else System.out.println("등록 실패");
		
	
	}







	/**
	 * 아이디와 비밀번호가 일치하는 회원이 존재할 경우 이름을 수정한다.
	 */
	private void updateName() throws Exception {
		System.out.println("ID, PW가 일치하는 회원이 있을 경우 이름 수정(UPDATE)");
		System.out.print("ID: ");
		String id=  sc.next();
		System.out.print("PW: ");
		String pw =  sc.next();
		
		// 이걸 통해서 일단 select 즉 입력받은 ID, PW가 있는지 먼저 select
		
		// 사용하려는 조건 자체가 유니크한 userNo밖에 없기 때문에 user객체가 필요가 없음
			
		int userNo = service.selectUserNo(id, pw);
		

		
		if (userNo==0) {
			System.out.println("해당 아이디, 비번과 일치하는 사람은 없다. ");
			return ;
		}

	
		// 조회 결과가 있다는 것은 또 입력을 하나 더 받아야 한다는 말
		
		System.out.print("수정해야 할 이름을 입력해주세요: ");
		
		String userNewName = sc.next();
		
		// 이 회원의 이름을 수정하고 서비스를 호출한다		
		// 이미 userNo를 통해 무슨 회원인지를 알 수 있다
		
		
		int result = service.updateName(userNewName, userNo );
		// 몇번을 수정해야 하나? 어떤 이름으로 수정해야 하나?
		
		if(result>0) System.out.println("수정 성공");
		else System.out.println("수정 실패");
		
		
		
		
		
	}







	/** 5. USER_NO를 입력받아 일치하는 User를 삭제
	 * @throws Exception
	 */
	private void deleteUser() throws Exception {
		System.out.println("\n======USER_NO를 입력 받아 일치하는 User 삭제(DELETE)");
		
		System.out.print("삭제하고 싶은 사용자 번호의 입력: ");
		
		int input = sc.nextInt();
		
		// 사용자 번호 = primary key이므로 중복이 절대 없음
		// 일치한다면 한 행, 없으면 행 없음
		// 1행의 조회 결과를 담기 위해서는 User DTO 객체 1개를 사용
		
		
		int result = service.deleteUser(input);
		
		if (result ==0) {
			System.out.println("일치하는 회원 없음");
			return ;
		}
		
		else {
			System.out.println(input+" 번 회원이 삭제 되었습니다. ");
		}

		
	}







	/**
	 * 4. USER_NO를 입력받아 일치하는 유저를 조회
	 */
	private void selectUser() throws Exception {
		System.out.println("\n======USER_NO를 입력 받아 일치하는 User 조회(SELECT)");
		
		System.out.print("사용자 번호의 입력: ");
		
		int input = sc.nextInt();
		
		// 사용자 번호 = primary key이므로 중복이 절대 없음
		// 일치한다면 한 행, 없으면 행 없음
		// 1행의 조회 결과를 담기 위해서는 User DTO 객체 1개를 사용
		User user = service.selectUser(input);
		
		if (user ==null) {
			System.out.println("일치하는 회원 없음");
			return ;
		}
		
		else {
			System.out.println(user);
		}
		
	}







	/**
	 * 3. User 중 이름에 검색어가 포함된 회원 조회
	 * 
	 */
	private void selectName() throws Exception { 
		System.out.println("\n=====User 중 이름에 검색어가 포함된 회원 조회===== ");
		System.out.print("검색어 입력: ");
		String keyword= sc.next();

		//결과가 하나거나 여럿일 수 있으니 User타입으로 리스트가 들어온다고 사고
		
		List<User>searchList = service.selectName(keyword);
		
		if(searchList.isEmpty()) {
			System.out.println("해당 글자를 포함한 사람이 없음");
			return;
			
		}
		
		else {
			for(User user : searchList) {
				System.out.println(user);
			}
			
		}
	}







	/**
	 * 2. User전체 조회 관련 view (SELECT)
	 * 
	 * 
	 */
	private void selectAll() throws Exception {
		System.out.println("\n====== 2. 전체 유저 목록 =====");
		// User뭉탱이가 있을 듯 => ArrayList를 통해 그 제한을 제네릭을 통해 User로 
		
		List <User> userList = service.selectAllUser();
		// select한 후 결과를 전체 반환받고 싶다
		
		// 조회 결과가 없다면?
		
		if(userList.isEmpty()) {
			System.out.println("\n*****조회 결과가 없습니다.*****");
			return ;
		}

		
		else {
			for(User u :userList) {
				System.out.println(u); // 주소값이 안 나오는 이유: lombock으로 toString 재정의해놨음
			}
			
			
		}
		
		
	}







	/**
	 * 1. insert하되 7번과는 달리 중복 검사 여부와 상관 없이 삽입
	 * user 등록 관련 view
	 */
	private void insertUser() throws Exception {
		System.out.println("\n====== 1. 유저 등록 =====");
		
		System.out.println("등록할 ID는? : ");
		String userId = sc.next();
		
		System.out.println("등록할 비밀번호는? : ");
		String userPassword = sc.next();
		
		System.out.println("등록할 이름은? : ");
		String userName = sc.next();
		
		// 3개를 담기가 귀찮아서 직접 데이터를 묶자 => User의 dto에 묶자.
		// 그렇게 해서 user 객체를 넘기겠다는 것
		User user = new User();
		user.setUserId(userId);
		user.setUserPw(userPassword);
		user.setUserName(userName);
		
		// 결과 행의 개수
		
	     int result= service.insertUser(user);
	     
	     // 반환된 result 결과에 따라 출력할 내용을 선택
	     
	     if(result>0) System.out.println("\n " + userId + " 라는 아이디로 사용자가 등록되었습니다."); 
	     else System.out.println("등록 실패");
	
	}
	
	
	
	
	
	

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
