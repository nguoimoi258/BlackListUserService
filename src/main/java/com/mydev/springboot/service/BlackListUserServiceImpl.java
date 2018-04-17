package com.mydev.springboot.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mydev.springboot.model.BlackListUser;
import com.mydev.springboot.repo.BlackListUserRepositoryImpl;

@Service
public class BlackListUserServiceImpl implements BlackListUserService{
	
	@Autowired
	private BlackListUserRepositoryImpl userRepositoryImpl;
	
	@Override
	public BlackListUser getUserById(UUID userId) {
		return userRepositoryImpl.findById(userId);
	}
	
	@Override
	public void createUser(BlackListUser User) {
		userRepositoryImpl.save(User);
	}

	@Override
	public void updateUser(BlackListUser User) {
		userRepositoryImpl.save(User);
	}

	@Override
	public void deleteUserById(UUID userId) {
		BlackListUser user = getUserById(userId);
		
		if(user!=null) {
			userRepositoryImpl.delete(userId);
		}		
	}
}
