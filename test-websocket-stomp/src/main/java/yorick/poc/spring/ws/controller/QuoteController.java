package yorick.poc.spring.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yorick.poc.spring.ws.service.QuotationService;

@RestController
@RequestMapping("/mock")
public class QuoteController {
	@Autowired
	private QuotationService service;
	
	@GetMapping("/start")
	public void start() {
		service.broadcastQuote();
	}
	
	@GetMapping("/stop")
	public void stop() {
		service.stop();
	}

}
