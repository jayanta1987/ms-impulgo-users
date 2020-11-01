package com.impulgo.users.service;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.impulgo.users.entity.User;
import com.impulgo.users.exception.UserAlreadyExistsException;
import com.impulgo.users.exception.UserNotFoundException;
import com.impulgo.users.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;  
	
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	
	public User getUserById(int id) {
		return userRepository.findById(id);
	}
	
	public void deleteUser(User user) {
		userRepository.delete(user);
	}
	
	public User deleteAndGetUser(int id) {
		User user = getUserById(id);
		 
		if (null==user)
			throw new UserNotFoundException("id -" + id);
		else
			deleteUser(user);
		
		return user;
		
	}
	
	public List<User> findAllUsers() {
		return userRepository.findAll();
		
	}
	
	
	public ResponseEntity<Object> createUpdateUser(User user) {
		if(null==user.getId() || user.getId()<=0) {//for create user
			
			if(userRepository.isUserExists(user.getEmail())) {
				throw new UserAlreadyExistsException(user.getEmail()+" is already registered with us.");
			}
			
			user.setPassword(user.getPassword());
			userRepository.save(user);
		}else {//update
			userRepository.update(user);
		}
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId())
				.toUri();

		if(null==user.getId() || user.getId()<=0) {//for create user
			return ResponseEntity.created(location).build();
		}else {
			return ResponseEntity.ok(user);
		}
	}
	
}
