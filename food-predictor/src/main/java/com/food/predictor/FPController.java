package com.food.predictor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.food.beans.LoginInput;
import com.food.beans.PredictorInput;
import com.food.beans.PredictorReply;

@Controller
public class FPController {

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String welcomeUser() {
		return "login";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ResponseEntity<String> loginUser(@RequestBody LoginInput input) {
		return ResponseEntity.ok("OK");
	}

	@RequestMapping(method = RequestMethod.POST, value = "/predict")
	@ResponseBody
	public PredictorReply predictFoodQuantity(@RequestBody PredictorInput input) {
		PredictorReply reply = new PredictorReply();
		reply.setCount(input.getCount());

		return reply;
	}

}
