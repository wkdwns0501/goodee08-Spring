package org.zerock.dto;

import java.util.List;
import java.util.stream.IntStream;

import lombok.Data;

@Data
public class ReplyListPagingDTO {
	
	private List<ReplyDTO> replyDTOList; // 게시물 목록
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
	
	// 페이징 생성자
	public ReplyListPagingDTO(List<ReplyDTO> replyDTOList, int totalCount, int page, int size) {
		this.replyDTOList = replyDTOList;
		this.totalCount = totalCount;
		this.page = page;
		this.size = size;
		
		int tempEnd = (int) (Math.ceil(page / (double) BLOCK_SIZE)) * BLOCK_SIZE;
		this.start = tempEnd - (BLOCK_SIZE - 1);
		this.prev = start != 1;
		
		this.lastPage = (int) Math.ceil(totalCount / (double) size);
		this.end = Math.min(tempEnd, this.lastPage);
		
		this.next = end < this.lastPage;
		
		this.pagePrev = this.page > 1;
		this.pageNext = this.page < this.lastPage;
		
		this.pageNums = IntStream.rangeClosed(start, end).boxed().toList();
	}
	
}
