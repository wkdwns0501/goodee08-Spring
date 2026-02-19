-- 데이터베이스 생성
CREATE DATABASE springdb;

-- 데이터베이스 접속 계정 생성
CREATE USER 'springdbuser'@'localhost' IDENTIFIED BY 'springdbuser';
CREATE USER 'springdbuser'@'%' IDENTIFIED BY 'springdbuser';

-- 데이터베이스 권한 설정
GRANT ALL PRIVILEGES ON springdb.* TO 'springdbuser'@'localhost';
GRANT ALL PRIVILEGES ON springdb.* TO 'springdbuser'@'%';