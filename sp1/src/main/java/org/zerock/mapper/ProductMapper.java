package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.dto.ProductDTO;
import org.zerock.dto.ProductListDTO;

public interface ProductMapper {
	
	int insert(ProductDTO productDTO);
	
	int insertImage(ProductDTO productDTO);
	
	List<ProductListDTO> selectList(@Param("skip") int skip, @Param("count") int count);
	
	int count();
	
	ProductDTO selectOne(@Param("pno") Integer pno);
	
	int updateOne(ProductDTO productDTO);
	
	int deleteOne(@Param("pno") Integer pno);
	
	int deleteImage(@Param("pno") Integer pno);
}
