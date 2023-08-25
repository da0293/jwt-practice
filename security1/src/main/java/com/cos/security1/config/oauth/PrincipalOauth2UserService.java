package com.cos.security1.config.oauth;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.repository.UserRepository;
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	// 구글로 받은 userRequest 데이터에 대한 후처리 되는 함수
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("userRequest : " + userRequest);
		System.out.println("getClientRegistration : " + userRequest.getClientRegistration());
		System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());
				
		OAuth2User oauth2User=super.loadUser(userRequest);
		// 구글로그인 버튼 클릭 -> 구글로그인창 -> 로그인완료 -> code리턴받음(OAuth-Client라이브러리로 받음) -> 받아서 코드를 통해 AccessToken 요청함 
		// 토큰을 요청하면 정보를 받는데 이 정보가 userRequest정보 -> 이 정보로 회원프로필을 받아야함( 이 때 사용하는 함수 loadUser함수 )
		// 즉 userRequest정보를 통해서 loadUser함수를 호출하고 회원프로필을 구글로부터 받는다
		System.out.println("getAttributes : "  + super.loadUser(userRequest).getAttributes());
		
		// 회원가입을 강제로 진행해볼 예정
		String provider=userRequest.getClientRegistration().getClientId();//Google
		String providerId=oauth2User.getAttribute("sub");
		String username=provider+"_"+providerId;//google_
		String password=bCryptPasswordEncoder.encode("겟인데어");
		String email=oauth2User.getAttribute("email");
		String role="ROLE_USER";
		
		//회원가입이 이미 되어있을수도 있음 
		com.cos.security1.model.User userEntity =userRepository.findByUsername(username);
		if(userEntity==null) {
			System.out.println("구글로그인이 최초입니다.");
			userEntity = com.cos.security1.model.User.builder()
					.username(username)
					.password(password)
					.email(email)
					.role(role)
					.provider(provider)
					.providerId(providerId)
					.build();
			userRepository.save(userEntity);
		}else {
			System.out.println("구글 로그인을 이미 한 적이 있씁니다.");
		}
		// 회원가입을 강제로 진행해볼 예정
		return new PrincipalDetails(userEntity, oauth2User.getAttributes());
	}
}
