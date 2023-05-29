package com.example.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerDemoController {
	
	Logger logger = LoggerFactory.getLogger(CircuitBreakerDemoController.class);
	
	@GetMapping("/sample-api")
//	@Retry(name = "sample-api", fallbackMethod = "getHardcodedResponse")
//	@CircuitBreaker(name = "default", fallbackMethod = "getHardcodedResponse")
//	@RateLimiter(name = "default")
	@Bulkhead(name = "default")
	public String getResponse() {
		logger.info("calling api: sample-api");
//		ResponseEntity<String> re = new RestTemplate().getForEntity("http://localhost:8080/dummy-api", String.class);
//		return re.getBody();
		return "sample-response";
	}
	
	public String getHardcodedResponse(Exception e) {
		logger.info("calling Fall-back method");
		return "fall-back response";
	}
}
