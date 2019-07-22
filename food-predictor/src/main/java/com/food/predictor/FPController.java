package com.food.predictor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.food.beans.PredictorInput;
import com.food.beans.PredictorReply;

@Controller
public class FPController {

	@RequestMapping(method = RequestMethod.POST, value = "/predict")
	@ResponseBody
	public PredictorReply welcomeUser(@RequestBody PredictorInput input) {
		PredictorReply reply = new PredictorReply();
		reply.setCount(input.getCount());

		return reply;
	}

}
