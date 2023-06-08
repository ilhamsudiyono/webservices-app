package com.miniproject.interviewcode.service.user;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.miniproject.interviewcode.model.user.User;


public interface IUserService {
	
	public ResponseEntity<?> getAllUsers(int pageNo, int pageSize);
	
	public ResponseEntity<?> getAllByNoTelp(String uniqid, String token);

	public ResponseEntity<?> getUserById(String userId);
	
	public User getListByIdUser(String string);
	
	public List<User> getListByNoTelp(String nama);
	
	public int updateUserLoginByNoTelp(String notelp);
	
	
	
	
	
	

}
