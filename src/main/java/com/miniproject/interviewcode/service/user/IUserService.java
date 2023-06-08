package com.miniproject.interviewcode.service.user;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.miniproject.interviewcode.model.user.User;


public interface IUserService {
	
	public ResponseEntity<?> getAllUsers(int pageNo, int pageSize);
	
	public ResponseEntity<?> getAllByNoTelp(String uniqid, String token);

	public ResponseEntity<?> getUserById(String userId);
	
	
	public User getListByIdUser(String string);
	
	public List<User> getListByNoTelp(String nama);

	public User findByNama(String nama);
	
	public User findByNoTelp(String noTlp);

	public List<User> findAllUsers();

	public User saveUser(User user);

	public List<String> findUsers(List<Long> idUser);
	
	public int updateUserLoginByNama(String nama);
	
	public int updateUserLoginByNoTelp(String notelp);
	
	public List<User> getNoTelpOrNama(String notelp, String nama);
	
	public User getListByNama(String nama);
	
	
	

}
