package org.zerock.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.dto.AccountDTO;
import org.zerock.dto.AccountRole;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class AccountMapperTests {
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AccountMapper accountMapper;
	
	@Test
	public void testEncoding() {
		String pw = "1111";
		String enPw = encoder.encode(pw); // 단방향만 가능
		log.info("enPw: " + enPw); // salt에 의해 매번 다른 값으로 해시화
		log.info("-----");
		
		boolean match = encoder.matches(pw, enPw); 
		// matches: enPw에서 salt를 가져오고 그 salt로 pw를 해시화해서 해시화된 enPw랑 비교
		log.info("match: " + match);
	}
	
	@Commit
	@Transactional
	@Test
	public void testInsert() {
		for (int i = 1; i <= 100; i++) {
			AccountDTO accountDTO = new AccountDTO();
			accountDTO.setUid("user" + i);
			accountDTO.setUpw(encoder.encode("1111"));
			accountDTO.setUname("User" + i);
			accountDTO.setEmail("user" + i + "@aaa.com");
			
			accountDTO.addRole(AccountRole.USER);
			if (i >= 80) {
				accountDTO.addRole(AccountRole.MANAGER);
			}
			if (i >= 90) {
				accountDTO.addRole(AccountRole.ADMIN);
			}
			
			accountMapper.insert(accountDTO);
			accountMapper.insertRoles(accountDTO);
		}
	}
	
	@Test
	public void testSelectOne() {
		String uid = "user100";
		AccountDTO accountDTO = accountMapper.selectOne(uid);
		log.info(accountDTO);
		log.info("roleNames: " + accountDTO.getRoleNames());
	}
	
}
