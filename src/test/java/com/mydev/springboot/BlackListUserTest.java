package com.mydev.springboot;

import java.util.UUID;

import org.springframework.web.client.RestTemplate;

import com.mydev.springboot.model.BlackListUser;

public class BlackListUserTest {

	public static final String REST_SERVICE_URI = "http://localhost:8080/api/user";
	
	 
	 // GET
	 private static void getUser() {
		 System.out.println("------Testing getUser Api------");
		 RestTemplate restTemplate = new RestTemplate();
		 BlackListUser result = restTemplate.getForObject(REST_SERVICE_URI+"/0947fcbd-e4cf-4632-a097-02e5ec4aab91", BlackListUser.class);
		 System.out.println(result);
	 }
	 
	 // Create
	 private static void createUser() {
		 System.out.println("------Testing createUser Api------");
		
		 UUID userId =  UUID.fromString("4647fcbd-e4cf-4632-a097-02e5ec4aab91");
		 BlackListUser user = new BlackListUser("Tran Thi Thao", userId, "09823342348", 10555000, "fbUrl", "imageName", "reason") ;
		
		 RestTemplate restTemplate = new RestTemplate();
		 BlackListUser result = restTemplate.postForObject(REST_SERVICE_URI, user, BlackListUser.class);	
		 System.out.println(result);
	 }
	 //Update
	 private static void updateUser() {
		
		UUID userId =  UUID.fromString("000001ad-23ec-d3a9-5b87-991c80000000");
		BlackListUser user = new BlackListUser("Ha thi Thai 22222", userId, "002313", 20, null,null, null) ;
			
		System.out.println("-------Testing update User API----------");	
		RestTemplate restTemplate = new RestTemplate();
		
		BlackListUser result = restTemplate.patchForObject(REST_SERVICE_URI, user, BlackListUser.class);
		
		System.out.println(result);
	 }
	 
	 //Delete
	 private static void deleteUser() {
		System.out.println("-------Testing update User API----------");	
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(REST_SERVICE_URI+"/user/000001ad-23ec-d3a9-5b87-991c80000000");
	 }
	 
	 public static void main(String[] args) {
		getUser();
		createUser();
		updateUser();
		deleteUser();
	}
}
