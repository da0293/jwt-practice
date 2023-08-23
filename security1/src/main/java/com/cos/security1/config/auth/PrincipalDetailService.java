package com.cos.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

// 시큐리티 설정에서 .loginProcessingUrl("/login")으로 걸어놔서
// /login요청이 오면 자동으로 UserDetailSErvice타입으로 IoC되어있는 loadUserByUsername함수가 실행된다.(규칙이다)

// 1. loginForm에서 로그인버튼을 누르면 /login으로 이동하려고 발동 
// 2. spring은 IoC컨n테이너에서 UserDetailService로 등록되어있는 객체 찾는다.
// 3. 그 객체를 찾으면 그 객체는 바로 loadUserByUsername 메서드를 호출한다. 
// 4. userRepository에 가져온 이름을 가진 유저가 있는지 확인해야한다.

@Service
public class PrincipalDetailService implements UserDetailsService{ 
	
	@Autowired
	private UserRepository userRepository;
	
	// 1. 시큐리티 session이 있고 들어갈 수 있는건 Authentication타입이고 이 타입안에 UserDetails타입이 들어와야한다.
	// 3. Authentication내부d에 UserDetails가 들어가고 시큐리티 세션안에 Authentication이 들어간다.
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = userRepository.findByUsername(username); // 이 이름을 가진 유저가 있는지 확인
		if( userEntity != null) { // 유저가 있다면
			return new PrincipalDetails(userEntity); //2.유저가 리턴됨
		}
		return null;
	}
	
}
