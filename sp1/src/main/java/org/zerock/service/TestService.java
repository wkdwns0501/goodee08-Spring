package org.zerock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mapper.TestMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional // 트랜잭션 기능이 필요한 메소드에만 적용하는 것이 좋음
// 클래스/인터페이스의 선언 혹은 메소드의 선언부에 적용 가능
// 메소드 레벨 > 클래스/인터페이스 레벨 순으로 우선순위가 결정됨
public class TestService {
	
	private final TestMapper testMapper;
	
	// 트랜잭션 설정 전/후
	public void insertAll(String str) {
		int resultA = testMapper.insertA(str);
		log.info("resultA: " + resultA);
		
		int resultB = testMapper.insertB(str);
		log.info("resultB: " + resultB);
	}
	
}
