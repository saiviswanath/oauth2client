package com.example.demooath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemooathApplication implements CommandLineRunner {
	private final Logger logger = LoggerFactory.getLogger(DemooathApplication.class);
	
	 @Value("#{ @environment['myapp.baseUrl'] }")
	 private String serverBaseUrl;
	
	@Autowired
	@Qualifier("myAppRestTemplate")
	private RestTemplate myAppRestTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DemooathApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        logger.info("RESULT: {}", myAppRestTemplate.getForObject(serverBaseUrl, String.class));

	}

}
