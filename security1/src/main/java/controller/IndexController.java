package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //View를 리턴하겠다. 
public class IndexController {

	// localhost8080
	@GetMapping({"","/"})
	public String index() {
		// 머스테치 기보폴더 src/main/resources/
		// 뷰리졸버 설정: templates(prefix).mustache(suffix)
		return "index"; // src/main/resources/templates/index.mustache
	}
	
	
}
