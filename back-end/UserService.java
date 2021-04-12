package com.project.vm.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.vm.entities.*;
import com.project.vm.exceptions.AlreadyExistsException;
import com.project.vm.exceptions.NotAUserException;
import com.project.vm.exceptions.NotFoundException;
import com.project.vm.repositories.ICustomerRepository;
import com.project.vm.repositories.IRoleRepository;
import com.project.vm.repositories.IUserRepository;
import com.project.vm.serviceInterfaces.IUserService;

@Service
public class UserService implements IUserService{

	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IRoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	ICustomerRepository customerRepository;

	@Override
	@Transactional
	public User updateUser(int id,User user) {
		Optional<User> u = userRepository.findByUsername(user.getUsername());
		if(u.isEmpty()) {
			throw new NotFoundException("User " +user.getId()+" not found");
		}
		User user2 = u.get();
		user2.setUsername(user.getUsername());
		user2.setEmail(user.getEmail());
		user2.setPassword(encoder.encode(user.getPassword()));
		return user;
	}

	@Override
	public void deleteUser(Long i) {
		Optional<User> user = userRepository.findById(i);
		if(user.isEmpty()){
			throw new NotFoundException("User not found with id : "+i);
		}
		User u = user.get();
		userRepository.delete(u);
		Customer customer = new Customer(u.getUsername(),u.getEmail());
		customerRepository.delete(customer);

	}

	@Override
	public User validateUser(User user) {
			User u = userRepository.findUserByUsernameAndEmail(user.getUsername(), user.getEmail());
			if(u == null) 
			{
				throw new NotAUserException("User name : "+user.getUsername()+" or the User email : "+user.getEmail()+" is invalid");
			}
		return user;
	}

	public User addUser(User user) {
//		Optional<User> use = userRepository.findById(user.getId());
//		if (use.isPresent()) {
//			throw new AlreadyExistsException("user already present");
		User u = userRepository.findUserByUsernameAndEmail(user.getUsername(), user.getEmail());
		if(u != null) 
		{
			throw new AlreadyExistsException("User name : "+user.getUsername()+" or the User email : "+user.getEmail()+" is invalid");
		} else {
			User newUser = new User(user.getUsername(), 
					user.getEmail(),
					 encoder.encode(user.getPassword()));
			Set<Role> roles = new HashSet<>();
			Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
			newUser.setRoles(roles);
			userRepository.save(newUser);
			
		}
		return user;
	}
	
	public Optional<User> getUserById(int id) {
		Optional<User> u = userRepository.findById((long) id);
		if (u == null) {
			throw new NotAUserException("not found");
		}
		return u;
	}

	public List<User> viewUsers() {
		List<User> users = userRepository.findAll();
		if (users.isEmpty()) {
			throw new NotFoundException("No users found");
		}
		return users;
	}

}