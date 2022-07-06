package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dao.UserRepo;
import com.example.demo.help.ErrorResponse;
import com.example.demo.model.Users;


@Controller
public class MainController {
	@Autowired
	UserRepo repo;
      
	@PostMapping(path="/adduser",produces= {"application/json"},consumes= {"application/json"})
	@ResponseBody
	public ResponseEntity<ErrorResponse>  addUser(@RequestBody Users user)
	{
		
		System.out.println(user.getFirstName());
		List list1=repo.findByPhoneNumber(user.getPhoneNumber());
		List list2=repo.findByEmail(user.getEmail());
		List list3=repo.findByUserName(user.getUserName());
		
		if(list1.isEmpty()&&list2.isEmpty()&&list3.isEmpty())
		{
			repo.save(user);
			return ResponseEntity.of(Optional.of(new ErrorResponse("Successfully Registered","200")));
		}
		else if(!list1.isEmpty())
		{
			return ResponseEntity.status(400).body(new ErrorResponse("Phone Number Already in use","400"));
		}
		else if(!list2.isEmpty())
			return ResponseEntity.status(400).body(new ErrorResponse("Email Already in use","400"));
		else
			return ResponseEntity.status(400).body(new ErrorResponse("Username Already in use","400"));
	}

	}
