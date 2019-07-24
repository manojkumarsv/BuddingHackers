package com.food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.food.beans.LoginInput;
import com.food.beans.PredictorInput;
import com.food.beans.PredictorReply;
import com.food.beans.RegistrationInput;
import com.food.cassandra.entity.User;
import com.food.cassandra.repositories.UserRepository;

@Controller
public class FPController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String welcomeUser() {
		return "login";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/register")
	public String register() {
		return "register";
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/register")
	public ResponseEntity<String> registerUser(@RequestBody RegistrationInput input) {
		if (!input.getPassword().equals(input.getConfirmPassword())) {
			return ResponseEntity.badRequest().body("Password does not match!");
		}
		if (userRepository.existsById(input.getUserName())) {
			return ResponseEntity.badRequest().body("User with name " + input.getUserName() + " already exists");
		}

		User user = new User();
		user.setUser(input.getUserName());
		user.setPassword(input.getPassword());
		user.setConfirmPassword(input.getConfirmPassword());
		user.setFirstName(input.getFirstName());
		user.setLastName(input.getLastName());
		user.setEmailAddress(input.getEmailAddress());
		user.setOrganization(input.getOrganization());
		user.setContactNumber(input.getContactNumber());

		userRepository.save(user);

		return ResponseEntity.ok("OK");
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ResponseEntity<String> loginUser(@RequestBody LoginInput input) {
		return ResponseEntity.ok("OK");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/predict")
	public String predictForm() {
		return "predict-form";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/predict")
	public PredictorReply predictFoodQuantity(@RequestBody PredictorInput input) {
		PredictorReply reply = new PredictorReply();
		reply.setCount(input.getCount());

		return reply;
	}

}
