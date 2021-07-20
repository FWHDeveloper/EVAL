package com.fatma.eval.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SuppressWarnings("deprecation")
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {
	@Bean
    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("This is the test email template for your email:\n%s\n");
        return message;
    }
 
}
