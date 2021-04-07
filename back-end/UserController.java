package com.module.Controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.module.Entity.*;
import com.module.Exceptions.DuplicateUserException;
import com.module.Exceptions.UserNotFoundException;

import com.module.RepoSitory.UserRepository;
import com.module.Service.UserService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
//@Api(value = "User", tags = { "UserAPI" })
@RequestMapping(path = "/api/v1")
public class UserController {

	//static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	@PostMapping("/users")
	public User addUser(@RequestBody User user) 
	{
		userService.addUser(user);
		return user;}

	@GetMapping("/users/{id}")
	public Optional<User>  getUserById( @PathVariable("id") Integer id) {
		Optional<User> user = userService.getUserById(id);
		return user;

	}
	@PutMapping("/users/{id}") 
	//@ApiOperation(value = "Update User email by the response body", notes = "Provide username and new email", response = User.class)
	//@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public User updateUser(@PathVariable("id") Integer id,@RequestBody User u) 
	{
		User user = userService.updateUser(id,u); 
		return u;
	}
	/**
	 * This method is for getting a list of all customers
	 * 
	 * @return List<User>
	 * @throws NotFoundException
	 * 
	 */
	@GetMapping("/users")
	//@PreAuthorize("hasRole('ADMIN')")
	//@ApiOperation(value = "View all users", response = Customer.class)
	public List<User> viewUsers() {
		List<User> user = userService.viewUsers();
		return user;
	}
	/**
	 * This method is for deleting a user by id
	 * @return 
	 * 
	 * @return String
	 * @throws NotFoundException
	 * 
	 */
	//@ApiOperation(value = "Deleting a user by id", notes = "Provide id", response = User.class)
	@DeleteMapping("/users/{id}")
	//@PreAuthorize("hasRole('ADMIN')")
	public void deleteUser( @PathVariable("id") Integer id) 
	{
		userService.deleteUser(id); 
		
	}

}


