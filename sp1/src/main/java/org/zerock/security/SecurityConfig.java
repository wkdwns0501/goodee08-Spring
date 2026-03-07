package org.zerock.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import lombok.extern.log4j.Log4j2;

// 이클래스를 스프링 설정 클래스로 등록하고,
// 내부의 @Bean 메서드들이 반환하는 객체를 스프링 빈으로 스프링 컨테이너에 등록하도록 하는 어노테이션
@Configuration // 보통 설정 클래스 위에 @Configuration + 메소드 위에 @Bean 세트로 많이 사용
@Log4j2
@EnableWebSecurity
// 스프링 시큐리티의 웹 보안 기능을 활성화시키고, 이클래스에서 정의한 보안 설정을 적용하도록 만드는 어노테이션
// 이제부터 모든 HTTP 요청(/*)은 시큐리티가 검사
// SecurityFilterChain을 자동으로 구성, 시큐리티 관련 기본 설정을 등록

// 메소드 단위에서 @PreAuthorize / @PostAuthorize 같은 메소드 보안 어노테이션 활성화
//@EnableMethodSecurity(prePostEnabled = true) // 예제에서는 XML 설정을 사용
public class SecurityConfig {
	
	@Autowired
	private DataSource dataSource;
	
	@Bean // 빈 등록 어노테이션
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		log.info("-------------- security config --------------");
		// 이 안에서 필터 체인을 직접 조립해서 리턴하도록 작성
		
		// 아이디/비밀번호 기반의 HTML 폼을 통한 로그인 기능을 활성화
		// 스프링 시큐리티의 기본 로그인 처리 흐름을 구성
		http.formLogin(config -> {
			// 커스텀 로그인 페이지의 경로를 지정
			config.loginPage("/account/login");
			
			// 로그인 성공 시 핸들러 등록
			config.successHandler(new CustomLoginSuccessHandler());
		});
		
		http.rememberMe(config -> {
			// 서버의 비밀키 값 설정 가능
			config.key("very-secret-key");
			// 깃허브에 커밋 -> 키 값이 그대로 노출
			// 보통은 환경 변수 / 설정 파일 사용
			
			config.tokenRepository(persistentTokenRepository());
			config.tokenValiditySeconds(60 * 60 * 24 * 30); // 30일 유지
		});
		
		// (참고) 로그아웃 시 추가로 다른 쿠키도 삭제하고 싶을 때 설정
//		http.logout(config -> {
//			config.deleteCookies("JSESSIONID", "remember-me");
//		});
		
		http.csrf(csrf -> csrf.disable()); // 예제할 때는 번거롭기 때문에 CSRF 완전 비활성화
		
		http.exceptionHandling(handler -> {
			// 403 응답을 직접 제어하는 설정
			handler.accessDeniedHandler(new Custom403Handler());
			
			// 특정 화면으로 보내는 간단한 설정
//			handler.accessDeniedPage(url);
		});
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // (참고) 인자값(int strength)으로 cost factor 넣을 수 있음
							// cost를 높이면 연산 반복 횟수가 올라감 -> 해시가 다르게 나옴
							// 즉, salt/cost에 의해 해시값이 결정
							// 해시를 느리게 설정해서 현실적인 시간 안에 비밀번호를 추측할 수 없게 만듦
							// 정상 사용자: 0.1초 걸려도 체감 거의 없음
							// 공격자(해커): 해시가 느릴수록 공격 비용 폭증
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		// 스프링 시큐리티에서 기본적으로 제공하는 토큰 저장을 위한 클래스
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
//		tokenRepository.setCreateTableOnStartup(true); // 테이블 자동 생성(권장 X)
		
		return tokenRepository;
	}
}
