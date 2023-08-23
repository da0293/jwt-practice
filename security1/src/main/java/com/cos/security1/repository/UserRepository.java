package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;
//CRUD 함수를 JpaRepository가 들고있음.
// @Repository라는 어노테이션이 없어도 IOC가 된다. 이유는 JpaRepository를 상속했기때문에다.
	public interface UserRepository extends JpaRepository<User, Integer>{ //UserRepository가 빈으로 등록 
	
	}
