package com.mydev.springboot.service;

import java.util.UUID;

import com.mydev.springboot.model.BlackListUser;

public interface BlackListUserService {
	
	BlackListUser getUserById(UUID userId);
	
	void createUser(BlackListUser User);
	
	void updateUser(BlackListUser User);
	
	void deleteUserById(UUID userId);
}
