package com.cos.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

// 시큐리티가 /login 주소요쳥이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료가 되면 시큐리티 session을 만들어줍니다.(Security ContextHolder라는 키값에 세션정보를 저장한다.)
// 들어갈 수 있는 오브젝트가 정해져 있다 => Authentication 타입 객체만
// Authentication안에 User정보가 있어야 된다.
// User오브젝트 타입 => UserDetails타입 객체 

// 시큐리티세션 영역에 세션정보를 저장해주는데 여기 들어갈 수 있는 객체는 Authentication만 유저정보는 UserDetails타입이어야한다.
// implements 받으면 PrincipalDetails타입을 Authentication객체 안에 넣을 수 있다!


public class PrincipalDetails implements UserDetails {

	private User user; // 콤포지션
	
	public PrincipalDetails(User user) {
		this.user=user;
	}
	
	// 해당user의 권한을 리턴하는 곳 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect=new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return user.getRole();// string리턴 
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		
		return user.getPassword(); // 패스워드 리턴
	}

	@Override
	public String getUsername() {
		return user.getUsername(); // 유저이름 리턴
	}

	@Override
	public boolean isAccountNonExpired() { // 너의 계정이 만료안돼었니
		return true; //아니오
	}

	@Override
	public boolean isAccountNonLocked() { // 너의 계쩡이 안잠겼니 
		return true; //아니오
	}

	@Override
	public boolean isCredentialsNonExpired() { // 비밀번호 기간이 너무 오래되지않았니
		return true; //아니오
	}

	@Override
	public boolean isEnabled() {
		// 우리 사이트에서 1년동안 회원이 로그인을 안하면 휴면 계쩡으로 하기로 함 
		// user.getloginDate() 로 가져와서 설정함 만약 1년을 초과하면 return false
		return true;
	}

}
