package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.dto.ReplyDTO;
import org.zerock.dto.ReplyListPagingDTO;
import org.zerock.mapper.ReplyMapper;
import org.zerock.service.exception.ReplyException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {

	private final ReplyMapper replyMapper;
	
	public void add(ReplyDTO replyDTO) {
		try {
			replyMapper.insert(replyDTO);
		} catch (Exception e) {
			throw new ReplyException(500, "INSERT ERROR");
		}
	}
	
	public ReplyListPagingDTO getListByPage(Long bno, int page, int size) {
		try {
			int skip = (page - 1) * size; 
			List<ReplyDTO> replyDTOList 
				= replyMapper.selectReplyListOfBoard(bno, skip, size);
			int replyCount = replyMapper.replyCountOfBoard(bno);
			return new ReplyListPagingDTO(replyDTOList, replyCount, page, size);
		} catch (Exception e) {
			throw new ReplyException(500, e.getMessage());
		}
	}
	
	public ReplyDTO read(Long rno) {
		try {
			return replyMapper.selectOne(rno);
		} catch (Exception e) {
			throw new ReplyException(404, "NOT FOUND");
		}
	}
	
	public void modify(ReplyDTO replyDTO) {
		try {
			int updateCount = replyMapper.update(replyDTO);
			if (updateCount == 0) {
				throw new ReplyException(404, "NOT FOUND");
			}
		} catch (Exception e) {
			throw new ReplyException(500, "UPDATE ERROR");
		}
	}
	
	public void remove(Long rno) {
		try {
		    int deleteCount = replyMapper.delete(rno);
		    if (deleteCount == 0) {
		      throw new ReplyException(404, "NOT FOUND");
		    }
		  } catch (ReplyException e) {
			  throw e;
		  } catch (Exception e) {
		  	log.error("Reply delete failed. rno={}", rno, e);
    			throw new ReplyException(500, "DELETE ERROR");
		  }
	}
	
}
