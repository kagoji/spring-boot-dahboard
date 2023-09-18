package com.kagoji.atfadashboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kagoji.atfadashboard.entity.User;
import com.kagoji.atfadashboard.model.UserModel;
import com.kagoji.atfadashboard.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User registerUser(UserModel userModel) {
		
		User user = new User();
		user.setUserName(userModel.getUserName());
		user.setPassword(passwordEncoder.encode(userModel.getPassword()));
		user.setRole("ADMIN");
		
		userRepository.save(user);
		return null;
		
	}
}
