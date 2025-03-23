package edu.kh.jdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 데이터 전송 객체 (Data Transfer Object)
 값을 묶어서 전달하는 용도의 객체
 
DB에 데이터를 묶어서 전달하거나, DB에서 조회한 결과를 가져올 때 사용

데이터 교환을 위한 객체로 주로 사용
 
구체적으로?
DB의 특정 테이블의 한 행의 데이터를 저장할 수 있는 형태로 클래스를 작성하기로 한다
 
 * */

// lombok: 자바 코드에서 반복적으로 작성해야할 코드를 자동으로 완성해주는 라이브러리


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Todo {
	private int todoNo;
	private String title;
	private String  complete;
	private String writeDate;
	
	
	/*
	 -> enrollDate를 왜 java.sql.Date라는 객체로 타입을 만들지 않고 String으로 했나?
	 답: DB조회 시 날짜 데이터를 원하는 형태의 문자열로 변환하여 조회할 예정이기 때문
	 가령 TO_CHAR(날짜,'YYYY-MM-DD')
	 */
	
	
	
}
