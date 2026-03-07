CREATE TABLE persistent_logins (
	username VARCHAR(64) NOT NULL,
  series VARCHAR(64) PRIMARY KEY,
  token VARCHAR(64) NOT NULL,
  last_used DATETIME NOT NULL -- DB 이식성까지 고려하면 TIMESTAMP
);