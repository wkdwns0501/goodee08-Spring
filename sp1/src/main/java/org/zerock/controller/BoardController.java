package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	
//	private final BoardService boardService;
	
	@GetMapping("/list")
	public void selectList() {
		log.info("/board/list");
	}
	
	// 게시물 등록은 GET 방식과 POST 방식 모두를 이용해서 처리
	// GET: 게시물 등록에 필요한 입력 화면 
	// POST: 게시물 등록 처리 후 게시물 목록으로 리다이렉트
	@GetMapping("/register")
	public void register() {
		log.info("GET /board/register");
	}
	
	@PostMapping("/register")
	public String registerPost() {
		log.info("POST /board/register");
		
		return "redirect:/board/list";
	}
	
	// 게시물 조회는 GET 방식으로 게시물의 번호로 해당 게시물을 Model에 담아서 전달하는 방식으로 구성
	// 경로의 마지막 값을 게시물의 번호로 활용
	@GetMapping("/read/{bno}")
	public String read(@PathVariable("bno") Long bno) {
		log.info("GET /board/read/" + bno);
		return "/board/read";
	}
	
	@GetMapping("/modify/{bno}")
	public String modify(@PathVariable("bno") Long bno) {
		log.info("GET /board/modify/" + bno);
		return "/board/modify";
	}
	
	@PostMapping("/modify")
	public String modifyPost() {
		log.info("POST /board/modify/123");
//		log.info("POST /board/modify/" + bno);
		return "redirect:/board/read/123";
//		return "redirect:/board/read" + bno;
	}
	
	@PostMapping("/remove")
	public String delete() {
		log.info("POST /board/remove/");
		
		return "redirect:/board/list";
	}
}
