package org.zerock.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class TimeMapperTests {
	
	@Autowired
	private TimeMapper timeMapper;
	
	@Test
	public void testGetTime() {
		log.info("----------------------");
		log.info("getTime: " + timeMapper.getTime());
		log.info("----------------------");
	}
	
	@Test
	public void testGetTimeByXML() {
		log.info("----------------------");
		log.info("getTime: " + timeMapper.getTimeByXML());
		log.info("----------------------");
	}
}
