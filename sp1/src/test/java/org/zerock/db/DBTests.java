package org.zerock.db;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.log4j.Log4j2;

@ExtendWith(SpringExtension.class) // JUnit(Jupiter)에 Spring TestContext Framework를 사용하도록 확장 등록
// 이게 없으면 그냥 순수 JUnit 테스트일 뿐, 스프링 환경에서 테스트(DI 등)가 실행되지 않음
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
// 테스트에 필요한 설정을 로딩하기 위해 설정
@Log4j2 // 로그 출력
public class DBTests {
	
	@Autowired // HikariCP에서 만들어진 DataSource 타입의 빈을 주입받음
	private DataSource dataSource;
	
	@Test
	public void testConnection() {
		try {
			log.info("---------------");
			log.info("dataSource: " + dataSource); // 빈으로 주입된 객체가 정상적으로 만들어졌는지 log로 확인
			log.info("connection: " + dataSource.getConnection());
			log.info("---------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
