package org.zerock.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.zerock.dto.BoardDTO;

public interface BoardMapper {
	
	int insert(BoardDTO boardDTO);
	
	List<BoardDTO> selectList();
	
	// 페이징 목록
	// 잘못된 코드
//	List<BoardDTO> selectListByPage(int skip, int count);
	// mapper.xml 에서
//	LIMIT #{skip}, #{count} -- MyBatis 는 skip, count라는 이름을 모름
	// MyBatis에서 여러 값을 전달하려면 @Param, Map 타입 또는 객체(DTO) 타입을 사용
	// 1. @Param 사용 (권장) - 매개변수가 한개 일때는 생략 가능
	// SQL에서 사용할 파라미터 이름을 명시적으로 지정하는 역할, 예: @Param("skip") -> SQL에서 #{skip}
	List<BoardDTO> selectListByPage(@Param("skip") int skip, @Param("count") int count);
	// 2. Map 사용
//	List<BoardDTO> selectListByPage(Map<String, Integer> param);
	// 3. DTO 객체 사용 (권장)
//	List<BoardDTO> selectListByPage(PageRequestDTO dto);
	
	int countForPaging();
	
	BoardDTO selectOne(Long bno);
	
	int update(BoardDTO boardDTO);
	
	int delete(Long bno);
	
}
