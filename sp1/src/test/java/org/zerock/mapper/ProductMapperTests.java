package org.zerock.mapper;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.dto.BoardDTO;
import org.zerock.dto.ProductDTO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class ProductMapperTests {
	
	@Autowired
	private ProductMapper productMapper;
	
	// 테스트에서 @Transactional의 기본 동작
	// 1. 테스트 시작 -> 트랜잭션 시작
	// 2. 테스트 종료 -> 무조건 ROLLBACK
	// 즉, 테스트는 DB를 더럽히지 않는 것이 기본 철학
	@Transactional
	@Commit // 테스트 트랜잭션을 ROLLBACK 하지 말고 COMMIT 해라(이 테스트는 실제로 DB에 반영되도록 설정)
	@Test
	public void testInsert() {
		ProductDTO productDTO = ProductDTO.builder()
										.pname("Product")
										.pdesc("Prodcut Desc")
										.writer("user01")
										.price(4000)
										.build();
		
		// 상품 테이블에 등록
		productMapper.insert(productDTO); // pno 없음
		
		// 상품 이미지 테이블에 등록
		productDTO.addImage(UUID.randomUUID().toString(), "test1.jpg");
		productDTO.addImage(UUID.randomUUID().toString(), "test2.jpg");
		
		productMapper.insertImage(productDTO); // pno 있음
	}
	
	@Transactional
	@Commit
	@Test
	public void testInsertDummies() {
		for (int i = 0; i < 45; i++) {
			ProductDTO productDTO = ProductDTO.builder()
					.pname("Product" + i)
					.pdesc("Prodcut Desc" + i)
					.writer("user" + (i % 10))
					.price(4000)
					.build();
			
			// 상품 테이블에 등록
			productMapper.insert(productDTO);
			
			// 상품 이미지 테이블에 등록
			productDTO.addImage(UUID.randomUUID().toString(), i + "_test_1.jpg");
			productDTO.addImage(UUID.randomUUID().toString(), i + "_test__2.jpg");
			
			productMapper.insertImage(productDTO);
		}
	}
	
	@Test
	public void testList() {
		productMapper.selectList(0, 10).forEach(p -> log.info(p));
		
		log.info(productMapper.count());
	}
	
	@Test
	public void testSelectOne() {
		Integer pno = 1;
		ProductDTO productDTO = productMapper.selectOne(pno);
		
		log.info("-------------------");
		log.info("product: " + productDTO);
		log.info("-------------------");
		productDTO.getImageList().forEach(img -> log.info(img));
	}
	
	@Transactional
	@Commit
	@Test
	public void testUpdateOne() {
		ProductDTO productDTO = ProductDTO.builder()
										.pno(1)
										.pname("Update Product")
										.pdesc("update")
										.price(6000)
										.build();
		
		productDTO.addImage(UUID.randomUUID().toString(), "test3.jpg");
		productDTO.addImage(UUID.randomUUID().toString(), "test4.jpg");
		productDTO.addImage(UUID.randomUUID().toString(), "test5.jpg");
		
		// 상품 수정 3단계
		// 1. 기존 이미지 삭제
		productMapper.deleteImage(productDTO.getPno());
		// 2. 상품 정보 수정
		productMapper.updateOne(productDTO);
		// 3. 상품 이미지 갱신
		productMapper.insertImage(productDTO);
	}
	
	@Test
	public void testDelete() {
		Integer pno = 1;
//		int deleteCount = productMapper.delete(bno);
		
		log.info("-------------------");
//		log.info("deleteCount: " + deleteCount);
	}
	
}
