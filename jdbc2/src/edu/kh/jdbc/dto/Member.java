package edu.kh.jdbc.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {
	private int memberNo;
	private String memberId;
	private String memberPw;
	private String memberName;
	private String enrollDate;
	
	// getter/setter, toString 등 추가
}