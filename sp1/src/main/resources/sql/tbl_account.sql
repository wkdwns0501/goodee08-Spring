-- 부모 테이블
CREATE TABLE tbl_account (
	uid VARCHAR(50) PRIMARY KEY,
  upw VARCHAR(100) NOT NULL COMMENT 'BCrypt 암호화 비밀번호',
  uname VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE,
  enabled BOOLEAN DEFAULT TRUE,
  created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 자식 테이블
CREATE TABLE tbl_account_roles (
	uid VARCHAR(50) NOT NULL, -- 한 계정이 여러 role을 가질 수 있음(1:N)
  rolename VARCHAR(50) NOT NULL,
  PRIMARY KEY (uid, rolename), -- 동일 계정에 동일한 역할 중복 방지
  FOREIGN KEY (uid) REFERENCES tbl_account(uid)
);