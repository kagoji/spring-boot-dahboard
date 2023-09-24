package com.kagoji.atfadashboard.service;

import com.kagoji.atfadashboard.entity.User;
import com.kagoji.atfadashboard.model.UserModel;

public interface UserService {

	User registerUser(UserModel userModel);
	User findByUserName(String userName);
	void saveUser(User user);

}
