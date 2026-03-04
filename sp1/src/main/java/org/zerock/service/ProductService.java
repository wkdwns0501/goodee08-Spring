package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.dto.ProductDTO;
import org.zerock.dto.ProductListDTO;
import org.zerock.dto.ProductListPagingDTO;
import org.zerock.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
@Transactional
public class ProductService {
	
	private final ProductMapper productMapper;
	
	public Integer add(ProductDTO productDTO) {
		productMapper.insert(productDTO);
		
		Integer pno = productDTO.getPno();
		
		// 이미지 리스트가 존재하고, 비어있지 않을 때만 실행
		if (productDTO.getImageList() != null 
				&& !productDTO.getImageList().isEmpty()) {
			productMapper.insertImage(productDTO);
		}
		
		return pno;
	}
	
	public ProductListPagingDTO getList(int page, int size) {
		page = page <= 0 ? 1 : page;
	    size = (size < 10 || size > 100) ? 10 : size;
	    
	    int total = productMapper.count();
	    int lastPage = (int) Math.ceil(total / (double) size);

	    if (lastPage == 0) lastPage = 1;
	    if (page > lastPage) page = lastPage;
	    int skip = (page - 1) * size;
	    
	    List<ProductListDTO> list = productMapper.selectList(skip, size);
	    return new ProductListPagingDTO(list, total, page, size);
	}
	
	public ProductDTO read(Integer pno) {
		return productMapper.selectOne(pno);
	}
	
	public void modify(ProductDTO productDTO) {
		// 기존 이미지 삭제
		productMapper.deleteImage(productDTO.getPno());
		// 상품 정보 수정
		productMapper.updateOne(productDTO);
		// 상품 이미지 갱신
		// 이미지 리스트가 존재하고, 비어있지 않을 때만 실행
		if (productDTO.getImageList() != null 
				&& !productDTO.getImageList().isEmpty()) {
			productMapper.insertImage(productDTO);
		}
	}
	
	public void remove(Integer pno) {
		productMapper.deleteOne(pno);
	}
	
}
