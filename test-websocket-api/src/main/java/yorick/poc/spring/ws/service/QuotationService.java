package yorick.poc.spring.ws.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import yorick.poc.spring.ws.handler.QuoteWebSocketHandler;
import yorick.poc.spring.ws.handler.msg.Quotation;

@Service
@Slf4j
public class QuotationService {

	@Autowired QuoteWebSocketHandler quotehandler;
	
	private volatile boolean running = false;
	
	private final Supplier<Quotation> mockQuoteSupplier = marketDataProvider();
	
    private volatile Thread thread;
	
	public void mockQuotation() {
		log.info("start mocking quotation");
		if (thread != null && thread.isAlive()) {
			log.info("quotation is already running");
			return;
		}
		thread = new Thread(() -> {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					log.info("publishing market data {}", Thread.currentThread().isInterrupted());
					quotehandler.publish(mockQuoteSupplier.get());
					Thread.sleep(500);
				} catch (InterruptedException | JsonProcessingException e) {
					log.error("failed to publish market data, {} {}",Thread.currentThread().getName(), Thread.currentThread().isInterrupted(),  e);
					return;
				}
			}

		});
		thread.setDaemon(true);
		thread.start();
		log.info("Thread is started:{}", thread.getName());
	}
	
	public void stopQuotation() {
		log.info("stop mocking quotation thread is alive:{}", thread!=null);
		if(thread!=null && thread.isAlive()) {
			thread.interrupt();
		}
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
