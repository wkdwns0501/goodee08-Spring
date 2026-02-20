package org.zerock.mapper;

import org.apache.ibatis.annotations.Select;

// SQL을 실행하기 위한 메소드를 정의하는 DAO 역할인 인터페이스
// root-context.xml에서 MyBatis의 Mapper 인터페이스로 등록되어야 함
public interface TimeMapper {
	// DB의 현재 시간을 조회하는 SQL
	@Select("SELECT NOW()")
	String getTime();
	
	String getTimeByXML();
}
