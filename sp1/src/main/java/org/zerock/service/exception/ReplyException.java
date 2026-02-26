package org.zerock.service.exception;

import lombok.Getter;

@Getter
public class ReplyException extends RuntimeException{

	private int code;
	
	public ReplyException(int code, String msg) {
		super(msg);
		this.code = code;
	}
	
}
