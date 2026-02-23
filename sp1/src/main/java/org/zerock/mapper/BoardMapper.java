package org.zerock.mapper;

import java.util.List;

import org.zerock.dto.BoardDTO;

public interface BoardMapper {
	
	int insert(BoardDTO boardDTO);
	
	List<BoardDTO> selectList();
	
	BoardDTO selectOne(Long bno);
	
	int update(BoardDTO boardDTO);
	
	int delete(Long bno);
	
}
