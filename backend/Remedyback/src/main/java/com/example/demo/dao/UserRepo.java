package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Users;
public interface UserRepo extends JpaRepository<Users,Integer>{
	 List<Users> findByPhoneNumber(String phone);
	 Users findByUserName(String userName);
	 List<Users> findByEmail(String email);
}
