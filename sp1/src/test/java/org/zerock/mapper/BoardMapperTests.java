package org.zerock.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.dto.BoardDTO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardMapperTests {
	
	@Autowired
	private BoardMapper boardMapper;

	@Test
	public void testInsert() {
		BoardDTO boardDTO = BoardDTO.builder()
				.title("title1")
				.content("content1")
				.writer("user01")
				.build();
		
		int insertCount = boardMapper.insert(boardDTO);
		
		log.info("-------------------");
		log.info("insertCount: " + insertCount);
	}
	
	@Test
	public void testInsert2() {
		BoardDTO boardDTO = BoardDTO.builder()
				.title("title1")
				.content("content1")
				.writer("user01")
				.build();
		
		int insertCount = boardMapper.insert(boardDTO);
		
		log.info("-------------------");
		log.info("insertCount: " + insertCount);
		
		log.info("===================");
		log.info("bno: " + boardDTO.getBno());
	}
	
	@Test
	public void testSelectList() {
//		boardMapper.selectList().stream() // 스트림은 중간 연산이 필요한 경우 사용
//			.forEach(board -> log.info("board: " + board));
		
		boardMapper.selectList()
			.forEach(board -> log.info("board: " + board));
		
//		boardMapper.selectList().forEach(log::info); // 축약형
	}
	
	@Test
	public void testSelectOne() {
		Long bno = 2L;
		BoardDTO boardDTO = boardMapper.selectOne(bno);
		
		log.info("-------------------");
		log.info("board: " + boardDTO);
	}
	
	@Test
	public void testUpdate() {
		BoardDTO boardDTO = BoardDTO.builder()
				.bno(5L)
				.title("updated title")
				.content("updated content")
				.delFlag(false)
				.build();
		
		int updateCount = boardMapper.update(boardDTO);
		
		log.info("-------------------");
		log.info("updateCount: " + updateCount);
	}
	
	@Test
	public void testDelete() {
		Long bno = 6L;
		int deleteCount = boardMapper.delete(bno);
		
		log.info("-------------------");
		log.info("deleteCount: " + deleteCount);
	}
	
}
