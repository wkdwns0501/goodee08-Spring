package org.zerock.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.dto.ProductDTO;
import org.zerock.dto.ProductListPagingDTO;
import org.zerock.mapper.ProductMapper;
import org.zerock.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
	
	private final ProductService productService;
	
	@GetMapping("/add")
	public void add() {
		log.info("product add");
	}
	
	@PostMapping("/add")
	public String add(ProductDTO productDTO, RedirectAttributes rttr,
					   @RequestParam("files") MultipartFile[] files) {
		log.info("----------------------------");
		log.info(productDTO);
		log.info(files);
		
		// 파일 업로드
		List<String> uploadNames = uploadFiles(files);
		
		uploadNames.forEach(name -> {
			String uuid = name.substring(0, 36); // UUID가 36자라서
			String fileName = name.substring(37);
			log.info(uuid);
			log.info(fileName);
			productDTO.addImage(uuid, fileName);
		});
		
		Integer pno = productService.add(productDTO);
		
		// 1회성 메세지
		// 예: 등록 / 수정 / 삭제 후 메세지 전달
		rttr.addFlashAttribute("result", pno);
		rttr.addFlashAttribute("action", "add");
		
		return "redirect:/product/list";
	}
	
	@GetMapping("/list")
	public String list(@RequestParam(name = "page", defaultValue = "1") int page, 
					@RequestParam(name = "size", defaultValue = "10") int size,
					 Model model) {
		ProductListPagingDTO productListDTO = productService.getList(page, size);
		model.addAttribute("productListDTO", productListDTO);
		return "/product/list";
	}
	
	@GetMapping("/read/{pno}")
	public String read(@PathVariable("pno") Integer pno, Model model) {
		ProductDTO productDTO = productService.read(pno);
		model.addAttribute("product", productDTO);
		return "/product/read";
	}
	
	@GetMapping("/modify/{pno}")
	public String modify(@PathVariable("pno") Integer pno, Model model) {
		ProductDTO productDTO = productService.read(pno);
		model.addAttribute("product", productDTO);
		return "/product/modify";
	}
	
	@PostMapping("/modify")
	public String modify(ProductDTO productDTO, RedirectAttributes rttr, 
						@RequestParam(name = "oldImages", required = false) String[] oldImages, 
						@RequestParam("files") MultipartFile[] files) {
		// 새로 추가된 파일 업로드
		List<String> newFileNames = uploadFiles(files);
		// oldImages : 기존 이미지 추가
		if (oldImages != null && oldImages.length > 0) {
			for (String oldImage : oldImages) {
				String uuid = oldImage.substring(0, 36);
				String fileName = oldImage.substring(37);
				productDTO.addImage(uuid, fileName);
			}
		}
		// newImages : 새로운 이미지 추가
		if (newFileNames != null && newFileNames.size() > 0) {
			for (String newImage : newFileNames) {
				String uuid = newImage.substring(0, 36);
				String fileName = newImage.substring(37);
				productDTO.addImage(uuid, fileName);
			}
		}
		productService.modify(productDTO);
		rttr.addFlashAttribute("result", productDTO.getPno());
		rttr.addFlashAttribute("action", "modify");
		return "redirect:/product/read/" + productDTO.getPno();
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("pno") Integer pno, RedirectAttributes rttr) {
		productService.remove(pno);
		rttr.addFlashAttribute("result", pno);
		rttr.addFlashAttribute("action", "remove");
		return "redirect:/product/list";
	}
	
	// 파일 업로드 기능
	private List<String> uploadFiles(MultipartFile[] files) throws RuntimeException { // throws 생략 가능
		List<String> uploadNames = new ArrayList<String>();
		
		// 파일을 첨부하지 않은 상태로 전송하면, files 배열 자체는 null이 아니고 길이가 1인 배열로 들어옴
		log.info("MultipartFile 배열 길이: " + files.length); // 확인용
		if (files == null || files.length == 0) {
			return uploadNames;
		}
		
		String uploadPath = "C:/upload";
		log.info("---------------------- uploadPath ----------------------");
		log.info(uploadPath);
		
		// (참고) 업로드 디렉토리 생성 보장
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) uploadDir.mkdirs();
		
		for (MultipartFile file : files) {
			// 파일 첨부를 안했을 시 방어 코드
			if (file.isEmpty()) continue;
			
			String fileName = file.getOriginalFilename();
			String uploadName = UUID.randomUUID().toString() + "_" + fileName;
			File targetFile = new File(uploadPath, uploadName);
			
			// 파일을 저장하는 2가지 방식
			// 1. 파일 가공이 필요할 때는 스트림 방식 사용(저수준 스트림 기반 복사)
			// 예: 암호화, 압축, 바이러스 검사, 이미지 리사이징, AWS S3 같은 클라우드 스토리지에 업로드 등
//			try (InputStream fin = file.getInputStream();
//				 OutputStream fos = new FileOutputStream(targetFile)) {
//				log.info(targetFile.getAbsolutePath());
//				
//				FileCopyUtils.copy(fin, fos);
//				
//				uploadNames.add(uploadName);
//			} catch (Exception e) {
//				log.error(e.getMessage());
//				throw new RuntimeException(e.getMessage()); // 예외를 삼키지 않고 다시 전달
//			}
			
			// 2. 단순 저장일 경우 스프링이 제공하는 API 사용 권장
			// 장점: 성능 최적화 및 코드 간결함(직접 InputStream을 열고 닫을 필요가 없음)
			// 단점: 저장 과정에 개입하기 어려움(단순 저장 외의 처리가 필요하면 부적합)
			try {
				log.info(targetFile.getAbsolutePath());
				
				file.transferTo(targetFile);
				
				uploadNames.add(uploadName);
			} catch (Exception e) {
				log.error(e.getMessage());
				throw new RuntimeException(e.getMessage()); // 예외를 삼키지 않고 다시 전달
			}
			
			// 이미지 파일인 경우 썸네일 이미지 생성 (Thumbnailator 라이브러리 사용)
			if (file.getContentType().startsWith("image")) { // image/jpg, image/png
				try {
					Thumbnails.of(targetFile)
							  .size(200, 200)
							  .toFile(new File(uploadPath, "s_" + uploadName));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		return uploadNames;
	}
	
	// 파일 삭제 기능
	// 예제에서 사용 X, 코드 참고만
	private void deleteFiles(List<String> fileNames) {
		try {
			File uploadPath = new File("C:/upload");
			for (String fileName : fileNames) {
				File targetFile = new File(uploadPath, fileName);
				targetFile.delete();
				
				// 썸네일도 삭제
				File targetThumb = new File(uploadPath, "s_" + fileName);
				targetThumb.delete();
			}
		} catch (Exception e) {
			// 물리적 파일 삭제는 부가 작업이므로, 실패하더라도 서비스 전체 흐름을 깨지 말고 로그만 남김
			// 실패해도 DB 상태는 정상, 나중에 정리 가능
			log.warn(e.getMessage());
		}
	}
	
}
