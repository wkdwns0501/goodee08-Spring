package org.zerock.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Aspect // 해당 클래스가 AOP의 관점(Aspect)을 정의하는 클래스임을 지정
@Component // 해당 클래스의 객체가 스프링에서 빈(Bean)으로 관리되는 대상임을 지정
public class LogAspect {
		
	// 특정 객체의 메소드가 실행될 때 전달되는 파라미터를 자동으로 받아서 로그로 출력하는 AOP 메소드
	
	// @Before: 메소드 실행 전에 실행되는 AOP 메소드임을 지정
	// execution(* org.zerock.service.*.*(..)):
	// - execution: 메소드 실행 시점을 지정하는 포인트컷 표현식
	// - org.zerock.service 패키지의 모든 클래스(*)의 모든 메소드(*)가 실행 전에 AOP 메소드(Advice)가 실행됨을 지정
	// 리턴 타입 : 맨 앞 * , 매개변수 : (..) 상관 없이 모든 메소드 
	@Before("execution(* org.zerock.service.*.*(..))")
	public void logBefore(JoinPoint joinPoint) { 
		// JoinPoint: 현재 실행 중인 메소드 정보, 전달된 파라미터, 타켓 객체 등의 정보를 담고 있는 객체
		log.info("-------------------------------");
		log.info("logBefore()");
		
		Object[] params = joinPoint.getArgs(); // 메소드에 전달된 파라미터 정보
		log.info(Arrays.toString(params)); // 파라미터 정보를 문자열로 변환하여 로그로 출력
		
		Object target = joinPoint.getTarget();
		log.info("타겟 객체: " + target); // 메소드가 실행되는 객체 정보
		
		log.info("-------------------------------");
	}
	
	// @Around: 메소드 실행 전과 후에 모두 실행되는 AOP 메소드임을 지정
	// 시간 성능 측정 
	@Around("execution(* org.zerock.service.*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) throws Throwable {
		log.info("-------------------------------");
		log.info("logTime()");
		
		long start = System.currentTimeMillis(); // 메소드 실행 시작 시간
		
		Object result = pjp.proceed(); // @Around는 이코드를 직접 호출해야 타겟 메소드가 실제로 실행됨
		
		long end = System.currentTimeMillis(); // 메소드 실행 종료 시간
		
		log.info("-------------------------------");
		log.info("실행 시간: " + (end - start) + "ms");
		
		return result;
	}
	
}
