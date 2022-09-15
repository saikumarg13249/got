package com.example.got.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.got.entities.Employee;
import com.example.got.entities.EmployeePost;
import com.example.got.entities.Posts;
import com.example.got.entities.Response;
import com.example.got.entities.User;

@RestController
public class UserGot {
	
	public static final String GOT_USER_URL = "https://s-alman.github.io/Website/src/User.json";
	public static final String GOT_CHARACTERS_BASE_URL = "https://anapioficeandfire.com/api/characters/34";
	public static final String USERS = "https://jsonplaceholder.typicode.com/users";
	public static final String POSTS = "https://jsonplaceholder.typicode.com/posts";
	
	@RequestMapping(value = "/usergot", method = RequestMethod.GET)
	public String processUserInfo() {
		RestTemplate template = new RestTemplate();
		 ResponseEntity<Response> users =  template.getForEntity(GOT_USER_URL, Response.class);
		List<User> usersList =  users.getBody().getUser();
		String result = "";
		//String url = GOT_CHARACTERS_BASE_URL+34;
		result = template.getForObject(GOT_CHARACTERS_BASE_URL, String.class).toString();
		//result = template.getForEntity(url, responseType)
		 for(User user : usersList) {
			int id =  user.getId();
			//String url = GOT_CHARACTERS_BASE_URL+id;
			//result = template.getForEntity(url, String.class).toString();
			//result = template.getForObject(url, String.class).toString();
			/*
			 * if(!result.isEmpty()) { break; }
			 */
		 }
		 return result;
	}
	
	@RequestMapping(value="/test", method= RequestMethod.GET)
	public List<EmployeePost> processUsers() {
		RestTemplate template = new RestTemplate();
		Employee[] response = template.getForEntity(USERS, Employee[].class).getBody();
		Posts[] response1 = template.getForEntity(POSTS, Posts[].class).getBody();
		List<EmployeePost> empList = new ArrayList<EmployeePost>();
		for(Employee employee: response) {
			for(Posts post : response1) {
				if(employee.getId() == post.getId()) {
					EmployeePost emp= new EmployeePost();
					emp.setBody(post.getBody());
					emp.setCompanyName(employee.getCompany().getName());
					emp.setTitle(post.getTitle());
					emp.setLat(employee.getAddress().getGeo().getLat());
					emp.setLng(employee.getAddress().getGeo().getLng());
					emp.setId(employee.getId());
					empList.add(emp);
				}
			}
		}
		return empList;
	}

}
