package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨.
@EnableMethodSecurity(securedEnabled = true,prePostEnabled = true) // secure 어노테이션 활성화, preAuthorize,postAuthorize 어노테이션 활성화
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {
	
	
	
	
	
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable) // 사이트 위변조 요청 방지
				.authorizeHttpRequests((authorizeRequests) -> { // 특정 URL에 대한 권한 설정.
					authorizeRequests.requestMatchers("/user/**").authenticated();
					authorizeRequests.requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER"); // ROLE_은 붙이면 안 된다.
																										// hasAnyRole()을
																										// 사용할 때 자동으로
																										// ROLE_이 붙기
																										// 때문이다.
					authorizeRequests.requestMatchers("/admin/**").hasRole("ADMIN"); // ROLE_은 붙이면 안 된다. hasRole()을 사용할
																						// 때 자동으로 ROLE_이 붙기 때문이다.
					authorizeRequests.anyRequest().permitAll();
				})

				.formLogin((formLogin) -> {
					formLogin.loginPage("/loginForm") // 권한이 필요한 요청은 해당 url로 리다이렉트
							.loginProcessingUrl("/login") // login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 해준다.
							.defaultSuccessUrl("/"); // 로그인 성공시 /주소로 이동
							
				})
				.oauth2Login((oauth2Login)->{
					oauth2Login.loginPage("/loginForm")
							.userInfoEndpoint()
							.userService(null);
				})
				.build();
	}
}
// oauth2Login.loginPage("/loginForm"); 
// 구글로그인이 완료된 뒤의 후처리가 필요함. Tipㅌ,(엑세스토큰 + 사용자정보 O)
// 1.코드받기 2.액세스토큰(권한 생김) 3.사용자프로필 가져오고 
// 4-1.그 정보를 토대로 회원가입을 진행시킴
// 