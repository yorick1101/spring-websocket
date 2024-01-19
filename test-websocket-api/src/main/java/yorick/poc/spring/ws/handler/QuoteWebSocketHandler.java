package yorick.poc.spring.ws.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import yorick.poc.spring.ws.handler.msg.Quotation;

@Slf4j
public class QuoteWebSocketHandler extends SessionRegisteredTextWebSocketHandler {

   @Autowired
   private ObjectMapper objectMapper;

	public void publish(Quotation marketData) throws JsonProcessingException {
		TextMessage msg = new TextMessage(objectMapper.writeValueAsString(marketData));
		this.getSessions().forEach(s-> {
			try {
				s.sendMessage(msg);
			} catch (IOException e1) {
				log.error("failed to publish market data", e1);
			}
		});
		
		
	}

}
