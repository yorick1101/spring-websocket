package yorick.poc.spring.ws.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import yorick.poc.spring.ws.handler.msg.Quotation;

@Service
@Slf4j
public class QuotationService {
	
	@Autowired
	private SimpMessagingTemplate template;
	
	private volatile boolean running = true;
	
	public void broadcastQuote() {
		running = true;
		new Thread() {
			@Override
			public void run() {
				while(running) {
                    try {
                        Thread.sleep(1000);
                        template.convertAndSend("/topic/quote", marketDataProvider().get());
                    } catch (InterruptedException e) {
                    	running = false;
                        log.error("{}",e);
                    }
			}
		}}.start();
	}
	
	public void stop() {
		running = false;
	}
	
	private Supplier<Quotation> marketDataProvider(){
		
		String symbol = "USDJPY";
		BigDecimal buy = BigDecimal.valueOf(110.0); 
		BigDecimal sell = BigDecimal.valueOf(115.0);
		BigDecimal last = BigDecimal.valueOf(112.0);
		BigDecimal open = BigDecimal.valueOf(111.0);
		BigDecimal high = BigDecimal.valueOf(123.0);
		BigDecimal low = BigDecimal.valueOf(103.0);
		BigDecimal close = null;
		
		Random random = new Random();
		
		return new Supplier<Quotation>() {

			@Override
			public Quotation get() {
			float floating = random.nextInt(1, 10)/10f;
			int direction = random.nextInt(0, 100)%2 == 0 ? 1 : -1;
			BigDecimal buy2 = buy.add(BigDecimal.valueOf(floating*direction));
			BigDecimal sell2 = sell.add(BigDecimal.valueOf(floating*direction));
				return new Quotation(symbol, buy2, sell2, last, open, high, low, close, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
			}
		};
	}

}
