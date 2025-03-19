package edu.kh.jdbc.common;

import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Scanner;

public class CreateXMLFile {

	public static void main(String[] args) {
		/*
		 
		xml을 만들어주는 클래스 (extensible markup language)

		xml = 단순화된 데이터 기술 형식

		가령 HTML = Markup 언어
		<>가 있는 것, <>안에 의미를 부여.



		XML 특징: 저장하고 있는 모든 데이터의 형식이 키와 값의 형식이다

		=> 일종의 Map형식이라고 보면 된다.

		XML파일을 만들면 일종의 Map과 비슷하게 사용할 수 있다는 말 Key와 Value모두 String (Properties의 특징과 유사)

		파일을 읽고 쓰기 위한 IO와 관련된 클래스

		Properties 컬렉션 (맵의 후손) => 키와 값이 모두 String

		Properties 객체는 XML파일을 읽고 쓰는 데 특화

		가치 있는 데이터를 저장하기 위해 => 2단계에서 쓰던 db연결정보는 공유되어선 안 되기에 xml파일 내에 두고 깃허브에 올리지 않아야 한다


		 * */

				try {			
					Scanner sc = new Scanner(System.in);
					
					// Properties 객체를 생성하여 xml파일을 다루겠다. 일종의 맵으로 key, value를 한다
					
					Properties prop = new Properties();
					
					System.out.print("생성할 파일의 이름을 입력: ");
					String fileName = sc.next();
					
					// 파일을 밖으로 내보내야 한다
					FileOutputStream fos = new FileOutputStream(fileName+".xml"); // 원래 인자로는 경로가 들어왔어야 함 
					// 경로도 없으니 그냥 현재 프로젝트 하에 파일을 하나 만들겠다는 것
					
					// 이제 properties 객체를 이용하여 실제 xml파일을 생성하자
					
					prop.storeToXML(fos, fileName+" .xml file creat!!!");
					System.out.println(fileName+".xml파일 생성 완료");
					
					
					
					
					
					
					
				} catch (Exception e) {
					System.out.println("xml파일 생성 중 예외 발생");
					e.printStackTrace();
				
				}
				
				
			
	}

	
}

