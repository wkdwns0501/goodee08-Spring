package org.zerock.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.dto.ReplyDTO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class ReplyMapperTests {
	
	@Autowired
	private ReplyMapper replyMapper;
	
	@Test
	public void testInsert() {
		Long bno = 1587L;
		
		// 새로운 댓글 생성
		ReplyDTO replyDTO = ReplyDTO.builder()
									.bno(bno)
									.replyText("Reply...")
									.replyer("user1")
									.build();
		
		int insertCount = replyMapper.insert(replyDTO);
		
		log.info("-------------------");
		log.info("insertCount: " + insertCount);
	}
	
	@Test
	public void testDummyInsert() {
		Long[] bnos = {1585L, 1586L, 1587L};
		
		for (Long bno : bnos) {
			for (int i = 0; i < 10; i++) {
				ReplyDTO replyDTO = ReplyDTO.builder()
						.bno(bno)
						.replyText("Sample Reply...")
						.replyer("replyer1")
						.build();
				replyMapper.insert(replyDTO);
			}
		}
	}
	
	@Test
	public void testSelectListOfBoard() {
		Long bno = 1585L;
		replyMapper.selectReplyListOfBoard(bno, 0, 10)
			.forEach(reply -> log.info("reply: " + reply));
	}
	
	@Test
	public void testSelectOne() {
		Long rno = 1L;
		ReplyDTO replyDTO = replyMapper.selectOne(rno);
		
		log.info("-------------------");
		log.info("reply: " + replyDTO);
	}
	
	@Test
	public void testUpdate() {
		ReplyDTO replyDTO = ReplyDTO.builder()
									.rno(1L)
									.replyText("Updated reply")
									.build();
		
		int updateCount = replyMapper.update(replyDTO);
		
		log.info("-------------------");
		log.info("updateCount: " + updateCount);
	}
	
	@Test
	public void testDelete() {
		Long rno = 1L;
		int deleteCount = replyMapper.delete(rno);
		
		log.info("-------------------");
		log.info("deleteCount: " + deleteCount);
	}
	
}
