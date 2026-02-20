package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.dto.HelloDTO;
import org.zerock.service.HelloService;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString 
@RequiredArgsConstructor // 생성자 주입을 위한 롬복 어노테이션, final이 붙은 필드에 대한 생성자를 자동으로 생성
@Controller // 해당 클래스의 객체가 스프링에서 빈(Bean)으로 관리되는 대상임을 지정
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
		
		helloService.hello1();
	}
	
	// 메소드가 void인 경우, 스프링이 요청 URL과 동일한 이름의 뷰를 찾음
	// 즉, 사용된 요청 경로 = 뷰 이름 
	
	@GetMapping("/ex2")
	public String ex2() {
		log.info("/hello/ex2");
		
		helloService.hello2("Hong Gil-dong");
		
		return "hello/success";
	}
	
	@GetMapping("/ex3")
	public String ex3() {
		log.info("/hello/ex3");
		
		// 브라우저에게 /hello/ex3re로 이동하라고 지시하는 리다이렉트 방식
		return "redirect:/hello/ex3re";
	}
	
	@GetMapping("/ex4")
	public void ex4(@RequestParam(name = "num", defaultValue = "1") int num, 
//					@RequestParam(name = "name", required = false) String name) { 
					@RequestParam("name") String name) { // 속성이 하나인 경우 그냥 문자열로 작성 가능
					// defaultValue는 파라미터 값이 없을 때 사용할 기본값, required는 필수 여부 지정
//					@RequestParam String name) { // 자바 컴파일 옵션 추가 필요 (권장X)
		log.info("/hello/ex4");
		log.info("num: " + num);
		log.info("name: " + name);
	}
	
	@GetMapping("/ex5")
	public void ex5(HelloDTO dto) { // 객체형은 별도의 어노테이션 없이 선언
		log.info("/hello/ex5");
		log.info(dto);
	}
	
	@GetMapping("/ex6")
	public void ex6(Model model) {
		model.addAttribute("name", "Hong Gil-dong");
		model.addAttribute("age", 16);
		// (참고) 뷰 렌더링 직전에 request attribute로 복사됨
	}
	
	@GetMapping("/ex7")
	public String ex7(RedirectAttributes rttr) {
		// 리다이렉트 시 새로운 요청으로 데이터를 전달하는 2가지 방법
		rttr.addAttribute("name", "Hong Gil-dong"); // 쿼리스트링으로 만들어 데이터 전달
		rttr.addFlashAttribute("age", 16); // 1회성 데이터 전달용
		// (참고) 내부적으로 세션에 임시 저장 후, 다음 요청에서 Model에 자동 주입되고 즉시 제거됨
		return "redirect:/hello/ex8";
	}
	
	@GetMapping("/ex8")
	public void ex8() {
		log.info("/hello/ex8");
	}
}
