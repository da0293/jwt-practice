package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller //View를 리턴하겠다!!
public class IndexController {
	
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	
	//localhost:8080/
	//localhost:8080
	@GetMapping({"","/"})
	public String index() {
		// 머스테치 기본폴더 경로 : src/main/resources/
		// 뷰리졸버 설정 : templates(prefix).mustache(suffix)생략가능
		return "index";
	}
	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	@GetMapping("/loginForm")
	public String loginForm() { // 스프링 시큐리티가 낚아챔 
		return "loginForm";
	}
	@GetMapping("/joinForm") // 회원가입 페이지 나오도록
	public String joinForm() { // 스프링 시큐리티가 낚아챔 
		return "joinForm";
	}
	@PostMapping("/join")
	public String join(User user) {
		System.out.println(user);
		user.setRole("ROLE_USER");
		String rawPassword=user.getPassword();
		String encPassword=bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		userRepository.save(user); // 회원가입 잘 됨 , 비밀번호 1234=> 시큐리티로 로그인을 할 수 없다. 이유는 패스워드 암호화가 안되서 
		return "redirect:/loginForm";
	}
}
