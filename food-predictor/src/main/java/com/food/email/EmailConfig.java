package com.food.email;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource(value = { "classpath:email.properties" })
public class EmailConfig {

	@Autowired
	private Environment environment;

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(environment.getProperty("smtp.host"));
		mailSender.setPort(Integer.parseInt(environment.getProperty("smtp.port")));

		mailSender.setUsername(environment.getProperty("sender.username"));
		mailSender.setPassword(environment.getProperty("sender.password"));

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		// props.put("mail.smtp.starttls.enable", "true");
		// props.put("mail.smtp.starttls.required", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", environment.getProperty("smtp.host"));
		props.put("mail.debug", "true");
		props.put("mail.smtp.socketFactory.fallback", "true");

		return mailSender;
	}

}