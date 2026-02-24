package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.dto.BoardDTO;
import org.zerock.dto.BoardListPagingDTO;
import org.zerock.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
	// 게시물 등록은 GET 방식과 POST 방식 모두를 이용해서 처리
	// GET: 게시물 등록에 필요한 입력 화면 
	// POST: 게시물 등록 처리 후 게시물 목록으로 리다이렉트
	@GetMapping("/add")
	public void add() {
		log.info("GET /board/add");
	}
	
	@PostMapping("/add")
	public String addPost(BoardDTO boardDTO, RedirectAttributes rttr) {
		log.info("POST /board/add");
		// 브라우저에서 전달되는 데이터는 BoardDTO를 통해서 자동 수집
		// 리턴된 값을 전달하기 위해 RedirectAttributes 사용
		rttr.addFlashAttribute("result", boardService.add(boardDTO));
		rttr.addFlashAttribute("action", "add");
		return "redirect:/board/list";
	}
	
	// 일반 목록
//	@GetMapping("/list")
//	public void selectList(Model model) {
//		log.info("/board/list");
//		model.addAttribute("boardList", boardService.getList());
//	}
	
	// 목록 + 페이징
	@GetMapping("/list")
	public void selectListByPage(@RequestParam(name = "page", defaultValue = "1") int page, 
								@RequestParam(name = "size", defaultValue = "10") int size, 
								Model model) {
		log.info("/board/list/" + page + "/size/" + size);
		// 방법 1 (보통의 경우 권장)
		BoardListPagingDTO dto = boardService.getListByPage(page, size);
		model.addAttribute("boardList", dto.getBoardDTOList());
		model.addAttribute("total", dto.getTotalCount());
		// 방법 2 (페이징 관련 값이 많아질수록 좋음)
//		model.addAttribute("boardDTO", boardService.getListByPage(page, size));
		// JSP의 forEach문 items를 ${boardDTO.boardDTOList}로 설정해야함 (작성 해놓음)
	}
	
	// 게시물 조회는 GET 방식으로 게시물의 번호로 해당 게시물을 Model에 담아서 전달하는 방식으로 구성
	// 경로의 마지막 값을 게시물의 번호로 활용
	@GetMapping("/read/{bno}")
	public String read(@PathVariable("bno") Long bno, Model model) {
		log.info("GET /board/read/" + bno);
		// BoardDTO 객체를 model에 담아서 전달
		model.addAttribute("board", boardService.read(bno));
		return "/board/read";
	}
	
	// GET 방식으로 수정하려고 하는 게시물을 확인하고, POST 방식으로 수정/삭제 처리
	@GetMapping("/modify/{bno}")
	public String modify(@PathVariable("bno") Long bno, Model model) {
		log.info("GET /board/modify/" + bno);
		model.addAttribute("board", boardService.read(bno));
		return "/board/modify";
	}
	
	@PostMapping("/modify")
	public String modifyPost(BoardDTO boardDTO) {
		log.info("POST /board/modify/" + boardDTO.getBno());
		boardService.modify(boardDTO);
		return "redirect:/board/read/" + boardDTO.getBno();
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {
		log.info("POST /board/remove");
		boardService.remove(bno);
		rttr.addFlashAttribute("result", bno);
		rttr.addFlashAttribute("action", "remove");
		return "redirect:/board/list";
	}
}
