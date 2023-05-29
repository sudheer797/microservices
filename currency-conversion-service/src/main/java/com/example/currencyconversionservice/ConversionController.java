package com.example.currencyconversionservice;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConversionController {
	@Autowired
	private CurrencyExchangeProxy exchangeProxy;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable double quantity) {
		HashMap<String, String> pathVariables = new HashMap<>();
		pathVariables.put("from", from);
		pathVariables.put("to", to);
		
		ResponseEntity<CurrencyConversion> re = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, pathVariables);
		CurrencyConversion conversion = re.getBody();
		return new CurrencyConversion(conversion.getId(), from, to, conversion.getConversionMultiple(), quantity, quantity*conversion.getConversionMultiple(), conversion.getEnvironment());
	}

	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConsumeByFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable double quantity) {
		CurrencyConversion conversion = exchangeProxy.getCurrencyExchange(from, to);
		return new CurrencyConversion(conversion.getId(), from, to, conversion.getConversionMultiple(), quantity,
				quantity * conversion.getConversionMultiple(), conversion.getEnvironment());
	}
}
