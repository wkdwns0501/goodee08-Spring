package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.dto.ReplyDTO;

public interface ReplyMapper {
	
	int insert(ReplyDTO replyDTO);
	
	List<ReplyDTO> selectReplyListOfBoard(@Param("bno") Long bno, 
									@Param("skip") int skip, 
									@Param("limit") int limit);
	
	int replyCountOfBoard (@Param("bno") Long bno);
	
	ReplyDTO selectOne(@Param("rno") Long rno);
	
	int update(ReplyDTO replyDTO);
//	int update(@Param("replyText") Long replyText);
	
	int delete(@Param("rno") Long rno);
}
