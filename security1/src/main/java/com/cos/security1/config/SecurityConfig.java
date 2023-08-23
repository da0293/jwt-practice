package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // IOC 빈(bean) 등록
@EnableWebSecurity // 활성화하면 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다. (필터 체인 관리 시작 어노테이션)
public class SecurityConfig {

	// 빈을 쓰면 해당 메서드의 리턴되는 오브젝트를 IOC로 등록해준다.
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeHttpRequests().requestMatchers("/user/**").authenticated() // 로그인 한 사람만 가능
				.requestMatchers("/manager/**").hasAnyRole("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN')") // manager나
																										// admin권한 있는 경우
				.requestMatchers("/admin/**").hasAnyRole("hasRole('ROLE_ADMIN')") // admin권한 있는 사람만
				.anyRequest().permitAll()// 위 3가지 url 아니면 누구나 들어갈 수 있다.
				.and()
				.formLogin()
				.loginPage("/loginForm")// 여기까지하면 커스텀 로그인으페이지로 다 들어갈 수 있다.
				.loginProcessingUrl("/login")// login주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다.(컨트롤러에 /login안만들어도된다.)
				.defaultSuccessUrl("/"); // 메인페이지로 이동할 것이다.
		//.usernameParameter("username2") // html에서 parameter값은 name값만 가져오는데 여기서 추가 설정 가능
		// 위 3부분은 403 에러
		return http.build();

	}
}
