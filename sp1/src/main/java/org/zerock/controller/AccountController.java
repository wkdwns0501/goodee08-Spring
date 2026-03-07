package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/account")
public class AccountController {
	
	@GetMapping("/login")
	public void login() {
		
	}
	
	@GetMapping("/logout")
	public void logout() {
		
	}
	
}
