package org.zerock.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	private Integer pno;
	private String pname;
	private String pdesc;
	private int price;
	private boolean sale;
	private String writer;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
	
	// 상품 이미지들
	@Builder.Default // Builder에 값이 없을 때 필드 초기화 값을 복사해서 사용
	private List<ProductImageDTO> imageList = new ArrayList<>(); // 필드 초기화 값
	// Builder에서 지정하지 않은 값은 해당 타입의 기본값이 들어감
	// 예: ProductDTO.builder().pname("abc").build();
	
	public void addImage(String uuid, String fileName) {
		ProductImageDTO imageDTO = ProductImageDTO.builder()
												.uuid(uuid)
												.fileName(fileName)
												.pno(this.pno)
												.ord(this.imageList.size())
												.build();
		
		imageList.add(imageDTO);
	}
	
	public void clearImage() {
		imageList.clear();
	}
	
}
