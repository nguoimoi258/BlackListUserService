package com.mydev.springboot.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlackListUser {
	
	@JsonProperty("FullName")
	private String fullName;
	
	@JsonProperty("UserId")
	private UUID userId;
	
	@JsonProperty("PhoneNumber")
	private String phoneNumber;
	
	@JsonProperty("Salary")
	private double salary;
	
	@JsonProperty("FbUrl")
	private String fbUrl;
	
	@JsonProperty("ImageName")
	private String imageName;
	
	@JsonProperty("Reason")
	private String reason;
	
	public BlackListUser(){
	}

	public BlackListUser(String fullName, UUID userId, String phoneNumber, double salary, String fbUrl,
			String imageName, String reason) {
		super();
		this.fullName = fullName;
		this.userId = userId;
		this.phoneNumber = phoneNumber;
		this.salary = salary;
		this.fbUrl = fbUrl;
		this.imageName = imageName;
		this.reason = reason;
	}	
}