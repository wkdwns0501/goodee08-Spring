package org.zerock.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.dto.ReplyDTO;
import org.zerock.dto.ReplyListPagingDTO;
import org.zerock.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/replies")
public class ReplyController {
	
	private final ReplyService replyService;
	
//	@PostMapping("/")
//	@PostMapping("")
	@PostMapping // 권장
	public ResponseEntity<Map<String, Long>> add(ReplyDTO replyDTO) {
		log.info(replyDTO);
		
		replyService.add(replyDTO);
		
		return ResponseEntity.ok(Map.of("result", replyDTO.getRno()));
	}
	
	@GetMapping("/{bno}/list")
	public ResponseEntity<ReplyListPagingDTO> getReplyListByPage(@PathVariable("bno") Long bno, 
										@RequestParam(name = "page", defaultValue = "1") int page, 
										@RequestParam(name = "size", defaultValue = "10") int size) {
		log.info("bno: " + bno);
		log.info("page: " + page);
		log.info("size: " + size);
		
		return ResponseEntity.ok(replyService.getListByPage(bno, page, size));
	}
	
	@GetMapping("/{rno}")
	public ResponseEntity<ReplyDTO> read(@PathVariable("rno") Long rno) {
		log.info("rno: " + rno);
		
		return ResponseEntity.ok(replyService.read(rno));
	}
	
	@PutMapping("/{rno}")
	public ResponseEntity<Map<String, String>> modify(@PathVariable("rno") Long rno, ReplyDTO replyDTO) {
		log.info("rno: " + rno);
		log.info("replyDTO: " + replyDTO);
		
		replyService.modify(replyDTO);
		
		return ResponseEntity.ok(Map.of("result", "updated"));
	}
	
	@DeleteMapping("/{rno}")
	public ResponseEntity<Map<String, String>> remove(@PathVariable("rno") Long rno) {
		log.info("rno: " + rno);
		
		replyService.remove(rno);
		
		return ResponseEntity.ok(Map.of("result", "deleted"));
	}
	
}
