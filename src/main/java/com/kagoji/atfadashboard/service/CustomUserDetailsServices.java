package com.kagoji.atfadashboard.service;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kagoji.atfadashboard.entity.User;
import com.kagoji.atfadashboard.repository.UserRepository;

@Service
public class CustomUserDetailsServices implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	//@Autowired
	//private CustomUserDetails customUserDetails;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(userName);
		if(user == null) {
			throw new UsernameNotFoundException("Username or password not found");
		}
		return new CustomUserDetails(user.getUserName(), user.getPassword(), authorities());
	}
	
	public Collection<? extends GrantedAuthority> authorities(){
		return Arrays.asList(new SimpleGrantedAuthority("ADMIN"));
		
	}

}
