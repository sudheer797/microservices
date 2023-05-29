package com.example.currencyexchangeservice;

import org.bouncycastle.crypto.RuntimeCryptoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
	@Autowired
	private Environment environment;
	@Autowired
	private CurrencyExchangeRepository exchangeRepository;
	
	@GetMapping("currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange getExchangeValue(@PathVariable String from, @PathVariable String to) {
		logger.info("getExchangeValue() called with {} to {}", from, to);
		CurrencyExchange exchange = exchangeRepository.findByFromAndTo(from, to).orElse(null);
		if(exchange == null)
			throw new RuntimeCryptoException("Currency not found");
		String port = environment.getProperty("local.server.port");
		exchange.setEnvironment(port);
		return exchange;
	}
}
