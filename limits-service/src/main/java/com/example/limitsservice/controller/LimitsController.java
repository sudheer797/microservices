package com.example.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.limitsservice.config.LimitsConfig;
import com.example.limitsservice.model.Limits;

@RestController
public class LimitsController {
	@Autowired
	private LimitsConfig limitsConfig;

	@GetMapping("/limits")
	public Limits retrieveLimits() {
//		return new Limits(1, 1000);
		return new Limits(limitsConfig.getMinimum(), limitsConfig.getMaximum());
	}
}
