package com.module.Service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.module.Entity.User;
import com.module.Exceptions.DuplicateUserException;
import com.module.Exceptions.NotValidUserException;
import com.module.Exceptions.UserNotFoundException;

import com.module.RepoSitory.UserRepository;

import com.module.Exceptions.*;

@Service
public class UserService {
	@Autowired
    UserRepository userRepo;
public User addUser(User user) 
{
	Optional<User> use = userRepo.findById(user.getId());
			if(use.isPresent()) 
			{
				throw new DuplicateUserException("user already present");
			}
			else
              {
				userRepo.save(user);
			  }
return user;
}
@Transactional
public User updateUser(int id,User user) {
	Optional<User> u = userRepo.findById(id);
	if(u.isEmpty()) {
		throw new UserNotFoundException("User " +user.getId()+" not found");
	}
	User user2 = u.get();
	user2.setUsername(user.getUsername());
	user2.setEmail(user.getEmail());
	user2.setPassword(user.getPassword());
	return user;
}

public void deleteUser(Integer i) {
	Optional<User> user = userRepo.findById(i);
	if(user.isEmpty()){
		throw new UserNotFoundException("User not found with id : "+i);
	}
	User u = user.get();
	userRepo.delete(u);

}

public Optional<User> getUserById(int id) {
		Optional<User> u = userRepo.findById(id);
		if(u == null) 
		{
			throw new NotValidUserException("not found");
		}
	return u;
}

public List<User> viewUsers() {
	List<User> users = userRepo.findAll();
	if(users.isEmpty()) {
		throw new UserNotFoundException("No users found");
	}
	return users;
}
}

	


