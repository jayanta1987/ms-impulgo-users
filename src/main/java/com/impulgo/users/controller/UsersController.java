package com.impulgo.users.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.impulgo.users.entity.User;
import com.impulgo.users.exception.UserNotFoundException;
import com.impulgo.users.service.UserService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class UsersController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users/email/{email}")
	public User getUserByEmail(@PathVariable String email){
		
		return userService.getUserByEmail(email);
	}
	
	@GetMapping("/users/id/{id}")
	public Optional<User> getUser(@PathVariable int id) {
		
		User user = userService.getUserById(id);
		if (null==user)
			throw new UserNotFoundException("id-" + id);
		
		return Optional.of(user);

	}
	
		
	@DeleteMapping("/users/{id}")
	public User deleteUser(@PathVariable int id) {
		
		return userService.deleteAndGetUser(id);

	}
	
	@GetMapping("/users/list")
	public List<User> retrieveAllUser() {
		
		return userService.findAllUsers();
	}

	@PostMapping("/users/save")
	public ResponseEntity<Object> saveUser(@RequestBody User user) {
		
		logger.info("Saving user {}",user);
		return userService.createUpdateUser(user);
		
	}

	

}
