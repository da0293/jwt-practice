package com.cos.security1.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class User {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String email;
	private String role; //ROLE_USER, ROLE_ADMIN
	
	@Builder
	public User(int id, String username, String password, String email, String role, String provider, String providerId,
			Timestamp createDate) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.provider = provider;
		this.providerId = providerId;
		this.createDate = createDate;
	}
	private String provider; // 구글인지, 네이버인지 등 구분
	private String providerId; // 받은  attribute정보 -> 이 정보로 강제 회원가입 진행함.
//	private Timestamp loginDate; //로그인할때마다 날짜 
	@CreationTimestamp
	private Timestamp createDate;
}
