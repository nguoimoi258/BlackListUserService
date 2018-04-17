package com.mydev.springboot.repo;

import java.util.UUID;

import com.mydev.springboot.model.BlackListUser;

public interface BlackListUserRepository {
	BlackListUser findById(UUID userId);
	void save(BlackListUser blackListUser);
	void delete(UUID userId);	
}
