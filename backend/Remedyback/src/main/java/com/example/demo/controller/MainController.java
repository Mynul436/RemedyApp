package com.example.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.MedicineRepo;
import com.example.demo.dao.UserRepo;
import com.example.demo.help.ErrorResponse;
import com.example.demo.model.AuthRequest;
import com.example.demo.model.Medicine;
import com.example.demo.model.Users;
import com.example.demo.service.FileUpload;
import com.example.demo.util.JwtUtil;


@Controller
public class MainController {
	@Autowired
	FileUpload fileup;
	@Autowired
	MedicineRepo mediRepo;
	@Autowired
	UserRepo repo;
	@Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
	PasswordEncoder p=new BCryptPasswordEncoder();
	@PostMapping(path="/adduser",produces= {"application/json"},consumes= {"application/json"})
	@ResponseBody
	public ResponseEntity<ErrorResponse>  addUser(@RequestBody Users user)
	{
		
		 
		String password=p.encode(user.getPassword());
		user.setPassword(password);
		
		System.out.println(user.getFirstName());
		List list1=repo.findByPhoneNumber(user.getPhoneNumber());
		List list2=repo.findByEmail(user.getEmail());
		Users list3=repo.findByUserName(user.getUserName());
		
		if(list1.isEmpty()&&list2.isEmpty()&&list3==null)
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
	@PostMapping(path="/login",produces= {"application/json"},consumes= {"application/json"})
	@ResponseBody
	public ResponseEntity<ErrorResponse> login(@RequestBody AuthRequest authRequest){
		Users auth=repo.findByUserName(authRequest.getUserName());
		boolean match=p.matches(authRequest.getPassword(), auth.getPassword());
		if(match){
		try {
			
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
			System.out.println(ex.getMessage());
            try {
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return ResponseEntity.status(200).body(new ErrorResponse(jwtUtil.generateToken(authRequest.getUserName()),"200"));
	}
	else
	{
		return ResponseEntity.status(400).body(new ErrorResponse("Invalid username/password","400"));
	}
	}
	@PostMapping(path="/addmedicine",produces= {"application/json"})
	@ResponseBody
	public ResponseEntity<ErrorResponse> addMedicine(@RequestParam("medicineImage") MultipartFile image, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("company") String company){
		if(image.getOriginalFilename().contains("png")||image.getOriginalFilename().contains("jpg")||image.getOriginalFilename().contains("jpeg")){
	   Medicine medi=new Medicine();
	   medi.setCompany(company);
	   medi.setDesc(description);
	   medi.setFileName(image.getOriginalFilename()+new Date());
	   medi.setName(name);
	   String msg=fileup.fileUpload(image);
	   if(!msg.equals("Error"))
	   {
		medi.setFileName(msg);
		medi.setId((int) (new Date().getTime()));
		mediRepo.save(medi);
		return ResponseEntity.status(200).body(new ErrorResponse("Medicine added sucessfully","200"));
	   }
	   else
		return ResponseEntity.status(500).body(new ErrorResponse("There was an error while uploading the file","500"));
	}
	else
	{
		return ResponseEntity.status(400).body(new ErrorResponse("Only png, jpg and jpeg format is allowed","400"));
	}
	}
}
