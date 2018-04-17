package com.mydev.springboot.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mydev.springboot.model.BlackListUser;
import com.mydev.springboot.service.BlackListUserService;
import com.mydev.springboot.utils.CustomErrorType;

@RestController
@RequestMapping("/api")
public class BlackListUserController {

	public static final Logger logger = LoggerFactory.getLogger(BlackListUserController.class);
	
    @Autowired
    BlackListUserService blackListUserService; //Service which will do all data retrieval/manipulation work
 
    // -------------------Retrieve Single User------------------------------------------
 
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") UUID userId) {
        logger.info("Fetching User with id {}", userId);
    	BlackListUser user = blackListUserService.getUserById(userId);   
    	if(user == null) {
    		logger.error("User with id {} not found.",userId);
    		return new ResponseEntity(new CustomErrorType("User with id " + userId
    									+ " not found"), HttpStatus.NOT_FOUND);

    	}
        return new ResponseEntity<BlackListUser>(user, HttpStatus.OK);
    }
 
    // -------------------Create a User-------------------------------------------
 
    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody BlackListUser User) {
    	    	    
    	logger.info("Creating User: {}", User);
    	
    	if (User.getUserId().toString() == "00000000-0000-0000-0000-000000000000") {
    		User.setUserId(UUID.randomUUID());	
    	}
    	
    	// create new user
    	blackListUserService.createUser(User);

    	// Return result
    	return new ResponseEntity<BlackListUser>(User, HttpStatus.OK);        
    }
 
    // ------------------- Update a User ------------------------------------------------
 
    @RequestMapping(value = "/user", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody BlackListUser user) { 
    	
    	UUID userId = user.getUserId();
    	
    	logger.info("Updating User with id {} ", userId);
        
        BlackListUser currentUser = blackListUserService.getUserById(userId);
      
        if(currentUser==null) {
        	logger.error("Unable to update. User with id {} not found.", userId);
        	return new ResponseEntity(new CustomErrorType("Unable to update. User  with id "+ userId + " not found"),
        			HttpStatus.NOT_FOUND);
        }
        
        currentUser.setFullName(user.getFullName());
        currentUser.setPhoneNumber(user.getPhoneNumber());
        currentUser.setFbUrl(user.getFbUrl());
        currentUser.setSalary(user.getSalary());
        currentUser.setImageName(user.getImageName());
        currentUser.setReason(user.getReason());
        blackListUserService.updateUser(currentUser);
        
        return new ResponseEntity<BlackListUser>(currentUser, HttpStatus.OK);
    }
 
    // ------------------- Delete a User-----------------------------------------
 
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") UUID userId) {
    		
    		logger.info("Fetching & deleting User with id {}", userId);
    		
    		BlackListUser user = blackListUserService.getUserById(userId);
    		
    		if(user==null) {
    			
    			logger.error("Unable to delete. User with id {} not found.", userId);
    			return new ResponseEntity(new CustomErrorType("Unable to delte. User with id " + userId + " not found"),
    					HttpStatus.NOT_FOUND);
    		}
    		
    		blackListUserService.deleteUserById(userId);
    		return new ResponseEntity<BlackListUser>(HttpStatus.NO_CONTENT);
    }
}
