package org.zerock.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface TestMapper {
	
	// MyBatis에서 #{str}은 Preparedstatement의 파라미터 바인딩(?)을 의미
	// #을 이용해서 메소드의 파라미터와 SQL을 매핑
	@Insert("INSERT INTO tb1_testA(col1) VALUES(#{str})")
	int insertA(@Param("str") String str);
	
	@Insert("INSERT INTO tb1_testB(col2) VALUES(#{str})")
	int insertB(@Param("str") String str);
}
