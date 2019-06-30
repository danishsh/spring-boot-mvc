package com.revolut.hello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public RevolutUser getUser(String name) {
		return userRepository.findById(name).orElse(null);
	}

	public void updateUser(RevolutUser newUser) {
		userRepository.save(newUser);
	}

	public List<RevolutUser> getAllUsers() {
		List<RevolutUser> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}
	
}
