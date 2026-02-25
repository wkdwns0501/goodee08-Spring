package org.zerock.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {
	
	private Long rno;
	private String replyText;
	private String replyer;
	private LocalDateTime replyDate;
	private LocalDateTime updateDate;
	private boolean delFlag;
	
	private Long bno;
	
}
