package org.zerock.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder // 필요한 데이터만을 추가해서 객체를 생성할 수 있는 방법을 제공
@NoArgsConstructor // 요청 파라미터 자동 수집 및 MyBatis에서 SELECT문의 결과를 처리할 때 필요(기본 생성자 + Setter 방식)
@AllArgsConstructor
public class BoardDTO {
	// 게시글 번호(bno)와 같은 식별자(PK) 데이터는 "값이 없음(null)"을 표현해야 하는 경우가 많으므로
	// Long 타입을 사용하는 것이 훨씬 안전하고 좋은 습관
	// (long을 쓰면 0으로 초기화 -> 0번 게시물인지, 번호가 없는건지 모호)
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private LocalDateTime regDate;
	private LocalDateTime updateDate;
	private boolean delFlag;
	
	public String getCreatedDate() {
//		return regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // 커스텀
		return regDate.format(DateTimeFormatter.ISO_DATE); // 정해져있는 패턴
	}
	
	// 빌더 패턴 연습 및 테스트용
	private static void testBuilder() {
		// 기존 생성자 방식: 순서에 맞춰야 하고, 어떤 값이 어떤 필드인지 한눈에 알기 어려움
		BoardDTO boardDTO1 = new BoardDTO(1L, "제목1", "내용1", "작성자1", null, null, false);
		
		// 빌더 패턴 방식: 순서에 상관이 없고, 필드 이름이 명시되므로 코드 이해도가 높아짐
		BoardDTO boardDTO2 = BoardDTO.builder()
									.title("제목2")
									.content("내용2")
									.writer("작성자2")
									.build();
		// @Builder
		// 롬복에서 제공하는 어노테이션으로 빌더 패턴(Builder Pattern)을 자동으로 구현해주는 역할
		// 장점: 생성자 파라미터 순서 기억 불필요, 필드 선택적 생성 가능
		// 사용 상황: 필드가 많거나 생성 과정이 복잡한 객체 생성 시 매우 유용
	}
}
