package org.zerock.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO implements UserDetails{
	
	private String uid;
	private String upw;
	private String uname;
	private String email;
	private List<AccountRole> roleNames = new ArrayList<>();
	
	public void addRole(AccountRole role) {
		roleNames.add(role);
	}
	
	public void clearRoles(){
		roleNames.clear();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roleNames.stream()
				.map(accountRole -> new SimpleGrantedAuthority("ROLE_" + accountRole.name()))
				.toList(); // List<SimpleGrantedAuthority>
	}

	@Override
	public String getPassword() {
		return upw;
	}

	@Override
	public String getUsername() {
		return uid; // 주의! uid가 아이디, uname은 이름
		// uid, uname 무엇을 반환할지에 따라 의미가 달라짐
		// 로그인 ID(식별값)를 리턴하는 것을 권장
	}
	
	// 기술적으로 필요 없지만 나중에 확장에 대비해서 재정의
	// 계정 활성화/잠금/만료 상태가 DB에 있음
	// 관리자가 계정을 정지시킬 수 있음
	// 비밀번호 만료 정책이 있음
	@Override
	public boolean isAccountNonExpired() {
		// 만료되지 않았음
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		// 잠긴 계정 아님
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		// 인증정보 활용 가능함
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		// 사용 가능(=활성 계정임)
		return true;
	}
	
}
