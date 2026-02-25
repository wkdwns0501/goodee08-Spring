CREATE TABLE tbl_reply (
	rno INT AUTO_INCREMENT PRIMARY KEY,
	replytext VARCHAR(500) NOT NULL,
	replyer VARCHAR(50) NOT NULL,
	replydate DATETIME DEFAULT CURRENT_TIMESTAMP,
	updatedate DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	delflag BOOLEAN DEFAULT FALSE,
	bno INT NOT NULL,
  CONSTRAINT fk_reply_board FOREIGN KEY (bno) REFERENCES tbl_board(bno),
  INDEX idx_reply_board (bno DESC, rno ASC) -- 일반적으로 정렬 기준 생략 가능(생략 시 ASC)
  -- ASC 인덱스로도 내림차순 정렬(Backward Scan)을 충분히 빠르게 처리할 수 있다
);