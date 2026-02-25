package org.zerock.dto;

import java.util.List;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardListPagingDTO {
	
	// BoardService의 기존 selectList()는 단순하게 목록 데이터만 전달했기 때문에 
	// List<BoardDTO>로 처리가 가능했지만 전체 데이터의 수가 같이 전달되기 때문에 
	// 두 종류의 데이터를 같이 담을 수 있는 BoardListPagingDTO라는 새로운 DTO를 생성해서 처리
	
	private List<BoardDTO> boardDTOList; // 게시물 목록
	private int totalCount; // 삭제되지 않은 전체 데이터 개수
	private int page; // 현재 페이지 번호
	private int size; // 한 페이지당 출력되는 데이터 개수
	
	// 페이지 번호의 처리 계산
	private static final int BLOCK_SIZE = 10; // 페이지 번호 블록 크기(blockSize)
	private int start; // 블록의 시작 번호
	private int end; // 블록의 마지막 번호
	private int lastPage; // 전체 마지막 페이지 번호
	private boolean prev;
	private boolean next;
	private boolean pagePrev; // 현재 페이지 기준 이전 페이지 존재 여부
	private boolean pageNext; // 현재 페이지 기준 다음 페이지 존재 여부
	private List<Integer> pageNums; // 예: 2번째 블록이면 Integer타입 11 ~ 20
	
	// 검색 관련 추가
	private String types;
	private String keyword;
	
	// 페이징 생성자
	public BoardListPagingDTO(List<BoardDTO> boardDTOList, int totalCount, int page, int size) {
		this.boardDTOList = boardDTOList;
		this.totalCount = totalCount;
		this.page = page;
		this.size = size;
		
		// start 계산을 위한 tempEnd 페이지
		// tempEnd: 현재 블록의 마지막 페이지(임시)
		// 여기서 10.0, 10 은 페이지 블록 크기(blockSize)
		int tempEnd = (int) (Math.ceil(page / (double) BLOCK_SIZE)) * BLOCK_SIZE;
		this.start = tempEnd - (BLOCK_SIZE - 1);
		this.prev = start != 1;
		
		// 정확한 end 페이지 번호 계산
		// lastPage: 전체 마지막 페이지
		// JSP에서 페이지 단위 이동(<, >) 조건에도 사용할 수 있게 필드로 보관
		this.lastPage = (int) Math.ceil(totalCount / (double) size);
		// 예: 위에서 계산한 start가 11이면 tempEnd는 20이 나옴
		// (상황1) 122 / 10.0 = 12.2 => 13 (이때는 lastPage 적용)
		// (상황2) 225 / 10.0 = 22.5 => 23 (이때는 tempEnd 적용)
		this.end = Math.min(tempEnd, this.lastPage);
		
		// 현재 블록의 마지막 페이지(end)가 전체 마지막 페이지(lastPage)보다 작으면 다음 블록 존재
		this.next = end < this.lastPage;
		
		// 페이지 단위 이동 버튼(<, >) 표시 여부
		this.pagePrev = this.page > 1;
		this.pageNext = this.page < this.lastPage;
		
		// 화면에 출력할 번호들 준비
		this.pageNums = IntStream.rangeClosed(start, end).boxed().toList();
	}
	
	// 페이징 + 검색 생성자
	public BoardListPagingDTO(List<BoardDTO> boardDTOList, int totalCount, 
							  int page, int size, String types, String keyword) {
		this.boardDTOList = boardDTOList;
		this.totalCount = totalCount;
		this.page = page;
		this.size = size;
		// 검색 관련 추가
		this.types = types;
		this.keyword = keyword;
		
		// start 계산을 위한 tempEnd 페이지
		// tempEnd: 현재 블록의 마지막 페이지(임시)
		// 여기서 10.0, 10 은 페이지 블록 크기(blockSize)
		int tempEnd = (int) (Math.ceil(page / (double) BLOCK_SIZE)) * BLOCK_SIZE;
		this.start = tempEnd - (BLOCK_SIZE - 1);
		this.prev = start != 1;
		
		// 정확한 end 페이지 번호 계산
		// lastPage: 전체 마지막 페이지
		// JSP에서 페이지 단위 이동(<, >) 조건에도 사용할 수 있게 필드로 보관
		this.lastPage = (int) Math.ceil(totalCount / (double) size);
		// 예: 위에서 계산한 start가 11이면 tempEnd는 20이 나옴
		// (상황1) 122 / 10.0 = 12.2 => 13 (이때는 lastPage 적용)
		// (상황2) 225 / 10.0 = 22.5 => 23 (이때는 tempEnd 적용)
		this.end = Math.min(tempEnd, this.lastPage);
		
		// 현재 블록의 마지막 페이지(end)가 전체 마지막 페이지(lastPage)보다 작으면 다음 블록 존재
		this.next = end < this.lastPage;
		
		// 페이지 단위 이동 버튼(<, >) 표시 여부
		this.pagePrev = this.page > 1;
		this.pageNext = this.page < this.lastPage;
		
		// 화면에 출력할 번호들 준비
		this.pageNums = IntStream.rangeClosed(start, end).boxed().toList();
	}
	
}
