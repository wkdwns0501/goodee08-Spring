CREATE TABLE tbl_product_image (
	ino INT AUTO_INCREMENT PRIMARY KEY,  -- 이미지 번호(고유식별자)
    pno INT NOT NULL,                    -- 상품 번호(외래키)
    filename VARCHAR(300) NOT NULL,      -- 실제 파일명 또는 저장된 경로
    uuid CHAR(36) NOT NULL,              -- 파일명 중복 방지를 위한 UUID
    ord INT DEFAULT 0,                   -- 이미지 정렬 순서
    regdate DATETIME DEFAULT CURRENT_TIMESTAMP,  -- 등록일
    FOREIGN KEY (pno) REFERENCES tbl_product(pno) ON DELETE CASCADE,
    INDEX idx_product_image_pno (pno, ord)
);