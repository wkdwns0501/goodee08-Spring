package org.zerock.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.info("authentication: " + authentication);
		
		// 세션에서 SavedRequest 확인
		SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
		log.info("savedRequest: " + savedRequest); // 인증이 필요해서 가로막힌 원래 요청 정보를 저장해 둔 객체
		
		// 로그인 후에 이동할 경로
		if (savedRequest != null) {
			response.sendRedirect(savedRequest.getRedirectUrl()); // 기존 경로(원래 이동할 경로) 정보
		} else {
			response.sendRedirect("/board/list");
		}
	}

}
