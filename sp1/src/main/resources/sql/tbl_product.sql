CREATE TABLE tbl_product (
	pno INT AUTO_INCREMENT PRIMARY KEY,  -- 상품 번호(고유식별자)
    pname VARCHAR(200) NOT NULL,         -- 상품 이름
    pdesc VARCHAR(1000) NOT NULL,        -- 상품 설명
    price INT NOT NULL,                  -- 상품 가격
    sale BOOLEAN DEFAULT FALSE,          -- 판매 여부(false)
    writer VARCHAR(100) NOT NULL,        -- 상품 등록자
    regdate DATETIME DEFAULT CURRENT_TIMESTAMP,
    moddate DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);