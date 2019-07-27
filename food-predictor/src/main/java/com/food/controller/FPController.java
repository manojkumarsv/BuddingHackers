package com.food.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.food.beans.LoginInput;
import com.food.beans.PredictorInput;
import com.food.beans.PredictorReply;
import com.food.beans.RegistrationInput;
import com.food.cassandra.entity.HistoricalData;
import com.food.cassandra.entity.User;
import com.food.cassandra.repositories.HistoricalDataRepository;
import com.food.cassandra.repositories.UserRepository;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
public class FPController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HistoricalDataRepository historicalDataRepository;

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private Configuration freemarkerConfig;

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String welcomeUser() {
		populateHistoricalData();
		
		PredictorInput input = new PredictorInput();
		input.setUsageDate("2019-01-01");
		input.setOccasion("Weekend");
		input.setAttendance(100);
		input.setFoodType("Veg");
		executeScript(input);
		
		return "login";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/register")
	public String register() {
		return "register";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/register")
	public ResponseEntity<String> registerUser(@RequestBody RegistrationInput input) {
		if (userRepository.existsById(input.getUserName())) {
			return ResponseEntity.badRequest().body("User with name " + input.getUserName() + " already exists");
		}

		User user = new User();
		user.setUser(input.getUserName());
		user.setPassword(input.getPassword());
		user.setFirstName(input.getFirstName());
		user.setLastName(input.getLastName());
		user.setEmailAddress(input.getEmailAddress());
		user.setOrganization(input.getOrganization());
		user.setContactNumber(input.getContactNumber());

		user = userRepository.save(user);
		sendRegistrationSuccessfulEmail(user);

		return ResponseEntity.ok("OK");
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ResponseEntity<String> loginUser(@RequestBody LoginInput input) {
		if (userRepository.existsById(input.getUserName())) {
			Optional<User> user = userRepository.findById(input.getUserName());
			if (user.isPresent()) {
				return ResponseEntity.ok("OK");
			} else {
				return ResponseEntity.badRequest().body("Password is invalid");
			}
		} else {
			return ResponseEntity.badRequest().body("Unknown user, " + input.getUserName());
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/predict")
	public String predictForm() {
		return "predict-form";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/predict")
	public ResponseEntity<String> predictFoodQuantity(@RequestBody PredictorInput input) {
		PredictorReply reply = new PredictorReply();
		reply.setQuantity(executeScript(input));

		return ResponseEntity.ok(reply.getQuantity());
	}

	private String executeScript(PredictorInput input) {
		Calendar c = Calendar.getInstance();

		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			c.setTime(format1.parse(input.getUsageDate()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		DateFormat format2 = new SimpleDateFormat("EEEE");
		String day = format2.format(c.getTime());

		StringBuilder builder = new StringBuilder("/opt/rh/rh-python36/root/usr/bin/python ");
		builder.append(System.getProperty("user.dir"));
		builder.append("/src/python/foodPredictorAlgorithm.py ");
		builder.append(day).append(" ");
		builder.append(input.getOccasion()).append(" ");
		builder.append(input.getFoodType()).append(" ");
		builder.append(input.getAttendance());

		Process process = null;
		BufferedReader in = null;
		String reply = null;
		try {
			ProcessBuilder pb = new ProcessBuilder(builder.toString().split(" "));
			pb.redirectErrorStream(true);
			process = pb.start();
			process.waitFor();
			in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				reply = line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return reply != null ? reply : new String();
	}

	private void sendRegistrationSuccessfulEmail(User user) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		Map<String, Object> model = new HashMap<>();
		model.put("user", user.getFirstName() + " " + user.getLastName());

		freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

		try {
			Template t = freemarkerConfig.getTemplate("welcome.ftl");
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

			helper.setFrom("admin@fp.com");
			helper.setTo(user.getEmailAddress());
			helper.setText(text, true);
			helper.setSubject("Your Registration with FPA is Successful!");

			sender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void populateHistoricalData() {
		Calendar c = Calendar.getInstance();
		BufferedReader br = null;
		try {
			br = new BufferedReader(
					new FileReader(new File(System.getProperty("user.dir") + "/src/main/resources/fwdata.csv")));
			String line = null;
			ArrayList<HistoricalData> historyList = new ArrayList<>();
			while ((line = br.readLine()) != null) {
				try {
					String[] row = line.split(",");
					DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
					try {
						c.setTime(format1.parse(row[0]));
					} catch (ParseException e1) {
						e1.printStackTrace();
					}

					HistoricalData data = new HistoricalData();
					data.setDate(c.getTimeInMillis());
					data.setDay(row[1]);
					data.setOccassion(row[2]);
					data.setAttendance(Integer.parseInt(row[3]));
					data.setSurplus(Integer.parseInt(row[4]));
					data.setAttended(Integer.parseInt(row[5]));

					historyList.add(data);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			historicalDataRepository.saveAll(historyList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
