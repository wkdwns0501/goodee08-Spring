package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zerock.service.HelloService;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Controller // 해당 클래스의 객체가 스프링에서 빈(Bean)으로 관리되는 대상임을 지정
@RequiredArgsConstructor // 생성자 주입을 위한 롬복 어노테이션, final이 붙은 필드에 대한 생성자를 자동으로 생성
@ToString 
@Log4j2
@RequestMapping("/hello")// '/hello'로 시작하는 요청을 HelloController에서 처리하도록 매핑
public class HelloController {
	
	private final HelloService helloService;
	
	// 롬복이 없을 경우 사용
//	@Autowired // 생성자가 하나만 있을 경우 생략 가능
//	public HelloController(HelloService helloService) {
//		this.helloService = helloService;
//	}
	
//	@RequestMapping(value = "/ex1", method = RequestMethod.GET) // 옛날 방식
	// 스프링 4.3 이후로는 @GetMapping, @PostMapping, @PutMapping, @DeleteMapping 등 사용 가능
	@GetMapping("/ex1")
	public void ex1() {
		log.info("/hello/ex1");
	}
	
	// 메소드가 void인 경우, 스프링이 요청 URL과 동일한 이름의 뷰를 찾음
	// 즉, 사용된 요청 경로 = 뷰 이름 
	
}
