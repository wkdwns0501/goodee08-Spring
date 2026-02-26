package org.zerock.controller.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zerock.service.exception.ReplyException;

import lombok.extern.log4j.Log4j2;

// 모든 @RestController에서 발생한 예외를 전역적으로 처리하여 HTTP 상태 코드와 JSON 응답으로 변환하기 위해 사용
// 즉, 전역 예외 처리 컴포넌트
@RestControllerAdvice
//@RestControllerAdvice(basePackages = "org.zerock.reply") // 해당 패키지의 컨트롤러에만 적용
//@RestControllerAdvice(assignableTypes = ReplyController.class) // 특정 컨트롤러만 지정
// 또는 특정 컨트롤러 클래스 내부에 @ExceptionHandler 작성
@Log4j2
public class ReplyControllerAdvice {
	
	// 특정한 타입의 예외가 발생할 때 동작하도록 설정
	@ExceptionHandler(ReplyException.class)
	public ResponseEntity<String> handleReplyError(ReplyException e){
		log.error(e.getMessage());
		
		// ResponseEntity 타입으로 404나 500 등과 같은 HTTP 응답 상태 코드와 함께 에러 메세지를 전송
		return ResponseEntity.status(e.getCode()).body(e.getMessage());
	}
	
}
