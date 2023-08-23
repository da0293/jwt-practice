package com.cos.security1.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		 MustacheViewResolver resolver = new MustacheViewResolver();

	      resolver.setCharset("UTF-8"); // 인코딩은 기본적으로 UTF8
	      resolver.setContentType("text/html;charset=UTF-8"); // html파일은 .UTF-8이다.
	      resolver.setPrefix("classpath:/templates/"); //classpath: 프로젝트 경로
	      resolver.setSuffix(".html"); // mustache로 끝나는 파일을 .html로 바꿔준다.

	      registry.viewResolver(resolver); // registry를 뷰 리졸버에 저장 
	}
}
